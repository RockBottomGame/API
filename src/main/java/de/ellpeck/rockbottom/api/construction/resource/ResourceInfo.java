/*
 * This file ("ResourceInfo.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api.construction.resource;

import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.List;

public class ResourceInfo{

    private final String name;
    private int amount;

    public ResourceInfo(String name){
        this(name, 1);
    }

    public ResourceInfo(String name, int amount){
        this.name = name;
        this.amount = amount;
    }

    public String getName(){
        return this.name;
    }

    public int getAmount(){
        return this.amount;
    }

    public ResourceInfo setAmount(int amount){
        this.amount = amount;
        return this;
    }

    public List<ItemInstance> getItems(){
        List<ItemInstance> list = ResourceRegistry.getResources(this.name);
        for(ItemInstance inst : list){
            inst.setAmount(this.amount);
        }
        return list;
    }

    public boolean containsItem(ItemInstance instance){
        if(instance.getAmount() >= this.amount){
            List<String> names = ResourceRegistry.getNames(instance);
            return names.contains(this.name);
        }
        else{
            return false;
        }
    }
}
