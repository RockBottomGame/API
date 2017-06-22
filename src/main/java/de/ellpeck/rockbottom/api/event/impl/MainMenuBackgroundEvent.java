/*
 * This file ("MainMenuBackgroundEvent.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.tile.Tile;

/**
 * This event is fired when the main menu background tries to place
 * a new {@link Tile} in the background
 * <br> It is not cancellable
 * <br>
 * <br> Note: Only tiles that use {@link de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer}
 * will be able to render in the main menu background
 */
public class MainMenuBackgroundEvent extends Event{

    public Tile tileToPlace;

    public MainMenuBackgroundEvent(Tile tileToPlace){
        this.tileToPlace = tileToPlace;
    }
}
