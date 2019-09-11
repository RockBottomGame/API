/*
 * This file ("IInventory.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.inventory;

import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public interface IInventory extends Iterable<ItemInstance> {

    void set(int id, ItemInstance instance);

    ItemInstance add(int id, int amount);

    ItemInstance remove(int id, int amount);

    ItemInstance get(int id);

    int getSlotAmount();

    void notifyChange(int slot);

    void addChangeCallback(BiConsumer<IInventory, Integer> callback);

    void removeChangeCallback(BiConsumer<IInventory, Integer> callback);

    ItemInstance addToSlot(int slot, ItemInstance instance, boolean simulate);

    boolean containsResource(IUseInfo info);

    boolean containsItem(ItemInstance inst);

    int getItemIndex(ItemInstance inst);

    int getNextFreeIndex();

    default int getActualSlot(IInventory inv, int slot) {
        return slot;
    }

    default boolean containsInv(IInventory inv) {
        return inv == this;
    }

    ItemInstance add(ItemInstance instance, boolean simulate);

    ItemInstance addExistingFirst(ItemInstance instance, boolean simulate);

    void fillRandomly(Random random, List<ItemInstance> items);
}
