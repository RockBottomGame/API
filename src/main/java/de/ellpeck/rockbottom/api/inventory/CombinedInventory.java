/*
 * This file ("CombinedInventory.java") is part of the RockBottomAPI by Ellpeck.
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

import com.google.common.collect.Iterators;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class CombinedInventory implements IInventory {

    private final IInventory[] inventories;

    public CombinedInventory(IInventory... inventories) {
        this.inventories = inventories;
    }

    @Override
    public void set(int id, ItemInstance instance) {
        this.executeOnInv(id, (inventory, integer) -> {
            inventory.set(integer, instance);
            return null;
        });
    }

    public <T> T executeOnInv(int id, BiFunction<IInventory, Integer, T> function) {
        int slotCounter = 0;
        for (IInventory inventory : this.inventories) {
            int amount = inventory.getSlotAmount();
            if (slotCounter + amount > id) {
                return function.apply(inventory, id - slotCounter);
            } else {
                slotCounter += amount;
            }
        }
        return null;
    }

    @Override
    public ItemInstance add(int id, int amount) {
        return this.executeOnInv(id, (inventory, integer) -> inventory.add(integer, amount));
    }

    @Override
    public ItemInstance remove(int id, int amount) {
        return this.executeOnInv(id, (inventory, integer) -> inventory.remove(integer, amount));
    }

    @Override
    public ItemInstance get(int id) {
        return this.executeOnInv(id, IInventory::get);
    }

    @Override
    public int getSlotAmount() {
        int sum = 0;
        for (IInventory inventory : this.inventories) {
            int slotAmount = inventory.getSlotAmount();
            sum += slotAmount;
        }
        return sum;
    }

    @Override
    public void notifyChange(int slot) {
        this.executeOnInv(slot, (inv, i) -> {
            inv.notifyChange(i);
            return null;
        });
    }

    @Override
    public void addChangeCallback(BiConsumer<IInventory, Integer> callback) {
        for (IInventory inventory : this.inventories) {
            inventory.addChangeCallback(callback);
        }
    }

    @Override
    public void removeChangeCallback(BiConsumer<IInventory, Integer> callback) {
        for (IInventory inventory : this.inventories) {
            inventory.removeChangeCallback(callback);
        }
    }

    @Override
    public ItemInstance addToSlot(int slot, ItemInstance instance, boolean simulate) {
        return this.executeOnInv(slot, (inv, i) -> inv.addToSlot(i, instance, simulate));
    }

    @Override
    public boolean containsResource(IUseInfo info) {
        for (IInventory inventory : this.inventories) {
            if (inventory.containsResource(info)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsItem(ItemInstance inst) {
        for (IInventory inventory : this.inventories) {
            if (inventory.containsItem(inst)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemIndex(ItemInstance inst) {
        for (int i = 0; i < this.getSlotAmount(); i++) {
            ItemInstance instance = this.get(i);
            if (instance != null && instance.getAmount() >= inst.getAmount() && instance.isEffectivelyEqual(inst)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ItemInstance add(ItemInstance instance, boolean simulate) {
        ItemInstance remaining = instance;
        for (IInventory inventory : this.inventories) {
            remaining = inventory.add(remaining, simulate);
            if (remaining == null) {
                break;
            }
        }
        return remaining;
    }

    @Override
    public ItemInstance addExistingFirst(ItemInstance instance, boolean simulate) {
        ItemInstance remaining = instance;
        for (IInventory inventory : this.inventories) {
            remaining = inventory.addExistingFirst(remaining, simulate);
            if (remaining == null) {
                break;
            }
        }
        return remaining;
    }

    @Override
    public void fillRandomly(Random random, List<ItemInstance> items) {
        for (ItemInstance instance : items) {
            int slot;
            do {
                slot = random.nextInt(this.getSlotAmount());
            }
            while (this.get(slot) != null);

            this.set(slot, instance);
        }
    }

    @Override
    public Iterator<ItemInstance> iterator() {
        Iterator<ItemInstance>[] its = new Iterator[this.inventories.length];
        for (int i = 0; i < its.length; i++) {
            its[i] = this.inventories[i].iterator();
        }
        return Iterators.concat(its);
    }
}
