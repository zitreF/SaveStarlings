package me.cocos.savestarlings.entity.environment;

import me.cocos.savestarlings.callback.Result;
import me.cocos.savestarlings.entity.environment.tree.PalmTree;
import me.cocos.savestarlings.entity.environment.rock.Rock;

public enum EnvironmentType {
    PALM_TREE(PalmTree::new),
    ROCK(Rock::new);

    private final Result<Environment> environmentResult;

    EnvironmentType(Result<Environment> environmentResult) {
        this.environmentResult = environmentResult;
    }

    public Result<Environment> getEnvironmentResult() {
        return this.environmentResult;
    }
}
