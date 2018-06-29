/*
 * This file ("InteractionEvent.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;

/**
 * This event is fired when an {@link AbstractEntityPlayer} interacts with the
 * world in any way. With the default keybinds, this happens when they click the
 * right mouse button. Changing any of the non-final values will result in the
 * point and information of interaction being changed. Cancelling the event will
 * result in no interaction taking place.
 */
public final class InteractionEvent extends Event {

    public final AbstractEntityPlayer player;
    public List<Entity> entities;
    public TileLayer layer;
    public int x;
    public int y;
    public double mouseX;
    public double mouseY;

    public InteractionEvent(AbstractEntityPlayer player, List<Entity> entities, TileLayer layer, int x, int y, double mouseX, double mouseY) {
        this.player = player;
        this.entities = entities;
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
}
