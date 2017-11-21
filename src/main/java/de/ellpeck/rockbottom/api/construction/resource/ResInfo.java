/*
 * This file ("ResInfo.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ResInfo{

    private final Item item;
    private final int meta;

    public ResInfo(ItemInstance instance){
        this(instance.getItem(), instance.getMeta());
    }

    public ResInfo(Item item, int meta){
        this.item = item;
        this.meta = meta;
    }

    public ResInfo(Tile tile){
        this(tile, 0);
    }

    public ResInfo(Tile tile, int meta){
        this(tile.getItem(), meta);
    }

    public ResInfo(Item item){
        this(item, 0);
    }

    public Item getItem(){
        return this.item;
    }

    public int getMeta(){
        return this.meta;
    }

    public ItemInstance asInstance(int amount){
        return new ItemInstance(this.item, amount, this.meta);
    }

    @Override
    public int hashCode(){
        int result = this.item.hashCode();
        result = 31*result+this.meta;
        return result;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        else if(o instanceof ResInfo){
            ResInfo that = (ResInfo)o;
            return this.meta == that.meta && this.item.equals(that.item);
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){
        return this.item+"@"+this.meta;
    }
}
