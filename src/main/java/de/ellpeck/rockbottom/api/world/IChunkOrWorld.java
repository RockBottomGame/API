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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.world;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.IAdditionalDataProvider;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.net.INetHandler;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * This class represents a chunk or world. It is special due to two classes
 * extending it that have slightly different behavior. As such, whenever the
 * word {@code world} is mentioned in the documentation of this class, it can
 * also refer to the {@code chunk} that is being accessed when callilng this
 * method. As such, some behavior might be different or unexpected and it might
 * cause crashes if you're not careful with how you use this class.
 * <p>
 * For instance, calling {@link #getState(int, int)} in an {@link IWorld} will
 * always return a valid {@link TileState}, however, calling the same method for
 * an {@link IChunk} will cause a crash if the position is outside of the
 * chunk's boundaries, as calling it from a chunk will still result in
 * world-level coordinates being used. For that reason, {@link IChunk} provides
 * a mulitude of methods like {@link IChunk#getStateInner(int, int)} that will
 * allow you to get properties of a chunk without needing to know its position
 * within the world.
 * <p>
 * To access the game's current world object, use {@link
 * IGameInstance#getWorld()}. See {@link IWorld} and {@link IChunk} for greater
 * documentation on the seperate behavior of chunks and worlds.
 *
 * @see IWorld
 * @see IChunk
 */
public interface IChunkOrWorld extends IAdditionalDataProvider {

    /**
     * Gets the current {@link TileState} at any given position in the world on
     * {@link TileLayer#MAIN}.
     *
     * @param x The x world coordinate
     * @param y The y world coordinate
     * @return The state
     */
    TileState getState(int x, int y);

    /**
     * Gets the current {@link TileState} at any given position in the world on
     * the given layer
     *
     * @param layer The layer
     * @param x     The x world coordinate
     * @param y     The y world coordinate
     * @return The state
     */
    TileState getState(TileLayer layer, int x, int y);

    /**
     * Sets the {@link TileState} at any given position in the world on {@link
     * TileLayer#MAIN} to the given state.
     *
     * @param x    The x world coordinate
     * @param y    The y word coordinate
     * @param tile The state to set
     */
    void setState(int x, int y, TileState tile);

    /**
     * Sets the {@link TileState} at any given position in the world on the
     * given layer to the given state.
     *
     * @param layer The layer
     * @param x     The x world coordinate
     * @param y     The y world coordinate
     * @param tile  The state to set
     */
    void setState(TileLayer layer, int x, int y, TileState tile);

    /**
     * Adds an {@link Entity} to the world for it to be updated ({@link
     * Entity#update(IGameInstance)} and saved and loaded to and from disk. If
     * this operation isn't done, an entity also will not render.
     *
     * @param entity The entity to add
     */
    void addEntity(Entity entity);

    /**
     * Adds a {@link TileEntity} to the world. This method is marked as
     * internal, as using {@link Tile#provideTileEntity(IWorld, int, int,
     * TileLayer)} if {@link Tile#canProvideTileEntity()} is true will make it
     * automatically be added to the world. Do not manually add tile entities to
     * the world.
     *
     * @param tile The tile entity to add
     */
    @ApiInternal
    void addTileEntity(TileEntity tile);

    /**
     * Removes an {@link Entity} from the world.
     *
     * @param entity The entity to remove
     */
    void removeEntity(Entity entity);

    /**
     * Removes a {@link TileEntity} from the world. This method is marked as
     * internal, as using {@link Tile#provideTileEntity(IWorld, int, int,
     * TileLayer)} if {@link Tile#canProvideTileEntity()} is true will make it
     * automatically be removed from the world as the tile is broken. Do not
     * manually remove tile entities from the world.
     *
     * @param layer The layer
     * @param x     The world x
     * @param y     The world y
     */
    @ApiInternal
    void removeTileEntity(TileLayer layer, int x, int y);

    /**
     * Gets the {@link TileEntity} at any given position in the world from the
     * given layer. Note that this will return a generic tile entity. If you
     * want to get a specific type and don't want to do an {@code instanceof}
     * check yourself, you can use {@link #getTileEntity(TileLayer, int, int,
     * Class)}.
     *
     * @param layer The layer
     * @param x     The world x
     * @param y     The world y
     * @return The tile entity at that position, or null if there is none
     * @see #getTileEntity(TileLayer, int, int, Class)
     */
    TileEntity getTileEntity(TileLayer layer, int x, int y);

    /**
     * Gets the {@link TileEntity} at any given position in the world from
     * {@link TileLayer#MAIN}. Note that this will return a generic tile entity.
     * If you want to get a specific type and don't want to do an {@code
     * instanceof} check yourself, you can use {@link #getTileEntity(int, int,
     * Class)}.
     *
     * @param x The world x
     * @param y The world y
     * @return The tile entity at that position, or null if there is none
     * @see #getTileEntity(int, int, Class)
     */
    TileEntity getTileEntity(int x, int y);

    /**
     * Gets the {@link TileEntity} at any given position in the world from the
     * given layer. If the tile entity at that position is either null or it
     * does not have the given class as a super class, this method will return
     * null.
     *
     * @param layer     The layer
     * @param x         The world x
     * @param y         The world y
     * @param tileClass The class that the tile entity should be extending or an
     *                  object of
     * @param <T>       The generic type representing the type of tile entity
     *                  that will be returned
     * @return The tile entity at that position, or null if there is none or it
     * is not of the provided type
     */
    <T extends TileEntity> T getTileEntity(TileLayer layer, int x, int y, Class<T> tileClass);

    /**
     * Gets the {@link TileEntity} at any given position in the world from
     * {@link TileLayer#MAIN}. If the tile entity at that position is either
     * null or it does not have the given class as a super class, this method
     * will return null.
     *
     * @param x         The world x
     * @param y         The world y
     * @param tileClass The class that the tile entity should be extending or an
     *                  object of
     * @param <T>       The generic type representing the type of tile entity
     *                  that will be returned
     * @return The tile entity at that position, or null if there is none or it
     * is not of the provided type
     */
    <T extends TileEntity> T getTileEntity(int x, int y, Class<T> tileClass);

    /**
     * Reevaluates if a {@link TileEntity} should be in the list of ticking tile
     * entities, and should, as such, be updated every tick or not. This method
     * re-calls {@link TileEntity#doesTick()} and adds or removes it from or to
     * the list of ticking tile entities as needed. Note that this very action
     * is automatically done when adding a tile entity to the world and this
     * method should not be needed to be called unless you want your tile entity
     * to have very specific ticking and performance behavior.
     *
     * @param tile The tile entitiy to reevaluate the ticking behavior for
     * @see TileEntity#doesTick()
     */
    void reevaluateTickBehavior(TileEntity tile);

    /**
     * Gets a list of all of the {@link Entity} objects in the world that are
     * currently within loaded chunks. Note that directly adding or removing to
     * or from this list will throw an {@link UnsupportedOperationException}.
     *
     * @return All entities
     * @see #addEntity(Entity)
     * @see #removeEntity(Entity)
     */
    List<Entity> getAllEntities();

    /**
     * Gets a list of all of the {@link TileEntity} objects in the world that
     * are currently within loaded chunks. Note that directly adding or removing
     * to or from this list will throw an {@link UnsupportedOperationException}.
     *
     * @return All tile entites
     * @see #addTileEntity(TileEntity)
     * @see #removeTileEntity(TileLayer, int, int)
     */
    List<TileEntity> getAllTileEntities();

    /**
     * Gets a list of all of the {@link TileEntity} objects in the world that
     * were marked as {@link TileEntity#doesTick()} at the point of being added
     * to the world. Note that directly adding or removing to or from this list
     * will throw an {@link UnsupportedOperationException}.
     *
     * @return All ticking tile entities
     * @see #addTileEntity(TileEntity)
     * @see #removeTileEntity(TileLayer, int, int)
     */
    List<TileEntity> getAllTickingTileEntities();

    /**
     * Gets an {@link Entity} that is currently in the world by its {@link
     * UUID}.
     *
     * @param id The unique id
     * @return The entity, or null if there is none
     * @see Entity#getUniqueId()
     */
    Entity getEntity(UUID id);

    /**
     * Gets a list of all of the {@link Entity} objects that are currently in
     * the given {@link BoundBox}.
     *
     * @param area The area to check for entities
     * @return All of the entities
     */
    List<Entity> getEntities(BoundBox area);

    /**
     * Gets a list of all of the {@link Entity} objects that are currently in
     * the given {@link BoundBox} and for which the given {@link Predicate}
     * applies.
     *
     * @param area The area to check for entities
     * @param test The predicate that needs to apply for them to be added to the
     *             list
     * @return All of the entities
     */
    List<Entity> getEntities(BoundBox area, Predicate<Entity> test);

    /**
     * Gets a list of all of the {@link Entity} objects that are currently in
     * the given {@link BoundBox} that also are objects of or whose classes
     * extend the given {@link Class}.
     *
     * @param area The area to check for entities
     * @param type The type that they need to be
     * @param <T>  A generic type representing the type of entities that are
     *             being looked for
     * @return All of the entities
     */
    <T extends Entity> List<T> getEntities(BoundBox area, Class<T> type);

    /**
     * Gets a list of all of the {@link Entity} objects that are currently in
     * the given {@link BoundBox} that also are objects of or whose classes
     * extend the given {@link Class} and for which the given {@link Predicate}
     * applies.
     *
     * @param area The area to check for entities
     * @param type The type that they need to be
     * @param test The predicate that needs to apply for them to be added to the
     *             list
     * @param <T>  A generic type representing the type of entities that are
     *             being looked for
     * @return All of the entities
     */
    <T extends Entity> List<T> getEntities(BoundBox area, Class<T> type, Predicate<T> test);

    /**
     * Gets the combined light at the given position in the world. The combined
     * light is a combination of {@link #getSkyLight(int, int)}, {@link
     * #getArtificialLight(int, int)} and the given {@link
     * IWorld#getCurrentTime()}.
     *
     * @param x The world x coordinate
     * @param y The world y coordinate
     * @return The light at the given position
     */
    byte getCombinedLight(int x, int y);

    /**
     * Gets the sky light at the given position in the world.
     *
     * @param x The world x coordinate
     * @param y The world y coordinate
     * @return The sky light
     */
    byte getSkyLight(int x, int y);

    /**
     * Gets the artificial light, meaning the light that is generated by tiles,
     * at the given position in the world.
     *
     * @param x The world x coordinate
     * @param y The world y coordinate
     * @return The artificial light
     */
    byte getArtificialLight(int x, int y);

    /**
     * Set the sky light at the given position in the world to the given value.
     * This method is marked as internal as you should use {@link
     * Tile#getLight(IWorld, int, int, TileLayer)} to actually provide light to
     * the world. If you just set light to any given position in the world, it
     * will cause it to be uneven and non-dynamic.
     *
     * @param x     The world x coordinate
     * @param y     The world y coordinate
     * @param light The light
     */
    @ApiInternal
    void setSkyLight(int x, int y, byte light);

    /**
     * Set the artificial light at the given position in the world to the given
     * value. This method is marked as internal as you should use {@link
     * Tile#getLight(IWorld, int, int, TileLayer)} to actually provide light to
     * the world. If you just set light to any given position in the world, it
     * will cause it to be uneven and non-dynamic.
     *
     * @param x     The world x coordinate
     * @param y     The world y coordinate
     * @param light The light
     */
    @ApiInternal
    void setArtificialLight(int x, int y, byte light);

    /**
     * Schedules an update to be done after the given time has passed at the
     * given position with the given metadata. After the time has passed, the
     * {@link Tile#onScheduledUpdate(IWorld, int, int, TileLayer, int)} method
     * of the tile at the given position will be called. Note that scheduled
     * updates get saved when the chunk gets saved, so that, when reloading the
     * world, a scheduled update whose time has not yet run out will continue to
     * tick down its timer.
     * <p>
     * Note that calling this method when the tile at the passed position is not
     * your own tile can cause unexpected behavior and is, as such, highly
     * discouraged.
     *
     * @param x             The world x coordinate
     * @param y             The world y coordinate
     * @param layer         The layer
     * @param scheduledMeta The metadata to pass to the tile's method to
     *                      distinguish between different kinds of updates
     * @param time          The time it should take in ticks for the update to
     *                      happen
     * @see Tile#onScheduledUpdate(IWorld, int, int, TileLayer, int)
     */
    void scheduleUpdate(int x, int y, TileLayer layer, int scheduledMeta, int time);

    /**
     * Schedules an update to be done after the given time has passed at the
     * given position. After the time has passed, the {@link
     * Tile#onScheduledUpdate(IWorld, int, int, TileLayer, int)} method of the
     * tile at the given position will be called. Note that scheduled updates
     * get saved when the chunk gets saved, so that, when reloading the world, a
     * scheduled update whose time has not yet run out will continue to tick
     * down its timer.
     * <p>
     * Note that calling this method when the tile at the passed position is not
     * your own tile can cause unexpected behavior and is, as such, highly
     * discouraged.
     *
     * @param x     The world x coordinate
     * @param y     The world y coordinate
     * @param layer The layer
     * @param time  The time it should take in ticks for the update to happen
     * @see Tile#onScheduledUpdate(IWorld, int, int, TileLayer, int)
     */
    void scheduleUpdate(int x, int y, TileLayer layer, int time);

    /**
     * Sets the chunk at the given world position dirty, meaning that it will be
     * guaranteed to be saved to disk the next time the world will be saved.
     *
     * @param x The world x coordinate
     * @param y The world y coordinate
     */
    void setDirty(int x, int y);

    /**
     * Gets the height of the chunk at the given position. First of all, the
     * chunk at the given position is queried and then the height of the terrain
     * at the given x coordinate is returned. Note that {@code bottomY} does not
     * have any direct influence on the actual height returned but that it is
     * merely used to get the chunk to query the height of.
     *
     * @param layer   The layer
     * @param x       The world x coordinate
     * @param bottomY The world y coordiate whose chunk should be queried for
     *                the height
     * @return The height of the chunk at the given x coordinate
     */
    int getChunkHeight(TileLayer layer, int x, int bottomY);

    /**
     * Gets the average height of the chunk at the given position. First of all,
     * the chunk at the given position is queried and then the average hight of
     * the chunk at that position is returned. Note that neither {@code x} nor
     * {@code bottomY} have a direct influence on the actual flatness of the
     * position, just the chunk at the position.
     *
     * @param layer   The layer
     * @param x       The world x coordinate
     * @param bottomY The world y coordiate whose chunk should be queried for
     *                the height
     * @return The height of the chunk at the given x coordinate
     */
    int getAverageChunkHeight(TileLayer layer, int x, int bottomY);

    /**
     * Gets the flatness of the chunk at the given position. First of all, the
     * chunk at the given position is queried and then the flatness of the chunk
     * at that position is returned. Note that neither {@code x} nor {@code y}
     * have a direct influence on the actual flatness of the position, just the
     * chunk at the position.
     *
     * @param layer The layer
     * @param x     The world x coordinate of the chunk to query
     * @param y     The world y coordinate of the chunk to query
     * @return The flatness of the chunk. This will be a number between 0 and 1,
     * where 1 means that all of the heights in the chunk are the same, and 0
     * means that none of the heights in the chunk are the same.
     */
    float getChunkFlatness(TileLayer layer, int x, int y);

    /**
     * Gets the {@link Biome} in the world at the given position.
     *
     * @param x The world x coordinate
     * @param y The world y coordinate
     * @return The biome
     * @see #getExpectedBiome(int, int)
     */
    Biome getBiome(int x, int y);

    /**
     * Sets the {@link Biome} in the world at the given position to the given
     * biome. This method is marked as internal as biomes should only be set by
     * their generation rather than directly. If you add a biome, you should use
     * its class' methods to determine where the biome should spawn in the
     * world.
     *
     * @param x     The world x coordinate
     * @param y     The world y coordinate
     * @param biome The biome to set
     */
    @ApiInternal
    void setBiome(int x, int y, Biome biome);

    /**
     * @return If the game is currently in client mode
     * @see INetHandler#isClient()
     */
    boolean isClient();

    /**
     * @return If the game is currently in server mode
     * @see INetHandler#isServer()
     */
    boolean isServer();

    /**
     * @return If the game is acting as a dedicated server
     * @see IGameInstance#isDedicatedServer()
     */
    boolean isDedicatedServer();

    /**
     * Returns true if the provided entity is the game's local player
     *
     * @param entity The entity
     * @return If the entity is the local player
     * @see INetHandler#isThePlayer(Entity)
     */
    boolean isLocalPlayer(Entity entity);

    /**
     * This method calls all of the {@link IWorldGenerator} objects that are
     * marked as {@link IWorldGenerator#generatesRetroactively()} for
     * retroactive generation, meaning their {@link IWorldGenerator#generate(IWorld,
     * IChunk)} methods will be called again. Note that most retroactive
     * generators can only generate once, so calling this method should
     * generally have little impact.
     * <p>
     * Note that retroactive generation is automatically done when a {@link
     * IChunk} is loaded from disk.
     */
    void callRetroactiveGeneration();

    /**
     * @return The seed of this world with which to seed {@link Random}
     * generators and {@link INoiseGen} objects for world generation. The seed
     * of the world is persistent, meaning that two worlds with the same seed
     * should also have the exact same terrain generation.
     */
    long getSeed();

    /**
     * Gets the {@link Biome} at the given position in the world without loading
     * the chunk at that position. This is useful in that, if you want to see
     * which biome would be at which position in a chunk that might not be
     * loaded or generated yet, you don't have to make it generate just to find
     * out the biome. Using this method over {@link #getBiome(int, int)} during
     * world generation greatly increases performance.
     *
     * @param x The world x coordinate
     * @param y The world y coordinate
     * @return The expected biome for that position
     */
    Biome getExpectedBiome(int x, int y);

    BiomeLevel getExpectedBiomeLevel(int x, int y);

    /**
     * Gets the height at the given position in the world without loading the
     * chunk at that position. The height of the world is calculated
     * independently of biomes or any other world generation features.
     * <p>
     * Note that, if caves or structures generate at any given position, the
     * {@link #getChunkHeight(TileLayer, int, int)} will be different from this
     * method's return value. The same will be true if a player places tiles in
     * the world.
     *
     * @param layer The layer
     * @param x     The world x coordinate
     * @return The expected height for that position
     */
    int getExpectedSurfaceHeight(TileLayer layer, int x);

    /**
     * Gets the average height in the given interval in the world without
     * loading the chunk at any position. The height of the world is calculated
     * independently of biomes or any other world generation features.
     * <p>
     * Note that, if caves or structures generate at any given position, the
     * {@link #getAverageChunkHeight(TileLayer, int, int)} will be different
     * from this method's return value. The same will be true if a player places
     * tiles in the world.
     *
     * @param layer  The layer
     * @param startX The leftmost world x coordinate, inclusive
     * @param endX   The rightmost world x coordinate, exclusive
     * @return The expected average height for that interval
     */
    int getExpectedAverageHeight(TileLayer layer, int startX, int endX);

    /**
     * Gets the flatness in the given interval in the world without loading the
     * chunk at any position. The height of the world is calculated
     * independently of biomes or any other world generation features.
     * <p>
     * Note that, if caves or structures generate at any given position, the
     * {@link #getChunkFlatness(TileLayer, int, int)} will be different from
     * this method's return value. The same will be true if a player places
     * tiles in the world.
     *
     * @param layer  The layer
     * @param startX The leftmost world x coordinate, inclusive
     * @param endX   The rightmost world x coordinate, exclusive
     * @return The flatness in the interval. This will be a number between 0 and
     * 1, where 1 means that all of the heights in the interval are the same,
     * and 0 means that none of the heights in the interval are the same.
     */
    float getExpectedSurfaceFlatness(TileLayer layer, int startX, int endX);
}
