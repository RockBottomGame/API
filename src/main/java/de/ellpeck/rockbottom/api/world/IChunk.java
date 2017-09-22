/*
 * This file ("IChunk.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.world;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.MutableInt;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The world is split up into parts with the dimensions
 * {@link de.ellpeck.rockbottom.api.Constants#CHUNK_SIZE}, {@link de.ellpeck.rockbottom.api.Constants#CHUNK_SIZE},
 * these parts are what are known as chunks.
 * <br> Every chunk is independent of one another and so they are being loaded and unloaded
 * when the player leaves their range to save on memory and to allow for the world to be infinte.
 * <br> Use this interface to interact with chunks
 */
public interface IChunk extends IChunkOrWorld{

    /**
     * @return The players that are in loading range of the chunk
     */
    List<AbstractEntityPlayer> getPlayersInRange();

    /**
     * @return The players that were in range of the chunk but have left it
     * @see #getLeftPlayerTimers()
     */
    List<AbstractEntityPlayer> getPlayersLeftRange();

    /**
     * @return The timers of the players that have left the range of the chunk
     * @see #getPlayersLeftRange()
     */
    Map<AbstractEntityPlayer, MutableInt> getLeftPlayerTimers();

    /**
     * Gets the chunk's grid x coordinate
     * <br> This is different from {@link #getX()} in that it
     * returns the grid coordinate of the chunk, meaning a chunk
     * at grid coordinates {@code 1}, {@code 1} would start at tile coordinates
     * {@link de.ellpeck.rockbottom.api.Constants#CHUNK_SIZE}, {@link de.ellpeck.rockbottom.api.Constants#CHUNK_SIZE}
     *
     * @return The x coordinate
     */
    int getGridX();

    /**
     * Gets the chunk's grid y coordinate
     * <br> This is different from {@link #getX()} in that it
     * returns the grid coordinate of the chunk, meaning a chunk
     * at grid coordinates {@code 1}, {@code 1} would start at tile coordinates
     * {@link de.ellpeck.rockbottom.api.Constants#CHUNK_SIZE}, {@link de.ellpeck.rockbottom.api.Constants#CHUNK_SIZE}
     *
     * @return The y coordinate
     */
    int getGridY();

    /**
     * Gets the {@link IWorld} that this chunk is in
     *
     * @return The world
     */
    IWorld getWorld();

    /**
     * Gets the chunk's tile x coordinate
     * <br> This is different from {@link #getGridX()} in that it
     * returns the coordinate of the chunk based on its tile coordinates, meaning a chunk
     * that contains tile coordinates {@code 1}, {@code 1} would be at grid coordinates
     * {@code 0}, {@code 0}
     *
     * @return The x coordinate
     */
    int getX();

    /**
     * Gets the chunk's tile y coordinate
     * <br> This is different from {@link #getGridX()} in that it
     * returns the coordinate of the chunk based on its tile coordinates, meaning a chunk
     * that contains tile coordinates {@code 1}, {@code 1} would be at grid coordinates
     * {@code 0}, {@code 0}
     *
     * @return The y coordinate
     */
    int getY();

    int getLowestAirUpwardsInner(TileLayer layer, int x, int y);

    TileState getStateInner(TileLayer layer, int x, int y);

    TileState getStateInner(int x, int y);

    void setStateInner(int x, int y, TileState tile);

    /**
     * Sets a tile at the specified coordinates inside of the chunk rather
     * than the tile coordinates in the world
     *
     * @param layer The layer
     * @param x     The x coordinate
     * @param y     The y coordinate
     */
    void setStateInner(TileLayer layer, int x, int y, TileState tile);

    /**
     * Gets the skylight at the specified coordinates inside of the chunk rather
     * than the tile coordinates in the world
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The skylight
     */
    byte getSkylightInner(int x, int y);

    /**
     * Sets the skylight at the specified coordinates inside of the chunk rather
     * than the tile coordinates in the world
     *
     * @param x     The x coordinate
     * @param y     The y coordinate
     * @param light The skylight
     */
    void setSkylightInner(int x, int y, byte light);

    /**
     * Gets the artificial light at the specified coordinates inside of the chunk rather
     * than the tile coordinates in the world
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The artificial light
     */
    byte getArtificialLightInner(int x, int y);

    /**
     * Sets the artificial light at the specified coordinates inside of the chunk rather
     * than the tile coordinates in the world
     *
     * @param x     The x coordinate
     * @param y     The y coordinate
     * @param light The artificial light
     */
    void setArtificialLightInner(int x, int y, byte light);

    /**
     * Puts the chunk into generating mode which means that it will not call
     * {@link Tile#onChangeAround(IWorld, int, int, TileLayer, int, int, TileLayer)}
     * or be marked dirty if anything changes
     *
     * @param generating If it is generating
     */
    void setGenerating(boolean generating);

    /**
     * @return If the chunk needs to be saved to disk
     */
    boolean needsSave();

    /**
     * @return If the chunk is ready to unload
     */
    boolean shouldUnload();

    /**
     * Saves the chunk to the given {@link DataSet}
     *
     * @param set The set
     */
    void save(DataSet set);

    /**
     * Updates the chunk
     * <br> This is not supposed to be used by mods
     *
     * @param game The chunk
     */
    void update(IGameInstance game);

    /**
     * Gets the combined light at the specified coordinates inside of the chunk rather
     * than the tile coordinates in the world
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The combined light
     */
    byte getCombinedLightInner(int x, int y);

    /**
     * @return The amount of currently scheduled updates
     * @see #scheduleUpdate(int, int, TileLayer, int)
     */
    int getScheduledUpdateAmount();

    Biome getBiomeInner(int x, int y);

    void setBiomeInner(int x, int y, Biome biome);

    Set<TileLayer> getLoadedLayers();
}
