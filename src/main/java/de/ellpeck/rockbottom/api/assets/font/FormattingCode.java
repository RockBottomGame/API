/*
 * This file ("FormattingCode.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame>.
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

import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.Util;

public class FormattingCode{

    public static final FormattingCode NONE = new FormattingCode(' ', -1, 0, "");
    public static final FormattingCode BLACK = new FormattingCode('0', Colors.BLACK, 2);
    public static final FormattingCode DARK_GRAY = new FormattingCode('1', Colors.DARK_GRAY, 2);
    public static final FormattingCode GRAY = new FormattingCode('2', Colors.GRAY, 2);
    public static final FormattingCode LIGHT_GRAY = new FormattingCode('3', Colors.LIGHT_GRAY, 2);
    public static final FormattingCode WHITE = new FormattingCode('4', Colors.WHITE, 2);
    public static final FormattingCode YELLOW = new FormattingCode('5', Colors.YELLOW, 2);
    public static final FormattingCode ORANGE = new FormattingCode('6', Colors.ORANGE, 2);
    public static final FormattingCode RED = new FormattingCode('7', Colors.RED, 2);
    public static final FormattingCode PINK = new FormattingCode('8', Colors.PINK, 2);
    public static final FormattingCode MAGENTA = new FormattingCode('9', Colors.MAGENTA, 2);
    public static final FormattingCode GREEN = new FormattingCode('a', Colors.GREEN, 2);

    public static final FormattingCode[] DEFAULT_CODES = new FormattingCode[]{BLACK, DARK_GRAY, GRAY, LIGHT_GRAY, WHITE, YELLOW, ORANGE, RED, PINK, MAGENTA, GREEN};

    private final char format;
    private final int color;
    private final int length;
    private final String strg;

    public FormattingCode(char format, int color, int length){
        this(format, color, length, "&"+format);
    }

    public FormattingCode(char format, int color, int length, String strg){
        this.format = format;
        this.color = color;
        this.length = length;
        this.strg = strg;
    }

    public static FormattingCode getFormat(String s, int index){
        if(s.length() > index+1 && s.charAt(index) == '&'){
            char formatChar = s.charAt(index+1);

            if(formatChar == '('){
                int closingIndex = s.indexOf(")", index+2);
                if(closingIndex > index+2){
                    String code = s.substring(index+2, closingIndex);
                    String[] colors = code.split(",");

                    if(colors.length == 3){
                        try{
                            return new FormattingCode(' ', Colors.rgb(Float.parseFloat(colors[0]), Float.parseFloat(colors[1]), Float.parseFloat(colors[2])), code.length()+3, "&("+code+")");
                        }
                        catch(Exception ignored){
                        }
                    }
                }
            }
            else if(formatChar == 'r'){
                return new FormattingCode('r', Colors.rainbow((Util.getTimeMillis()/10)%256), 2);
            }
            else{
                for(FormattingCode code : DEFAULT_CODES){
                    if(formatChar == code.format){
                        return code;
                    }
                }
            }
        }
        return NONE;
    }

    public int getColor(){
        return this.color;
    }

    public int getLength(){
        return this.length;
    }

    @Override
    public String toString(){
        return this.strg;
    }
}
