/*
 * This file ("IGraphics.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.List;

public interface IGraphics{

    /**
     * Renders a slot icon in a {@link de.ellpeck.rockbottom.api.gui.Gui} at the specified x and y coordinates
     * containg the specified {@link ItemInstance}
     *
     * @param game    The current game instance
     * @param manager The current instance of {@link IAssetManager}
     * @param slot    The {@link ItemInstance} to be drawn inside of the slot
     * @param x       The x coordinate
     * @param y       The y coordinate
     * @param scale   The scale
     */
    void renderSlotInGui(IGameInstance game, IAssetManager manager, ItemInstance slot, float x, float y, float scale, boolean hovered);

    /**
     * Renders an {@link ItemInstance} in a {@link de.ellpeck.rockbottom.api.gui.Gui} at the specified x and y coordinates
     *
     * @param game    The current game instance
     * @param manager The current instance of {@link IAssetManager}
     * @param slot    The {@link ItemInstance} to be drawn inside of the slot
     * @param x       The x coordinate
     * @param y       The y coordinate
     * @param scale   The scale
     * @param color   The filter to be applied to the {@link de.ellpeck.rockbottom.api.item.Item} renderer
     */
    void renderItemInGui(IGameInstance game, IAssetManager manager, ItemInstance slot, float x, float y, float scale, int color);

    /**
     * Describes an item using a hovering info ({@link #drawHoverInfo(IGameInstance, IAssetManager, float, float, float, boolean, boolean, int, List)})
     *
     * @param game     The current game instance
     * @param manager  The current isntance of {@link IAssetManager}
     * @param instance The {@link ItemInstance} to be described
     */
    void describeItem(IGameInstance game, IAssetManager manager, ItemInstance instance);

    /**
     * Draws a hovering info ({@link #drawHoverInfo(IGameInstance, IAssetManager, float, float, float, boolean, boolean, int, List)})
     * at the specified mouse coordinates
     *
     * @param game            The current game instance
     * @param manager         The current isntance of {@link IAssetManager}
     * @param firstLineOffset The offset between the first line and the other lines
     * @param maxLength       The maximum length before the info wraps around
     * @param text            The text to be displayed
     */
    void drawHoverInfoAtMouse(IGameInstance game, IAssetManager manager, boolean firstLineOffset, int maxLength, String... text);

    /**
     * Draws a hovering info ({@link #drawHoverInfo(IGameInstance, IAssetManager, float, float, float, boolean, boolean, int, List)})
     * at the specified mouse coordinates
     *
     * @param game            The current game instance
     * @param manager         The current isntance of {@link IAssetManager}
     * @param firstLineOffset The offset between the first line and the other lines
     * @param maxLength       The maximum length before the info wraps around
     * @param text            The text to be displayed
     */
    void drawHoverInfoAtMouse(IGameInstance game, IAssetManager manager, boolean firstLineOffset, int maxLength, List<String> text);

    /**
     * Draws a hovering info at the specified coordinates
     *
     * @param game            The current game instance
     * @param manager         The current isntance of {@link IAssetManager}
     * @param x               The x coordinate
     * @param y               The y coordinate
     * @param scale           The scale of the info
     * @param firstLineOffset The offset between the first line and the other lines
     * @param canLeaveScreen  If the hover info can leave the screen
     * @param maxLength       The maximum length before the info wraps around
     * @param text            The text to be displayed
     */
    void drawHoverInfo(IGameInstance game, IAssetManager manager, float x, float y, float scale, boolean firstLineOffset, boolean canLeaveScreen, int maxLength, List<String> text);

    void pushMatrix();

    void popMatrix();

    void bindColor(int color);

    void bindColor(float r, float g, float b, float a);

    void backgroundColor(int color);

    void backgroundColor(float r, float g, float b, float a);

    void scale(float scaleX, float scaleY);

    void translate(float x, float y);

    void rotate(float angle);

    void drawRect(float x, float y, float width, float height, int color);

    void drawRect(float x, float y, float width, float height, float lineWidth, int color);

    void fillRect(float x, float y, float width, float height, int color);
}
