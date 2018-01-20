/*
 * This file ("TextureBank.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.render.engine;

import org.lwjgl.opengl.GL13;

/**
 * Texture banks as defined by {@link GL13#GL_TEXTURE0} through {@link
 * GL13#GL_TEXTURE31}. As these texture banks are not necessarily all supported
 * by graphics cards (as documented in {@link GL13#glActiveTexture(int)}, please
 * use these sparingly and try to stay within the first eight or so banks.
 */
public enum TextureBank{
    BANK_1,
    BANK_2,
    BANK_3,
    BANK_4,
    BANK_5,
    BANK_6,
    BANK_7,
    BANK_8,
    BANK_9,
    BANK_10,
    BANK_11,
    BANK_12,
    BANK_13,
    BANK_14,
    BANK_15,
    BANK_16,
    BANK_17,
    BANK_18,
    BANK_19,
    BANK_20,
    BANK_21,
    BANK_22,
    BANK_23,
    BANK_24,
    BANK_25,
    BANK_26,
    BANK_27,
    BANK_28,
    BANK_29,
    BANK_30,
    BANK_31,
    BANK_32;

    public static final TextureBank[] BANKS = values();
}
