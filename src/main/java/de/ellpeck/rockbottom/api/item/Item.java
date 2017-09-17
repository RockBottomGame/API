/*
 * This file ("Item.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.item;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.construction.resource.ResInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResourceRegistry;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Item{

    protected final IResourceName name;
    protected final IResourceName unlocName;

    protected int maxAmount = 999;

    public Item(IResourceName name){
        this.name = name;
        this.unlocName = this.name.addPrefix("item.");
    }

    public IItemRenderer getRenderer(){
        return null;
    }

    public Item register(){
        RockBottomAPI.ITEM_REGISTRY.register(this.getName(), this);
        return this;
    }

    public Item addResource(String name){
        for(int i = 0; i <= this.getHighestPossibleMeta(); i++){
            ResourceRegistry.addResources(name, new ResInfo(this, i));
        }
        return this;
    }

    public int getMaxAmount(){
        return this.maxAmount;
    }

    public IResourceName getName(){
        return this.name;
    }

    public IResourceName getUnlocalizedName(ItemInstance instance){
        return this.unlocName;
    }

    public String getLocalizedName(ItemInstance instance){
        return RockBottomAPI.getGame().getAssetManager().localize(this.getUnlocalizedName(instance));
    }

    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced){
        desc.add(instance.getDisplayName());
    }

    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance){
        return false;
    }

    @Override
    public String toString(){
        return this.getName().toString();
    }

    public int getDespawnTime(ItemInstance instance){
        return 24000;
    }

    public boolean isDataSensitive(ItemInstance instance){
        return false;
    }

    public Map<ToolType, Integer> getToolTypes(ItemInstance instance){
        return Collections.emptyMap();
    }

    public float getMiningSpeed(IWorld world, int x, int y, TileLayer layer, Tile tile, boolean isRightTool){
        return 1F;
    }

    public int getHighestPossibleMeta(){
        return 0;
    }
}
