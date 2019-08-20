/*
 * This file ("ItemLiquidContainer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.item;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class ItemLiquidContainer extends ItemBasic {

    public ItemLiquidContainer(ResourceName name) {
        super(name);
    }

    public static DataSet getLiquidData(ItemInstance instance, boolean create) {
        ModBasedDataSet data = create ? instance.getOrCreateAdditionalData() : instance.getAdditionalData();
        if (data != null) {
            DataSet set = data.getDataSet(ResourceName.intern("liquid"));
            if (create && set.isEmpty()) {
                data.addDataSet(ResourceName.intern("liquid"), set);
            }
            return set;
        } else {
            return null;
        }
    }

    public static int getLiquidAmount(ItemInstance instance) {
        DataSet set = getLiquidData(instance, false);
        return set != null ? set.getInt("amount") : 0;
    }

    public static ResourceName getLiquid(ItemInstance instance) {
        DataSet set = getLiquidData(instance, false);
        return set != null ? new ResourceName(set.getString("name")) : null;
    }

    public static void storeLiquid(ItemInstance instance, ResourceName liquid, int amount) {
        DataSet set = getLiquidData(instance, true);
        if (liquid == null) {
            set.clear();
        } else {
            set.addString("name", liquid.toString());
            set.addInt("amount", amount);
        }
    }

    public abstract boolean allowsLiquid(ItemInstance instance, ResourceName name);

    public abstract int getCapacity(ItemInstance instance);

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        int stored = getLiquidAmount(instance);
        TileState state = world.getState(TileLayer.LIQUIDS, x, y);
        Tile tile = state.getTile();

        if (stored <= 0) { // grab liquid from world
            if (tile instanceof TileLiquid) {
                IntProp amountProp = ((TileLiquid) tile).level;
                int tileAmount = state.get(amountProp) + 1;
                if (this.getCapacity(instance) > 0) { // container capacity > 0
                    int toStore = Math.min(tileAmount, this.getCapacity(instance));
                    if (this.allowsLiquid(instance, tile.getName())) {
                        if (!world.isClient()) {
                            storeLiquid(instance, tile.getName(), toStore);
                            player.getInv().notifyChange(player.getSelectedSlot());

                            if (toStore >= tileAmount) {
                                world.setState(TileLayer.LIQUIDS, x, y, GameContent.TILE_AIR.getDefState());
                            } else {
                                world.setState(TileLayer.LIQUIDS, x, y, state.prop(amountProp, tileAmount - 1 - toStore));
                            }
                        }
                        return true;
                    }
                }
            }
        } else { // put liquid in world
            if (tile.isAir()) {
                if (!world.isClient()) {
                    ResourceName liquid = getLiquid(instance);
                    Tile toPlace = Registries.TILE_REGISTRY.get(liquid);
                    if (toPlace instanceof TileLiquid) {
                        IntProp amountProp = ((TileLiquid) toPlace).level;
                        int toPutDown = Math.min(stored, amountProp.getVariants());

                        TileState placeState = toPlace.getDefState().prop(amountProp, toPutDown - 1);
                        world.setState(TileLayer.LIQUIDS, x, y, placeState);

                        if (toPutDown >= stored) {
                            storeLiquid(instance, null, 0);
                        } else {
                            storeLiquid(instance, liquid, stored - toPutDown);
                        }
                        player.getInv().notifyChange(player.getSelectedSlot());
                    }
                }
                return true;
            }
        }
        return false;
    }
}
