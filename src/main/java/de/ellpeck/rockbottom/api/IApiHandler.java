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

import com.google.gson.JsonObject;
import de.ellpeck.rockbottom.api.construction.IRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.data.set.AbstractDataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * This class exposes a multitude of utility methods that are too big to be
 * inside the API's code and are, as such, inside of the game. To access the API
 * handler, use {@link RockBottomAPI#getApiHandler()}.
 */
public interface IApiHandler{

    void writeDataSet(AbstractDataSet set, File file, boolean asJson);

    void readDataSet(AbstractDataSet set, File file, boolean asJson);

    void writeDataSet(DataOutput stream, AbstractDataSet set) throws Exception;

    void readDataSet(DataInput stream, AbstractDataSet set) throws Exception;

    void writeDataSet(JsonObject main, AbstractDataSet set) throws Exception;

    void readDataSet(JsonObject main, AbstractDataSet set) throws Exception;

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

    void construct(IWorld world, double x, double y, Inventory inventory, IRecipe recipe, int amount, List<IUseInfo> inputs, Function<List<ItemInstance>, List<ItemInstance>> outputGetter);

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
}
