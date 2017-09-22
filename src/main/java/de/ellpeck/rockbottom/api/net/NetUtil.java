/*
 * This file ("NetUtil.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.net;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.util.logging.Level;

public final class NetUtil{

    public static void writeSetToBuffer(DataSet set, ByteBuf buf){
        try{
            RockBottomAPI.getApiHandler().writeSet(new ByteBufOutputStream(buf), set);
        }
        catch(Exception e){
            RockBottomAPI.logger().log(Level.SEVERE, "Couldn't write data set to buffer", e);
        }
    }

    public static void readSetFromBuffer(DataSet set, ByteBuf buf){
        try{
            RockBottomAPI.getApiHandler().readSet(new ByteBufInputStream(buf), set);
        }
        catch(Exception e){
            RockBottomAPI.logger().log(Level.SEVERE, "Couldn't read data set from buffer", e);
        }
    }

    public static void writeStringToBuffer(String s, ByteBuf buf){
        buf.writeInt(s.length());

        for(char c : s.toCharArray()){
            buf.writeChar(c);
        }
    }

    public static String readStringFromBuffer(ByteBuf buf){
        char[] chars = new char[buf.readInt()];

        for(int i = 0; i < chars.length; i++){
            chars[i] = buf.readChar();
        }

        return new String(chars);
    }
}
