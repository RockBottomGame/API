/*
 * This file ("TileEntity.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.tile.entity;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class TileEntity {

    public final IWorld world;
    public final int x;
    public final int y;
    public final TileLayer layer;

    public TileEntity(IWorld world, int x, int y, TileLayer layer) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

    public void update(IGameInstance game) {
        if (this.world.isServer()) {
            if (this.world.getTotalTime() % this.getSyncInterval() == 0 && this.needsSync()) {
                this.sendToClients();
                this.onSync();
            }
        }
    }

    public boolean shouldRemove() {
        return false;
    }

    public void save(DataSet set, boolean forSync) {

    }

    public void load(DataSet set, boolean forSync) {

    }

    protected boolean needsSync() {
        return false;
    }

    protected void onSync() {

    }

    protected int getSyncInterval() {
        return 10;
    }

    public void sendToClients() {
        if (this.world.isServer()) {
            RockBottomAPI.getInternalHooks().packetTileEntityData(this);
        }
    }

    public boolean doesSave() {
        return true;
    }

    public void dropInventory(IInventory inventory) {
        for (ItemInstance inst : inventory) {
            if (inst != null) {
                AbstractEntityItem.spawn(this.world, inst, this.x + 0.5, this.y + 0.5, Util.RANDOM.nextGaussian() * 0.1, Util.RANDOM.nextGaussian() * 0.1);
            }
        }
    }

    public IFilteredInventory getTileInventory() {
        return null;
    }

    public boolean shouldMakeChunkPersist(IChunk chunk) {
        return false;
    }

    public boolean doesTick() {
        return false;
    }
}
