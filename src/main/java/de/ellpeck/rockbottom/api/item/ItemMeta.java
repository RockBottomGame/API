/*
 * This file ("ItemMeta.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.item;

import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.render.item.ItemMetaRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.ArrayList;
import java.util.List;

public class ItemMeta extends ItemBasic{

    public final List<IResourceName> subResourceNames = new ArrayList<>();
    public final List<IResourceName> subUnlocNames = new ArrayList<>();

    public ItemMeta(IResourceName name){
        this(name, true);
    }

    public ItemMeta(IResourceName name, boolean addDirectly){
        super(name);

        if(addDirectly){
            this.addSubItem(name);
        }
    }

    public ItemMeta addSubItem(IResourceName name){
        this.subResourceNames.add(name.addPrefix("items."));
        this.subUnlocNames.add(name.addPrefix("item."));
        return this;
    }

    @Override
    protected IItemRenderer createRenderer(IResourceName name){
        return new ItemMetaRenderer(name);
    }

    @Override
    public IResourceName getUnlocalizedName(ItemInstance instance){
        int meta = instance.getMeta();

        if(meta >= 0 && this.subUnlocNames.size() > meta){
            return this.subUnlocNames.get(meta);
        }
        else{
            return super.getUnlocalizedName(instance);
        }
    }

    @Override
    public int getHighestPossibleMeta(){
        return Math.max(this.subUnlocNames.size(), this.subResourceNames.size())-1;
    }
}
