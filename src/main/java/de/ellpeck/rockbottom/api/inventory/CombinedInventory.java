package de.ellpeck.rockbottom.api.inventory;

import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.function.BiFunction;

import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class CombinedInventory implements IInventory {
    private final IInventory[] inventories;

    public CombinedInventory(IInventory... inventories) {
        this.inventories = inventories;
    }

    @Override
    public void set(int id, ItemInstance instance) {
        this.<Void>executeOnSlot(id, (inventory, integer) -> {
            inventory.set(integer, instance);
            return null;
        });
    }

    private <T> T executeOnSlot(int id, BiFunction<IInventory, Integer, T> function) {
        for (int i = 0; i < inventories.length; i++) {
            int offset = id - offset(i);
            if (inventories[i].getSlotAmount() > offset) {
                return function.apply(inventories[i], offset);
            }
        }
        throw new IndexOutOfBoundsException("Id: " + id + " is not within the bounds of the contained inventories");
    }

    private int offset(int i) {
        return range(0, i).map(j -> inventories[j].getSlotAmount()).sum();
    }

    @Override
    public ItemInstance add(int id, int amount) {
        return this.executeOnSlot(id, (inventory, integer) -> inventory.add(integer, amount));
    }

    @Override
    public ItemInstance remove(int id, int amount) {
        return this.executeOnSlot(id, (inventory, integer) -> inventory.remove(integer, amount));
    }

    @Override
    public ItemInstance get(int id) {
        return this.executeOnSlot(id, IInventory::get);
    }

    @Override
    public int getSlotAmount() {
        return stream(inventories).mapToInt(IInventory::getSlotAmount).sum();
    }

    @Override
    public void notifyChange(int slot) {
        this.executeOnSlot(slot, (inv, i) -> {
            inv.notifyChange(i);
            return null;
        });
    }

    @Override
    public void addChangeCallback(IInvChangeCallback callback) {
        for (IInventory inventory : inventories) {
            inventory.addChangeCallback(callback);
        }
    }

    @Override
    public void removeChangeCallback(IInvChangeCallback callback) {
        for (IInventory inventory : inventories) {
            inventory.removeChangeCallback(callback);
        }
    }
}
