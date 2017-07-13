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

import de.ellpeck.rockbottom.api.assets.IAssetManager;
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
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.util.List;
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
     * its {@link Entity#ticksExisted} value and updates its {@link Entity#fallAmount}
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

    /**
     * Renders a slot icon in a {@link de.ellpeck.rockbottom.api.gui.Gui} at the specified x and y coordinates
     * containg the specified {@link ItemInstance}
     *
     * @param game    The current game instance
     * @param manager The current instance of {@link IAssetManager}
     * @param g       The current graphics context
     * @param slot    The {@link ItemInstance} to be drawn inside of the slot
     * @param x       The x coordinate
     * @param y       The y coordinate
     * @param scale   The scale
     */
    void renderSlotInGui(IGameInstance game, IAssetManager manager, Graphics g, ItemInstance slot, float x, float y, float scale);

    /**
     * Renders an {@link ItemInstance} in a {@link de.ellpeck.rockbottom.api.gui.Gui} at the specified x and y coordinates
     *
     * @param game    The current game instance
     * @param manager The current instance of {@link IAssetManager}
     * @param g       The current graphics context
     * @param slot    The {@link ItemInstance} to be drawn inside of the slot
     * @param x       The x coordinate
     * @param y       The y coordinate
     * @param scale   The scale
     * @param color   The filter to be applied to the {@link de.ellpeck.rockbottom.api.item.Item} renderer
     */
    void renderItemInGui(IGameInstance game, IAssetManager manager, Graphics g, ItemInstance slot, float x, float y, float scale, Color color);

    /**
     * Describes an item using a hovering info ({@link #drawHoverInfo(IGameInstance, IAssetManager, Graphics, float, float, float, boolean, boolean, int, List)})
     *
     * @param game     The current game instance
     * @param manager  The current isntance of {@link IAssetManager}
     * @param g        The current graphics context
     * @param instance The {@link ItemInstance} to be described
     */
    void describeItem(IGameInstance game, IAssetManager manager, Graphics g, ItemInstance instance);

    /**
     * Draws a hovering info ({@link #drawHoverInfo(IGameInstance, IAssetManager, Graphics, float, float, float, boolean, boolean, int, List)})
     * at the specified mouse coordinates
     *
     * @param game            The current game instance
     * @param manager         The current isntance of {@link IAssetManager}
     * @param g               The current graphics context
     * @param firstLineOffset The offset between the first line and the other lines
     * @param maxLength       The maximum length before the info wraps around
     * @param text            The text to be displayed
     */
    void drawHoverInfoAtMouse(IGameInstance game, IAssetManager manager, Graphics g, boolean firstLineOffset, int maxLength, String... text);

    /**
     * Draws a hovering info ({@link #drawHoverInfo(IGameInstance, IAssetManager, Graphics, float, float, float, boolean, boolean, int, List)})
     * at the specified mouse coordinates
     *
     * @param game            The current game instance
     * @param manager         The current isntance of {@link IAssetManager}
     * @param g               The current graphics context
     * @param firstLineOffset The offset between the first line and the other lines
     * @param maxLength       The maximum length before the info wraps around
     * @param text            The text to be displayed
     */
    void drawHoverInfoAtMouse(IGameInstance game, IAssetManager manager, Graphics g, boolean firstLineOffset, int maxLength, List<String> text);

    /**
     * Draws a hovering info at the specified coordinates
     *
     * @param game            The current game instance
     * @param manager         The current isntance of {@link IAssetManager}
     * @param g               The current graphics context
     * @param x               The x coordinate
     * @param y               The y coordinate
     * @param scale           The scale of the info
     * @param firstLineOffset The offset between the first line and the other lines
     * @param canLeaveScreen  If the hover info can leave the screen
     * @param maxLength       The maximum length before the info wraps around
     * @param text            The text to be displayed
     */
    void drawHoverInfo(IGameInstance game, IAssetManager manager, Graphics g, float x, float y, float scale, boolean firstLineOffset, boolean canLeaveScreen, int maxLength, List<String> text);

    int[] interpolateLight(IWorld world, int x, int y);

    Color[] interpolateWorldColor(int[] interpolatedLight, TileLayer layer);

    Color getColorByLight(int light, TileLayer layer);

    INoiseGen makeSimplexNoise(Random random);

    boolean isToolEffective(AbstractEntityPlayer player, ItemInstance instance, Tile tile, TileLayer layer, int x, int y);

    boolean placeTile(int x, int y, TileLayer layer, AbstractEntityPlayer player, ItemInstance selected, Tile tile);
}
