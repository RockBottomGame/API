/*
 * This file ("PartModBasedDataSet.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;

import java.io.DataInput;
import java.io.DataOutput;

public final class PartModBasedDataSet extends BasicDataPart<ModBasedDataSet> {

    public static final IPartFactory<PartModBasedDataSet> FACTORY = new IPartFactory<PartModBasedDataSet>() {
        @Override
        public PartModBasedDataSet parse(JsonElement element) {
            if (element.isJsonObject()) {
                try {
                    ModBasedDataSet set = new ModBasedDataSet();
                    RockBottomAPI.getApiHandler().readDataSet(element.getAsJsonObject(), set);
                    return new PartModBasedDataSet(set);
                } catch (Exception ignored) {
                }
            }
            return null;
        }

        @Override
        public PartModBasedDataSet parse(DataInput stream) throws Exception {
            ModBasedDataSet data = new ModBasedDataSet();
            RockBottomAPI.getApiHandler().readDataSet(stream, data);
            return new PartModBasedDataSet(data);
        }

        @Override
        public int getPriority() {
            return 100;
        }
    };

    public PartModBasedDataSet(ModBasedDataSet data) {
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
    public IPartFactory<? extends DataPart<ModBasedDataSet>> getFactory() {
        return FACTORY;
    }
}
