/*
 * This file ("WorldObjectCollisionEvent.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.particle.Particle;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.BoundBox;

import java.util.List;

/**
 * This event is fired when a {@link MovableWorldObject} collides with a set of
 * {@link BoundBox} objects in the world. Most likely, the object in question is
 * an {@link Entity} or a {@link Particle} and the list of bounding boxes comes
 * from the surrounding {@link Tile} objects in the world. This event cannot be
 * cancelled.
 */
public final class WorldObjectCollisionEvent extends Event {

    public final MovableWorldObject object;
    public final BoundBox entityBoundBox;
    public final List<BoundBox> boundBoxes;

    public WorldObjectCollisionEvent(MovableWorldObject object, BoundBox entityBoundBox, List<BoundBox> boundBoxes) {
        this.object = object;
        this.entityBoundBox = entityBoundBox;
        this.boundBoxes = boundBoxes;
    }
}
