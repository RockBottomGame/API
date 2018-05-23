/*
 * This file ("PartShort.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data.set.part.num;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import de.ellpeck.rockbottom.api.data.set.part.BasicDataPart;
import de.ellpeck.rockbottom.api.data.set.part.DataPart;
import de.ellpeck.rockbottom.api.data.set.part.IPartFactory;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Locale;

public final class PartShort extends BasicDataPart<Short>{

    public static final IPartFactory<PartShort> FACTORY = new IPartFactory<PartShort>(){
        @Override
        public PartShort parse(String name, JsonElement element){
            if(element.isJsonPrimitive()){
                JsonPrimitive prim = element.getAsJsonPrimitive();
                if(prim.isString()){
                    String string = prim.getAsString().toLowerCase(Locale.ROOT);
                    if(string.endsWith("s")){
                        try{
                            return new PartShort(name, Short.parseShort(string.substring(0, string.length()-1)));
                        }
                        catch(Exception ignored){
                        }
                    }
                }
            }
            return null;
        }

        @Override
        public PartShort parse(String name, DataInput stream) throws Exception{
            return new PartShort(name, stream.readShort());
        }
    };

    public PartShort(String name, Short data){
        super(name, data);
    }

    @Override
    public void write(DataOutput stream) throws IOException{
        stream.writeShort(this.data);
    }

    @Override
    public JsonElement write(){
        return new JsonPrimitive(this.data);
    }

    @Override
    public IPartFactory<? extends DataPart<Short>> getFactory(){
        return FACTORY;
    }
}
