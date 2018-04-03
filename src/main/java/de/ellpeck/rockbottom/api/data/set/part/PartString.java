/*
 * This file ("PartString.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data.set.part;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.io.DataInput;
import java.io.DataOutput;

public final class PartString extends BasicDataPart<String>{

    public PartString(String name, String data){
        super(name, data);
    }

    public PartString(String name){
        super(name);
    }

    @Override
    public void write(DataOutput stream) throws Exception{
        stream.writeInt(this.data.length());

        for(char c : this.data.toCharArray()){
            stream.writeChar(c);
        }
    }

    @Override
    public void read(DataInput stream) throws Exception{
        char[] chars = new char[stream.readInt()];

        for(int i = 0; i < chars.length; i++){
            chars[i] = stream.readChar();
        }

        this.data = new String(chars);
    }

    @Override
    public JsonElement write(){
        return new JsonPrimitive(this.data);
    }

    @Override
    public void read(JsonElement element){
        this.data = element.getAsString();
    }
}
