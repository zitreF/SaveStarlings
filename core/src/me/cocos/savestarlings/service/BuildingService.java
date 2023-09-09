package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.Ray;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.entity.building.BuildingType;
import me.cocos.savestarlings.entity.building.base.StarBase;
import me.cocos.savestarlings.hud.Hud;
import me.cocos.savestarlings.hud.node.table.BuildingsTable;
import me.cocos.savestarlings.util.SoundUtil;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;

import java.util.concurrent.CompletableFuture;

public class BuildingService {

    private static final Plane PLANE = new Plane(Vector3.Y, 0);
    private static final BlendingAttribute OPACITY_ATTRIBUTE = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 0.5f);
    private static final ColorAttribute RED_COLOR_ATTRIBUTE = PBRColorAttribute.createBaseColorFactor(new Color(1f, 51f / 255f, 51f / 255f, 0.75f));
    private static final ColorAttribute GREEN_COLOR_ATTRIBUTE = PBRColorAttribute.createBaseColorFactor(new Color(84f / 255f, 189f / 255f, 46f / 255f, 0.75f));
    private final EntityService entityService;
    private final EnvironmentService environmentService;
    private final Hud hud;
    private final Vector2 mouseCoords;
    private final Vector3 intersection;
    private BuildingType currentBuilding;
    private boolean isPlaceable;

    public BuildingService(EntityService entityService, EnvironmentService environmentService, Hud hud) {
        this.entityService = entityService;
        this.environmentService = environmentService;
        this.hud = hud;
        this.mouseCoords = new Vector2();
        this.intersection = new Vector3();
        this.currentBuilding = null;
        this.hud.setBuildingService(this);
        this.isPlaceable = true;
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
    }

    private boolean isMouseOverHudElement() {
        return this.hud.hit(this.mouseCoords.x, this.mouseCoords.y, false) != null;
    }

    private boolean isBuildingInScene() {
        return this.environmentService.getSceneService().getRenderableProviders().contains(this.currentBuilding.getScene(), false);
    }

    private void addBuildingToScene() {
        for (Material material : currentBuilding.getScene().modelInstance.materials) {
            material.set(GREEN_COLOR_ATTRIBUTE, OPACITY_ATTRIBUTE);
        }
        this.environmentService.addScene(this.currentBuilding.getScene());
    }

    private void handleBuildingPlacement() {
        if (currentBuilding == null) return;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            this.environmentService.getSceneService().removeScene(this.currentBuilding.getScene());
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

                if (this.isBuildingCollision(x, z)) {
                    if (isPlaceable) {
                        for (Material material : currentBuilding.getScene().modelInstance.materials) {
                            material.set(RED_COLOR_ATTRIBUTE, OPACITY_ATTRIBUTE);
                        }
                        this.isPlaceable = false;
                    }
                } else {
                    if (isPressed) {
                        Building building = this.currentBuilding.getBuildingResult().result(intersection);
                        SoundUtil.playSound("building/build.mp3");
                        this.addBuilding(building);
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

    private final Vector3 translation = new Vector3();
    private final Vector2 collisionPosition = new Vector2();
    private final Vector2 otherPosition = new Vector2();

    private boolean isBuildingCollision(float x, float z) {
        for (Building existingBuilding : entityService.getBuildings()) {
            Vector3 temp = existingBuilding.getScene().modelInstance.transform.getTranslation(translation);
            otherPosition.set(temp.x, temp.z);
            BuildingType building = this.currentBuilding;
            this.collisionPosition.set(otherPosition.x, otherPosition.y);
            if (collisionPosition.dst2(x, z) - (building.getSize() * building.getSize()) <= existingBuilding.getDimension() * existingBuilding.getDimension()) {
                return true;
            }
        }
        return false;
    }


    private void handleHudClick() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (this.currentBuilding != null) {
                this.environmentService.getSceneService().removeScene(this.currentBuilding.getScene());
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
