/*
 * This file ("NameToIndexInfo.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.util.reg;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.ellpeck.rockbottom.api.data.IDataManager;
import de.ellpeck.rockbottom.api.data.settings.IJsonSettings;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import io.netty.buffer.ByteBuf;

import java.io.File;
import java.util.Map;

@ApiInternal
public final class NameToIndexInfo implements IJsonSettings {

    private final IndexRegistry<ResourceName> reg;
    private final File legacyFile;
    private final File file;
    private boolean needsSave;

    public NameToIndexInfo(String name, File legacyFile, File file, int max) {
        this.legacyFile = legacyFile;
        this.file = file;
        this.reg = new IndexRegistry<>(name, max, false);
    }

    public <T> void populate(NameRegistry<T> registry) {
        for (Map.Entry<ResourceName, T> entry : registry.map.entrySet()) {
            ResourceName key = entry.getKey();

            if (this.getId(key) < 0) {
                this.reg.registerNextFree(key);
                this.needsSave = true;
            }
        }
    }

    public int getId(ResourceName name) {
        return this.reg.getId(name);
    }

    public ResourceName get(int id) {
        return this.reg.get(id);
    }

    public boolean needsSave() {
        return this.needsSave;
    }

    public void fromBuffer(ByteBuf buf) {
        this.reg.map.clear();

        int amount = buf.readInt();
        for (int i = 0; i < amount; i++) {
            this.reg.map.put(buf.readInt(), new ResourceName(NetUtil.readStringFromBuffer(buf)));
        }
    }

    public void toBuffer(ByteBuf buf) {
        buf.writeInt(this.reg.getSize());

        for (Map.Entry<Integer, ResourceName> entry : this.reg.map.entrySet()) {
            buf.writeInt(entry.getKey());
            NetUtil.writeStringToBuffer(entry.getValue().toString(), buf);
        }
    }

    @Override
    public void load(JsonObject object) {
        this.reg.map.clear();

        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            ResourceName name = new ResourceName(entry.getKey());
            int id = entry.getValue().getAsInt();
            this.reg.map.put(id, name);
        }
    }

    @Override
    public void save(JsonObject object) {
        for (Map.Entry<Integer, ResourceName> entry : this.reg.map.entrySet()) {
            object.addProperty(entry.getValue().toString(), entry.getKey());
        }
        this.needsSave = false;
    }

    @Override
    public File getSettingsFile(IDataManager manager) {
        return this.file;
    }

    @Override
    public String getName() {
        return "Name to index info " + this.reg;
    }
}
