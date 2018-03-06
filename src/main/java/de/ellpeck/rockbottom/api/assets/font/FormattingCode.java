/*
 * This file ("FormattingCode.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.util.Colors;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class FormattingCode{

    private static final Map<Character, FormattingCode> DEFAULT_CODES = new TreeMap<>(Character:: compare);

    public static final FormattingCode NONE = new FormattingCode(' ', Colors.NO_COLOR, FontProp.NONE, 0, "");
    public static final FormattingCode RESET_COLOR = new FormattingCode('y', Colors.RESET_COLOR).registerAsDefault();
    public static final FormattingCode RESET_PROPS = new FormattingCode('x', FontProp.RESET).registerAsDefault();

    public static final FormattingCode BLACK = new FormattingCode('0', Colors.BLACK).registerAsDefault();
    public static final FormattingCode DARK_GRAY = new FormattingCode('1', Colors.DARK_GRAY).registerAsDefault();
    public static final FormattingCode GRAY = new FormattingCode('2', Colors.GRAY).registerAsDefault();
    public static final FormattingCode LIGHT_GRAY = new FormattingCode('3', Colors.LIGHT_GRAY).registerAsDefault();
    public static final FormattingCode WHITE = new FormattingCode('4', Colors.WHITE).registerAsDefault();
    public static final FormattingCode YELLOW = new FormattingCode('5', Colors.YELLOW).registerAsDefault();
    public static final FormattingCode ORANGE = new FormattingCode('6', Colors.ORANGE).registerAsDefault();
    public static final FormattingCode RED = new FormattingCode('7', Colors.RED).registerAsDefault();
    public static final FormattingCode PINK = new FormattingCode('8', Colors.PINK).registerAsDefault();
    public static final FormattingCode MAGENTA = new FormattingCode('9', Colors.MAGENTA).registerAsDefault();
    public static final FormattingCode GREEN = new FormattingCode('a', Colors.GREEN).registerAsDefault();

    public static final FormattingCode UNDERLINED = new FormattingCode('u', FontProp.UNDERLINED).registerAsDefault();
    public static final FormattingCode STRIKETHROUGH = new FormattingCode('s', FontProp.STRIKETHROUGH).registerAsDefault();
    public static final FormattingCode ITALICS = new FormattingCode('i', FontProp.ITALICS).registerAsDefault();
    public static final FormattingCode UPSIDE_DOWN = new FormattingCode('t', FontProp.UPSIDE_DOWN).registerAsDefault();
    public static final FormattingCode BOLD = new FormattingCode('b', FontProp.BOLD).registerAsDefault();
    public static final FormattingCode RANDOM = new FormattingCode('c', FontProp.RANDOM).registerAsDefault();

    private final char format;
    private final int color;
    private final int length;
    private final String strg;
    private final FontProp prop;

    public FormattingCode(char format, FontProp prop){
        this(format, Colors.NO_COLOR, prop, 2, "&"+format);
    }

    public FormattingCode(char format, int color){
        this(format, color, FontProp.NONE, 2, "&"+format);
    }

    public FormattingCode(char format, int color, FontProp prop, int length, String strg){
        this.format = format;
        this.color = color;
        this.length = length;
        this.strg = strg;
        this.prop = prop;
    }

    public static FormattingCode getFormat(String s, int index){
       return RockBottomAPI.getInternalHooks().getFormattingCode(s, index, DEFAULT_CODES);
    }

    public FormattingCode registerAsDefault(){
        if(this.format != ' ' && !DEFAULT_CODES.containsKey(this.format)){
            DEFAULT_CODES.put(this.format, this);
            return this;
        }
        else{
            throw new RuntimeException("Tried to register two formatting codes as a default with key "+this.format+"!");
        }
    }

    public int getColor(){
        return this.color;
    }

    public FontProp getProp(){
        return this.prop;
    }

    public int getLength(){
        return this.length;
    }

    @Override
    public String toString(){
        return this.strg;
    }

    public static Map<Character, FormattingCode> getDefaultCodes(){
        return Collections.unmodifiableMap(DEFAULT_CODES);
    }
}
