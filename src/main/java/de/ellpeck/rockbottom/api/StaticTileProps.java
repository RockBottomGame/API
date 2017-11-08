/*
 * This file ("StaticTileProps.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api;

import de.ellpeck.rockbottom.api.tile.state.BoolProp;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileProp;

/**
 * A list of static {@link TileProp} objects that are used for certain tiles
 * from the game. Use these to interact with vanilla tiles from {@link
 * GameContent} to see their values. Every prop in here has a list of tiles that
 * it applies to.
 */
public final class StaticTileProps{

    /**
     * Determines if a tile is naturally generated or placed manually. Used by
     * {@link GameContent#TILE_LOG} and {@link GameContent#TILE_LEAVES}
     */
    public static final BoolProp NATURAL = new BoolProp("natural", true);
    /**
     * Determines which direction a torch is facing. Used by {@link
     * GameContent#TILE_TORCH}
     */
    public static final IntProp TORCH_FACING = new IntProp("facing", 0, 4);

}
