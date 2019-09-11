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
 * © 2018 Ellpeck
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
    public int lastDamageTime;
    public int deathTimer;
    protected int health;
    protected int maxHealth;
    protected int maxBreath = 10;
    protected int breath;
    protected int damageCooldown;

    public EntityLiving(IWorld world) {
        super(world);
        this.maxHealth = this.getInitialMaxHealth();
        this.health = this.getMaxHealth();
        this.breath = this.maxBreath;
    }

    @Override
    public void update(IGameInstance game) {
        super.update(game);
        this.isDropping = false;
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

        if (this.damageCooldown > 0) {
            this.damageCooldown--;
        }

        if (this.jumpTimeout > 0) {
            this.jumpTimeout--;
        }

        if (!this.world.isClient()) {
            if (this.health <= 0) {
                if (!this.isDead()) {
                    this.setDead(true);
                }
            } else {
                if (this.health < this.getMaxHealth()) {
                    RegenEvent event = new RegenEvent(this, this.getRegenRate(), 1);
                    if (RockBottomAPI.getEventHandler().fireEvent(event) != EventResult.CANCELLED) {
                        if (this.ticksExisted % event.regenRate == 0) {
                            this.health += event.addedHealth;
                        }
                    }
                }

                int breath = this.getBreath();
                if (!this.canBreathe) {
                    if (this.ticksExisted % 40 == 0) {
                        if (breath > 0) {
                            this.setBreath(breath - 1);
                        } else {
                            this.takeDamage(20);
                        }
                    }
                } else if (this.ticksExisted % 20 == 0 && breath < this.getMaxBreath()) {
                    this.setBreath(breath + 1);
                }
            }
        }

        if (this.isDead()) {
            if (this.deathTimer < this.getDeathLingerTime()) {
                this.deathTimer++;
            }
        } else {
            this.deathTimer = 0;
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

    protected int getDamageCooldown() {
        return 10;
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

    public boolean takeDamage(int amount) {
        if (this.damageCooldown <= 0) {
            EntityDamageEvent event = new EntityDamageEvent(this, amount);
            if (RockBottomAPI.getEventHandler().fireEvent(event) != EventResult.CANCELLED) {
                if (!this.world.isClient()) {
                    this.setHealth(this.getHealth() - event.amount);
                }

                this.lastDamageTime = this.world.getTotalTime();
                this.damageCooldown = this.getDamageCooldown();

                if (this.world.isServer()) {
                    RockBottomAPI.getInternalHooks().packetDamage(this.world, this.getX(), this.getY(), this.getUniqueId(), amount);
                }
                return true;
            }
        }
        return false;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getBreath() {
        return this.breath;
    }

    public void setBreath(int breath) {
        this.breath = breath;
    }

    public int getMaxBreath() {
        return this.maxBreath;
    }

    public void setMaxBreath(int maxBreath) {
        this.maxBreath = maxBreath;
    }

    public abstract int getInitialMaxHealth();

    public abstract int getRegenRate();

    public abstract float getKillReward(AbstractEntityPlayer player);

    @Override
    public void save(DataSet set, boolean forFullSync) {
        super.save(set, forFullSync);

        set.addBoolean("jumping", this.jumping);
        set.addInt("jump_ticks", this.jumpTicks);
        set.addInt("jump_timeout", this.jumpTimeout);
        set.addInt("health", this.health);
        set.addInt("max_health", this.maxHealth);
        set.addInt("breath", this.breath);
        set.addInt("max_breath", this.maxBreath);
    }

    @Override
    public void load(DataSet set, boolean forFullSync) {
        super.load(set, forFullSync);

        this.jumping = set.getBoolean("jumping");
        this.jumpTicks = set.getInt("jump_ticks");
        this.jumpTimeout = set.getInt("jump_timeout");
        this.health = set.getInt("health");
        this.maxHealth = set.getInt("max_health");
        this.breath = set.getInt("breath");
        this.maxBreath = set.getInt("max_breath");
    }

    @Override
    public boolean onAttack(AbstractEntityPlayer player, double mouseX, double mouseY, int intendedDamage) {
        if (!this.world.isClient()) {
            if (this.takeDamage(intendedDamage)) {
                if (player != null && this.getHealth() <= 0) {
                    player.gainSkill(this.getKillReward(player));
                }
            }
        }
        return true;
    }
}
