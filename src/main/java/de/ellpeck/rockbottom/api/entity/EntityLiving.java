/*
 * This file ("EntityLiving.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.impl.EntityDamageEvent;
import de.ellpeck.rockbottom.api.event.impl.RegenEvent;
import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class EntityLiving extends Entity {

    public boolean jumping;
    public int jumpTicks;
    public int jumpTimeout;
    protected int health;
    protected int maxHealth;
    public int lastDamageTime;
    public int deathTimer;

    public EntityLiving(IWorld world) {
        super(world);
        this.maxHealth = this.getInitialMaxHealth();
        this.health = this.getMaxHealth();
    }

    @Override
    public void update(IGameInstance game) {
        super.update(game);

        if (this.jumping) {
            if (this.jumpTicks > 0 && this.collidedVert) {
                this.motionY = 0;
                this.jumping = false;
                this.jumpTicks = 0;
                this.jumpTimeout = this.getJumpTimeout();
            } else {
                this.jumpTicks++;
            }
        }

        if (this.jumpTimeout > 0) {
            this.jumpTimeout--;
        }

        if (!this.world.isClient()) {
            if (this.health <= 0) {
                if (!this.dead) {
                    this.setDead(true);
                } else if (this.deathTimer < this.getDeathLingerTime()) {
                    this.deathTimer++;
                }
            } else {
                if (this.health < this.getMaxHealth()) {
                    RegenEvent event = new RegenEvent(this, this.getRegenRate(), 1);
                    if (RockBottomAPI.getEventHandler().fireEvent(event) != EventResult.CANCELLED) {
                        if (this.world.getTotalTime() % event.regenRate == 0) {
                            this.health += event.addedHealth;
                        }
                    }
                }
            }
        }
    }

    public int getDeathLingerTime() {
        return 40;
    }

    public boolean jump(double motion) {
        if (this.onGround && !this.jumping && this.jumpTimeout <= 0) {
            this.motionY += motion;
            this.jumping = true;
            return true;
        } else {
            return false;
        }
    }

    protected int getJumpTimeout() {
        return 3;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public boolean shouldBeRemoved() {
        return this.isDead() && this.deathTimer >= this.getDeathLingerTime();
    }

    @Override
    public void setReadyToRemove() {
        this.deathTimer = this.getDeathLingerTime();
        super.setReadyToRemove();
    }

    public void takeDamage(int amount) {
        EntityDamageEvent event = new EntityDamageEvent(this, amount);
        if (RockBottomAPI.getEventHandler().fireEvent(event) != EventResult.CANCELLED) {
            this.setHealth(this.getHealth() - event.amount);
            this.lastDamageTime = this.world.getTotalTime();
        }
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public abstract int getInitialMaxHealth();

    public abstract int getRegenRate();

    @Override
    public void save(DataSet set) {
        super.save(set);

        set.addBoolean("jumping", this.jumping);
        set.addInt("jump_ticks", this.jumpTicks);
        set.addInt("jump_timeout", this.jumpTimeout);
        set.addInt("health", this.health);
        set.addInt("max_health", this.maxHealth);
    }

    @Override
    public void load(DataSet set) {
        super.load(set);

        this.jumping = set.getBoolean("jumping");
        this.jumpTicks = set.getInt("jump_ticks");
        this.jumpTimeout = set.getInt("jump_timeout");
        this.health = set.getInt("health");
        if (set.hasKey("max_health")) { //TODO Remove this legacy compat check
            this.maxHealth = set.getInt("max_health");
        }
    }

    @Override
    public boolean onAttack(AbstractEntityPlayer player, double mouseX, double mouseY, int intendedDamage) {
        this.takeDamage(intendedDamage);
        return true;
    }
}
