/*
 * This file ("TilePebbles.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.tile;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TilePebbles extends TileBasic{

    public TilePebbles(){
        super(RockBottomAPI.createInternalRes("pebbles"));
    }

    @Override
    protected ItemTile createItemTile(){
        return new ItemTile(this.getName()){
            @Override
            protected IItemRenderer createRenderer(IResourceName name){
                return new DefaultItemRenderer(name);
            }
        };
    }

    @Override
    public BoundBox getBoundBox(IWorld world, int x, int y){
        return null;
    }

    @Override
    public boolean isFullTile(){
        return false;
    }

    @Override
    public boolean canStay(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer){
        return world.getState(layer, x, y-1).getTile().isFullTile();
    }

    @Override
    public boolean canPlace(IWorld world, int x, int y, TileLayer layer){
        return world.getState(layer, x, y-1).getTile().isFullTile();
    }
}
