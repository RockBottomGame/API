/*
 * This file ("IAnimation.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.assets.anim;

import de.ellpeck.rockbottom.api.assets.tex.ITexture;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IAnimation{

    void setFrameFinishedCallback(BiConsumer<AnimationRow, Integer> consumer);

    void setRowFinishedCallback(Consumer<AnimationRow> consumer);

    void drawFrame(int row, int frame, float x, float y, float scale, int filter);

    void drawFrame(int row, int frame, float x, float y, float scale, int[] light, int filter);

    void drawFrame(int row, int frame, float x1, float y1, float x2, float y2, float srcX1, float srcY1, float srcX2, float srcY2, int[] light, int filter);

    void drawRow(int row, float x, float y, float scale, int filter);

    void drawRow(int row, float x, float y, float scale, int[] light, int filter);

    void drawRow(int row, float x1, float y1, float x2, float y2, float srcX1, float srcY1, float srcX2, float srcY2, int[] light, int filter);

    void drawRow(long timeOffsetMillis, int row, float x, float y, float scale, int filter);

    void drawRow(long timeOffsetMillis, int row, float x, float y, float scale, int[] light, int filter);

    void drawRow(long timeOffsetMillis, int row, float x1, float y1, float x2, float y2, float srcX1, float srcY1, float srcX2, float srcY2, int[] light, int filter);

    ITexture getTexture();
}
