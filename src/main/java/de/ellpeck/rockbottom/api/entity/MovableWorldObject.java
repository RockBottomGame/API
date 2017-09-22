/*
 * This file ("MovableWorldObject.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame>.
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

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.event.impl.WorldObjectCollisionEvent;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.util.List;

public abstract class MovableWorldObject{

    public IWorld world;

    public double x;
    public double y;

    public double motionX;
    public double motionY;

    public boolean collidedHor;
    public boolean collidedVert;
    public boolean onGround;

    public MovableWorldObject(IWorld world){
        this.world = world;
    }

    public void setPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void move(double motionX, double motionY){
        if(motionX != 0 || motionY != 0){
            double motionXBefore = motionX;
            double motionYBefore = motionY;

            BoundBox ownBox = this.getBoundingBox();
            BoundBox tempBox = ownBox.copy().add(this.x+motionX, this.y+motionY);
            List<BoundBox> boxes = this.world.getCollisions(tempBox);

            RockBottomAPI.getEventHandler().fireEvent(new WorldObjectCollisionEvent(this, tempBox, boxes));

            if(motionY != 0){
                if(!boxes.isEmpty()){
                    tempBox.set(ownBox).add(this.x, this.y);

                    for(BoundBox box : boxes){
                        if(motionY != 0){
                            if(!box.isEmpty()){
                                motionY = box.getYDistanceWithMax(tempBox, motionY);
                            }
                        }
                        else{
                            break;
                        }
                    }
                }

                this.y += motionY;
            }

            if(motionX != 0){
                if(!boxes.isEmpty()){
                    tempBox.set(ownBox).add(this.x, this.y);

                    for(BoundBox box : boxes){
                        if(motionX != 0){
                            if(!box.isEmpty()){
                                motionX = box.getXDistanceWithMax(tempBox, motionX);
                            }
                        }
                        else{
                            break;
                        }
                    }
                }

                this.x += motionX;
            }

            this.collidedHor = motionX != motionXBefore;
            this.collidedVert = motionY != motionYBefore;
            this.onGround = this.collidedVert && motionYBefore < 0;
        }
    }

    public abstract BoundBox getBoundingBox();
}
