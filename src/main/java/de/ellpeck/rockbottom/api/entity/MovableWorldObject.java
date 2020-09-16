/*
 * This file ("MovableWorldObject.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.BoundingBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;

public abstract class MovableWorldObject {

    public final BoundingBox currentBounds = new BoundingBox();
    public IWorld world;
    public double motionX;
    public double motionY;
    public boolean collidedHor;
    public boolean collidedVert;
    public boolean onGround;
    public double lastTickX;
    public double lastTickY;

    public MovableWorldObject(IWorld world) {
        this.world = world;
        this.resetBounds();
    }

    public void setPos(double x, double y) {
        this.setBounds(x, y);
        this.onPositionReset();
    }

    public void resetBounds() {
        this.setBoundsOrigin(this.getOriginX(), this.getOriginY());
        this.onPositionReset();
    }

    public void setBounds(double x, double y) {
        this.currentBounds.set(x, y, x, y).expand(this.getWidth() / 2D, this.getHeight() / 2D);
    }

    public void setBoundsOrigin(double x, double y) {
        this.currentBounds.set(x, y, x + this.getWidth(), y + this.getHeight());
    }

    public double getX() {
        return this.currentBounds.getCenterX();
    }

    public double getY() {
        return this.currentBounds.getCenterY();
    }

    public double getOriginX() {
        return this.currentBounds.getMinX();
    }

    public double getOriginY() {
        return this.currentBounds.getMinY();
    }

    public void onPositionReset() {
        this.lastTickX = this.getX();
        this.lastTickY = this.getY();
    }

    public double getLerpedX() {
        return Util.lerp(this.lastTickX, this.getX(), RockBottomAPI.getGame().getTickDelta());
    }

    public double getLerpedY() {
        return Util.lerp(this.lastTickY, this.getY(), RockBottomAPI.getGame().getTickDelta());
    }

    @ApiInternal
    public void move() {
        this.lastTickX = this.getX();
        this.lastTickY = this.getY();
        RockBottomAPI.getInternalHooks().doWorldObjectMovement(this);
    }

    @ApiInternal
    public void onTileCollision(int x, int y, TileLayer layer, TileState state, BoundingBox objBox, BoundingBox objBoxMotion, List<BoundingBox> boxes) {

    }

    @ApiInternal
    public void onEntityCollision(Entity entity, BoundingBox thisBox, BoundingBox thisBoxMotion, BoundingBox otherBox, BoundingBox otherBoxMotion) {

    }

    @ApiInternal
    public void onTileIntersection(int x, int y, TileLayer layer, TileState state, BoundingBox objBox, BoundingBox objBoxMotion, List<BoundingBox> boxes) {

    }

    @ApiInternal
    public void onEntityIntersection(Entity entity, BoundingBox thisBox, BoundingBox thisBoxMotion, BoundingBox otherBox, BoundingBox otherBoxMotion) {

    }

    public boolean canCollideWithTile(TileState state, int x, int y, TileLayer layer) {
        return true;
    }

    public abstract float getWidth();

    public abstract float getHeight();
}
