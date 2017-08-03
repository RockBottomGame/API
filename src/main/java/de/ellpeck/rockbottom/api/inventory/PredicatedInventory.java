package de.ellpeck.rockbottom.api.inventory;

import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.function.Predicate;

public class PredicatedInventory extends Inventory {
    private final Predicate<ItemInstance> predicate;

    public PredicatedInventory(int slotAmount, Predicate<ItemInstance> predicate) {
        super(slotAmount);
        this.predicate = predicate;
    }

    @Override
    public void set(int id, ItemInstance instance) {
        if (predicate.test(instance))
            super.set(id, instance);
    }

    @Override
    public ItemInstance addToSlot(int slot, ItemInstance instance, boolean simulate) {
        if (predicate.test(instance))
            return super.addToSlot(slot, instance, simulate);
        return instance;
    }
}
