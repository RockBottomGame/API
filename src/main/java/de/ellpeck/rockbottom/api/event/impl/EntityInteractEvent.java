/*
 * This file ("EntityInteractEvent.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.event.Event;

/**
 * This event is fired when an interaction with an {@link Entity} is taken,
 * right before its {@link Entity#onInteractWith(AbstractPlayerEntity, double,
 * double)} method is called. Cancelling the event will make the interaction not
 * take place.
 */
public final class EntityInteractEvent extends Event {

    public final AbstractPlayerEntity player;
    public final Entity entity;
    public final double x;
    public final double y;
    public final boolean isDestroyKey;

    public EntityInteractEvent(AbstractPlayerEntity player, Entity entity, double x, double y, boolean isDestroyKey) {
        this.player = player;
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.isDestroyKey = isDestroyKey;
    }
}
