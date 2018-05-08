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
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.effect.ActiveEffect;
import de.ellpeck.rockbottom.api.effect.IEffect;
import de.ellpeck.rockbottom.api.entity.ai.AITask;
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

import java.util.*;
import java.util.function.ToIntFunction;

public class Entity extends MovableWorldObject implements IAdditionalDataProvider{

    public int chunkX;
    public int chunkY;

    public Direction facing = Direction.NONE;

    private final List<ActiveEffect> effects = new ArrayList<>();
    private final List<AITask> aiTasks = new ArrayList<>();
    public AITask currentAiTask;
    public int ticksExisted;
    public double fallStartY;
    public boolean isFalling;
    public double lastX;
    public double lastY;
    public boolean isClimbing;
    public boolean canClimb;
    protected boolean dead;
    private UUID uniqueId = UUID.randomUUID();
    private ModBasedDataSet additionalData;

    public Entity(IWorld world){
        super(world);
    }

    public UUID getUniqueId(){
        return this.uniqueId;
    }

    public void setUniqueId(UUID uniqueId){
        this.uniqueId = uniqueId;
    }

    public IEntityRenderer getRenderer(){
        return null;
    }

    public void update(IGameInstance game){
        RockBottomAPI.getInternalHooks().doDefaultEntityUpdate(game, this, this.effects, this.aiTasks);
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
        if(this.dead != dead){
            if(RockBottomAPI.getEventHandler().fireEvent(new EntityDeathEvent(this)) != EventResult.CANCELLED){
                this.dead = dead;

                if(this.world.isServer()){
                    RockBottomAPI.getNet().sendToAllPlayersWithLoadedPos(this.world, new PacketDeath(this.getUniqueId()), this.x, this.y);
                }
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

    public void moveToChunk(IChunk chunk){
        this.chunkX = chunk.getGridX();
        this.chunkY = chunk.getGridY();
    }

    @Override
    public boolean hasAdditionalData(){
        return this.additionalData != null;
    }

    @Override
    public ModBasedDataSet getAdditionalData(){
        return this.additionalData;
    }

    @Override
    public void setAdditionalData(ModBasedDataSet set){
        this.additionalData = set;
    }

    @Override
    public ModBasedDataSet getOrCreateAdditionalData(){
        if(this.additionalData == null){
            this.additionalData = new ModBasedDataSet();
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
        set.addBoolean("falling", this.isFalling);
        set.addDouble("fall_start_y", this.fallStartY);
        set.addInt("facing", this.facing.ordinal());

        if(this.additionalData != null){
            set.addModBasedDataSet("data", this.additionalData);
        }

        int amount = this.effects.size();
        for(int i = 0; i < amount; i++){
            DataSet sub = new DataSet();
            this.effects.get(i).save(sub);
            set.addDataSet("effect_"+i, sub);
        }
        set.addInt("effect_amount", amount);
    }

    public void load(DataSet set){
        this.setPos(set.getDouble("x"), set.getDouble("y"));
        this.motionX = set.getDouble("motion_x");
        this.motionY = set.getDouble("motion_y");
        this.ticksExisted = set.getInt("ticks");
        this.setDead(set.getBoolean("dead"));
        this.isFalling = set.getBoolean("falling");
        this.fallStartY = set.getDouble("fall_start_y");
        this.facing = Direction.DIRECTIONS[set.getInt("facing")];

        if(set.hasKey("data")){
            this.additionalData = set.getModBasedDataSet("data");
        }

        this.effects.clear();
        int amount = set.getInt("effect_amount");
        for(int i = 0; i < amount; i++){
            DataSet sub = set.getDataSet("effect_"+i);
            ActiveEffect effect = ActiveEffect.load(sub);
            if(effect != null){
                effect.getEffect().onAddedOrLoaded(effect, this, true);
                this.effects.add(effect);
            }
        }
    }

    public boolean doesSave(){
        return true;
    }

    public void onCollideWithTile(int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes){

    }

    public void onCollideWithEntity(Entity otherEntity, BoundBox thisBox, BoundBox thisBoxMotion, BoundBox otherBox, BoundBox otherBoxMotion){

    }

    public void onIntersectWithTile(int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes){

    }

    public void onIntersectWithEntity(Entity otherEntity, BoundBox thisBox, BoundBox thisBoxMotion, BoundBox otherBox, BoundBox otherBoxMotion){

    }

    public boolean onInteractWith(AbstractEntityPlayer player, double mouseX, double mouseY){
        return false;
    }

    public int getInteractionPriority(AbstractEntityPlayer player, double mouseX, double mouseY){
        return -100;
    }

    public boolean onAttack(AbstractEntityPlayer player, double mouseX, double mouseY, int intendedDamage){
        return false;
    }

    public boolean shouldStartClimbing(int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes){
        return false;
    }

    public boolean canCollideWith(MovableWorldObject object, BoundBox entityBox, BoundBox entityBoxMotion){
        return false;
    }

    public double getMaxInteractionDistance(IWorld world, double mouseX, double mouseY, AbstractEntityPlayer player){
        return AbstractEntityPlayer.RANGE;
    }

    @Override
    public final void onTileCollision(int x, int y, TileLayer layer, TileState state, BoundBox objBox, BoundBox objBoxMotion, List<BoundBox> boxes){
        Tile tile = state.getTile();
        tile.onCollideWithEntity(this.world, x, y, layer, state, objBox, objBoxMotion, boxes, this);
        this.onCollideWithTile(x, y, layer, state, objBox, objBoxMotion, boxes);
    }

    @Override
    public final void onEntityCollision(Entity entity, BoundBox thisBox, BoundBox thisBoxMotion, BoundBox otherBox, BoundBox otherBoxMotion){
        this.onCollideWithEntity(entity, thisBox, thisBoxMotion, otherBox, otherBoxMotion);
        entity.onCollideWithEntity(this, otherBox, otherBoxMotion, thisBox, thisBoxMotion);
    }

    @Override
    public final void onTileIntersection(int x, int y, TileLayer layer, TileState state, BoundBox objBox, BoundBox objBoxMotion, List<BoundBox> boxes){
        Tile tile = state.getTile();

        if(tile.canClimb(this.world, x, y, layer, state, objBox, objBoxMotion, boxes, this)){
            this.canClimb = true;

            if(!this.onGround){
                if(this.shouldStartClimbing(x, y, layer, state, objBox, objBoxMotion, boxes)){
                    this.isClimbing = true;
                }
            }
        }

        tile.onIntersectWithEntity(this.world, x, y, layer, state, objBox, objBoxMotion, boxes, this);
        this.onIntersectWithTile(x, y, layer, state, objBox, objBoxMotion, boxes);
    }

    @Override
    public final void onEntityIntersection(Entity entity, BoundBox thisBox, BoundBox thisBoxMotion, BoundBox otherBox, BoundBox otherBoxMotion){
        this.onIntersectWithEntity(entity, thisBox, thisBoxMotion, otherBox, otherBoxMotion);
        entity.onIntersectWithEntity(this, otherBox, otherBoxMotion, thisBox, thisBoxMotion);
    }

    public boolean shouldMakeChunkPersist(IChunk chunk){
        return false;
    }

    @Override
    public float getWidth(){
        return 1F;
    }

    @Override
    public float getHeight(){
        return 1F;
    }

    public List<ActiveEffect> getActiveEffects(){
        return Collections.unmodifiableList(this.effects);
    }

    public int addEffect(ActiveEffect effect){
        IEffect underlying = effect.getEffect();
        if(underlying.isInstant(this)){
            underlying.activateInstant(effect, this);
            return 0;
        }
        else{
            for(ActiveEffect active : this.effects){
                if(active.getEffect() == underlying){
                    int maxAdd = Math.min(effect.getTime(), active.getEffect().getMaxDuration(this)-active.getTime());
                    active.addTime(maxAdd);
                    return effect.getTime()-maxAdd;
                }
            }

            this.effects.add(effect);
            underlying.onAddedOrLoaded(effect, this, false);
            return 0;
        }
    }

    public boolean removeEffect(IEffect effect){
        for(int i = this.effects.size()-1; i >= 0; i--){
            ActiveEffect active = this.effects.get(i);
            if(active.getEffect() == effect){
                this.effects.remove(i);
                effect.onRemovedOrEnded(active, this, false);
                return true;
            }
        }
        return false;
    }

    public boolean hasEffect(IEffect effect){
        for(ActiveEffect active : this.effects){
            if(active.getEffect() == effect){
                return true;
            }
        }
        return false;
    }

    public void addAiTask(AITask task){
        this.aiTasks.add(task);
        this.aiTasks.sort(Comparator.comparingInt((ToIntFunction<AITask>)AITask :: getPriority).reversed());
    }
}
