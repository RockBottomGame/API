/*
 * This file ("PlayerStatsEvent.java") is part of the RockBottomAPI by Ellpeck.
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
 * This event is fired when any of the provided {@link #player}'s statistics is
 * required queried using {@link AbstractEntityPlayer#getRange()}, {@link
 * AbstractEntityPlayer#getMoveSpeed()} and similar methods. The type of
 * statistic being queried is indicated by the {@link #statType}. Changing the
 * {@link #value} will cause the stat to be changed. The event cannot be
 * cancelled.
 */
public final class PlayerStatsEvent extends Event {

    public final AbstractEntityPlayer player;
    public final StatType statType;
    public double value;

    public PlayerStatsEvent(AbstractEntityPlayer player, StatType statType, double value) {
        this.player = player;
        this.statType = statType;
        this.value = value;
    }

    public enum StatType {
        MOVE_SPEED,
        CLIMB_SPEED,
        JUMP_HEIGHT,
        RANGE
    }
}
