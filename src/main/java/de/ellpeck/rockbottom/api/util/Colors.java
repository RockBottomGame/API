/*
 * This file ("Colors.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.util;

import org.lwjgl.opengl.GL11;

import java.util.Random;

public final class Colors {

    public static final int NO_COLOR = Integer.MAX_VALUE;
    public static final int RESET_COLOR = Integer.MIN_VALUE;

    public static final int TRANSPARENT = 0x00FFFFFF;
    public static final int WHITE = 0xFFFFFFFF;
    public static final int BLACK = 0xFF000000;
    public static final int DARK_GRAY = 0xFF404040;
    public static final int GRAY = 0xFF808080;
    public static final int LIGHT_GRAY = 0xFFC0C0C0;
    public static final int YELLOW = 0xFFFFD800;
    public static final int ORANGE = 0xFFFF6A00;
    public static final int RED = 0xFFFF0000;
    public static final int PINK = 0xFFFF006E;
    public static final int MAGENTA = 0xFFFF00DC;
    public static final int GREEN = 0xFF00820F;
    public static final int PURPLE = 0xFFB200FF;
    public static final int CYAN = 0xFF00FFFF;
    public static final int BROWN = 0xFF7F5736;
    public static final int LIME = 0xFF00FF21;
    public static final int BLUE = 0xFF0000FF;
    public static final int LIGHT_BLUE = 0xFF0094FF;

    public static int rgb(int r, int g, int b) {
        return rgb(r, g, b, 255);
    }

    public static int rgb(int r, int g, int b, int a) {
        return ((a & 255) << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255);
    }

    public static int rgb(float red, float green, float blue) {
        return rgb((int) (red * 255F), (int) (green * 255F), (int) (blue * 255F), 255);
    }

    public static int rgb(float red, float green, float blue, float alpha) {
        return rgb((int) (red * 255F), (int) (green * 255F), (int) (blue * 255F), (int) (alpha * 255F));
    }

    public static float getR(int color) {
        return (float) getRInt(color) / 255F;
    }

    public static float getG(int color) {
        return (float) getGInt(color) / 255F;
    }

    public static float getB(int color) {
        return (float) getBInt(color) / 255F;
    }

    public static float getA(int color) {
        return (float) getAInt(color) / 255F;
    }

    public static int getRInt(int color) {
        return (color >> 16) & 255;
    }

    public static int getGInt(int color) {
        return (color >> 8) & 255;
    }

    public static int getBInt(int color) {
        return color & 255;
    }

    public static int getAInt(int color) {
        return (color >> 24) & 255;
    }

    public static int setA(int color, float a) {
        return rgb(getR(color), getG(color), getB(color), a);
    }

    public static int multiplyA(int color, float multiplier) {
        return setA(color, getA(color) * multiplier);
    }

    public static int random(Random rand) {
        return rgb(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    public static String toFormattingCode(int color) {
        return toFormattingCode(getR(color), getG(color), getB(color));
    }

    public static String toFormattingCode(float r, float g, float b) {
        return "&(" + r + ',' + g + ',' + b + ')';
    }

    public static void bind(float r, float g, float b, float a) {
        GL11.glColor4f(r, g, b, a);
    }

    public static void bind(int color) {
        bind(getR(color), getG(color), getB(color), getA(color));
    }

    public static int lerp(int c1, int c2, float factor) {
        return lerp(getR(c1), getG(c1), getB(c1), getA(c1), getR(c2), getG(c2), getB(c2), getA(c2), factor);
    }

    public static int lerp(float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2, float factor) {
        float fRev = 1F - factor;
        return rgb(r1 * fRev + r2 * factor, g1 * fRev + g2 * factor, b1 * fRev + b2 * factor, a1 * fRev + a2 * factor);
    }

    public static int multiply(int color, float factor) {
        return rgb(getR(color) * factor, getG(color) * factor, getB(color) * factor);
    }

    public static int multiply(int c1, int c2) {
        return multiply(getR(c1), getG(c1), getB(c1), getA(c1), getR(c2), getG(c2), getB(c2), getA(c2));
    }

    public static int multiply(float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        return rgb(r1 * r2, g1 * g2, b1 * b2, a1 * a2);
    }

    public static int multiply(int color, float r, float g, float b, float a) {
        return rgb(getR(color) * r, getG(color) * g, getB(color) * b, getA(color) * a);
    }

    public static int rainbow(float pos) {
        if (pos < 85F) {
            return rgb((pos * 3F) / 255F, (255F - pos * 3F) / 255F, 0F);
        } else if (pos < 170F) {
            return rgb((255F - (pos -= 85F) * 3F) / 255F, 0F, (pos * 3F) / 255F);
        } else {
            return rgb(0F, ((pos -= 170F) * 3F) / 255F, (255F - pos * 3F) / 255F);
        }
    }
}
