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
import de.ellpeck.rockbottom.api.event.impl.TooltipEvent;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * The graphics context to interact with OpenGL as well as a multitude of
 * game-provided rendering methods. To access these methods, use the {@link
 * IGraphics} object given to you in render methods or access {@link
 * IGameInstance#getGraphics()}
 */
public interface IGraphics{

    /**
     * Renders a slot. This displays the (by default) green box based on the gui
     * color as well as the item inside and the amount. To make a slot that has
     * functionality, use {@link ItemContainer} to create functioning slots.
     *
     * @param game    The game instance
     * @param manager The asset manager
     * @param slot    The content of the slot to draw
     * @param x       The x coordinate
     * @param y       The y coordinate
     * @param scale   The scale
     * @param hovered If the slot is currently being hovered or not (ie if it
     *                should show the hover info of what is in the slot)
     */
    void renderSlotInGui(IGameInstance game, IAssetManager manager, ItemInstance slot, float x, float y, float scale, boolean hovered);

    /**
     * Renders an item and its amount. This is similar to {@link
     * #renderSlotInGui(IGameInstance, IAssetManager, ItemInstance, float,
     * float, float, boolean)}, only that it doesn't render the box below the
     * item.
     *
     * @param game    The game instance
     * @param manager The asset manager
     * @param slot    The item to draw
     * @param x       The x coordinate
     * @param y       The y coordinate
     * @param scale   The scale
     * @param color   The color to filter the item's renderer with
     */
    void renderItemInGui(IGameInstance game, IAssetManager manager, ItemInstance slot, float x, float y, float scale, int color);

    /**
     * Renders an item's hover over information on the mouse cursor position.
     * Additionally, it draws the {@link IGameInstance#isItemInfoDebug()} text
     * and also fires the {@link TooltipEvent}.
     *
     * @param game     The game instance
     * @param manager  The asset manager
     * @param instance The item whose information to draw
     */
    void describeItem(IGameInstance game, IAssetManager manager, ItemInstance instance);

    /**
     * Draws a hovering information at the mouse's position using the specified
     * text
     *
     * @param game            The game instance
     * @param manager         The asset manager
     * @param firstLineOffset If the first line should have a bit of a gap below
     *                        it making it act as a "headline"
     * @param maxLength       The maximum length before the tooltip wraps into
     *                        the next line
     * @param text            The array of lines of text to draw
     */
    void drawHoverInfoAtMouse(IGameInstance game, IAssetManager manager, boolean firstLineOffset, int maxLength, String... text);

    /**
     * Draws a hovering information at the mouse's position using the specified
     * text
     *
     * @param game            The game instance
     * @param manager         The asset manager
     * @param firstLineOffset If the first line should have a bit of a gap below
     *                        it making it act as a "headline"
     * @param maxLength       The maximum length before the tooltip wraps into
     *                        the next line
     * @param text            The list of lines of text to draw
     */
    void drawHoverInfoAtMouse(IGameInstance game, IAssetManager manager, boolean firstLineOffset, int maxLength, List<String> text);

    /**
     * Draws a hovering information at the specified position using the
     * specified text
     *
     * @param game            The game instance
     * @param manager         The asset manager
     * @param x               The x position
     * @param y               The y position
     * @param scale           The scale of the tooltip
     * @param firstLineOffset If the first line should have a bit of a gap below
     *                        it making it act as a "headline"
     * @param canLeaveScreen  If the tooltip can leave the screen or should stop
     *                        at the borders
     * @param maxLength       The maximum length before the tooltip wraps into
     *                        the next line
     * @param text            The list of lines of text to draw
     */
    void drawHoverInfo(IGameInstance game, IAssetManager manager, float x, float y, float scale, boolean firstLineOffset, boolean canLeaveScreen, int maxLength, List<String> text);

    /**
     * Pushes the GL matrix. See {@link GL11#glPushMatrix()} for reference.
     */
    void pushMatrix();

    /**
     * Pops the GL matrix. See {@link GL11#glPopMatrix()} for reference.
     */
    void popMatrix();

    /**
     * Binds the specified color to the GL context. See {@link
     * GL11#glColor4f(float, float, float, float)} for reference. Note that
     * every hex color used in the game needs to contain an alpha component for
     * it to display.
     *
     * @param color The color
     */
    void bindColor(int color);

    /**
     * Binds the specifid color to the GL context. See {@link
     * GL11#glColor4f(float, float, float, float)} for reference.
     *
     * @param r The red component (0-1)
     * @param g The green component (0-1)
     * @param b The blue component (0-1)
     * @param a The alpha (0-1)
     */
    void bindColor(float r, float g, float b, float a);

    /**
     * Sets the background color (the color to fill the screen with at the start
     * of every render cycle). See {@link GL11#glClearColor(float, float, float,
     * float)} for reference. Note that every hex color used in the game needs
     * to contain an alpha component for it to display.
     *
     * @param color The color
     */
    void backgroundColor(int color);

    /**
     * Sets the background color (the color to fill the screen with at the start
     * of every render cycle). See {@link GL11#glClearColor(float, float, float,
     * float)} for reference.
     *
     * @param r The red component (0-1)
     * @param g The green component (0-1)
     * @param b The blue component (0-1)
     * @param a The alpha (0-1)
     */
    void backgroundColor(float r, float g, float b, float a);

    /**
     * Scales the GL context by the specified x and y scale. See {@link
     * GL11#glScalef(float, float, float)} for reference.
     *
     * @param scaleX The x scale
     * @param scaleY The y scale
     */
    void scale(float scaleX, float scaleY);

    /**
     * Translates the GL context by the specified x and y coordinates. See
     * {@link GL11#glPixelTransferf(int, float)} for reference.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    void translate(float x, float y);

    /**
     * Rotates the GL context by the specified angle. See {@link
     * GL11#glRotatef(float, float, float, float)} for reference. Note that this
     * will only ever rotate around one axis as anything else makes little sense
     * in a 2d game.
     *
     * @param angle The angle in degrees to rotate by
     */
    void rotate(float angle);

    /**
     * Draws a colored, unfilled rectangle at the specified x and y
     * coordinates.
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param width  The width
     * @param height The height
     * @param color  The color
     */
    void drawRect(float x, float y, float width, float height, int color);

    /**
     * Draws a colored, unfilled rectangle at the specified x and y
     * coordinates.
     *
     * @param x         The x coordinate
     * @param y         The y coordinate
     * @param width     The width
     * @param height    The height
     * @param lineWidth The width of the outlines
     * @param color     The color
     */
    void drawRect(float x, float y, float width, float height, float lineWidth, int color);

    /**
     * Draws a colored, filled rectangle at the spcified x and y coordinates.
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param width  The width
     * @param height The height
     * @param color  The color
     */
    void fillRect(float x, float y, float width, float height, int color);
}
