/*
 * This file ("DespawnHandler.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.entity.Entity;

public abstract class DespawnHandler<T extends Entity> {

    private int despawnTimer;

    public abstract boolean isReadyToDespawn(T entity);

    public abstract double getMaxPlayerDistance(T entity);

    public abstract int getDespawnTime(T entity);

    public void despawn(T entity){
        entity.setDead(true);
    }

    public void tickTimer() {
        this.despawnTimer++;
    }

    public void resetTimer() {
        this.despawnTimer = 0;
    }

    public int getTimer(){
        return this.despawnTimer;
    }
}
