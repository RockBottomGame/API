/*
 * This file ("IChunkOrWorld.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * This is implemented by both {@link IWorld} and {@link IChunk}
 */
public interface IChunkOrWorld{

    TileState getState(int x, int y);

    TileState getState(TileLayer layer, int x, int y);

    void setState(int x, int y, TileState tile);

    void setState(TileLayer layer, int x, int y, TileState tile);

    /**
     * Adds the specified {@link Entity} to the world
     *
     * @param entity The entity
     */
    void addEntity(Entity entity);

    /**
     * Adds the specified {@link TileEntity} to the world
     *
     * @param tile The tileentity
     */
    void addTileEntity(TileEntity tile);

    /**
     * Removes the specified {@link Entity} from the world
     *
     * @param entity The entity
     */
    void removeEntity(Entity entity);

    /**
     * Removes the {@link TileEntity} at the specified coordinates from the world
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    void removeTileEntity(TileLayer layer, int x, int y);

    /**
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The {@link TileEntity} at the specified coordinates
     */
    TileEntity getTileEntity(TileLayer layer, int x, int y);

    TileEntity getTileEntity(int x, int y);

    /**
     * Gets a {@link TileEntity} of a certain type at the specified coordinates
     *
     * @param x         The x coordinate
     * @param y         The y coordinate
     * @param tileClass The tileentity class
     * @param <T>       The type
     * @return The tileentity of the type, or {@code null} if there is none or it is not the right type
     */
    <T extends TileEntity> T getTileEntity(TileLayer layer, int x, int y, Class<T> tileClass);

    <T extends TileEntity> T getTileEntity(int x, int y, Class<T> tileClass);

    /**
     * @return A list of all {@link Entity} in the world
     */
    List<Entity> getAllEntities();

    /**
     * @return A list of all {@link TileEntity} in the world
     */
    List<TileEntity> getAllTileEntities();

    /**
     * Gets an {@link Entity} with the specified {@link UUID}
     *
     * @param id The id
     * @return The entity
     */
    Entity getEntity(UUID id);

    /**
     * Gets a list of all {@link Entity} in the specified {@link BoundBox}
     *
     * @param area The bounding box
     * @return The list
     */
    List<Entity> getEntities(BoundBox area);

    /**
     * Gets a list of all {@link Entity} in the specified {@link BoundBox}
     * that pass the specified {@link Predicate}
     *
     * @param area The bounding box
     * @param test The test to pass
     * @return The list
     */
    List<Entity> getEntities(BoundBox area, Predicate<Entity> test);

    /**
     * Gets a list of all {@link Entity} in the specified {@link BoundBox} that are instances of
     * or extend the specified {@link Class}
     *
     * @param area The bounding box
     * @param type The type class
     * @return The list
     */
    <T extends Entity> List<T> getEntities(BoundBox area, Class<T> type);

    /**
     * Gets a list of all {@link Entity} in the specified {@link BoundBox} that are instances of
     * or extend the specified {@link Class} and pass the specified {@link Predicate}
     *
     * @param area The bounding box
     * @param type The type class
     * @param test The test to pass
     * @return The list
     */
    <T extends Entity> List<T> getEntities(BoundBox area, Class<T> type, Predicate<T> test);

    /**
     * Gets a combination of {@link #getSkyLight(int, int)}, {@link #getArtificialLight(int, int)}
     * and the time of day of the world for the given coordinate to determine the light at which things
     * are displayed
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The light
     */
    byte getCombinedLight(int x, int y);

    /**
     * Gets the sky light at the given coordinates
     * <br> This is light caused by there being no blocks obstructing the background
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The light
     */
    byte getSkyLight(int x, int y);

    /**
     * Gets the artifical light at the given coordinates
     * <br> This can be things like torches and furnaces that emit light
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The light
     */
    byte getArtificialLight(int x, int y);

    /**
     * Sets the sky light at the given coordinates to the given amount
     * <br> This is light caused by there being no blocks obstructing the background
     * <br> This is not supposed to be used by mods
     *
     * @param x     The x coordinate
     * @param y     The y coordinate
     * @param light The light
     */
    void setSkyLight(int x, int y, byte light);

    /**
     * Sets the artifical light at the given coordinates to the given amount
     * <br> This can be things like torches and furnaces that emit light
     * <br> This is not supposed to be used by mods
     *
     * @param x     The x coordinate
     * @param y     The y coordinate
     * @param light The light
     */
    void setArtificialLight(int x, int y, byte light);

    /**
     * Schedules an update in the world that will cause {@link Tile#onScheduledUpdate(IWorld, int, int, TileLayer)}
     * to be called for the tile at the specified coordinates after the specified time has run out
     * <br> Note: {@link Tile#onScheduledUpdate(IWorld, int, int, TileLayer)} will be called on the server and
     * on the client, even when only scheduling an update on the server
     *
     * @param x     The x coordinate
     * @param y     The y coordinate
     * @param layer The layer
     * @param time  The time in ticks
     */
    void scheduleUpdate(int x, int y, TileLayer layer, int time);

    /**
     * Tells the world or chunk that it needs to be saved
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    void setDirty(int x, int y);

    /**
     * Gets the lowest position from the specified y coordiante upwards at the specified
     * x coordiante that contains air
     * <br> Is used to determine the x coordiante of the world's spawn point
     *
     * @param layer The layer
     * @param x     The x coordinate
     * @param y     The y coordinate
     * @return The lowest y coordinate upwards
     */
    int getLowestAirUpwards(TileLayer layer, int x, int y);

    int getLowestAirUpwards(TileLayer layer, int x, int y, boolean ignoreReplaceableTiles);

    Biome getBiome(int x, int y);

    void setBiome(int x, int y, Biome biome);

    boolean isClient();

    boolean isServer();

    void callRetroactiveGeneration();
}
