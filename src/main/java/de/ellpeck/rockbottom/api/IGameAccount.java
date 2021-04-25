package de.ellpeck.rockbottom.api;

import de.ellpeck.rockbottom.api.render.IPlayerDesign;

import java.util.UUID;

public interface IGameAccount {

    String getDisplayName();

    IPlayerDesign getPlayerDesign();

    boolean isVerified();

    UUID getUUID();

    void setDisplayName(String newName);

    void setPlayerDesign(IPlayerDesign design);

    void setUuid(UUID uuid);

    void verify(boolean verify);
}
