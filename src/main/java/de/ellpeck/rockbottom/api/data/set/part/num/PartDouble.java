/*
 * This file ("PartDouble.java") is part of the RockBottomAPI by Ellpeck.
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
import java.util.Locale;

public final class PartDouble extends BasicDataPart<Double> {

    public static final IPartFactory<PartDouble> FACTORY = new IPartFactory<PartDouble>() {
        @Override
        public PartDouble parse(String name, JsonElement element) {
            if (element.isJsonPrimitive()) {
                JsonPrimitive prim = element.getAsJsonPrimitive();
                if (prim.isString()) {
                    String string = prim.getAsString().toLowerCase(Locale.ROOT);
                    if (string.endsWith("d")) {
                        try {
                            return new PartDouble(name, Double.parseDouble(string));
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
            return null;
        }

        @Override
        public PartDouble parse(String name, DataInput stream) throws Exception {
            return new PartDouble(name, stream.readDouble());
        }
    };

    public PartDouble(String name, Double data) {
        super(name, data);
    }

    @Override
    public void write(DataOutput stream) throws Exception {
        stream.writeDouble(this.data);
    }

    @Override
    public JsonElement write() {
        return new JsonPrimitive(this.data + "d");
    }

    @Override
    public IPartFactory<? extends DataPart<Double>> getFactory() {
        return FACTORY;
    }
}
