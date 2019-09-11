/*
 * This file ("AbstractInventory.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.event.impl.InventoryChangeEvent;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.*;
import java.util.function.BiConsumer;

public abstract class AbstractInventory implements IInventory {

    protected final Set<BiConsumer<IInventory, Integer>> callbacks = new HashSet<>();

    @Override
    public ItemInstance add(int id, int amount) {
        ItemInstance inst = this.get(id);
        if (inst != null) {
            inst.addAmount(amount);

            this.notifyChange(id);
            return inst;
        } else {
            return null;
        }
    }

    @Override
    public ItemInstance remove(int id, int amount) {
        ItemInstance inst = this.get(id);
        if (inst != null) {
            this.set(id, inst.removeAmount(amount).nullIfEmpty());
            return inst;
        } else {
            return null;
        }
    }

    @Override
    public void notifyChange(int slot) {
        RockBottomAPI.getEventHandler().fireEvent(new InventoryChangeEvent(this, slot));

        for (BiConsumer<IInventory, Integer> callback : this.callbacks) {
            callback.accept(this, slot);
        }
    }

    @Override
    public void addChangeCallback(BiConsumer<IInventory, Integer> callback) {
        if (!this.callbacks.contains(callback)) {
            this.callbacks.add(callback);
            RockBottomAPI.logger().config("Added change callback " + callback + " to inventory " + this);
        } else {
            RockBottomAPI.logger().warning("Tried adding change callback " + callback + " to inventory " + this + " but it was already present!");
        }
    }

    @Override
    public void removeChangeCallback(BiConsumer<IInventory, Integer> callback) {
        if (this.callbacks.remove(callback)) {
            RockBottomAPI.logger().config("Removed change callback " + callback + " from inventory " + this);
        } else {
            RockBottomAPI.logger().warning("Tried removing change callback " + callback + " to inventory " + this + " but it wasn't part of it!");
        }
    }

    @Override
    public ItemInstance addToSlot(int slot, ItemInstance instance, boolean simulate) {
        ItemInstance slotInst = this.get(slot);

        if (slotInst == null) {
            if (!simulate) {
                this.set(slot, instance);
            }
            return null;
        } else if (slotInst.isEffectivelyEqual(instance)) {
            int space = slotInst.getMaxAmount() - slotInst.getAmount();

            if (space >= instance.getAmount()) {
                if (!simulate) {
                    this.add(slot, instance.getAmount());
                }
                return null;
            } else {
                if (!simulate) {
                    this.add(slot, space);
                }
                instance.removeAmount(space);
            }
        }
        return instance.nullIfEmpty();
    }

    @Override
    public ItemInstance add(ItemInstance instance, boolean simulate) {
        ItemInstance copy = instance.copy();

        for (int i = 0; i < this.getSlotAmount(); i++) {
            copy = this.addToSlot(i, copy, simulate);

            if (copy == null) {
                return null;
            }
        }

        return copy;
    }

    @Override
    public ItemInstance addExistingFirst(ItemInstance instance, boolean simulate) {
        ItemInstance copy = instance.copy();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < this.getSlotAmount(); j++) {
                if (i == 1 || (this.get(j) != null && this.get(j).isEffectivelyEqual(instance))) {
                    copy = this.addToSlot(j, copy, simulate);

                    if (copy == null) {
                        return null;
                    }
                }
            }
        }

        return copy;
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
    public boolean containsItem(ItemInstance inst) {
        return this.getItemIndex(inst) >= 0;
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
    public int getNextFreeIndex() {
        for (int i = 0; i < this.getSlotAmount(); i++) {
            if (this.get(i) == null)
                return i;
        }
        return -1;
    }

    @Override
    public boolean containsResource(IUseInfo info) {
        for (ItemInstance inst : info.getItems()) {
            if (this.containsItem(inst)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<ItemInstance> iterator() {
        return new Iterator<ItemInstance>() {
            private int index;

            @Override
            public boolean hasNext() {
                return this.index < AbstractInventory.this.getSlotAmount();
            }

            @Override
            public ItemInstance next() {
                if (this.index >= AbstractInventory.this.getSlotAmount()) {
                    throw new NoSuchElementException();
                } else {
                    ItemInstance inst = AbstractInventory.this.get(this.index);
                    this.index++;
                    return inst;
                }
            }
        };
    }
}
