/*
 * This file ("RestrictedSlot.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui.container;

import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.function.Predicate;

public class RestrictedSlotContainer extends SlotContainer {

    private final Predicate<ItemInstance> test;

    public RestrictedSlotContainer(IInventory inventory, int slot, int x, int y, Predicate<ItemInstance> test) {
        super(inventory, slot, x, y);
        this.test = test;
    }

    @Override
    public boolean canPlace(ItemInstance instance) {
        return this.test.test(instance);
    }
}
