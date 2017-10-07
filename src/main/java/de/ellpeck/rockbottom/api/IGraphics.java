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

    void renderSlotInGui(IGameInstance game, IAssetManager manager, ItemInstance slot, float x, float y, float scale, boolean hovered);

    void renderItemInGui(IGameInstance game, IAssetManager manager, ItemInstance slot, float x, float y, float scale, int color);

    void describeItem(IGameInstance game, IAssetManager manager, ItemInstance instance);

    void drawHoverInfoAtMouse(IGameInstance game, IAssetManager manager, boolean firstLineOffset, int maxLength, String... text);

    void drawHoverInfoAtMouse(IGameInstance game, IAssetManager manager, boolean firstLineOffset, int maxLength, List<String> text);

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
