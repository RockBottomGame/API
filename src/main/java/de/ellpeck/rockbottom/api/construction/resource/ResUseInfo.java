/*
 * This file ("ResUseInfo.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.construction.resource;

import com.google.common.base.Preconditions;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.ArrayList;
import java.util.List;

public class ResUseInfo implements IUseInfo{

    private final String name;
    private int amount;

    public ResUseInfo(String name){
        this(name, 1);
    }

    public ResUseInfo(String name, int amount){
        this.name = name;
        this.amount = amount;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public int getAmount(){
        return this.amount;
    }

    @Override
    public ResUseInfo setAmount(int amount){
        this.amount = amount;
        return this;
    }

    @Override
    public List<ItemInstance> getItems(){
        List<ResInfo> resources = RockBottomAPI.getResourceRegistry().getResources(this.name);
        Preconditions.checkState(!resources.isEmpty(), "Found resource usage info "+this+" that uses resource "+this.name+" that does not have any resource items assigned to it. This is not allowed!");

        List<ItemInstance> list = new ArrayList<>();
        for(ResInfo info : resources){
            list.add(info.asInstance(this.amount));
        }
        return list;
    }

    @Override
    public boolean containsItem(ItemInstance instance){
        List<String> names = RockBottomAPI.getResourceRegistry().getNames(new ResInfo(instance));
        return names.contains(this.name);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        else if(o instanceof ResUseInfo){
            ResUseInfo that = (ResUseInfo)o;
            return this.amount == that.amount && this.name.equals(that.name);
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        int result = this.name.hashCode();
        result = 31*result+this.amount;
        return result;
    }

    @Override
    public String toString(){
        return this.name+" x"+this.amount;
    }
}
