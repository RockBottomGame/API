/*
 * This file ("ItemPickupEvent.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.item.ItemInstance;

/**
 * This event is fired when an {@link AbstractEntityItem} is being picked up by
 * an {@link AbstractEntityPlayer}. Note that this event is fired before a check
 * is done to see if there is enough space for the item. Cancelling the event
 * will make the item not be picked up.
 */
public final class ItemPickupEvent extends Event {

    public final AbstractEntityPlayer player;
    public final AbstractEntityItem item;
    public ItemInstance instance;

    public ItemPickupEvent(AbstractEntityPlayer player, AbstractEntityItem item, ItemInstance instance) {
        this.player = player;
        this.item = item;
        this.instance = instance;
    }
}
