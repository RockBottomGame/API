/*
 * This file ("IFont.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.assets.font;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAsset;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.List;

public interface IFont extends IAsset{

    IResourceName ID = RockBottomAPI.createInternalRes("font");

    void drawStringFromRight(float x, float y, String s, float scale);

    void drawCenteredString(float x, float y, String s, float scale, boolean centeredOnY);

    void drawFadingString(float x, float y, String s, float scale, float fadeTotal, float fadeInEnd, float fadeOutStart);

    void drawString(float x, float y, String s, float scale);

    void drawString(float x, float y, String s, float scale, int color);

    void drawCutOffString(float x, float y, String s, float scale, int length, boolean fromRight, boolean basedOnCharAmount);

    void drawSplitString(float x, float y, String s, float scale, int length);

    void drawString(float x, float y, String s, int drawStart, int drawEnd, float scale, int color, int shadowColor);

    void drawString(float x, float y, String s, int drawStart, int drawEnd, float scale, int color);

    void drawAutoScaledString(float x, float y, String s, float maxScale, int width, int color, int shadowColor, boolean centeredOnX, boolean centeredOnY);

    void drawCharacter(float x, float y, char character, float scale, int color, FontProp prop, int shadowColor);

    void drawCharacter(float x, float y, char character, float scale, int color, FontProp prop);

    String removeFormatting(String s);

    float getWidth(String s, float scale);

    float getHeight(float scale);

    List<String> splitTextToLength(int length, float scale, boolean wrapFormatting, String... lines);

    List<String> splitTextToLength(int length, float scale, boolean wrapFormatting, List<String> lines);
}
