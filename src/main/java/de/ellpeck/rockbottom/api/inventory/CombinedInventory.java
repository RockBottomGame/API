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
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api.inventory;

import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.function.BiConsumer;
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
    public void addChangeCallback(BiConsumer<IInventory, Integer> callback){
        for(IInventory inventory : this.inventories){
            inventory.addChangeCallback(callback);
        }
    }

    @Override
    public void removeChangeCallback(BiConsumer<IInventory, Integer> callback){
        for(IInventory inventory : this.inventories){
            inventory.removeChangeCallback(callback);
        }
    }

    @Override
    public ItemInstance addToSlot(int slot, ItemInstance instance, boolean simulate){
        return this.executeOnInv(slot, (inv, i) -> inv.addToSlot(i, instance, simulate));
    }
}
