package de.ellpeck.rockbottom.api.entity.player;

public enum Gamemode {

    SURVIVAL,
    CREATIVE;

    public boolean isSurvival(){
        return this.equals(SURVIVAL);
    }

    public boolean isCreative(){
        return this.equals(CREATIVE);
    }

}
