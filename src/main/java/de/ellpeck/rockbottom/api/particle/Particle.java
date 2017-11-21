/*
 * This file ("Particle.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.particle;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.IWorld;

public class Particle extends MovableWorldObject{

    protected final BoundBox boundingBox = new BoundBox(-0.125, -0.125, 0.125, 0.125);

    protected int maxLife;
    protected int life;

    protected boolean dead;

    public Particle(IWorld world, double x, double y, double motionX, double motionY, int maxLife){
        super(world);
        this.motionX = motionX;
        this.motionY = motionY;
        this.maxLife = maxLife;

        this.setPos(x, y);
    }

    @Override
    public BoundBox getBoundingBox(){
        return this.boundingBox;
    }

    public void update(IGameInstance game){
        this.life++;

        if(this.life >= this.maxLife){
            this.setDead();
        }

        this.applyMotion();
        this.move();

        if(this.onGround){
            this.motionY = 0;
        }
    }

    public void setDead(){
        this.dead = true;
    }

    protected void applyMotion(){
        this.motionY -= 0.02;

        this.motionX *= this.onGround ? 0.8 : 0.98;
        this.motionY *= 0.99;
    }

    public void render(IGameInstance game, IAssetManager manager, IGraphics g, float x, float y, int filter){

    }

    public boolean isDead(){
        return this.dead;
    }
}
