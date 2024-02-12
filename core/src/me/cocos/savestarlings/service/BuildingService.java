package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.Ray;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.entity.building.BuildingType;
import me.cocos.savestarlings.entity.environment.Environment;
import me.cocos.savestarlings.entity.livingentitiy.unit.Colossus;
import me.cocos.savestarlings.hud.Hud;
import me.cocos.savestarlings.util.GridUtil;
import me.cocos.savestarlings.util.SoundUtil;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;

import java.util.concurrent.CompletableFuture;

public class BuildingService {

    private static final Plane PLANE = new Plane(Vector3.Y, 0f);
    public static final BlendingAttribute OPACITY_ATTRIBUTE = new BlendingAttribute(GL32.GL_SRC_ALPHA, GL32.GL_ONE_MINUS_SRC_ALPHA, 0.5f);
    public static final ColorAttribute RED_COLOR_ATTRIBUTE = PBRColorAttribute.createBaseColorFactor(new Color(1f, 51f / 255f, 51f / 255f, 0.75f));
    public static final ColorAttribute GREEN_COLOR_ATTRIBUTE = PBRColorAttribute.createBaseColorFactor(new Color(84f / 255f, 189f / 255f, 46f / 255f, 0.75f));
    private final EntityService entityService;
    private final EnvironmentService environmentService;
    private final Hud hud;
    private final Vector2 mouseCoords;
    private final Vector3 intersection;
    private final Scene rangeCapsule;
    private BuildingType currentBuilding;
    private Scene grid;
    private boolean isPlaceable;
    private boolean mouseOverHud;

    public BuildingService(EntityService entityService, EnvironmentService environmentService, Hud hud) {
        this.entityService = entityService;
        this.environmentService = environmentService;
        this.hud = hud;
        this.mouseCoords = new Vector2();
        this.intersection = new Vector3();
        this.currentBuilding = null;
        this.hud.setBuildingService(this);
        this.isPlaceable = false;
        this.mouseOverHud = false;
        ModelBuilder modelBuilder = new ModelBuilder();

        Model blueCapsuleModel = modelBuilder.createBox(
                1f, 0.5f, 1f,
                new Material(),
                VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position);


        this.rangeCapsule = new Scene(blueCapsuleModel);
        rangeCapsule.modelInstance.transform.setTranslation(0f, 1f, 0f);

        GameService.getInstance().getEnvironmentService().getSceneService().addSceneWithoutShadows(rangeCapsule, false);
    }

    public void update() {
        this.updateMouseCoords();

        if (this.isMouseOverHudElement()) {
            this.handleHudClick();
            return;
        }

        if (this.currentBuilding != null && !this.isBuildingInScene()) {
            this.addBuildingToScene();
        }

        this.handleBuildingPlacement();
    }

    private void updateMouseCoords() {
        this.mouseCoords.set(Gdx.input.getX(), Gdx.input.getY());
        this.hud.getViewport().unproject(this.mouseCoords);
        this.mouseOverHud = this.hud.hit(this.mouseCoords.x, this.mouseCoords.y, true) != null;
    }

    public boolean isMouseOverHudElement() {
        return this.mouseOverHud;
    }

    private boolean isBuildingInScene() {
        return this.environmentService.getSceneService().getRenderableProviders().contains(this.currentBuilding.getScene(), false);
    }

    private void addBuildingToScene() {
        for (Material material : currentBuilding.getScene().modelInstance.materials) {
            material.set(GREEN_COLOR_ATTRIBUTE, OPACITY_ATTRIBUTE);
        }
        this.environmentService.addScene(this.currentBuilding.getScene());
        this.grid = GridUtil.createGrid(-0f, 0f, new Vector2(0, 0));
        this.environmentService.addSceneWithoutShadows(grid);
    }

    private void handleBuildingPlacement() {
        if (currentBuilding == null) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                int mouseX = Gdx.input.getX();
                int mouseY = Gdx.input.getY();
                Ray ray = environmentService.getSceneService().camera.getPickRay(mouseX, mouseY);
                if (Intersector.intersectRayPlane(ray, PLANE, intersection)) {
                    Colossus colossus = new Colossus(intersection);
                    entityService.addEntity(colossus);
                }
            }
            return;
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            this.environmentService.removeScene(this.currentBuilding.getScene());
            this.environmentService.removeSceneWithoutShadows(grid);
            this.currentBuilding = null;
            return;
        }

        boolean isPressed = Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);

        CompletableFuture.runAsync(() -> {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            Ray ray = environmentService.getSceneService().camera.getPickRay(mouseX, mouseY);
            if (Intersector.intersectRayPlane(ray, PLANE, intersection)) {
                float x = currentBuilding.getPositionFunction().apply(intersection.x);
                float z = currentBuilding.getPositionFunction().apply(intersection.z);

                currentBuilding.getScene().modelInstance.transform.setTranslation(x, intersection.y, z);
                grid.modelInstance.transform.setTranslation(new Vector3(x, 0, z));

                if (this.isBuildingCollision(x, z)) {
                    for (Material material : currentBuilding.getScene().modelInstance.materials) {
                        material.set(RED_COLOR_ATTRIBUTE, OPACITY_ATTRIBUTE);
                    }
                    this.isPlaceable = false;
                } else {
                    if (isPressed) {
                        Gdx.app.postRunnable(() -> {
                            Building building = this.currentBuilding.getBuildingResult().result(intersection);
                            this.addBuilding(building);
                            this.isPlaceable = true;
                        });
                        SoundUtil.playSound("building/build.mp3");
                        return;
                    }
                    if (!isPlaceable) {
                        for (Material material : currentBuilding.getScene().modelInstance.materials) {
                            material.set(GREEN_COLOR_ATTRIBUTE, OPACITY_ATTRIBUTE);
                        }
                        this.isPlaceable = true;
                    }
                }
            }
        });
    }

    private final Vector2 otherPosition = new Vector2();
    private final Rectangle collision = new Rectangle();

    private boolean isBuildingCollision(float x, float z) {
        BuildingType building = this.currentBuilding;
        collision.set(x, z, building.getSize(), building.getSize());
        this.rangeCapsule.modelInstance.transform.setToTranslation(collision.x, 1f, collision.y);
        this.rangeCapsule.modelInstance.transform.scale(collision.width, 1f, collision.height);
        for (Building existingBuilding : entityService.getBuildings()) {
            Vector3 temp = existingBuilding.getPosition();
            otherPosition.set(temp.x, temp.z);
            if (existingBuilding.getBounding().overlaps(collision)) {
                return true;
            }
        }
        for (Environment environment : entityService.getEnvironments()) {
            Vector3 temp = environment.getPosition();
            otherPosition.set(temp.x, temp.z);
            if (environment.getBounding().overlaps(collision)) {
                return true;
            }
        }
        return false;
    }


    private void handleHudClick() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (this.currentBuilding != null) {
                this.environmentService.removeScene(this.currentBuilding.getScene());
                this.environmentService.removeSceneWithoutShadows(grid);
                this.currentBuilding = null;
            }
        }
    }

    private void addBuilding(Building building) {
        this.entityService.addBuilding(building);
    }

    public void setCurrentBuilding(BuildingType currentBuilding) {
        this.currentBuilding = currentBuilding;
    }
}
