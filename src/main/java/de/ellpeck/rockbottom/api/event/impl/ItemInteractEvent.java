/*
 * This file ("ItemInteractEvent.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

/**
 * This event is fired when an interaction is taken using an {@link Item} before
 * its {@link Item#onInteractWith(IWorld, int, int, TileLayer, double, double,
 * AbstractEntityPlayer, ItemInstance)} method is called. Cancelling the event
 * will make the interaction not take place.
 */
public final class ItemInteractEvent extends Event{

    public final AbstractEntityPlayer player;
    public final ItemInstance instance;
    public final double x;
    public final double y;

    public ItemInteractEvent(AbstractEntityPlayer player, ItemInstance instance, double x, double y){
        this.player = player;
        this.instance = instance;
        this.x = x;
        this.y = y;
    }
}
