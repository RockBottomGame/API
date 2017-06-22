/*
 * This file ("TileEntityTickEvent.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;

/**
 * This event is fired when a {@link TileEntity} is trying to tick
 * <br> Cancelling the event will cancel the tick
 */
public class TileEntityTickEvent extends Event{

    public final TileEntity tileEntity;

    public TileEntityTickEvent(TileEntity tileEntity){
        this.tileEntity = tileEntity;
    }
}
