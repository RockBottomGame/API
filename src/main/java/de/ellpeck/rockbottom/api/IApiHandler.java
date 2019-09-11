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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api;

import com.google.common.collect.ListMultimap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.construction.compendium.ICompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.compendium.PlayerCompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.data.set.AbstractDataSet;
import de.ellpeck.rockbottom.api.data.set.part.DataPart;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.IPlayerDesign;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.BiomeGen;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * This class exposes a multitude of utility methods that are too big to be
 * inside the API's code and are, as such, inside of the game. To access the API
 * handler, use {@link RockBottomAPI#getApiHandler()}.
 */
public interface IApiHandler {

    /**
     * Writes a data set to the given file either as binary or as a json
     *
     * @param set    The data set to write to file
     * @param file   The file to write to
     * @param asJson Wether it should be stored as json
     */
    void writeDataSet(AbstractDataSet set, File file, boolean asJson);

    /**
     * Reads a data set from the given file either as binary or as a json and
     * stores the data in the set.
     *
     * @param set    The set to store the data in
     * @param file   The file to read from
     * @param asJson Wether or not it should be stored as json
     */
    void readDataSet(AbstractDataSet set, File file, boolean asJson);

    /**
     * Writes a data set directly to a data output of any kind, throwing an
     * exception if something fails.
     *
     * @param stream The output to write to
     * @param set    The set to write to the output
     * @throws Exception if writing fails for some reason
     */
    void writeDataSet(DataOutput stream, AbstractDataSet set) throws Exception;

    /**
     * Reads a data set directly from a data input of any kind, throwing an
     * exception if something fails.
     *
     * @param stream The input to read from
     * @param set    The set to save the input in
     * @throws Exception if reading fails for some reason
     */
    void readDataSet(DataInput stream, AbstractDataSet set) throws Exception;

    /**
     * Writes a data set directly to a json object head, throwing an exception
     * if something fails.
     *
     * @param main The json object to write to
     * @param set  The set to write
     * @throws Exception if writing fails for some reason
     */
    void writeDataSet(JsonObject main, AbstractDataSet set) throws Exception;

    /**
     * Reads a data set directly from a json object head, throwing an exception
     * if something fails.
     *
     * @param main The json object to read from
     * @param set  The set to save the input in
     * @throws Exception if reading fails for some reason
     */
    void readDataSet(JsonObject main, AbstractDataSet set) throws Exception;

    DataPart readDataPart(JsonElement element) throws Exception;

    /**
     * Interpolates the light at a position in the world. The four integers in
     * the returned array specify the light at each four corners of the tile at
     * the position.
     *
     * @param world The world
     * @param x     The x coordinate
     * @param y     The y coordinate
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
     * @return The four colors
     */
    int[] interpolateWorldColor(int[] interpolatedLight, TileLayer layer);

    List<BoundBox> getDefaultPlatformBounds(IWorld world, int x, int y, TileLayer layer, double tileWidth, double tileHeight, TileState state, MovableWorldObject object, BoundBox objectBox);

    /**
     * Searches the inventory for items as IUseInfo specified in the given parameters.
     *
     * @param inventory The inventory to search
     * @param inputs    The list of item infos to find
     * @param simulate  Whether to simulate the process. False will remove the items found from inventory IF it has found all inputs.
     * @param out       The list to be provided which will store the ItemInstances found.
     * @return True enough items are in the inventory and it can be collected. False otherwise.
     */
    boolean collectItems(IInventory inventory, List<IUseInfo> inputs, boolean simulate, List<ItemInstance> out);

    /**
     * Similar to {@link IApiHandler#construct(AbstractEntityPlayer, Inventory, Inventory, PlayerCompendiumRecipe, TileEntity, int, List, List, Function, float)}
     * but safe to use on both client and server side (client side sends packet to server).
     *
     * @param player  The player doing this construction. Can be null if
     *                the construction is automated. Note that the skill
     *                reward is not used then.
     * @param recipe  The recipe to construct
     * @param machine The tile entity doing the crafting
     */
    void defaultConstruct(AbstractEntityPlayer player, PlayerCompendiumRecipe recipe, TileEntity machine);

    /**
     * This is a utility method that you can call for custom construction of
     * items via the compendium, if you, for example, create a custom, more
     * complex, {@link ICompendiumRecipe} class, or if you create a machine that
     * should automatically construct something.
     *
     * @param player          The player doing this construction. Can be null if
     *                        the construction is automated. Note that the skill
     *                        reward is not used then.
     * @param inputInventory  The inventory that the construction takes items
     *                        from
     * @param outputInventory The inventory that the output items go into
     * @param recipe          The recipe to be constructed
     * @param machine         The machine
     * @param amount          The amount of times this recipe should be
     *                        constructed
     * @param recipeInputs    The items that are input into the recipe
     * @param actualInputs    The actual {@link ItemInstance}s used in the construction of the recipe.
     *                        Can be {@code null} if the inputs should be calculated by the method.
     * @param outputGetter    A function that is called to get the outputs of
     *                        the recipe based on the recipeInputs that were actually
     *                        taken from the inventory
     * @param skillReward     The amount of skill points you get for
     *                        constructing this recipe
     * @return A list of items that couldn't fit into the output inventory
     * specified
     * @see IApiHandler#defaultConstruct(AbstractEntityPlayer, PlayerCompendiumRecipe, TileEntity)
     */
    List<ItemInstance> construct(AbstractEntityPlayer player, Inventory inputInventory, Inventory outputInventory, PlayerCompendiumRecipe recipe, TileEntity machine, int amount, List<IUseInfo> recipeInputs, List<ItemInstance> actualInputs, Function<List<ItemInstance>, List<ItemInstance>> outputGetter, float skillReward);

    /**
     * Gets a color in the world based on a light value between 0 and {@link
     * Constants#MAX_LIGHT}.
     *
     * @param light The light
     * @param layer The layer
     * @return The color
     */
    int getColorByLight(int light, TileLayer layer);

    /**
     * Returns a new {@link INoiseGen} of the Simplex Noise kind based on the
     * specified seed.
     *
     * @param seed The seed
     * @return The noise generator
     */
    INoiseGen makeSimplexNoise(long seed);

    /**
     * Creates a {@link Logger} with the specified name that will output to the
     * main logger and also written to the log file.
     *
     * @param name The name
     * @return The new logger
     */
    Logger createLogger(String name);

    void renderPlayer(AbstractEntityPlayer player, IGameInstance game, IAssetManager manager, IRenderer g, IPlayerDesign design, float x, float y, float scale, int row, int light);

    int generateBasicHeight(IWorld world, TileLayer layer, int x, INoiseGen noiseGen, int minHeight, int maxHeight, int maxMountainHeight);

    void initBiomeGen(IWorld world, int seedScramble, int blobSize, long[] layerSeeds, ListMultimap<BiomeLevel, Biome> biomesPerLevel, Map<BiomeLevel, Integer> totalWeights, BiomeGen gen);

    void generateBiomeGen(IWorld world, IChunk chunk, BiomeGen gen, Map<Biome, INoiseGen> biomeNoiseGens);

    Biome getBiome(IWorld world, int x, int y, int height, Map<BiomeLevel, Integer> totalWeights, ListMultimap<BiomeLevel, Biome> biomesPerLevel, Random biomeRandom, int blobSize, long[] layerSeeds, INoiseGen levelHeightNoise, int levelTransition, int biomeTransition);

    BiomeLevel getSmoothedLevelForPos(IWorld world, int x, int y, int height, int levelTransition, ListMultimap<BiomeLevel, Biome> biomesPerLevel, INoiseGen levelHeightNoise);
}
