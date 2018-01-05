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
import de.ellpeck.rockbottom.api.assets.IShaderProgram;
import de.ellpeck.rockbottom.api.assets.ITexture;
import de.ellpeck.rockbottom.api.event.impl.TooltipEvent;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.engine.IDisposable;
import de.ellpeck.rockbottom.api.render.engine.IVAO;
import de.ellpeck.rockbottom.api.render.engine.IVBO;
import de.ellpeck.rockbottom.api.util.ApiInternal;

import java.util.List;

/**
 * The graphics context to interact with OpenGL as well as a multitude of
 * game-provided rendering methods. To access these methods, use the {@link
 * IRenderer} object given to you in render methods or access {@link
 * IGameInstance#getRenderer()}
 */
public interface IRenderer extends IDisposable{

    void setProgram(IShaderProgram program);

    void setTexture(ITexture texture);

    void addTriangle(float x1, float y1, float x2, float y2, float x3, float y3, int color, float u1, float v1, float u2, float v2, float u3, float v3);

    void addVertex(float x, float y, int color, float u, float v);

    void begin();

    void end();

    void flush();

    void rotate(float angle);

    void setRotation(float angle);

    void translate(float x, float y);

    void setTranslation(float x, float y);

    void scale(float x, float y);

    void setScale(float x, float y);

    float getAngle();

    float getTranslationX();

    float getTranslationY();

    float getScaleX();

    float getScaleY();

    IShaderProgram getProgram();

    ITexture getTexture();

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
     * Additionally, it draws the {@link IRenderer#isItemInfoDebug()} text and
     * also fires the {@link TooltipEvent}.
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
     * Draws a colored, unfilled rectangle at the specified x and y
     * coordinates.
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param width  The width
     * @param height The height
     * @param color  The color
     */
    void addEmptyRect(float x, float y, float width, float height, int color);

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
    void addEmptyRect(float x, float y, float width, float height, float lineWidth, int color);

    /**
     * Draws a colored, filled rectangle at the spcified x and y coordinates.
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param width  The width
     * @param height The height
     * @param color  The color
     */
    void addFilledRect(float x, float y, float width, float height, int color);

    void unbindTexture();

    void unbindVAO();

    void unbindVBO();

    void unbindShaderProgram();

    IVAO createVAO();

    IVBO createVBO(boolean isStatic);

    @ApiInternal
    void calcScales();

    float getDisplayRatio();

    float getGuiScale();

    float getWorldScale();

    float getWidthInWorld();

    float getHeightInWorld();

    float getWidthInGui();

    float getHeightInGui();

    float getMouseInGuiX();

    float getMouseInGuiY();

    boolean isDebug();

    boolean isItemInfoDebug();

    boolean isChunkBorderDebug();

    boolean isGuiDebug();

    double getMousedTileX();

    double getMousedTileY();

    int getFlushes();
}
