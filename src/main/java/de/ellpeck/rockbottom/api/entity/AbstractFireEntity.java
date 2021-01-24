package de.ellpeck.rockbottom.api.entity;

import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class AbstractFireEntity extends Entity {

    public AbstractFireEntity(IWorld world) {
        super(world);
    }

    public abstract int getLifespan();

    public abstract int getLife();

    public abstract float getSize();
}
