/*
 * This file ("PartList.java") is part of the RockBottomAPI by Ellpeck.
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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.ArrayList;
import java.util.List;

public class PartList extends BasicDataPart<List<? extends DataPart>> {

    public static final IPartFactory<PartList> FACTORY = new IPartFactory<PartList>() {
        @Override
        public PartList parse(JsonElement element) throws Exception {
            if (element.isJsonArray()) {
                List<DataPart> parts = new ArrayList<>();
                for (JsonElement e : element.getAsJsonArray()) {
                    parts.add(RockBottomAPI.getApiHandler().readDataPart(e));
                }
                return new PartList(parts);
            } else {
                return null;
            }
        }

        @Override
        public PartList parse(DataInput stream) throws Exception {
            List<DataPart> parts = new ArrayList<>();
            int size = stream.readInt();
            if (size > 0) {
                IPartFactory factory = Registries.PART_REGISTRY.get((int) stream.readByte());
                for (int i = 0; i < size; i++) {
                    parts.add(factory.parse(stream));
                }
            }
            return new PartList(parts);
        }
    };

    public PartList(List<? extends DataPart> data) {
        super(data);
    }

    @Override
    public void write(DataOutput stream) throws Exception {
        stream.writeInt(this.data.size());
        for (int i = 0; i < this.data.size(); i++) {
            DataPart part = this.data.get(i);
            if (i == 0) {
                stream.writeByte(Registries.PART_REGISTRY.getId(part.getFactory()));
            }
            part.write(stream);
        }
    }

    @Override
    public JsonElement write() throws Exception {
        JsonArray array = new JsonArray();
        for (DataPart part : this.data) {
            array.add(part.write());
        }
        return array;
    }

    @Override
    public IPartFactory<? extends DataPart<List<? extends DataPart>>> getFactory() {
        return FACTORY;
    }
}
