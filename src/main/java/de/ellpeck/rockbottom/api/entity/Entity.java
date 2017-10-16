/*
 * This file ("Entity.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.entity;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.IAdditionalDataProvider;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.impl.EntityDeathEvent;
import de.ellpeck.rockbottom.api.net.packet.toclient.PacketDeath;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;
import java.util.UUID;

public class Entity extends MovableWorldObject implements IAdditionalDataProvider{

    protected final BoundBox boundingBox = new BoundBox(-0.5, -0.5, 0.5, 0.5);

    public int chunkX;
    public int chunkY;

    public Direction facing = Direction.NONE;

    public int ticksExisted;
    public double fallStartY;
    public boolean isFalling;
    public double lastX;
    public double lastY;
    public boolean isClimbing;
    public boolean canClimb;
    protected boolean dead;
    protected UUID uniqueId;
    private DataSet additionalData;

    public Entity(IWorld world){
        super(world);
        this.uniqueId = UUID.randomUUID();
    }

    public UUID getUniqueId(){
        return this.uniqueId;
    }

    public IEntityRenderer getRenderer(){
        return null;
    }

    public void update(IGameInstance game){
        RockBottomAPI.getApiHandler().doDefaultEntityUpdate(this);
    }

    public boolean doesSync(){
        return true;
    }

    public int getSyncFrequency(){
        return 40;
    }

    public void applyMotion(){
        if(!this.isClimbing){
            this.motionY -= 0.025;
        }

        this.motionX *= 0.5;
        this.motionY *= this.isClimbing ? 0.5 : 0.98;
    }

    public boolean isDead(){
        return this.dead;
    }

    public boolean shouldBeRemoved(){
        return this.isDead();
    }

    public void onRemoveFromWorld(){

    }

    public boolean shouldRender(){
        return !this.isDead();
    }

    public void setDead(boolean dead){
        if(RockBottomAPI.getEventHandler().fireEvent(new EntityDeathEvent(this)) != EventResult.CANCELLED){
            this.dead = dead;

            if(this.world.isServer()){
                RockBottomAPI.getNet().sendToAllPlayers(this.world, new PacketDeath(this.getUniqueId()));
            }
        }
    }

    public void kill(){
        this.setDead(true);
    }

    public int getRenderPriority(){
        return 0;
    }

    public void onGroundHit(double fallDistance){

    }

    @Override
    public BoundBox getBoundingBox(){
        return this.boundingBox;
    }

    public void moveToChunk(IChunk chunk){
        this.chunkX = chunk.getGridX();
        this.chunkY = chunk.getGridY();
    }

    @Override
    public boolean hasAdditionalData(){
        return this.additionalData != null;
    }

    @Override
    public DataSet getAdditionalData(){
        return this.additionalData;
    }

    @Override
    public void setAdditionalData(DataSet set){
        this.additionalData = set;
    }

    @Override
    public DataSet getOrCreateAdditionalData(){
        if(this.additionalData == null){
            this.additionalData = new DataSet();
        }
        return this.additionalData;
    }

    public void save(DataSet set){
        set.addDouble("x", this.x);
        set.addDouble("y", this.y);
        set.addDouble("motion_x", this.motionX);
        set.addDouble("motion_y", this.motionY);
        set.addInt("ticks", this.ticksExisted);
        set.addBoolean("dead", this.isDead());
        set.addUniqueId("uuid", this.uniqueId);
        set.addBoolean("falling", this.isFalling);
        set.addDouble("fall_start_y", this.fallStartY);
        set.addInt("facing", this.facing.ordinal());

        if(this.additionalData != null){
            set.addDataSet("data", this.additionalData);
        }
    }

    public void load(DataSet set){
        this.setPos(set.getDouble("x"), set.getDouble("y"));
        this.motionX = set.getDouble("motion_x");
        this.motionY = set.getDouble("motion_y");
        this.ticksExisted = set.getInt("ticks");
        this.setDead(set.getBoolean("dead"));
        this.uniqueId = set.getUniqueId("uuid");
        this.isFalling = set.getBoolean("falling");
        this.fallStartY = set.getDouble("fall_start_y");
        this.facing = Direction.DIRECTIONS[set.getInt("facing")];

        if(set.hasKey("data")){
            this.additionalData = set.getDataSet("data");
        }
    }

    public boolean doesSave(){
        return true;
    }

    public void onCollideWithTile(int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes){

    }

    public void onCollideWithEntity(Entity otherEntity, BoundBox thisBox, BoundBox thisBoxMotion, BoundBox otherBox, BoundBox otherBoxMotion){

    }

    public boolean onInteractWith(AbstractEntityPlayer player, double mouseX, double mouseY){
        return false;
    }

    public boolean shouldStartClimbing(int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes){
        return true;
    }

    @Override
    public final void onTileCollision(int x, int y, TileLayer layer, TileState state, BoundBox objBox, BoundBox objBoxMotion, List<BoundBox> boxes){
        Tile tile = state.getTile();

        if(tile.canClimb(this.world, x, y, layer, state, objBox, objBoxMotion, boxes, this)){
            this.canClimb = true;

            if(!this.onGround){
                if(this.shouldStartClimbing(x, y, layer, state, objBox, objBoxMotion, boxes)){
                    this.isClimbing = true;
                }
            }
        }

        tile.onCollideWithEntity(this.world, x, y, layer, state, objBox, objBoxMotion, boxes, this);
        this.onCollideWithTile(x, y, layer, state, objBox, objBoxMotion, boxes);
    }

    @Override
    public final void onEntityCollision(Entity entity, BoundBox thisBox, BoundBox thisBoxMotion, BoundBox otherBox, BoundBox otherBoxMotion){
        this.onCollideWithEntity(entity, thisBox, thisBoxMotion, otherBox, otherBoxMotion);
        entity.onCollideWithEntity(this, otherBox, otherBoxMotion, thisBox, thisBoxMotion);
    }

    public boolean canCollideWith(MovableWorldObject object, BoundBox entityBox, BoundBox entityBoxMotion){
        return false;
    }
}
