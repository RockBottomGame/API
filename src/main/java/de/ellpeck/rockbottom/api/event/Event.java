/*
 * This file ("Event.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.event;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.impl.BreakEvent;

/**
 * This is the basic class for any kind of event that can be handled and fired
 * using the {@link IEventHandler}.
 * <p>
 * Generally speaking, an event class' variables should all be public and either
 * final or non-final, based on if they should be modifyable or not.
 * <p>
 * As an example of this behavior, the {@link BreakEvent} has a final {@link
 * AbstractEntityPlayer} parameter, as the player that is breaking a tile should
 * not be changed. However, the positions and wether or not the breaking
 * operation was offective are non-final, meaning an event listener is able to
 * change which position a tile is being broken at and wether or not it should
 * drop its item.
 * <p>
 * Keep in mind that, when creating a custom event, you should always query any
 * non-final variables that you pass into the event after firing it to ensure
 * that the outcome of the event actually influences the outcome of your
 * operation.
 */
public class Event{

}
