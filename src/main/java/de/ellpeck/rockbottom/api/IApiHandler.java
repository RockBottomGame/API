/*
 * This file ("IApiHandler.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.part.DataPart;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.util.Random;

/**
 * The API handler can be used for executing code that is not part
 * of the API through simple methods
 */
public interface IApiHandler{

    /**
     * Writes the specified {@link DataSet} to the specified {@link File}
     *
     * @param set  The set
     * @param file The file
     */
    void writeDataSet(DataSet set, File file);

    /**
     * Reads the specified {@link DataSet} from the specified {@link File}
     *
     * @param set  The set
     * @param file The file
     */
    void readDataSet(DataSet set, File file);

    /**
     * Writes the specified {@link DataSet} to the specified {@link DataOutput}
     *
     * @param stream The output to write to
     * @param set    The set
     * @throws Exception If writing to the output fails in some way
     */
    void writeSet(DataOutput stream, DataSet set) throws Exception;

    /**
     * Reads the specified {@link DataSet} from the specified {@link DataInput}
     *
     * @param stream The input to read from
     * @param set    The set
     * @throws Exception If reading from the input fails in some way
     */
    void readSet(DataInput stream, DataSet set) throws Exception;

    /**
     * Writes the specified {@link DataPart} to the specified {@link DataOutput}
     *
     * @param stream The output to write to
     * @param part   The part
     * @throws Exception If writing to the output fails in some way
     */
    void writePart(DataOutput stream, DataPart part) throws Exception;

    /**
     * Reads a {@link DataPart} from the specified {@link DataInput} and returns it
     *
     * @param stream The input to read from
     * @return The read {@link DataPart}
     * @throws Exception If reading from the input fails in some way
     */
    DataPart readPart(DataInput stream) throws Exception;

    /**
     * Updates an {@link Entity}'s {@link Entity#x} and {@link Entity#y} values
     * depending on its {@link Entity#motionX} and {@link Entity#motionY}, increases
     * its {@link Entity#ticksExisted} value and so on
     * <br> Not supposed to be used by mods
     *
     * @param entity The entity
     */
    void doDefaultEntityUpdate(Entity entity);

    /**
     * Does everything necessary for moving instances of {@link ItemInstance} around between
     * slots of a {@link de.ellpeck.rockbottom.api.gui.container.ItemContainer} using the {@link ComponentSlot}
     * provided
     * <br> Not supposed to be used by mods
     *
     * @param game   The current game instance
     * @param button The button pressed
     * @param x      The x coordinate of the mouse
     * @param y      The y coordiante of the mouse
     * @param slot   The slot
     * @return If the movement was successful
     */
    boolean doDefaultSlotMovement(IGameInstance game, int button, float x, float y, ComponentSlot slot);

    int[] interpolateLight(IWorld world, int x, int y);

    int[] interpolateWorldColor(int[] interpolatedLight, TileLayer layer);

    int getColorByLight(int light, TileLayer layer);

    INoiseGen makeSimplexNoise(Random random);

    boolean isToolEffective(AbstractEntityPlayer player, ItemInstance instance, Tile tile, TileLayer layer, int x, int y);

    boolean placeTile(int x, int y, TileLayer layer, AbstractEntityPlayer player, ItemInstance selected, Tile tile);
}
