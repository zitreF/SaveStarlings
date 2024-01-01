package me.cocos.savestarlings.hud.popup;

public enum BuildingPopupType {

    BLAST_CANNON("BLAST CANNON", "Need a massive killer? We're the bomb! We can kill many starlings with a couple of blasts. Killing enemy crowds is our thing!!", "ui/popup/turrets/blast_cannon_popup.png"),
    SNIPER_TOWER("SNIPER TOWER", "Our aim is unprecedented, we can kill a star fly from far far away! We specialize in killing enemies one by one from a distance before they even detect us!!", "ui/popup/turrets/sniper_tower_popup.png");

    private final String name;
    private final String description;
    private final String texture;

    BuildingPopupType(String name, String description, String texture) {
        this.name = name;
        this.description = description;
        this.texture = texture;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTexture() {
        return this.texture;
    }
}
