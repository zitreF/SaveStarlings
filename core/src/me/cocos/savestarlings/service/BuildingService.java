package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import me.cocos.savestarlings.entity.building.Building;
import me.cocos.savestarlings.entity.building.BuildingType;
import me.cocos.savestarlings.hud.Hud;
import me.cocos.savestarlings.util.SoundUtil;

import java.util.concurrent.CompletableFuture;

public class BuildingService {

    private static final BlendingAttribute OPACITY_ATTRIBUTE = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 0.5f);
    private final EntityService entityService;
    private final EnvironmentService environmentService;
    private final Hud hud;
    private final Vector2 mouseCoords;
    private final Vector3 intersection;
    private BuildingType currentBuilding;

    public BuildingService(EntityService entityService, EnvironmentService environmentService, Hud hud) {
        this.entityService = entityService;
        this.environmentService = environmentService;
        this.hud = hud;
        this.mouseCoords = new Vector2();
        this.intersection = new Vector3();
        this.currentBuilding = null;
        this.hud.setBuildingService(this);
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
            material.set(OPACITY_ATTRIBUTE);
        }
        this.environmentService.addScene(this.currentBuilding.getScene());
    }

    private void handleBuildingPlacement() {
        if (currentBuilding == null) return;

        boolean isPressed = Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);

        CompletableFuture.runAsync(() -> {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            Ray ray = environmentService.getSceneService().camera.getPickRay(mouseX, mouseY);
            if (Intersector.intersectRayPlane(ray, new Plane(Vector3.Y, 0), intersection)) {
                float x = MathUtils.round(intersection.x / 2.5f) * 2.5f;
                float z = MathUtils.round(intersection.z / 2.5f) * 2.5f;
                currentBuilding.getScene().modelInstance.transform.setTranslation(x, intersection.y, z);

                Vector2 location = new Vector2(x, z);

                if (isPressed && !this.isBuildingCollision(location, new Vector2(2.5f, 2.5f))) {
                    Building building = this.currentBuilding.getBuildingResult().result(intersection);
                    SoundUtil.play("build.mp3");
                    this.addBuilding(building);
                }
            }
        });
    }

    private boolean isBuildingCollision(Vector2 otherPosition, Vector2 otherDimensions) {
        for (Building existingBuilding : entityService.getBuildings()) {
            Vector2 dimensions = existingBuilding.getDimensions();
            Vector2 position = new Vector2(existingBuilding.getPosition().x, existingBuilding.getPosition().z);
            if (position.x < otherPosition.x + otherDimensions.x &&
                    position.x + dimensions.x > otherPosition.x &&
                    position.y < otherPosition.y + otherDimensions.y &&
                    position.y + dimensions.y > otherPosition.y) {
                System.out.println(otherPosition.x);
                System.out.println(otherDimensions.x);
                System.out.println(position.x + " : " + (otherPosition.x + otherDimensions.x));
                System.out.println(position.x < otherPosition.x + otherDimensions.x);
                System.out.println(position.x + dimensions.x > otherPosition.x);
                System.out.println(position.y < otherPosition.y + otherDimensions.y);
                System.out.println(position.y + dimensions.y > otherPosition.y);
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
