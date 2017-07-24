/*
 * This file ("WorldObjectCollisionEvent.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.util.BoundBox;

import java.util.List;

/**
 * This event is fired when a {@link MovableWorldObject} collides with a multitude of {@link BoundBox}es
 * <br> It is not cancellable
 */
public class WorldObjectCollisionEvent extends Event{

    public final MovableWorldObject object;
    public final BoundBox entityBoundBox;
    public final List<BoundBox> boundBoxes;

    public WorldObjectCollisionEvent(MovableWorldObject object, BoundBox entityBoundBox, List<BoundBox> boundBoxes){
        this.object = object;
        this.entityBoundBox = entityBoundBox;
        this.boundBoxes = boundBoxes;
    }
}
