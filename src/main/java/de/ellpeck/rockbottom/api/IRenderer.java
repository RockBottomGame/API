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
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.event.impl.TooltipEvent;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.engine.*;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.Util;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.util.List;

/**
 * The graphics context to interact with OpenGL as well as a multitude of
 * game-provided rendering methods. To access these methods, use the {@link
 * IRenderer} object given to you in render methods or access {@link
 * IGameInstance#getRenderer()}
 */
public interface IRenderer extends IDisposable {

    /**
     * Sets the {@link IShaderProgram} that the game should fall back to when
     * {@link #setProgram(IShaderProgram)} is called with a null parameter. This
     * should only be used internally to set the world and gui shaders.
     *
     * @param program The new default shader program
     */
    @ApiInternal
    void setDefaultProgram(IShaderProgram program);

    /**
     * Adds a certain region of a texture to this renderer. This method gets
     * called by all {@link ITexture#draw(float, float)} methods, so using those
     * will be more convenient in most cases.
     *
     * @param texture The texture to set
     * @param x       The top left x coordinate
     * @param y       The top left y coordinate
     * @param x2      The top right x coordinate
     * @param y2      The top right y coordinate
     * @param x3      The bottom right x coordinate
     * @param y3      The bottom right y coordinate
     * @param x4      The bottom left x coordinate
     * @param y4      The bottom left y coordinate
     * @param srcX    The top left x coordinate of the texture
     * @param srcY    The top left y coordinate of the texture
     * @param srcX2   The bottom right x coordinate of the texture
     * @param srcY2   The bottom right y coordinate of the texture
     * @param light   The light that the four corners of the drawn region should
     *                interpolate between, or null if there should be no light
     *                influence
     * @param filter  A filter color to modify this texture by
     * @see VertexProcessor#addTexturedRegion(IRenderer, ITexture, float, float,
     * float, float, float, float, float, float, float, float, float, float,
     * int, int, int, int)
     */
    void addTexturedRegion(ITexture texture, float x, float y, float x2, float y2, float x3, float y3, float x4, float y4, float srcX, float srcY, float srcX2, float srcY2, int[] light, int filter);

    /**
     * Adds a triangle to this renderer. This method gets called by {@link
     * #addTexturedRegion(ITexture, float, float, float, float, float, float,
     * float, float, float, float, float, float, int[], int)} twice to draw the
     * two triangles of the square (or square-ish) region.
     *
     * @param x1     The first x coordinate
     * @param y1     The first y coordinate
     * @param x2     The second x coordinate
     * @param y2     The second y coordinate
     * @param x3     The third x coordinate
     * @param y3     The third y coordinate
     * @param color1 The color at the first corner
     * @param color2 The color at the second corner
     * @param color3 The color at the third corner
     * @param u1     The first texture x
     * @param v1     The first texture y
     * @param u2     The second texture x
     * @param v2     The second texture y
     * @param u3     The third texture x
     * @param v3     The third texture y
     * @see VertexProcessor#addTriangle(IRenderer, float, float, float, float,
     * float, float, int, int, int, float, float, float, float, float, float)
     */
    void addTriangle(float x1, float y1, float x2, float y2, float x3, float y3, int color1, int color2, int color3, float u1, float v1, float u2, float v2, float u3, float v3);

    /**
     * Puts a single vertex component into this renderer and returns it for
     * convenience
     *
     * @param f The component to add
     * @return This renderer
     */
    IRenderer put(float f);

    /**
     * Puts a single vertex into this renderer. This method gets called three
     * times by {@link #addTriangle(float, float, float, float, float, float,
     * int, int, int, float, float, float, float, float, float)} to add the
     * three vertices for the three corners.
     *
     * @param x     The x coordinate
     * @param y     The y coordinate
     * @param color The color
     * @param u     The texture x
     * @param v     The texture y
     * @see VertexProcessor#addVertex(IRenderer, float, float, int, float,
     * float)
     */
    void addVertex(float x, float y, int color, float u, float v);

    /**
     * Begins rendering with this renderer. This method is internal and there
     * should not be any reasons for a mod to call it.
     */
    @ApiInternal
    void begin();

    /**
     * Finishes rendering with this renderer and flushes the current rendering
     * context. This method is internal and there should not be any reasons for
     * a mod to call it.
     */
    @ApiInternal
    void end();

    /**
     * Flushes the current rendering context, rendering everything that has been
     * added to the renderer since the last call to this method. This method is
     * internal and there should not be any reasons for a mod to call it.
     */
    @ApiInternal
    void flush();

    /**
     * Rotates this renderer's rendering context by the given angle. This will
     * only affect any vertices that are added to this renderer after calling
     * this method.
     *
     * @param angle The angle
     */
    void rotate(float angle);

    /**
     * Sets the center of rotation for this renderer. This will only affect any
     * vertices that are added to this renerer after calling this method.
     *
     * @param x The rotation center's x
     * @param y The rotation center's y
     */
    void setRotationCenter(float x, float y);

    /**
     * @return This renderer's rotation center's x
     */
    float getRotationCenterX();

    /**
     * @return This renderer's rotation center's y
     */
    float getRotationCenterY();

    /**
     * Translates this renderer's origin by the given amount. This will only
     * affect any vertices that are added to this renderer after calling this
     * method.
     *
     * @param x The origin x
     * @param y The origin y
     */
    void translate(float x, float y);

    /**
     * Rather than translating by a certain amount (see {@link #translate(float,
     * float)}), this method sets the translation of this renderer to the given
     * x and y coordinates.
     *
     * @param x The origin x
     * @param y The origin y
     */
    void setTranslation(float x, float y);

    /**
     * Scales the renderer by the given amount. This will only affect any
     * vertices that are added to this renderer after calling this method.
     *
     * @param x The x scale
     * @param y The y scale
     */
    void scale(float x, float y);

    /**
     * Rather than scaling by a given amount (see {@link #scale(float, float)}),
     * this method sets the scale of the renderer directly.
     *
     * @param x The x scale
     * @param y The y scale
     */
    void setScale(float x, float y);

    /**
     * Mirrors the renderer either horizontally or vertically. This will only
     * affect any vertices that are added to this renderer after calling this
     * method.
     *
     * @param hor  If the renderer should be mirrored horizontally
     * @param vert If the renderer should be mirrored vertically
     */
    void mirror(boolean hor, boolean vert);

    /**
     * Rather than mirroring the renderer (see {@link #mirror(boolean,
     * boolean)}), this method sets the mirrored states of this renderer to the
     * given values.
     *
     * @param hor  If the renderer should be mirrored horizontally
     * @param vert If the renderer should be mirrored vertically
     */
    void setMirrored(boolean hor, boolean vert);

    /**
     * Resets this renderer's translation, rotation, scale, mirrored states and
     * texture origin back to their default values.
     */
    void resetTransformation();

    /**
     * @return This renderer's rotation
     */
    float getRotation();

    /**
     * Rather than rotating by a given angle (see {@link #rotate(float)}), this
     * method merely sets the rotation of this renderer to the given rotation.
     * You can use this to reset the rotation back to 0 or its original state.
     *
     * @param angle The angle
     */
    void setRotation(float angle);

    /**
     * @return This renderer's translation x
     */
    float getTranslationX();

    /**
     * @return This renderer's translation y
     */
    float getTranslationY();

    /**
     * @return This renderer's x scale
     */
    float getScaleX();

    /**
     * @return This renderer's y scale
     */
    float getScaleY();

    /**
     * @return Whether this renderer is mirrored horizontally
     */
    boolean isMirroredHor();

    /**
     * @return Whether this renderer is mirrored vertically
     */
    boolean isMirroredVert();

    /**
     * @return The renderer's current shader program, set using {@link
     * #setProgram(IShaderProgram)}
     */
    IShaderProgram getProgram();

    /**
     * Sets the {@link IShaderProgram} that the renderer should use to draw.
     * Calling this method while the renderer is active will cause the current
     * vertices to be flushed and rendered with the last set shader program.
     *
     * @param program The new shader program
     */
    void setProgram(IShaderProgram program);

    /**
     * @return The renderer's current texture, set using {@link
     * #setTexture(ITexture)}
     */
    ITexture getTexture();

    /**
     * Sets the {@link ITexture} that the renderer should render any vertices
     * with. Calling this method while the renderer is active will cause the
     * current vertices to be flushed and rendererd with the last set texture.
     *
     * @param texture The new texture
     */
    void setTexture(ITexture texture);

    /**
     * Renders a slot. This displays the (by default) green box based on the gui
     * color as well as the item inside and the amount. To make a slot that has
     * functionality, use {@link ItemContainer} to create functioning slots.
     *
     * @param game         The game instance
     * @param manager      The asset manager
     * @param slot         The content of the slot to draw
     * @param x            The x coordinate
     * @param y            The y coordinate
     * @param scale        The scale
     * @param hovered      If the slot is currently being hovered or not. If
     *                     this value is true, then the slot will be rendered
     *                     with a slightly brighter color
     * @param canPlaceInto If the slot can contain the item that is currently on
     *                     the cursor. If this value is true, then the slot will
     *                     be rendered in grayscale
     */
    void renderSlotInGui(IGameInstance game, IAssetManager manager, ItemInstance slot, float x, float y, float scale, boolean hovered, boolean canPlaceInto);

    /**
     * Renders an item and its amount. This is similar to {@link
     * #renderSlotInGui(IGameInstance, IAssetManager, ItemInstance, float,
     * float, float, boolean, boolean)}, only that it doesn't render the box
     * below the item.
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
     * Renders an item and its amount. This is similar to {@link
     * #renderSlotInGui(IGameInstance, IAssetManager, ItemInstance, float,
     * float, float, boolean, boolean)}, only that it doesn't render the box
     * below the item.
     *
     * @param game              The game instance
     * @param manager           The asset manager
     * @param slot              The item to draw
     * @param x                 The x coordinate
     * @param y                 The y coordinate
     * @param scale             The scale
     * @param color             The color to filter the item's renderer with
     * @param displayAmount     If the amount of items in this instance should
     *                          be displayed as a little number below the item
     * @param displayDurability If the durability of the item should be
     *                          displayed
     */
    void renderItemInGui(IGameInstance game, IAssetManager manager, ItemInstance slot, float x, float y, float scale, int color, boolean displayAmount, boolean displayDurability);

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

    /**
     * Activates a certain texture bank. This can be used to draw from multiple
     * textures at the same time, however it requires a custom shader to do so.
     *
     * @param bank The texture bank to read/write to and from
     */
    void activateTextureBank(TextureBank bank);

    /**
     * Unbinds the currently bound texture
     */
    void unbindTexture();

    /**
     * Unbinds all currently bound textures in all banks
     */
    void unbindAllTextures();

    /**
     * Unbinds the currently bound vao
     */
    void unbindVAO();

    /**
     * Unbinds the currently bound vbo
     */
    void unbindVBO();

    /**
     * Unbinds the currently bound shader program
     */
    void unbindShaderProgram();

    /**
     * Creates a new vao for any modder to use
     *
     * @return A new vao
     */
    IVAO createVAO();

    /**
     * Creates a new vbo for any modder to use
     *
     * @param isStatic Wether or not this vbo should use {@link
     *                 GL15#GL_STATIC_DRAW} or {@link GL15#GL_DYNAMIC_DRAW}
     */
    IVBO createVBO(boolean isStatic);

    /**
     * Calculates the gui and world scale. This is an internal method that
     * should not be called by modders.
     */
    @ApiInternal
    void calcScales();

    /**
     * Gets the display's ratio as a float. For example, if the window's current
     * display ratio was 16/9, that is the value that this method would return.
     *
     * @return The display ratio
     */
    float getDisplayRatio();

    /**
     * @return The scale with which the gui is currently being rendered. This
     * factors in both the gui scale option and the {@link #getDisplayRatio()}.
     */
    float getGuiScale();

    /**
     * @return The scale with which the world is currently being rendered. This
     * factors in both the world scale option and the {@link
     * #getDisplayRatio()}.
     */
    float getWorldScale();

    /**
     * @return The width of the currently visible area of the world, in pixels.
     * @see #getWidthInGui()
     */
    float getWidthInWorld();

    /**
     * @return The height of the currently visible area of the world, in pixels.
     * @see #getHeightInGui()
     */
    float getHeightInWorld();

    /**
     * @return The width of the currently visible area of a gui, in pixels. This
     * is {@link IGameInstance#getWidth()} multiplied by {@link
     * #getGuiScale()}.
     * @see #getWidthInWorld()
     */
    float getWidthInGui();

    /**
     * @return The height of the currently visible area of a gui, in pixels.
     * This is {@link IGameInstance#getHeight()} multiplied by {@link
     * #getGuiScale()}.
     * @see #getHeightInWorld()
     */
    float getHeightInGui();

    /**
     * @return The y position of the mouse on gui scale level
     * @see #getGuiScale()
     */
    float getMouseInGuiX();

    /**
     * @return The y position of the mouse on gui scale level
     * @see #getGuiScale()
     */
    float getMouseInGuiY();

    /**
     * @return If the renderer is currently in debug mode, meaning the debug
     * menu with a lot of additional information is open. This is activated by
     * pressing F1.
     */
    boolean isDebug();

    /**
     * @return If the renderer is currently in item info debug mode, meaning
     * hovering over an item in an inventory will reveal additional information
     * about its status. This is activated by pressing F5.
     */
    boolean isItemInfoDebug();

    /**
     * @return If the renderer is currently in chunk border debug mode, meaning
     * that the borders of chunks will be shown in the world as green lines.
     * This is activated by pressing F6.
     */
    boolean isChunkBorderDebug();

    /**
     * @return If the renderer is currently in gui debug mode, meaning that
     * opening and looking at a gui will show the bounding boxes of all of their
     * components, along with their names and positions. This is activated by
     * pressing F4.
     */
    boolean isGuiDebug();

    /**
     * @return If the renderer is currently in line debug mode, meaning that it
     * will render a direct line between the drawn vertices rather than actually
     * filling them with color or a texture. This is activated by pressing F2.
     */
    boolean isLineDebug();

    /**
     * @return If the renderer is currently in biome debug mode, meaning that
     * semi-transparent colored squares will be shown over every tile indicating
     * its biome.
     */
    boolean isBiomeDebug();

    /**
     * @return If the renderer is currently in height debug mode, meaning that a
     * line will be shown on every tile to indicate the height of that position
     * in the chunk.
     */
    boolean isHeightDebug();

    boolean isBoundBoxDebug();

    /**
     * Returns the x position of the tile that the mouse is currently over.
     * Rounding this value down using something like {@link Util#floor(double)}
     * will reveal the tile's grid position in the world, whereas the decimal
     * will reveal the percentage of how far into the tile the mouse cursor is.
     *
     * @return The x position of the moused tile
     */
    double getMousedTileX();

    /**
     * Returns the y position of the tile that the mouse is currently over.
     * Rounding this value down using something like {@link Util#floor(double)}
     * will reveal the tile's grid position in the world, whereas the decimal
     * will reveal the percentage of how far into the tile the mouse cursor is.
     *
     * @return The y position of the moused tile
     */
    double getMousedTileY();

    /**
     * @return The amount of flushes that this renderer has done since the last
     * render frame.
     * @see #flush()
     */
    int getFlushes();

    /**
     * Sets the background color for this renderer to clear the screen with.
     * This is used for the sky and the main menu background's color.
     *
     * @param color The color
     */
    void backgroundColor(int color);

    /**
     * Gets the current vertices that this renderer is about to render. These
     * should only be modified using {@link #put(float)}.
     *
     * @return The vertices
     */
    FloatBuffer getVertices();

    /**
     * @return The amount of vertices that are currently in the buffer
     */
    int getVertexAmount();

    double getCameraX();

    double getCameraY();
}
