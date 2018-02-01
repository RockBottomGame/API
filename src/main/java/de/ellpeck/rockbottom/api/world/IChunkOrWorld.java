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

import de.ellpeck.rockbottom.api.data.set.IAdditionalDataProvider;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface IChunkOrWorld extends IAdditionalDataProvider{

    TileState getState(int x, int y);

    TileState getState(TileLayer layer, int x, int y);

    void setState(int x, int y, TileState tile);

    void setState(TileLayer layer, int x, int y, TileState tile);

    void addEntity(Entity entity);

    @ApiInternal
    void addTileEntity(TileEntity tile);

    void removeEntity(Entity entity);

    @ApiInternal
    void removeTileEntity(TileLayer layer, int x, int y);

    TileEntity getTileEntity(TileLayer layer, int x, int y);

    TileEntity getTileEntity(int x, int y);

    <T extends TileEntity> T getTileEntity(TileLayer layer, int x, int y, Class<T> tileClass);

    <T extends TileEntity> T getTileEntity(int x, int y, Class<T> tileClass);

    List<Entity> getAllEntities();

    List<TileEntity> getAllTileEntities();

    Entity getEntity(UUID id);

    List<Entity> getEntities(BoundBox area);

    List<Entity> getEntities(BoundBox area, Predicate<Entity> test);

    <T extends Entity> List<T> getEntities(BoundBox area, Class<T> type);

    <T extends Entity> List<T> getEntities(BoundBox area, Class<T> type, Predicate<T> test);

    byte getCombinedLight(int x, int y);

    byte getSkyLight(int x, int y);

    byte getArtificialLight(int x, int y);

    @ApiInternal
    void setSkyLight(int x, int y, byte light);

    @ApiInternal
    void setArtificialLight(int x, int y, byte light);

    void scheduleUpdate(int x, int y, TileLayer layer, int scheduledMeta, int time);

    void scheduleUpdate(int x, int y, TileLayer layer, int time);

    void setDirty(int x, int y);

    int getLowestAirUpwards(TileLayer layer, int x, int y);

    int getLowestAirUpwards(TileLayer layer, int x, int y, boolean ignoreReplaceableTiles);

    Biome getBiome(int x, int y);

    @ApiInternal
    void setBiome(int x, int y, Biome biome);

    boolean isClient();

    boolean isServer();

    boolean isDedicatedServer();

    boolean hasLocalPlayer();

    boolean isLocalPlayer(Entity entity);

    void callRetroactiveGeneration();

    long getSeed();
}
