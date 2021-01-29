/*
 * This file ("MakeCameraCoordsEvent.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.event.Event;

/**
 * This event is fired every tick when the renderer decides what to center the
 * camera on. By default, it gets centered on the {@link
 * IGameInstance#getPlayer()}'s position, but you can change the values using
 * this event. It cannot be cancelled.
 */
public final class MakeCameraCoordsEvent extends Event {

    public final AbstractPlayerEntity player;
    public double cameraX;
    public double cameraY;
    public final boolean isCameraMode;

    public MakeCameraCoordsEvent(AbstractPlayerEntity player, double cameraX, double cameraY, boolean isCameraMode) {
        this.player = player;
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.isCameraMode = isCameraMode;
    }
}
