/*
 * This file ("PartUniqueId.java") is part of the RockBottomAPI by Ellpeck.
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
import com.google.gson.JsonObject;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.UUID;

public class PartUniqueId extends BasicDataPart<UUID>{

    public PartUniqueId(String name, UUID data){
        super(name, data);
    }

    public PartUniqueId(String name){
        super(name);
    }

    @Override
    public void write(DataOutput stream) throws Exception{
        stream.writeLong(this.data.getMostSignificantBits());
        stream.writeLong(this.data.getLeastSignificantBits());
    }

    @Override
    public void read(DataInput stream) throws Exception{
        this.data = new UUID(stream.readLong(), stream.readLong());
    }

    @Override
    public JsonElement write() throws Exception{
        JsonObject object = new JsonObject();
        object.addProperty("most", this.data.getMostSignificantBits());
        object.addProperty("least", this.data.getLeastSignificantBits());
        return object;
    }

    @Override
    public void read(JsonElement element) throws Exception{
        JsonObject object = element.getAsJsonObject();
        this.data = new UUID(object.get("most").getAsLong(), object.get("least").getAsLong());
    }
}
