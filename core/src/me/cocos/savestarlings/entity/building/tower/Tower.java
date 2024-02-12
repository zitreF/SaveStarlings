package me.cocos.savestarlings.entity.building.tower;

import com.badlogic.gdx.math.Vector3;
import me.cocos.savestarlings.service.GameService;
import net.mgsx.gltf.scene3d.scene.Scene;

public abstract class Tower implements Defense {

    protected boolean isCapsuleEnabled;
    protected Scene rangeCapsule;

    protected void handleHover(float delta) {
        if (this.isHovered()) {
            if (!isCapsuleEnabled) {
                this.isCapsuleEnabled = true;
                rangeCapsule.modelInstance.transform.setToScaling(0f, 0f, 0f);
                GameService.getInstance().getEnvironmentService().getSceneService().addSceneWithoutShadows(rangeCapsule, false);
            }

            float maxSize = 1f;
            float growthRate = 5f;

            float currentSize = rangeCapsule.modelInstance.transform.getScaleX();

            float newSize = Math.min(currentSize + delta * growthRate, maxSize);

            newSize = Math.min(newSize, maxSize);

            rangeCapsule.modelInstance.transform.setToScaling(newSize, newSize, newSize);
            Vector3 position = this.getPosition();
            rangeCapsule.modelInstance.transform.setTranslation(position.x, position.y, position.z);
        } else {
            if (isCapsuleEnabled) {
                this.isCapsuleEnabled = false;
                GameService.getInstance().getEnvironmentService().getSceneService().removeSceneWithoutShadows(rangeCapsule);
            }
        }
    }

    public abstract int getRange();

    public abstract boolean isHovered();
}
