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

public abstract class EntityLiving extends Entity{

    public boolean jumping;
    protected int jumpTimeout;
    protected int health;

    public EntityLiving(IWorld world){
        super(world);
        this.health = this.getMaxHealth();
    }

    @Override
    public void update(IGameInstance game){
        super.update(game);

        if(this.jumping && this.collidedVert){
            this.motionY = 0;
            this.jumping = false;
            this.jumpTimeout = this.getJumpTimeout();
        }

        if(this.jumpTimeout > 0){
            this.jumpTimeout--;
        }

        if(!this.world.isClient()){
            if(this.health <= 0){
                if(!this.dead){
                    this.kill();
                }
            }
            else{
                if(this.health < this.getMaxHealth()){
                    RegenEvent event = new RegenEvent(this, this.getRegenRate(), 1);
                    if(RockBottomAPI.getEventHandler().fireEvent(event) != EventResult.CANCELLED){
                        if(this.world.getTotalTime()%event.regenRate == 0){
                            this.health += event.addedHealth;
                        }
                    }
                }
            }
        }
    }

    public void jump(double motion){
        if(this.onGround && !this.jumping && this.jumpTimeout <= 0){
            this.motionY += motion;
            this.jumping = true;
        }
    }

    protected int getJumpTimeout(){
        return 3;
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public void takeDamage(int amount){
        EntityDamageEvent event = new EntityDamageEvent(this, amount);
        if(RockBottomAPI.getEventHandler().fireEvent(event) != EventResult.CANCELLED){
            this.setHealth(this.getHealth()-event.amount);
        }
    }

    public abstract int getMaxHealth();

    public abstract int getRegenRate();

    @Override
    public void save(DataSet set){
        super.save(set);

        set.addBoolean("jumping", this.jumping);
        set.addInt("jump_timeout", this.jumpTimeout);
        set.addInt("health", this.health);
    }

    @Override
    public void load(DataSet set){
        super.load(set);

        this.jumping = set.getBoolean("jumping");
        this.jumpTimeout = set.getInt("jump_timeout");
        this.health = set.getInt("health");
    }

    @Override
    public boolean onAttack(AbstractEntityPlayer player, double mouseX, double mouseY, int intendedDamage){
        this.takeDamage(intendedDamage);
        return true;
    }
}
