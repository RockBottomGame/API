/*
 * This file ("Constants.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.INetHandler;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.UUID;

/**
 * This class houses a list of constant values that are used by the game to do
 * different things. It might be useful to have access to these values for
 * modders.
 */
public final class Constants{

    /**
     * The seperator for any resources, game or mod based.
     *
     * @see ResourceName
     */
    public static final String RESOURCE_SEPARATOR = "/";

    /**
     * The amount of ticks that a second should have
     */
    public static final int TARGET_TPS = 40;
    /**
     * The amount of random tile updates that should take place per tick
     *
     * @see Tile#updateRandomly(IWorld, int, int, TileLayer)
     */
    public static final int RANDOM_TILE_UPDATES = 5;

    /**
     * The amount of tiles that there are, horizontally and vertically, in a
     * chunk
     */
    public static final int CHUNK_SIZE = 32;
    /**
     * The maximum light value that any given position in the world can have
     *
     * @see IWorld#getSkyLight(int, int)
     * @see IWorld#getArtificialLight(int, int)
     * @see IWorld#getCombinedLight(int, int)
     */
    public static final byte MAX_LIGHT = 30;

    /**
     * The amount of ticks that a full day in the world should have
     */
    public static final int TIME_PER_DAY = 24000;

    /**
     * The amount of chunks in each direction (the radius) that any given player
     * should load in the world
     *
     * @see IWorld#isChunkLoaded(int, int)
     * @see IWorld#isPosLoaded(int, int)
     */
    public static final int CHUNK_LOAD_DISTANCE = 3;
    /**
     * The amount of chunks in each direction (the radius) that should be
     * persistent surrounding the chunk at 0, 0.
     *
     * @see IChunk#isConstantlyPersistent()
     */
    public static final int PERSISTENT_CHUNK_DISTANCE = 1;
    /**
     * The amount of time that a chunk should stay loaded at minimum. This is a
     * buffer timer in case players rapidly move around on a chunk border to
     * avoid lag. Additionally, this reduces performance loss with entities or
     * tiles accidentally loading neighboring chunks.
     */
    public static final int CHUNK_LOAD_TIME = 250;

    /**
     * The permission level that the server admin (the host of the local server)
     * should have by default.
     *
     * @see INetHandler#getCommandLevel(AbstractEntityPlayer)
     * @see INetHandler#setCommandLevel(UUID, int)
     */
    public static final int ADMIN_PERMISSION = 10;

    /**
     * This is a link to the changelog file that the update gui gets its data
     * from on startup
     */
    public static final String UPDATE_LINK = "https://raw.githubusercontent.com/RockBottomGame/Changelog/master/changelog.json";
    /**
     * This is a link to the website of the author of the game, Ellpeck
     */
    public static final String ELLPECK_LINK = "https://ellpeck.de";
    /**
     * This is a link to the website of the game
     */
    public static final String WEBSITE_LINK = "https://rockbottom.ellpeck.de";
}
