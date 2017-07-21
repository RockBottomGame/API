/*
 * This file ("Tile.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.tile;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.EntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.StateHandler;
import de.ellpeck.rockbottom.api.tile.state.TileProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;
import org.newdawn.slick.Input;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tile{

    public static final BoundBox DEFAULT_BOUNDS = new BoundBox(0, 0, 1, 1);
    private static final IResourceName LOC_ADVANCED = RockBottomAPI.createInternalRes("info.advanced_info");
    private static final IResourceName LOC_LAYER = RockBottomAPI.createInternalRes("info.layer_placement");

    protected final IResourceName name;

    protected Map<ToolType, Integer> effectiveTools = new HashMap<>();
    protected boolean forceDrop;
    protected float hardness = 1F;

    protected final StateHandler possibleStates;

    public Tile(IResourceName name){
        this.name = name;

        this.createNonStaticProps();
        this.possibleStates = new StateHandler(this);
    }

    public ITileRenderer getRenderer(){
        return null;
    }

    public BoundBox getBoundBox(IWorld world, int x, int y){
        return DEFAULT_BOUNDS;
    }

    public void getBoundBoxes(IWorld world, int x, int y, List<BoundBox> list){
        BoundBox box = this.getBoundBox(world, x, y);
        if(box != null && !box.isEmpty()){
            list.add(box.copy().add(x, y));
        }
    }

    public boolean canBreak(IWorld world, int x, int y, TileLayer layer){
        if(layer == TileLayer.MAIN){
            return true;
        }
        else{
            if(!world.getState(x, y).getTile().isFullTile()){
                for(Direction dir : Direction.ADJACENT){
                    Tile tile = world.getState(layer, x+dir.x, y+dir.y).getTile();
                    if(!tile.isFullTile()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canPlace(IWorld world, int x, int y, TileLayer layer){
        if(!this.canPlaceInLayer(layer)){
            return false;
        }

        if(!world.getState(layer.getOpposite(), x, y).getTile().isAir()){
            return true;
        }

        for(TileLayer testLayer : TileLayer.LAYERS){
            for(Direction dir : Direction.ADJACENT){
                Tile tile = world.getState(testLayer, x+dir.x, y+dir.y).getTile();
                if(!tile.isAir()){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canPlaceInLayer(TileLayer layer){
        return layer != TileLayer.BACKGROUND || !this.canProvideTileEntity();
    }

    public Tile register(){
        RockBottomAPI.TILE_REGISTRY.register(this.getName(), this);

        if(this.hasItem()){
            this.createItemTile().register();
        }

        return this;
    }

    protected ItemTile createItemTile(){
        return new ItemTile(this.getName());
    }

    protected boolean hasItem(){
        return true;
    }

    public Item getItem(){
        if(this.hasItem()){
            return RockBottomAPI.ITEM_REGISTRY.get(this.getName());
        }
        else{
            return null;
        }
    }

    public void onRemoved(IWorld world, int x, int y, TileLayer layer){

    }

    public void onAdded(IWorld world, int x, int y, TileLayer layer){

    }

    public boolean onInteractWith(IWorld world, int x, int y, double mouseX, double mouseY, AbstractEntityPlayer player){
        return false;
    }

    public boolean canReplace(IWorld world, int x, int y, TileLayer layer, Tile replacementTile){
        return false;
    }

    public void onDestroyed(IWorld world, int x, int y, Entity destroyer, TileLayer layer, boolean shouldDrop){
        if(shouldDrop){
            if(!RockBottomAPI.getNet().isClient()){
                List<ItemInstance> drops = this.getDrops(world, x, y, layer, destroyer);
                if(drops != null && !drops.isEmpty()){
                    for(ItemInstance inst : drops){
                        EntityItem.spawn(world, inst, x+0.5, y+0.5, Util.RANDOM.nextGaussian()*0.1, Util.RANDOM.nextGaussian()*0.1);
                    }
                }
            }
        }
    }

    public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer){
        Item item = this.getItem();
        if(item != null){
            return Collections.singletonList(new ItemInstance(item));
        }
        else{
            return null;
        }
    }

    public TileEntity provideTileEntity(IWorld world, int x, int y){
        return null;
    }

    public boolean canProvideTileEntity(){
        return false;
    }

    public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer){
        if(!this.canStay(world, x, y, layer, changedX, changedY, changedLayer)){
            world.destroyTile(x, y, layer, null, true);
        }
    }

    public boolean canStay(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer){
        return true;
    }

    public boolean isFullTile(){
        return true;
    }

    public void updateRandomly(IWorld world, int x, int y){

    }

    public void updateRandomlyForRendering(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player){

    }

    public void doBreak(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer breaker, boolean isRightTool, boolean allowDrop){
        world.destroyTile(x, y, layer, breaker, allowDrop && (this.forceDrop || isRightTool));
    }

    public void doPlace(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer){
        world.setState(layer, x, y, this.getPlacementState(world, x, y, layer, instance, placer));
    }

    public TileState getPlacementState(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer){
        return this.getDefState();
    }

    public float getHardness(IWorld world, int x, int y, TileLayer layer){
        return this.hardness;
    }

    public Tile setHardness(float hardness){
        this.hardness = hardness;
        return this;
    }

    public Tile setForceDrop(){
        this.forceDrop = true;
        return this;
    }

    public boolean isToolEffective(IWorld world, int x, int y, TileLayer layer, ToolType type, int level){
        for(Map.Entry<ToolType, Integer> entry : this.effectiveTools.entrySet()){
            if(entry.getKey() == type && level >= entry.getValue()){
                return true;
            }
        }
        return false;
    }

    public Tile addEffectiveTool(ToolType type, int level){
        this.effectiveTools.put(type, level);
        return this;
    }

    public int getLight(IWorld world, int x, int y, TileLayer layer){
        return 0;
    }

    public float getTranslucentModifier(IWorld world, int x, int y, TileLayer layer, boolean skylight){
        if(!this.isFullTile() && skylight){
            return 1F;
        }
        else{
            return layer == TileLayer.BACKGROUND ? 0.9F : 0.8F;
        }
    }

    public boolean isAir(){
        return false;
    }

    public IResourceName getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return this.getName().toString();
    }

    public void onScheduledUpdate(IWorld world, int x, int y, TileLayer layer){

    }

    public boolean canClimb(IWorld world, int x, int y, TileLayer layer, Entity entity){
        return false;
    }

    public void onCollideWithEntity(IWorld world, int x, int y, TileLayer layer, Entity entity){

    }

    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced){
        if(isAdvanced){
            for(TileLayer layer : TileLayer.LAYERS){
                if(this.canPlaceInLayer(layer)){
                    desc.add(FormattingCode.GRAY+manager.localize(LOC_LAYER, manager.localize(layer.name)));
                }
            }
        }
        else{
            desc.add(FormattingCode.DARK_GRAY+manager.localize(LOC_ADVANCED, Input.getKeyName(RockBottomAPI.getGame().getSettings().keyAdvancedInfo.key)));
        }
    }

    public TileProp[] getProperties(){
        return new TileProp[0];
    }

    public TileState getDefState(){
        return this.possibleStates.getDefault();
    }

    public <T extends Comparable> TileState getDefStateWithProp(TileProp<T> prop, T value){
        return this.getDefState().withProperty(prop, value);
    }

    protected void createNonStaticProps(){

    }
}
