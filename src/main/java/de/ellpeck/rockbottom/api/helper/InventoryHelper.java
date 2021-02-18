package de.ellpeck.rockbottom.api.helper;

import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InventoryHelper {

    /**
     * Collects all the items in the inventory into single item instances
     * even if the inventory has items separated.
     * <p>
     * For example if you collect the players inventory and it has 1 stick slot with amount set to 5 and 3 stone slots each with amount set to 20,
     * this method will return a list of 2 items, 1 stick with amount set to 5 and 1 stone with amount set to 60.
     *
     * @param inventory the inventory to collect items from.
     * @return The list of items collected into single type instances.
     */
    public static List<ItemInstance> collectItems(IInventory inventory) {
        List<ItemInstance> ret = new ArrayList<>();

        for (ItemInstance item : inventory) {
            if (item == null) {
                continue;
            }
            boolean saved = false;
            for (ItemInstance savedItem : ret) {
                if (savedItem.isEffectivelyEqual(item)) {
                    savedItem.addAmount(item.getAmount());
                    saved = true;
                    break;
                }
            }
            if (!saved) {
                ret.add(item.copy());
            }
        }

        return ret;
    }

    /**
     * Attempts to remove the item from the inventory, deducting the amounts from multiple item instances if needed.
     *
     * For example if you try to remove 14 stone from the players inventory which has 2 instances of 10 stones each,
     * it will first deduct 10 from the first instance and then 6 from the second instance leaving a single item with 4 stone.
     * @param inv The inventory to remove the item from
     * @param item The item to remove from the inventory
     * @return the remaining amount that was not removed from the inventory
     */
    public static int removeFrom(IInventory inv, IUseInfo item) {
        IUseInfo toRemove = item.copy();
        for (int slot = 0; slot < inv.getSlotAmount(); slot++) {
            ItemInstance stored = inv.get(slot);
            if (stored != null && toRemove.containsItem(stored)) {
                int amountToRemove = Math.min(toRemove.getAmount(), stored.getAmount());
                inv.remove(slot, amountToRemove);
                toRemove.setAmount(toRemove.getAmount() - amountToRemove);
                if (toRemove.getAmount() == 0) {
                    break;
                }
            }
        }

        return toRemove.getAmount();
    }
}
