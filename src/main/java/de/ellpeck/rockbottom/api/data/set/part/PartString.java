/*
 * This file ("PartString.java") is part of the RockBottomAPI by Ellpeck.
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

public final class PartString extends BasicDataPart<String> {

    public static final IPartFactory<PartString> FACTORY = new IPartFactory<PartString>() {
        @Override
        public PartString parse(JsonElement element) {
            if (element.isJsonPrimitive()) {
                JsonPrimitive prim = element.getAsJsonPrimitive();
                if (prim.isString()) {
                    return new PartString(prim.getAsString());
                }
            }
            return null;
        }

        @Override
        public PartString parse(DataInput stream) throws Exception {
            char[] chars = new char[stream.readInt()];
            for (int i = 0; i < chars.length; i++) {
                chars[i] = stream.readChar();
            }
            return new PartString(new String(chars));
        }

        @Override
        public int getPriority() {
            return -50;
        }
    };

    public PartString(String data) {
        super(data);
    }

    @Override
    public void write(DataOutput stream) throws Exception {
        stream.writeInt(this.data.length());

        for (char c : this.data.toCharArray()) {
            stream.writeChar(c);
        }
    }

    @Override
    public JsonElement write() {
        return new JsonPrimitive(this.data);
    }

    @Override
    public IPartFactory<? extends DataPart<String>> getFactory() {
        return FACTORY;
    }
}
