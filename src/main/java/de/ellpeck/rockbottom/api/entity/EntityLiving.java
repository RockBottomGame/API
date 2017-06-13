/*
 * This file ("EntityLiving.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.entity;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class EntityLiving extends Entity{

    protected int health;
    protected boolean jumping;

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
        }

        if(this.health <= 0){
            this.kill();
        }
        else{
            if(!RockBottomAPI.getNet().isClient()){
                if(this.health < this.getMaxHealth()){
                    if(this.world.getWorldInfo().totalTimeInWorld%this.getRegenRate() == 0){
                        this.health++;
                    }
                }
            }
        }
    }

    public void jump(double motion){
        if(this.onGround && !this.jumping){
            this.motionY += motion;
            this.jumping = true;
        }
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public abstract int getMaxHealth();

    public abstract int getRegenRate();

    @Override
    public void save(DataSet set){
        super.save(set);

        set.addBoolean("jumping", this.jumping);
        set.addInt("health", this.health);
    }

    @Override
    public void load(DataSet set){
        super.load(set);

        this.jumping = set.getBoolean("jumping");
        this.health = set.getInt("health");
    }
}
