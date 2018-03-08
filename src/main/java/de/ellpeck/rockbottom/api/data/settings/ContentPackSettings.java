/*
 * This file ("ContentPackSettings.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.ellpeck.rockbottom.api.content.pack.ContentPack;
import de.ellpeck.rockbottom.api.data.IDataManager;
import de.ellpeck.rockbottom.api.util.ApiInternal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ApiInternal
public class ContentPackSettings implements IJsonSettings{

    private final List<SettingsEntry> enabledPacks = new ArrayList<>();
    private final SettingsEntry defaultPack = new SettingsEntry(ContentPack.DEFAULT_PACK_ID, 0);

    @Override
    public void load(JsonObject object){
        this.enabledPacks.clear();

        if(object.has("packs")){
            JsonArray array = object.get("packs").getAsJsonArray();
            for(JsonElement element : array){
                JsonObject entryObj = element.getAsJsonObject();
                this.enabledPacks.add(new SettingsEntry(entryObj.get("id").getAsString(), entryObj.get("prio").getAsInt()));
            }
        }
    }

    @Override
    public void save(JsonObject object){
        JsonArray array = new JsonArray();
        for(SettingsEntry entry : this.enabledPacks){
            JsonObject entryObj = new JsonObject();
            entryObj.addProperty("id", entry.id);
            entryObj.addProperty("prio", entry.priority);
            array.add(entryObj);
        }
        object.add("packs", array);
    }

    @Override
    public File getSettingsFile(IDataManager manager){
        return manager.getContentPackSettingsFile();
    }

    @Override
    public String getName(){
        return "Content pack settings";
    }

    public boolean isDisabled(String id){
        return this.getEntry(id) == null;
    }

    public int getPriority(String id){
        SettingsEntry entry = this.getEntry(id);
        return entry == null ? Integer.MIN_VALUE : entry.priority;
    }

    private SettingsEntry getEntry(String id){
        if(id.equals(this.defaultPack.id)){
            return this.defaultPack;
        }
        else{
            for(SettingsEntry entry : this.enabledPacks){
                if(entry.id.equals(id)){
                    return entry;
                }
            }
            return null;
        }
    }

    private static class SettingsEntry{

        public final String id;
        public final int priority;

        public SettingsEntry(String id, int priority){
            this.id = id;
            this.priority = priority;
        }
    }
}
