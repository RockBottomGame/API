/*
 * This file ("TileInventory.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class TileInventory extends BasicFilteredInventory {

    /**
     * Creates a tile inventory that has a single slot that is an output slot
     *
     * @param tile the tile entity itself
     */
    public TileInventory(TileEntity tile) {
        this(tile, 1, Collections.emptyList(), Collections.singletonList(0));
    }

    /**
     * Creates a tile inventory that has a single slot that is an input slot.
     * The function determines if the given item can be put into the slot or
     * not
     *
     * @param tile the tile entity itself
     * @param canInput if it can be inputted into the tile entity
     */
    public TileInventory(TileEntity tile, Function<ItemInstance, Boolean> canInput) {
        this(tile, 1, (inst -> canInput.apply(inst) ? Collections.singletonList(0) : Collections.emptyList()), Collections.emptyList());
    }

    public TileInventory(TileEntity tile, int slotAmount, List<Integer> inputOutputSlots) {
        this(tile, slotAmount, inputOutputSlots, inputOutputSlots);
    }

    public TileInventory(TileEntity tile, int slotAmount, List<Integer> inputSlots, List<Integer> outputSlots) {
        this(tile, slotAmount, inst -> inputSlots, outputSlots);
    }

    public TileInventory(TileEntity tile, int slotAmount, Function<ItemInstance, List<Integer>> inputSlotFunction, List<Integer> outputSlots) {
        super(slotAmount, inputSlotFunction, outputSlots);
        this.addChangeCallback((inv, slot) -> tile.world.setDirty(tile.x, tile.y));
    }
}
