package de.ellpeck.rockbottom.api.tile;

import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class TileType {

    public final ResourceName name;
    public final Factory factory;

    public TileType(ResourceName name, Factory factory) {
        this.name = name;
        this.factory = factory;
    }

    public Tile create() {
        return this.factory.create();
    }

    public interface Factory {
        Tile create();
    }
}
