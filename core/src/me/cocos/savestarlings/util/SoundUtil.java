package me.cocos.savestarlings.util;

import com.badlogic.gdx.audio.Sound;
import me.cocos.savestarlings.service.AssetService;

import java.util.concurrent.CompletableFuture;

public class SoundUtil {

    private static final String SOUND_PATH = "sounds/";

    public static void play(String name) {
        CompletableFuture.runAsync(() -> {
            Sound sound = AssetService.getAsset(SOUND_PATH+name);
            sound.play(1f);
        });
    }
}
