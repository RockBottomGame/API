/*
 * This file ("IApiHandler.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.part.DataPart;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.util.logging.Logger;

/**
 * The API handler is used mostly by the API to unexpose certain methods like
 * the movements of entities that need to be called from the API, but are too
 * complex to be inside of it. However, it also exposes a multitude of methods
 * that can be useful to modders. To access the API handler, use {@link
 * RockBottomAPI#getApiHandler()}.
 */
public interface IApiHandler{

    /**
     * Writes a {@link DataSet} to the location of the specified {@link File}.
     *
     * @param set  The data set
     * @param file The file
     */
    void writeDataSet(DataSet set, File file);

    /**
     * Reads a {@link DataSet} from the location of the specified {@link File}.
     *
     * @param set  The data set
     * @param file The file
     */
    void readDataSet(DataSet set, File file);

    /**
     * Writes a {@link DataSet} to the specified {@link DataOutput}.
     *
     * @param stream The data output
     * @param set    The data set
     *
     * @throws Exception when anything goes wrong during saving
     */
    void writeSet(DataOutput stream, DataSet set) throws Exception;

    /**
     * Reads a {@link DataSet} from the specified {@link DataInput}.
     *
     * @param stream The data input
     * @param set    The data set
     *
     * @throws Exception when anything goes wrong during saving
     */
    void readSet(DataInput stream, DataSet set) throws Exception;

    /**
     * Writes a {@link DataPart} to the specified {@link DataOutput}.
     *
     * @param stream The data output
     * @param part   The data part
     *
     * @throws Exception when anything goes wrong during saving
     */
    void writePart(DataOutput stream, DataPart part) throws Exception;

    /**
     * Reads a {@link DataPart} from the specified {@link DataInput} and returns
     * its instance.
     *
     * @param stream The data input
     *
     * @return The read data part
     *
     * @throws Exception when anything goes wrong during loading
     */
    DataPart readPart(DataInput stream) throws Exception;

    @ApiInternal
    void doDefaultEntityUpdate(Entity entity);

    @ApiInternal
    void doWorldObjectMovement(MovableWorldObject object);

    @ApiInternal
    boolean doDefaultSlotMovement(IGameInstance game, int button, float x, float y, ComponentSlot slot);

    @ApiInternal
    boolean doDefaultShiftClicking(IGameInstance game, GuiContainer gui, ComponentSlot slot);

    /**
     * Interpolates the light at a position in the world. The four integers in
     * the returned array specify the light at each four corners of the tile at
     * the position.
     *
     * @param world The world
     * @param x     The x coordinate
     * @param y     The y coordinate
     *
     * @return The interpolated light
     */
    int[] interpolateLight(IWorld world, int x, int y);

    /**
     * Interpolates the four colors of the corners of a position in the world
     * based on the {@link TileLayer} and the interpolated light from {@link
     * #interpolateLight(IWorld, int, int)}.
     *
     * @param interpolatedLight The interpolated light
     * @param layer             The layer
     *
     * @return The four colors
     */
    int[] interpolateWorldColor(int[] interpolatedLight, TileLayer layer);

    /**
     * Gets a color in the world based on a light value between 0 and {@link
     * Constants#MAX_LIGHT}.
     *
     * @param light The light
     * @param layer The layer
     *
     * @return The color
     */
    int getColorByLight(int light, TileLayer layer);

    /**
     * Returns a new {@link INoiseGen} of the Simplex Noise kind based on the
     * specified seed.
     *
     * @param seed The seed
     *
     * @return The noise generator
     */
    INoiseGen makeSimplexNoise(long seed);

    @ApiInternal
    boolean isToolEffective(AbstractEntityPlayer player, ItemInstance instance, Tile tile, TileLayer layer, int x, int y);

    @ApiInternal
    boolean placeTile(int x, int y, TileLayer layer, AbstractEntityPlayer player, ItemInstance selected, Tile tile, boolean removeItem, boolean simulate);

    /**
     * Creates a {@link Logger} with the specified name that will output to the
     * main logger and also written to the log file.
     *
     * @param name The name
     *
     * @return The new logger
     */
    Logger createLogger(String name);

    @ApiInternal
    Logger logger();

    @ApiInternal
        //Liquid behavior kindly provided by superaxander
    void doDefaultLiquidBehavior(IWorld world, int x, int y, TileLayer layer, TileLiquid tile);
}
