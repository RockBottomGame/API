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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.data.set.part;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;

import java.io.DataInput;
import java.io.DataOutput;

public final class PartDataSet extends BasicDataPart<DataSet> {

    public static final IPartFactory<PartDataSet> FACTORY = new IPartFactory<PartDataSet>() {
        @Override
        public PartDataSet parse(JsonElement element) {
            if (element.isJsonObject()) {
                try {
                    DataSet set = new DataSet();
                    RockBottomAPI.getApiHandler().readDataSet(element.getAsJsonObject(), set);
                    return new PartDataSet(set);
                } catch (Exception ignored) {
                }
            }
            return null;
        }

        @Override
        public PartDataSet parse(DataInput stream) throws Exception {
            DataSet data = new DataSet();
            RockBottomAPI.getApiHandler().readDataSet(stream, data);
            return new PartDataSet(data);
        }
    };

    public PartDataSet(DataSet data) {
        super(data);
    }

    @Override
    public void write(DataOutput stream) throws Exception {
        RockBottomAPI.getApiHandler().writeDataSet(stream, this.data);
    }

    @Override
    public JsonElement write() throws Exception {
        JsonObject object = new JsonObject();
        RockBottomAPI.getApiHandler().writeDataSet(object, this.data);
        return object;
    }

    @Override
    public IPartFactory getFactory() {
        return FACTORY;
    }
}
