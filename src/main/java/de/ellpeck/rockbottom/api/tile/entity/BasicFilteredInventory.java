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

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Direction;

import java.util.List;

public class BasicFilteredInventory extends FilteredInventory{

    private final List<Integer> inputSlots;
    private final List<Integer> outputSlots;

    public BasicFilteredInventory(int slotAmount, List<Integer> inputOutputSlots){
        this(slotAmount, inputOutputSlots, inputOutputSlots);
    }

    public BasicFilteredInventory(int slotAmount, List<Integer> inputSlots, List<Integer> outputSlots){
        super(slotAmount);
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
    }

    @Override
    public List<Integer> getInputSlots(ItemInstance instance, Direction dir){
        return this.inputSlots;
    }

    @Override
    public List<Integer> getOutputSlots(Direction dir){
        return this.outputSlots;
    }
}
