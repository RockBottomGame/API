/*
 * This file ("PartShortArray.java") is part of the RockBottomAPI by Ellpeck.
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
import com.google.gson.JsonPrimitive;
import de.ellpeck.rockbottom.api.data.set.part.BasicDataPart;
import de.ellpeck.rockbottom.api.data.set.part.DataPart;
import de.ellpeck.rockbottom.api.data.set.part.IPartFactory;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.Arrays;
import java.util.Locale;

public final class PartShortArray extends BasicDataPart<short[]>{

    public static final IPartFactory<PartShortArray> FACTORY = new IPartFactory<PartShortArray>(){
        @Override
        public PartShortArray parse(String name, JsonElement element){
            if(element.isJsonArray()){
                JsonArray array = element.getAsJsonArray();
                if(array.size() > 0){
                    JsonElement first = array.get(0);
                    if(first.isJsonPrimitive()){
                        JsonPrimitive prim = first.getAsJsonPrimitive();
                        if(prim.isString()){
                            if(prim.getAsString().toLowerCase(Locale.ROOT).endsWith("s")){
                                try{
                                    short[] data = new short[array.size()];
                                    for(int i = 0; i < array.size(); i++){
                                        String string = array.get(i).getAsString();
                                        data[i] = Short.parseShort(string.substring(0, string.length()-1));
                                    }
                                    return new PartShortArray(name, data);
                                }
                                catch(Exception ignored){
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }

        @Override
        public PartShortArray parse(String name, DataInput stream) throws Exception{
            int amount = stream.readInt();
            short[] data = new short[amount];
            for(int i = 0; i < amount; i++){
                data[i] = stream.readShort();
            }
            return new PartShortArray(name, data);
        }
    };

    public PartShortArray(String name, short[] data){
        super(name, data);
    }

    @Override
    public void write(DataOutput stream) throws Exception{
        stream.writeInt(this.data.length);
        for(int i : this.data){
            stream.writeShort(i);
        }
    }

    @Override
    public JsonElement write(){
        JsonArray array = new JsonArray();
        for(int i = 0; i < this.data.length; i++){
            array.add(this.data[i]+"s");
        }
        return array;
    }

    @Override
    public String toString(){
        return Arrays.toString(this.data);
    }

    @Override
    public IPartFactory<? extends DataPart<short[]>> getFactory(){
        return FACTORY;
    }
}
