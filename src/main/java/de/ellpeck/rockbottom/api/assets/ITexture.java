/*
 * This file ("ITexture.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.assets;

import com.google.gson.JsonElement;

import java.nio.ByteBuffer;
import java.util.Random;

public interface ITexture extends IAsset{

    int TOP_LEFT = 0;
    int BOTTOM_LEFT = 1;
    int BOTTOM_RIGHT = 2;
    int TOP_RIGHT = 3;

    void bind();

    void param(int param, int value);

    int getId();

    int getWidth();

    int getHeight();

    ByteBuffer getPixelData();

    void unbind();

    void draw(float x, float y);

    void draw(float x, float y, float scale);

    void draw(float x, float y, float width, float height);

    void draw(float x, float y, float width, float height, int[] light);

    void draw(float x, float y, float width, float height, int filter);

    void draw(float x, float y, float width, float height, int[] light, int filter);

    void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2);

    void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light);

    void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int filter);

    void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light, int filter);

    JsonElement getAdditionalData(String name);

    ITexture getVariation(Random random);

    ITexture getPositionalVariation(int x, int y);

    ITexture getSubTexture(float x, float y, float width, float height);

    ITexture getSubTexture(float x, float y, float width, float height, boolean inheritVariations, boolean inheritData);

    int getTextureColor(int x, int y);
}
