/*
 * This file ("ItemUseInfo.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;

import java.util.Collections;
import java.util.List;

public class ItemUseInfo implements IUseInfo{

    private final ItemInstance instance;

    public ItemUseInfo(Tile tile, int amount, int meta){
        this(new ItemInstance(tile, amount, meta));
    }

    public ItemUseInfo(Tile tile, int amount){
        this(new ItemInstance(tile, amount));
    }

    public ItemUseInfo(Tile tile){
        this(new ItemInstance(tile));
    }

    public ItemUseInfo(Item item, int amount, int meta){
        this(new ItemInstance(item, amount, meta));
    }

    public ItemUseInfo(Item item, int amount){
        this(new ItemInstance(item, amount));
    }

    public ItemUseInfo(Item item){
        this(new ItemInstance(item));
    }

    public ItemUseInfo(ItemInstance instance){
        this.instance = instance;
    }

    @Override
    public int getAmount(){
        return this.instance.getAmount();
    }

    @Override
    public ItemUseInfo setAmount(int amount){
        this.instance.setAmount(amount);
        return this;
    }

    @Override
    public List<ItemInstance> getItems(){
        return Collections.singletonList(this.instance);
    }

    @Override
    public boolean containsItem(ItemInstance instance){
        return this.instance.isEffectivelyEqual(instance);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        else if(o instanceof ItemUseInfo){
            ItemUseInfo that = (ItemUseInfo)o;
            return this.instance.equals(that.instance);
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return this.instance.hashCode();
    }

    @Override
    public String toString(){
        return this.instance.toString();
    }
}
