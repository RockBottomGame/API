/*
 * This file ("MultiTile.java") is part of the RockBottomAPI by Ellpeck.
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

import com.google.common.base.Preconditions;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;

public abstract class MultiTile extends TileBasic{

    public IntProp propSubX = new IntProp("subX", 0, this.getWidth());
    public IntProp propSubY = new IntProp("subY", 0, this.getHeight());
    private boolean[][] structure;

    public MultiTile(ResourceName name){
        super(name);
        this.addProps(this.propSubX, this.propSubY);
    }

    @Override
    protected ITileRenderer createRenderer(ResourceName name){
        return new MultiTileRenderer(name, this);
    }

    protected abstract boolean[][] makeStructure();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getMainX();

    public abstract int getMainY();

    public boolean isStructurePart(int x, int y){
        if(this.structure == null){
            this.structure = this.makeStructure();
            Preconditions.checkState(this.areDimensionsValid(), "MultiTile with name "+this.name+" has invalid structure dimensions!");
        }

        return this.structure[this.getHeight()-1-y][x];
    }

    @Override
    public boolean canPlace(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player){
        int startX = x-this.getMainX();
        int startY = y-this.getMainY();

        for(int addX = 0; addX < this.getWidth(); addX++){
            for(int addY = 0; addY < this.getHeight(); addY++){
                if(this.isStructurePart(addX, addY)){
                    int theX = startX+addX;
                    int theY = startY+addY;

                    if(!world.getState(layer, theX, theY).getTile().canReplace(world, theX, theY, layer)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void doPlace(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer){
        if(!world.isClient()){
            int startX = x-this.getMainX();
            int startY = y-this.getMainY();

            for(int addX = 0; addX < this.getWidth(); addX++){
                for(int addY = 0; addY < this.getHeight(); addY++){
                    if(this.isStructurePart(addX, addY)){
                        TileState state = this.getPlacementState(world, x, y, layer, instance, placer);
                        world.setState(layer, startX+addX, startY+addY, state.overrideProps(this.getState(addX, addY), this.propSubX, this.propSubY));
                    }
                }
            }
        }
    }

    @Override
    public void doBreak(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer breaker, boolean isRightTool, boolean allowDrop){
        if(!world.isClient()){
            Pos2 start = this.getBottomLeft(x, y, world.getState(layer, x, y));

            for(int addX = 0; addX < this.getWidth(); addX++){
                for(int addY = 0; addY < this.getHeight(); addY++){
                    if(this.isStructurePart(addX, addY)){
                        world.destroyTile(start.getX()+addX, start.getY()+addY, layer, breaker, allowDrop && (this.forceDrop || isRightTool));
                    }
                }
            }
        }
    }

    @Override
    public void onDestroyed(IWorld world, int x, int y, Entity destroyer, TileLayer layer, boolean shouldDrop){
        super.onDestroyed(world, x, y, destroyer, layer, shouldDrop && this.isMainPos(x, y, world.getState(layer, x, y)));
    }

    public boolean isMainPos(int x, int y, TileState state){
        Pos2 main = this.getMainPos(x, y, state);
        return main.getX() == x && main.getY() == y;
    }

    public Pos2 getInnerCoord(TileState state){
        return new Pos2(state.get(this.propSubX), state.get(this.propSubY));
    }

    public TileState getState(Pos2 coord){
        return this.getState(coord.getX(), coord.getY());
    }

    public TileState getState(int x, int y){
        return this.getDefState().prop(this.propSubX, x).prop(this.propSubY, y);
    }

    public Pos2 getMainPos(int x, int y, TileState state){
        return this.getBottomLeft(x, y, state).add(this.getMainX(), this.getMainY());
    }

    public Pos2 getBottomLeft(int x, int y, TileState state){
        Pos2 inner = this.getInnerCoord(state);
        return inner.set(x-inner.getX(), y-inner.getY());
    }

    private boolean areDimensionsValid(){
        if(this.structure.length != this.getHeight()){
            return false;
        }
        else{
            for(boolean[] row : this.structure){
                if(row.length != this.getWidth()){
                    return false;
                }
            }
        }
        return this.isStructurePart(this.getMainX(), this.getMainY());
    }

    @Override
    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced){
        super.describeItem(manager, instance, desc, isAdvanced);

        if(isAdvanced){
            desc.add("");
            desc.add(FormattingCode.LIGHT_GRAY+manager.localize(ResourceName.intern("info.size"), this.getWidth(), this.getHeight()));
        }
    }
}
