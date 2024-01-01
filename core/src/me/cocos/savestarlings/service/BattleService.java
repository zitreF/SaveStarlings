package me.cocos.savestarlings.service;

import com.badlogic.gdx.Gdx;
import me.cocos.savestarlings.hud.Hud;
import me.cocos.savestarlings.hud.popup.impl.BattlePopup;

import java.util.concurrent.CompletableFuture;

public class BattleService {

    public BattleService() {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Gdx.app.postRunnable(() -> {
                BattlePopup battlePopup = new BattlePopup("Firebit attacks!");
                battlePopup.show(Hud.getInstance());
            });
        });
    }

    public void update(float delta) {
    }
}
