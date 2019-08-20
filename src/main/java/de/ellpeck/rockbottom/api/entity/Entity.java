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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.entity;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.IAdditionalDataProvider;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.data.set.part.PartDataSet;
import de.ellpeck.rockbottom.api.effect.ActiveEffect;
import de.ellpeck.rockbottom.api.effect.IEffect;
import de.ellpeck.rockbottom.api.entity.ai.AITask;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.entity.spawn.DespawnHandler;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.impl.EntityDeathEvent;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.*;
import java.util.function.ToIntFunction;

public class Entity extends MovableWorldObject implements IAdditionalDataProvider {

    private final List<ActiveEffect> effects = new ArrayList<>();
    private final List<AITask> aiTasks = new ArrayList<>();
    public int chunkX;
    public int chunkY;
    public Direction facing = Direction.NONE;
    public AITask currentAiTask;
    public int ticksExisted;
    public double fallStartY;
    public boolean isFalling;
    public double lastSyncX;
    public double lastSyncY;
    public boolean isClimbing;
    public boolean canClimb;
    public TileState submergedLiquid;
    public boolean canBreathe = true;
    public boolean canSwim;
    public boolean isFlying;
    public boolean isDropping;
    public double interpolationX;
    public double interpolationY;
    protected boolean dead;
    private UUID uniqueId = UUID.randomUUID();
    private ModBasedDataSet additionalData;

    public Entity(IWorld world) {
        super(world);
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public IEntityRenderer getRenderer() {
        return null;
    }

    public void update(IGameInstance game) {
        RockBottomAPI.getInternalHooks().doDefaultEntityUpdate(game, this, this.effects, this.aiTasks);
    }

    @Override
    public void onPositionReset() {
        super.onPositionReset();
        if (this.doesInterpolate()) {
            this.interpolationX = this.getOriginX();
            this.interpolationY = this.getOriginY();
        }
    }

    public boolean doesSync() {
        return true;
    }

    public int getSyncFrequency() {
        return 40;
    }

    public boolean doesInterpolate() {
        return false;
    }

    public boolean shouldBeFalling() {
        return !this.onGround && !this.isClimbing;
    }

    public void applyMotion() {
        if (!this.isClimbing && !this.isFlying) {
            this.motionY -= 0.025;
        }

        this.motionX *= 0.5;
        this.motionY *= this.isClimbing ? 0.5 : 0.98;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void setDead(boolean dead) {
        if (this.isDead() != dead) {
            if (RockBottomAPI.getEventHandler().fireEvent(new EntityDeathEvent(this)) != EventResult.CANCELLED) {
                this.dead = dead;

                if (this.world.isServer()) {
                    RockBottomAPI.getInternalHooks().packetDeath(this.world, this.getX(), this.getY(), this.getUniqueId());
                }
            }
        }
    }

    public void sendToClients() {
        if (this.world.isServer()) {
            RockBottomAPI.getInternalHooks().packetEntityData(this);
        }
    }

    public void setReadyToRemove() {
        this.setDead(true);
    }

    public boolean shouldBeRemoved() {
        return this.isDead();
    }

    public void onRemoveFromWorld() {

    }

    public boolean shouldRender() {
        return true;
    }

    public int getRenderPriority() {
        return 0;
    }

    public void onGroundHit(double fallDistance) {

    }

    public void moveToChunk(IChunk chunk) {
        this.chunkX = chunk.getGridX();
        this.chunkY = chunk.getGridY();
    }

    public void moveToWorld(IWorld world) {
        this.world = world;
    }

    @Override
    public boolean hasAdditionalData() {
        return this.additionalData != null;
    }

    @Override
    public ModBasedDataSet getAdditionalData() {
        return this.additionalData;
    }

    @Override
    public void setAdditionalData(ModBasedDataSet set) {
        this.additionalData = set;
    }

    @Override
    public ModBasedDataSet getOrCreateAdditionalData() {
        if (this.additionalData == null) {
            this.additionalData = new ModBasedDataSet();
        }
        return this.additionalData;
    }

    public void save(DataSet set, boolean forFullSync) {
        set.addDouble("x", this.getOriginX());
        set.addDouble("y", this.getOriginY());
        set.addDouble("motion_x", this.motionX);
        set.addDouble("motion_y", this.motionY);
        set.addBoolean("dead", this.dead);
        set.addBoolean("falling", this.isFalling);
        set.addDouble("fall_start_y", this.fallStartY);
        set.addBoolean("flying", this.isFlying);
        set.addInt("facing", this.facing.ordinal());

        if (this.additionalData != null) {
            set.addModBasedDataSet("data", this.additionalData);
        }

        List<PartDataSet> effects = new ArrayList<>();
        for (int i = 0; i < this.effects.size(); i++) {
            DataSet sub = new DataSet();
            this.effects.get(i).save(sub);
            effects.add(new PartDataSet(sub));
        }
        set.addList("effects", effects);

        if (!forFullSync) {
            if (this.currentAiTask != null) {
                DataSet sub = new DataSet();
                sub.addInt("id", this.getTaskId(this.currentAiTask));
                this.currentAiTask.save(sub, false, this);
                set.addDataSet("task", sub);
            }
            set.addInt("ticks", this.ticksExisted);
        }
    }

    public void load(DataSet set, boolean forFullSync) {
        this.setBoundsOrigin(set.getDouble("x"), set.getDouble("y"));
        this.onPositionReset();

        this.motionX = set.getDouble("motion_x");
        this.motionY = set.getDouble("motion_y");
        this.dead = set.getBoolean("dead");
        this.isFalling = set.getBoolean("falling");
        this.fallStartY = set.getDouble("fall_start_y");
        this.isFlying = set.getBoolean("flying");
        this.facing = Direction.DIRECTIONS[set.getInt("facing")];

        if (set.hasKey("data")) {
            this.additionalData = set.getModBasedDataSet("data");
        }

        this.effects.clear();
        List<PartDataSet> effects = set.getList("effects");
        for (PartDataSet part : effects) {
            ActiveEffect effect = ActiveEffect.load(part.get());
            if (effect != null) {
                effect.getEffect().onAddedOrLoaded(effect, this, true);
                this.effects.add(effect);
            }
        }

        if (!forFullSync) {
            DataSet sub = set.getDataSet("task");
            if (!sub.isEmpty()) {
                this.currentAiTask = this.aiTasks.get(sub.getInt("id"));
                if (this.currentAiTask != null) {
                    this.currentAiTask.load(sub, false, this);
                }
            }
            this.ticksExisted = set.getInt("ticks");
        }
    }

    public boolean doesSave() {
        return true;
    }

    public void onCollideWithTile(int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes) {

    }

    public void onCollideWithEntity(Entity otherEntity, BoundBox thisBox, BoundBox thisBoxMotion, BoundBox otherBox, BoundBox otherBoxMotion) {

    }

    public void onIntersectWithTile(int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes) {

    }

    public void onIntersectWithEntity(Entity otherEntity, BoundBox thisBox, BoundBox thisBoxMotion, BoundBox otherBox, BoundBox otherBoxMotion) {

    }

    public boolean onInteractWith(AbstractEntityPlayer player, double mouseX, double mouseY) {
        return false;
    }

    public boolean onInteractWithBreakKey(AbstractEntityPlayer player, double mouseX, double mouseY) {
        return false;
    }

    public int getInteractionPriority(AbstractEntityPlayer player, double mouseX, double mouseY) {
        return -100;
    }

    public boolean onAttack(AbstractEntityPlayer player, double mouseX, double mouseY, int intendedDamage) {
        return false;
    }

    public boolean shouldStartClimbing(int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes) {
        return false;
    }

    public boolean canCollideWith(MovableWorldObject object, BoundBox entityBox, BoundBox entityBoxMotion) {
        return false;
    }

    public double getMaxInteractionDistance(IWorld world, double mouseX, double mouseY, AbstractEntityPlayer player) {
        return player.getRange();
    }

    @Override
    public final void onTileCollision(int x, int y, TileLayer layer, TileState state, BoundBox objBox, BoundBox objBoxMotion, List<BoundBox> boxes) {
        Tile tile = state.getTile();
        tile.onCollideWithEntity(this.world, x, y, layer, state, objBox, objBoxMotion, boxes, this);
        this.onCollideWithTile(x, y, layer, state, objBox, objBoxMotion, boxes);
    }

    @Override
    public final void onEntityCollision(Entity entity, BoundBox thisBox, BoundBox thisBoxMotion, BoundBox otherBox, BoundBox otherBoxMotion) {
        this.onCollideWithEntity(entity, thisBox, thisBoxMotion, otherBox, otherBoxMotion);
        entity.onCollideWithEntity(this, otherBox, otherBoxMotion, thisBox, thisBoxMotion);
    }

    @Override
    public final void onTileIntersection(int x, int y, TileLayer layer, TileState state, BoundBox objBox, BoundBox objBoxMotion, List<BoundBox> boxes) {
        Tile tile = state.getTile();

        if (!this.canClimb || !this.isClimbing) {
            if (tile.canClimb(this.world, x, y, layer, state, objBox, objBoxMotion, boxes, this)) {
                this.canClimb = true;

                if (!this.onGround) {
                    if (this.shouldStartClimbing(x, y, layer, state, objBox, objBoxMotion, boxes)) {
                        this.isClimbing = true;
                    }
                }
            }
        }

        if (!this.canSwim) {
            if (tile.canSwim(this.world, x, y, layer, this)) {
                for (BoundBox box : boxes) {
                    if (this.collidedHor ? box.intersects(objBox) : box.contains(this.getX(), this.getY())) {
                        this.canSwim = true;
                        break;
                    }
                }
            }
        }

        tile.onIntersectWithEntity(this.world, x, y, layer, state, objBox, objBoxMotion, boxes, this);
        this.onIntersectWithTile(x, y, layer, state, objBox, objBoxMotion, boxes);
    }

    @Override
    public final void onEntityIntersection(Entity entity, BoundBox thisBox, BoundBox thisBoxMotion, BoundBox otherBox, BoundBox otherBoxMotion) {
        this.onIntersectWithEntity(entity, thisBox, thisBoxMotion, otherBox, otherBoxMotion);
        entity.onIntersectWithEntity(this, otherBox, otherBoxMotion, thisBox, thisBoxMotion);
    }

    public boolean shouldMakeChunkPersist(IChunk chunk) {
        return false;
    }

    @Override
    public float getWidth() {
        return 1F;
    }

    @Override
    public float getHeight() {
        return 1F;
    }

    public List<ActiveEffect> getActiveEffects() {
        return Collections.unmodifiableList(this.effects);
    }

    public int addEffect(ActiveEffect effect) {
        IEffect underlying = effect.getEffect();
        if (underlying.isInstant(this)) {
            underlying.activateInstant(effect, this);
            return 0;
        } else {
            for (ActiveEffect active : this.effects) {
                if (active.getEffect() == underlying) {
                    int maxAdd = Math.min(effect.getTime(), active.getEffect().getMaxDuration(this) - active.getTime());
                    active.addTime(maxAdd);

                    int level = effect.getLevel();
                    if (active.getLevel() < level) {
                        active.setLevel(level);
                    }

                    return effect.getTime() - maxAdd;
                }
            }

            this.effects.add(effect);
            underlying.onAddedOrLoaded(effect, this, false);
            return 0;
        }
    }

    public boolean removeEffect(IEffect effect) {
        for (int i = this.effects.size() - 1; i >= 0; i--) {
            ActiveEffect active = this.effects.get(i);
            if (active.getEffect() == effect) {
                this.effects.remove(i);
                effect.onRemovedOrEnded(active, this, false);
                return true;
            }
        }
        return false;
    }

    public int getEffectModifier(IEffect effect) {
        for (ActiveEffect active : this.effects) {
            if (active.getEffect() == effect) {
                return active.getLevel();
            }
        }
        return 0;
    }

    public boolean hasEffect(IEffect effect) {
        return this.getEffectModifier(effect) > 0;
    }

    public void addAiTask(AITask task) {
        this.aiTasks.add(task);
        this.aiTasks.sort(Comparator.comparingInt((ToIntFunction<AITask>) AITask::getPriority).reversed());
    }

    public AITask getTask(int id) {
        return id >= 0 && id < this.aiTasks.size() ? this.aiTasks.get(id) : null;
    }

    public int getTaskId(AITask task) {
        return this.aiTasks.indexOf(task);
    }

    public DespawnHandler getDespawnHandler() {
        return null;
    }

    public void applyKnockback(Entity source, double knockback) {
        double moveX = this.getX() - source.getX();
        double moveY = this.getY() - source.getY();
        double length = Util.distance(0, 0, moveX, moveY);

        this.motionX += knockback * (moveX / length);
        this.motionY += knockback * (moveY / length);
    }

    public double getEyeHeight() {
        return this.getHeight() / 2D;
    }
}
