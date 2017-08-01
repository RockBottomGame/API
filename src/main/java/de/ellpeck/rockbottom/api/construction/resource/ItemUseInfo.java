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

import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.Collections;
import java.util.List;

public class ItemUseInfo implements IResUseInfo{

    private final ItemInstance instance;

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
}
