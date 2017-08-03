package de.ellpeck.rockbottom.api.inventory;

import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.function.BiFunction;

public class CombinedInventory implements IInventory{

    private final IInventory[] inventories;

    public CombinedInventory(IInventory... inventories){
        this.inventories = inventories;
    }

    @Override
    public void set(int id, ItemInstance instance){
        this.executeOnInv(id, (inventory, integer) -> {
            inventory.set(integer, instance);
            return null;
        });
    }

    public <T> T executeOnInv(int id, BiFunction<IInventory, Integer, T> function){
        int slotCounter = 0;
        for(IInventory inventory : this.inventories){
            int amount = inventory.getSlotAmount();
            if(slotCounter+amount > id){
                return function.apply(inventory, id-slotCounter);
            }
            else{
                slotCounter += amount;
            }
        }
        return null;
    }

    @Override
    public ItemInstance add(int id, int amount){
        return this.executeOnInv(id, (inventory, integer) -> inventory.add(integer, amount));
    }

    @Override
    public ItemInstance remove(int id, int amount){
        return this.executeOnInv(id, (inventory, integer) -> inventory.remove(integer, amount));
    }

    @Override
    public ItemInstance get(int id){
        return this.executeOnInv(id, IInventory:: get);
    }

    @Override
    public int getSlotAmount(){
        int sum = 0;
        for(IInventory inventory : this.inventories){
            int slotAmount = inventory.getSlotAmount();
            sum += slotAmount;
        }
        return sum;
    }

    @Override
    public void notifyChange(int slot){
        this.executeOnInv(slot, (inv, i) -> {
            inv.notifyChange(i);
            return null;
        });
    }

    @Override
    public void addChangeCallback(IInvChangeCallback callback){
        for(IInventory inventory : this.inventories){
            inventory.addChangeCallback(callback);
        }
    }

    @Override
    public void removeChangeCallback(IInvChangeCallback callback){
        for(IInventory inventory : this.inventories){
            inventory.removeChangeCallback(callback);
        }
    }
}
