package de.ellpeck.rockbottom.api.entity.player;

public enum GameMode {

    SURVIVAL,
    CREATIVE;

    public boolean isSurvival(){
        return this.equals(SURVIVAL);
    }

    public boolean isCreative(){
        return this.equals(CREATIVE);
    }

}
