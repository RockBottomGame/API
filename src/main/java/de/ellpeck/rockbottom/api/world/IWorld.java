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
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api.world;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.IPlayerDesign;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.util.reg.NameToIndexInfo;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import io.netty.channel.Channel;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface IWorld extends IChunkOrWorld{

    IChunk getChunkFromGridCoords(int gridX, int gridY);

    IChunk getChunk(double x, double y);

    boolean isPosLoaded(int x, int y);

    boolean isChunkLoaded(int x, int y);

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

    int getTotalTime();

    void setCurrentTime(int time);

    @ApiInternal
    void setTotalTime(int time);

    void notifyNeighborsOfChange(int x, int y, TileLayer layer);

    AbstractEntityPlayer createPlayer(UUID id, IPlayerDesign design, Channel channel);

    AbstractEntityPlayer getPlayer(UUID id);

    AbstractEntityPlayer getPlayer(String name);

    void destroyTile(int x, int y, TileLayer layer, Entity destroyer, boolean shouldDrop);

    int getSpawnX();

    void causeLightUpdate(int x, int y);

    @ApiInternal
    void unloadChunk(IChunk chunk);

    @ApiInternal
    void savePlayer(AbstractEntityPlayer player);

    @ApiInternal
    List<IWorldGenerator> getSortedGenerators();

    @ApiInternal
    List<IWorldGenerator> getSortedRetroactiveGenerators();

    @ApiInternal
    void save();

    List<AbstractEntityPlayer> getAllPlayers();

    void removeEntity(Entity entity, IChunk chunk);

    boolean isDaytime();

    boolean isNighttime();

    File getFolder();

    File getPlayerFolder();

    File getChunksFolder();

    String getName();

    void playSound(AbstractEntityPlayer player, IResourceName name, double x, double y, double z, float pitch, float volume);

    void broadcastSound(AbstractEntityPlayer player, IResourceName name, float pitch, float volume);

    void playSound(IResourceName name, double x, double y, double z, float pitch, float volume, AbstractEntityPlayer except);

    void broadcastSound(IResourceName name, float pitch, float volume, AbstractEntityPlayer except);

    void playSound(IResourceName name, double x, double y, double z, float pitch, float volume);

    void broadcastSound(IResourceName name, float pitch, float volume);

    byte getCombinedVisualLight(int x, int y);

    boolean isStoryMode();
}
