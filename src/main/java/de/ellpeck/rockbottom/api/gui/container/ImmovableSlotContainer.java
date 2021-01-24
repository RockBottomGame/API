package de.ellpeck.rockbottom.api.gui.container;

import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ImmovableSlotContainer extends SlotContainer {

    public ImmovableSlotContainer(IInventory inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public boolean canRemove(int amount) {
        return false;
    }
}
