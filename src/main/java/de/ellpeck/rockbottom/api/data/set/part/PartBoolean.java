/*
 * This file ("PartBoolean.java") is part of the RockBottomAPI by Ellpeck.
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
import com.google.gson.JsonPrimitive;

import java.io.DataInput;
import java.io.DataOutput;

public final class PartBoolean extends BasicDataPart<Boolean> {

    public static final IPartFactory<PartBoolean> FACTORY = new IPartFactory<PartBoolean>() {
        @Override
        public PartBoolean parse(JsonElement element) {
            if (element.isJsonPrimitive()) {
                JsonPrimitive prim = element.getAsJsonPrimitive();
                if (prim.isBoolean()) {
                    return new PartBoolean(prim.getAsBoolean());
                }
            }
            return null;
        }

        @Override
        public PartBoolean parse(DataInput stream) throws Exception {
            return new PartBoolean(stream.readBoolean());
        }
    };

    public PartBoolean(Boolean data) {
        super(data);
    }

    @Override
    public void write(DataOutput stream) throws Exception {
        stream.writeBoolean(this.data);
    }

    @Override
    public JsonElement write() {
        return new JsonPrimitive(this.data);
    }

    @Override
    public IPartFactory getFactory() {
        return FACTORY;
    }
}
