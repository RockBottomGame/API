/*
 * This file ("ResetMovedPlayerEvent.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;

/**
 * This event is fired when a simple calculation in the server results in the
 * {@link AbstractEntityPlayer} in question having moved farther than they
 * should be able to. If this event is not cancelled, the player will be reset
 * to the last calculated coordinates if the moved distance is greater than the
 * allowed distance. You can change the moved distance (that is squared,
 * however) or the allowed distance (which is not squared!) if you want the
 * player to be allowed to move further.
 */
public final class ResetMovedPlayerEvent extends Event {

    public final AbstractEntityPlayer player;
    public final double lastCalcX;
    public final double lastCalcY;
    public final int fallingTicks;
    public final int climbingTicks;

    public double distanceSqMoved;
    public double allowedDefaultDistance;

    public ResetMovedPlayerEvent(AbstractEntityPlayer player, double lastCalcX, double lastCalcY, int fallingTicks, int climbingTicks, double distanceSqMoved, double allowedDefaultDistance) {
        this.player = player;
        this.lastCalcX = lastCalcX;
        this.lastCalcY = lastCalcY;
        this.fallingTicks = fallingTicks;
        this.climbingTicks = climbingTicks;
        this.distanceSqMoved = distanceSqMoved;
        this.allowedDefaultDistance = allowedDefaultDistance;
    }
}
