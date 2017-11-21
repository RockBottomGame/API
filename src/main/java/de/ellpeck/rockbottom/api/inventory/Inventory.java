/*
 * This file ("Inventory.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class Inventory implements IInventory{

    protected final Set<BiConsumer<IInventory, Integer>> callbacks = new HashSet<>();
    protected final ItemInstance[] slots;

    public Inventory(int slotAmount){
        this.slots = new ItemInstance[slotAmount];
    }

    public ItemInstance add(ItemInstance instance, boolean simulate){
        ItemInstance copy = instance.copy();

        for(int i = 0; i < this.slots.length; i++){
            copy = this.addToSlot(i, copy, simulate);

            if(copy == null){
                return null;
            }
        }

        return copy;
    }    @Override
    public void set(int id, ItemInstance instance){
        this.slots[id] = instance;
        this.notifyChange(id);
    }

    public ItemInstance addExistingFirst(ItemInstance instance, boolean simulate){
        ItemInstance copy = instance.copy();

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < this.slots.length; j++){
                if(i == 1 || (this.slots[j] != null && this.slots[j].isEffectivelyEqual(instance))){
                    copy = this.addToSlot(j, copy, simulate);

                    if(copy == null){
                        return null;
                    }
                }
            }
        }

        return copy;
    }    @Override
    public ItemInstance add(int id, int amount){
        ItemInstance inst = this.slots[id];
        if(inst != null){
            inst.addAmount(amount);

            this.notifyChange(id);
            return inst;
        }
        else{
            return null;
        }
    }

    public void save(DataSet set){
        for(int i = 0; i < this.slots.length; i++){
            ItemInstance slot = this.slots[i];

            if(slot != null){
                DataSet subset = new DataSet();
                slot.save(subset);
                set.addDataSet("item_"+i, subset);
            }
        }
    }    @Override
    public ItemInstance remove(int id, int amount){
        ItemInstance inst = this.slots[id];
        if(inst != null){
            inst.removeAmount(amount);

            if(inst.getAmount() <= 0){
                this.set(id, null);
                return null;
            }

            this.notifyChange(id);
            return inst;
        }
        else{
            return null;
        }
    }

    public void load(DataSet set){
        for(int i = 0; i < this.slots.length; i++){
            DataSet subset = set.getDataSet("item_"+i);
            if(!subset.isEmpty()){
                this.slots[i] = ItemInstance.load(subset);
            }
            else{
                this.slots[i] = null;
            }
        }
    }    @Override
    public ItemInstance get(int id){
        return this.slots[id];
    }

    @Override
    public int getSlotAmount(){
        return this.slots.length;
    }

    @Override
    public void notifyChange(int slot){
        for(BiConsumer<IInventory, Integer> callback : this.callbacks){
            callback.accept(this, slot);
        }
    }

    @Override
    public void addChangeCallback(BiConsumer<IInventory, Integer> callback){
        if(!this.callbacks.contains(callback)){
            this.callbacks.add(callback);
            RockBottomAPI.logger().config("Added change callback "+callback+" to inventory "+this);
        }
        else{
            RockBottomAPI.logger().warning("Tried adding change callback "+callback+" to inventory "+this+" but it was already present!");
        }
    }

    @Override
    public void removeChangeCallback(BiConsumer<IInventory, Integer> callback){
        this.callbacks.remove(callback);
        RockBottomAPI.logger().config("Removed change callback "+callback+" from inventory "+this);
    }





    @Override
    public ItemInstance addToSlot(int slot, ItemInstance instance, boolean simulate){
        ItemInstance slotInst = this.slots[slot];

        if(slotInst == null){
            if(!simulate){
                this.set(slot, instance);
            }
            return null;
        }
        else if(slotInst.isEffectivelyEqual(instance)){
            int space = slotInst.getMaxAmount()-slotInst.getAmount();

            if(space >= instance.getAmount()){
                if(!simulate){
                    this.add(slot, instance.getAmount());
                }
                return null;
            }
            else{
                if(!simulate){
                    this.add(slot, space);

                    instance.removeAmount(space);
                    if(instance.getAmount() <= 0){
                        return null;
                    }
                }
            }
        }
        return instance;
    }




}
