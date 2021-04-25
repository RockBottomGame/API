/*
 * This file ("IWorld.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.render.IPlayerDesign;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.NameToIndexInfo;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import io.netty.channel.Channel;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IWorld extends IChunkOrWorld {

    IChunk getChunkFromGridCoords(int gridX, int gridY);

    IChunk getChunk(double x, double y);

    boolean isPosLoaded(double x, double y);

    boolean isPosLoaded(double x, double y, boolean checkGenerating);

    boolean isChunkLoaded(int x, int y);

    boolean isChunkLoaded(int x, int y, boolean checkGenerating);

    int getWeatherHeight();

    /**
     * The lowest y position that the world will attempt to load to, to find the highest block in the world.
     * @return the lower bound for initial weather tile checks
     */
    default int getMinimumWeatherHeight() {
        return -1000;
    }

    int getHighestTilePos(int x);

    int getIdForState(TileState state);

    TileState getStateForId(int id);

    NameToIndexInfo getTileRegInfo();

    int getIdForBiome(Biome biome);

    Biome getBiomeForId(int id);

    NameToIndexInfo getBiomeRegInfo();

    DynamicRegistryInfo getRegInfo();

    @ApiInternal
    WorldInfo getWorldInfo();

    int getCurrentTime();

    void setCurrentTime(int time);

    int getTotalTime();

    @ApiInternal
    void setTotalTime(int time);

    boolean isTimeFrozen();

    void setTimeFrozen(boolean frozen);

    void notifyNeighborsOfChange(int x, int y, TileLayer layer);

    AbstractPlayerEntity createPlayer(String username, UUID id, IPlayerDesign design, Channel channel, boolean loadOrSwapLast);

    AbstractPlayerEntity getPlayer(UUID id);

    AbstractPlayerEntity getPlayer(String name);

    void destroyTile(int x, int y, TileLayer layer, Entity destroyer, boolean shouldDrop);

    int getSpawnX();

    void causeLightUpdate(int x, int y);

    IWorldGenerator getGenerator(ResourceName name);

    @ApiInternal
    void unloadChunk(IChunk chunk);

    @ApiInternal
    void savePlayer(AbstractPlayerEntity player);

    float getSkylightModifier(boolean doMinMax);

    @ApiInternal
    Map<ResourceName, IWorldGenerator> getAllGenerators();

    @ApiInternal
    List<IWorldGenerator> getSortedLoopingGenerators();

    @ApiInternal
    List<IWorldGenerator> getSortedRetroactiveGenerators();

    @ApiInternal
    void save();

    List<AbstractPlayerEntity> getAllPlayers();

    void removeEntity(Entity entity, IChunk chunk);

    boolean isDaytime();

    boolean isNighttime();

    File getFolder();

    File getPlayerFolder();

    File getChunksFolder();

    String getName();

    void playSound(AbstractPlayerEntity player, ResourceName name, double x, double y, double z, float pitch, float volume);

    void broadcastSound(AbstractPlayerEntity player, ResourceName name, float pitch, float volume);

    void playSound(ResourceName name, double x, double y, double z, float pitch, float volume, AbstractPlayerEntity except);

    void broadcastSound(ResourceName name, float pitch, float volume, AbstractPlayerEntity except);

    void playSound(ResourceName name, double x, double y, double z, float pitch, float volume);

    void broadcastSound(ResourceName name, float pitch, float volume);

    byte getCombinedVisualLight(int x, int y);

    boolean isStoryMode();

    AbstractPlayerEntity getClosestPlayer(double x, double y, AbstractPlayerEntity excluding);

    AbstractPlayerEntity getClosestPlayer(double x, double y);

    @ApiInternal
    void addPlayer(AbstractPlayerEntity player);

    @ApiInternal
    void removePlayer(AbstractPlayerEntity player);

    void travelToSubWorld(Entity entity, ResourceName subWorld, double x, double y);

    IWorld getSubWorld(ResourceName name);

    List<? extends IWorld> getSubWorlds();

    IWorld getMainWorld();

    float getSleepingPercentage();

    ResourceName getSubName();

    @ApiInternal
    void setSubName(ResourceName subName);

    @ApiInternal
    void unloadEverything();
}
