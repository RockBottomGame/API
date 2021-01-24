/*
 * This file ("SpawnBehavior.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame/>.
 * View information on the project at <https://rockbottom.ellpeck.de/>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 *
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.entity.spawn;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class SpawnBehavior<T extends Entity> {

    protected final ResourceName name;

    public SpawnBehavior(ResourceName name) {
        this.name = name;
    }

    public abstract T createEntity(IWorld world, double x, double y);

    public abstract double getMinPlayerDistance(IWorld world, AbstractPlayerEntity player);

    public abstract double getMaxPlayerDistance(IWorld world, AbstractPlayerEntity player);

    public abstract int getSpawnTries(IWorld world);

    public abstract int getPackSize(IWorld world, double x, double y);

    public abstract boolean belongsToCap(Entity entity);

    public abstract double getEntityCapArea(IWorld world, AbstractPlayerEntity player);

    public abstract int getEntityCap(IWorld world);

    public boolean isReadyToSpawn(IWorld world) {
        return true;
    }

    public boolean canSpawnHere(IWorld world, double x, double y) {
        int theX = Util.floor(x);
        int theY = Util.floor(y);
        return !world.getState(theX, theY).getTile().isFullTile() && world.getState(theX, theY - 1).getTile().isFullTile();
    }

    public int getSpawnFrequency(IWorld world) {
        return 20;
    }

    public ResourceName getName() {
        return this.name;
    }

    public SpawnBehavior register() {
        Registries.SPAWN_BEHAVIOR_REGISTRY.register(this.getName(), this);
        return this;
    }
}
