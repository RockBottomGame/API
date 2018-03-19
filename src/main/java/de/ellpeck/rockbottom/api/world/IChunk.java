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
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.Counter;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IChunk extends IChunkOrWorld{

    @ApiInternal
    List<AbstractEntityPlayer> getPlayersInRange();

    @ApiInternal
    List<AbstractEntityPlayer> getPlayersLeftRange();

    @ApiInternal
    Map<AbstractEntityPlayer, Counter> getLeftPlayerTimers();

    int getGridX();

    int getGridY();

    IWorld getWorld();

    int getX();

    int getY();

    int getHeight(TileLayer layer, int x);

    int getHeightInner(TileLayer layer, int x);

    TileState getStateInner(TileLayer layer, int x, int y);

    TileState getStateInner(int x, int y);

    void setStateInner(int x, int y, TileState tile);

    void setStateInner(TileLayer layer, int x, int y, TileState tile);

    byte getSkylightInner(int x, int y);

    @ApiInternal
    void setSkylightInner(int x, int y, byte light);

    byte getArtificialLightInner(int x, int y);

    @ApiInternal
    void setArtificialLightInner(int x, int y, byte light);

    @ApiInternal
    void setGenerating(boolean generating);

    @ApiInternal
    boolean needsSave();

    @ApiInternal
    boolean shouldUnload();

    void setDirty();

    @ApiInternal
    void save(DataSet set);

    @ApiInternal
    void update(IGameInstance game);

    byte getCombinedLightInner(int x, int y);

    @ApiInternal
    int getScheduledUpdateAmount();

    Biome getBiomeInner(int x, int y);

    Biome getMostProminentBiome();

    int getAverageHeight(TileLayer layer);

    @ApiInternal
    void setBiomeInner(int x, int y, Biome biome);

    @ApiInternal
    Set<TileLayer> getLoadedLayers();

    /**
     * @deprecated Use {@link #getHeight(TileLayer, int, int)} instead
     */
    @Deprecated
    int getLowestAirUpwardsInner(TileLayer layer, int x, int y);

    /**
     * @deprecated Use {@link #getHeight(TileLayer, int, int)} instead
     */
    @Deprecated
    int getLowestAirUpwardsInner(TileLayer layer, int x, int y, boolean ignoreReplaceableTiles);

    boolean isPersistent();

    boolean isGenerating();
}
