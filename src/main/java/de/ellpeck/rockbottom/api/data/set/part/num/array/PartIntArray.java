/*
 * This file ("PartIntArray.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data.set.part.num.array;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.data.set.part.BasicDataPart;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.Arrays;

public final class PartIntArray extends BasicDataPart<int[]>{

    public PartIntArray(String name){
        super(name);
    }

    public PartIntArray(String name, int[] data){
        super(name, data);
    }

    @Override
    public void write(DataOutput stream) throws Exception{
        stream.writeInt(this.data.length);
        for(int i : this.data){
            stream.writeInt(i);
        }
    }

    @Override
    public void read(DataInput stream) throws Exception{
        int amount = stream.readInt();
        this.data = new int[amount];

        for(int i = 0; i < amount; i++){
            this.data[i] = stream.readInt();
        }
    }

    @Override
    public JsonElement write(){
        JsonArray array = new JsonArray();
        for(int i = 0; i < this.data.length; i++){
            array.add(this.data[i]);
        }
        return array;
    }

    @Override
    public void read(JsonElement element){
        JsonArray array = element.getAsJsonArray();
        this.data = new int[array.size()];

        for(int i = 0; i < array.size(); i++){
            this.data[i] = array.get(i).getAsInt();
        }
    }

    @Override
    public String toString(){
        return Arrays.toString(this.data);
    }
}
