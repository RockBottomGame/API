/*
 * This file ("IWorld.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.world;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.IPlayerDesign;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.NameToIndexInfo;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import io.netty.channel.Channel;

import java.util.List;
import java.util.UUID;

/**
 * A world is the area that entities and tiles exist in
 * <br> Use this interface to interact with the world
 */
public interface IWorld extends IChunkOrWorld{

    /**
     * Gets an {@link IChunk} from the specified grid coordinates
     * <br> This is different from {@link #getChunk(double, double)} in that it
     * gets a chunk based on the grid coordinates of the chunk, meaning a chunk
     * at grid coordinates {@code 1}, {@code 1} would start at tile coordinates
     * {@link de.ellpeck.rockbottom.api.Constants#CHUNK_SIZE}, {@link de.ellpeck.rockbottom.api.Constants#CHUNK_SIZE}
     *
     * @param gridX The grid x coordinate
     * @param gridY The grid y coordinate
     * @return The chunk at the specified coordinates
     */
    IChunk getChunkFromGridCoords(int gridX, int gridY);

    /**
     * Gets an {@link IChunk} from the specified tile coordinates
     * <br> This is different from {@link #getChunkFromGridCoords(int, int)} in that it
     * getst a chunk based on the tile coordinates of the chunk, meaning a chunk
     * that contains tile coordinates {@code 1}, {@code 1} would be at grid coordinates
     * {@code 0}, {@code 0}
     *
     * @param x The tile x coordinate
     * @param y The tile y coordinate
     * @return The chunk at the specified coordinates
     */
    IChunk getChunk(double x, double y);

    /**
     * @param x The tile x coordinate
     * @param y The tile y coordinate
     * @return {@code true} if the chunk that contains the specified tile coordinates is loaded, {@code false} otherwise
     */
    boolean isPosLoaded(int x, int y);

    /**
     * @param x The grid x coordinate
     * @param y The grid y coordinate
     * @return {@code true} if the chunk at the specified grid coordinates is loaded, {@code false} otherwise
     */
    boolean isChunkLoaded(int x, int y);

    /**
     * Gets a {@link java.util.ArrayList} of {@link BoundBox} of tiles that are contained by the specified {@link BoundBox}
     *
     * @param area The bound box to check for tiles in
     * @return The collisions of tiles inside the specified area
     */
    List<BoundBox> getCollisions(BoundBox area);

    /**
     * @param tile The tile
     * @return The id that the specified tile is saved as in the world's save files
     */
    int getIdForTile(Tile tile);

    /**
     * @param id The id
     * @return The tile that is saved in the world's save files using the specified id
     */
    Tile getTileForId(int id);

    /**
     * @return Information about which ids tiles are saved with in the world file
     * @see #getIdForTile(Tile)
     * @see #getTileForId(int)
     */
    NameToIndexInfo getTileRegInfo();

    int getIdForBiome(Biome biome);

    Biome getBiomeForId(int id);

    NameToIndexInfo getBiomeRegInfo();

    /**
     * @return Information like the seed and time of the world
     */
    WorldInfo getWorldInfo();

    /**
     * Notifies neighboring tiles that a change or an update has occured, calling their
     * {@link Tile#onChangeAround(IWorld, int, int, TileLayer, int, int, TileLayer)} methods
     *
     * @param x     The x coordinate
     * @param y     The y coordinate
     * @param layer The layer
     */
    void notifyNeighborsOfChange(int x, int y, TileLayer layer);

    /**
     * Creates a player from the given {@link UUID} and adds it to the world
     * <br> Not supposed to be used by mods
     *
     * @param id      The id of the player
     * @param channel A channel if it is a connected player
     * @return The created player
     */
    AbstractEntityPlayer createPlayer(UUID id, IPlayerDesign design, Channel channel);

    /**
     * Gets a player with the specified {@link UUID} from the world
     *
     * @param id The id of the player
     * @return The player, or {@code null} if there is no player with the id
     */
    AbstractEntityPlayer getPlayer(UUID id);

    AbstractEntityPlayer getPlayer(String name);

    /**
     * Helper method to remove a tile, spawn its particles and spawn its drops
     *
     * @param x          The x coordinate
     * @param y          The y coordinate
     * @param layer      The layer
     * @param destroyer  The entity destroying it, or {@code null} if there is none
     * @param shouldDrop If its items should drop
     */
    void destroyTile(int x, int y, TileLayer layer, Entity destroyer, boolean shouldDrop);

    /**
     * @return The spawn coordiante of the world
     */
    int getSpawnX();

    /**
     * Causes a light update at the given position
     * <br> Will recursively calculate light in the surrounding area
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    void causeLightUpdate(int x, int y);

    /**
     * Forces a chunk to unload
     * <br> Not supposed to be used by mods
     *
     * @param chunk The chunk
     */
    void unloadChunk(IChunk chunk);

    /**
     * Saves the specified player to disk
     * <br> Not supposed to be used by mods
     *
     * @param player The player
     */
    void savePlayer(AbstractEntityPlayer player);
}
