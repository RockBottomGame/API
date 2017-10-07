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
import de.ellpeck.rockbottom.api.data.set.IAdditionalDataProvider;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Counter;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IChunk extends IChunkOrWorld, IAdditionalDataProvider{

    List<AbstractEntityPlayer> getPlayersInRange();

    List<AbstractEntityPlayer> getPlayersLeftRange();

    Map<AbstractEntityPlayer, Counter> getLeftPlayerTimers();

    int getGridX();

    int getGridY();

    IWorld getWorld();

    int getX();

    int getY();

    int getLowestAirUpwardsInner(TileLayer layer, int x, int y);

    int getLowestAirUpwardsInner(TileLayer layer, int x, int y, boolean ignoreReplaceableTiles);

    TileState getStateInner(TileLayer layer, int x, int y);

    TileState getStateInner(int x, int y);

    void setStateInner(int x, int y, TileState tile);

    void setStateInner(TileLayer layer, int x, int y, TileState tile);

    byte getSkylightInner(int x, int y);

    void setSkylightInner(int x, int y, byte light);

    byte getArtificialLightInner(int x, int y);

    void setArtificialLightInner(int x, int y, byte light);

    void setGenerating(boolean generating);

    boolean needsSave();

    boolean shouldUnload();

    void save(DataSet set);

    void update(IGameInstance game);

    byte getCombinedLightInner(int x, int y);

    int getScheduledUpdateAmount();

    Biome getBiomeInner(int x, int y);

    void setBiomeInner(int x, int y, Biome biome);

    Set<TileLayer> getLoadedLayers();
}
