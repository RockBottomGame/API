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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api;

import de.ellpeck.rockbottom.api.tile.TileTallPlant;
import de.ellpeck.rockbottom.api.tile.state.BoolProp;
import de.ellpeck.rockbottom.api.tile.state.EnumProp;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileProp;

/**
 * A list of static {@link TileProp} objects that are used for certain tiles
 * from the game. Use these to interact with vanilla tiles from {@link
 * GameContent} to see their values. Every prop in here has a list of tiles that
 * it applies to.
 */
public final class StaticTileProps {

    /**
     * Determines if a tile is naturally generated or placed manually. Used by
     * {@link GameContent#TILE_LEAVES}
     */
    public static final BoolProp NATURAL = new BoolProp("natural", true);
    /**
     * Determines which direction a torch is facing. Used by {@link
     * GameContent#TILE_TORCH} and {@link GameContent#TILE_GRASS_TORCH}
     */
    public static final IntProp TORCH_FACING = new IntProp("facing", 0, 4);
    /**
     * Determines how long it will take for a torch to turn off. Used by {@link
     * GameContent#TILE_GRASS_TORCH}
     */
    public static final IntProp TORCH_TIMER = new IntProp("timer", 0, 10);
    /**
     * Determines which type of variation a log tile is. Used by {@link
     * GameContent#TILE_LOG}
     */
    public static final EnumProp<LogType> LOG_VARIANT = new EnumProp<>("variant", LogType.PLACED, LogType.class);
    /**
     * Determines how far grown a sapling is. Used by {@link
     * GameContent#TILE_SAPLING}
     */
    public static final IntProp SAPLING_GROWTH = new IntProp("growth", 0, 5);
    /**
     * Determines how far grown a plant is. Used by {@link TileTallPlant}
     */
    public static final IntProp PLANT_GROWTH = new IntProp("growth", 0, 10);
    /**
     * Determines if a plant is considered alive.
     * Used by {@link GameContent#TILE_COTTON}
     */
    public static final BoolProp ALIVE = new BoolProp("alive", false);
    /**
     * Determines how much progress a spinning wheel has made.
     */
    public static final IntProp SPINNING_STAGE = new IntProp("spinning_stage", 0, 8);
    /**
     * Determines if a tile is the top or bottom half of a door. Used by {@link
     * GameContent#TILE_WOOD_DOOR}, {@link GameContent#TILE_WOOD_DOOR_OLD} and
     * {@link GameContent#TILE_CORN}
     */
    public static final BoolProp TOP_HALF = new BoolProp("top_half", false);
    /**
     * Determines if a tile is opened. Used by {@link GameContent#TILE_WOOD_DOOR}
     * and {@link GameContent#TILE_WOOD_DOOR_OLD}
     */
    public static final BoolProp OPEN = new BoolProp("open", false);
    /**
     * Determines if a tile is facing right (rather than left). Used by {@link
     * GameContent#TILE_WOOD_DOOR} and {@link GameContent#TILE_WOOD_DOOR_OLD}
     */
    public static final BoolProp FACING_RIGHT = new BoolProp("facing_right", false);
    /**
     * Determines if a tile has a copper canister inside of it. Used by {@link
     * GameContent#TILE_COPPER}
     */
    public static final BoolProp HAS_CANISTER = new BoolProp("has_canister", false);
    /**
     * Determines how far grown a sapling is. Used by {@link
     * GameContent#TILE_STARDROP}
     */
    public static final IntProp STARDROP_GROWTH = new IntProp("growth", 0, 3);
    /**
     * Determines if a tile has ladder inside of it. Used by {@link
     * GameContent#TILE_PLATFORM}
     */
    public static final BoolProp HAS_LADDER = new BoolProp("has_ladder", false);
    /**
     * Determines which corners of a tile have been chiseled.
     * The chiseled corner is represented by the bit of the 16 different values.
     * Bit 0 - Top Left
     * Bit 1 - Top Right
     * Bit 2 - Bottom Left
     * Bit 3 - Bottom Right
     */
    public static final IntProp CHISEL_STATE = new IntProp("chisel_state", 0, 15);

    /**
     * An enum of log types that are used for the {@link #LOG_VARIANT}
     * property.
     */
    public enum LogType {
        PLACED,
        BRANCH_LEFT,
        BRANCH_RIGHT,
        TRUNK_TOP,
        TRUNK_MIDDLE,
        TRUNK_BOTTOM,
        ROOT_LEFT,
        ROOT_RIGHT;

        /**
         * @return If a certain log type is considered to be natural, meaning
         * not player-placed
         */
        public boolean isNatural() {
            return this != PLACED;
        }
    }
}
