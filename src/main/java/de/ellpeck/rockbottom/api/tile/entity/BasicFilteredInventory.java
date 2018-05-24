/*
 * This file ("BasicFilteredInventory.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Direction;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class BasicFilteredInventory extends Inventory implements IFilteredInventory{

    private final Function<ItemInstance, List<Integer>> inputSlotFunction;
    private final List<Integer> outputSlots;

    public BasicFilteredInventory(int slotAmount, List<Integer> inputOutputSlots){
        this(slotAmount, inputOutputSlots, inputOutputSlots);
    }

    public BasicFilteredInventory(int slotAmount, List<Integer> inputSlots, List<Integer> outputSlots){
        this(slotAmount, inst -> inputSlots, outputSlots);
    }

    public BasicFilteredInventory(int slotAmount, Function<ItemInstance, List<Integer>> inputSlotFunction, List<Integer> outputSlots){
        super(slotAmount);
        this.inputSlotFunction = inputSlotFunction;
        this.outputSlots = Collections.unmodifiableList(outputSlots);
    }

    @Override
    public List<Integer> getInputSlots(ItemInstance instance, Direction dir){
        return this.inputSlotFunction.apply(instance);
    }

    @Override
    public List<Integer> getOutputSlots(Direction dir){
        return this.outputSlots;
    }
}
