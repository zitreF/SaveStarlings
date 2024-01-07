package me.cocos.savestarlings.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import me.cocos.savestarlings.asset.AssetService;

import java.util.concurrent.CompletableFuture;

public class SoundUtil {

    private static final String SOUND_PATH = "sounds/";
    private static final String MUSIC_PATH = "sounds/music/";

    public static void playSound(String name) {
        CompletableFuture.runAsync(() -> {
            Sound sound = AssetService.getAsset(SOUND_PATH+name);
            sound.play(1f);
        });
    }

    public static void playSound(String name, float volume) {
        CompletableFuture.runAsync(() -> {
            Sound sound = AssetService.getAsset(SOUND_PATH+name);
            sound.play(volume);
        });
    }

    public static void playMusic(String name) {
        CompletableFuture.runAsync(() -> {
            Music sound = AssetService.getAsset(MUSIC_PATH+name);
            sound.setLooping(true);
            sound.setVolume(1f);
            sound.play();
        });
    }
}
