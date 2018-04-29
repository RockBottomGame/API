/*
 * This file ("PartDataSet.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.data.set.DataSet;

import java.io.DataInput;
import java.io.DataOutput;

public final class PartDataSet extends BasicDataPart<DataSet>{

    public PartDataSet(String name){
        super(name);
    }

    public PartDataSet(String name, DataSet data){
        super(name, data);
    }

    @Override
    public void write(DataOutput stream) throws Exception{
        this.data.write(stream);
    }

    @Override
    public void read(DataInput stream) throws Exception{
        this.data = new DataSet();
        this.data.read(stream);
    }

    @Override
    public JsonElement write() throws Exception{
        JsonObject object = new JsonObject();
        this.data.write(object);
        return object;
    }

    @Override
    public void read(JsonElement element) throws Exception{
        this.data = new DataSet();
        this.data.read(element.getAsJsonObject());
    }
}
