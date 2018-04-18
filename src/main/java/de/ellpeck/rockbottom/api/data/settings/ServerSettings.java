/*
 * This file ("ServerSettings.java") is part of the RockBottomAPI by Ellpeck.
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

import com.google.gson.JsonObject;
import de.ellpeck.rockbottom.api.data.IDataManager;
import de.ellpeck.rockbottom.api.util.ApiInternal;

import java.io.File;

@ApiInternal
public final class ServerSettings implements IJsonSettings{

    public int autosaveIntervalSeconds;
    public int maxPlayerAmount;
    public String worldName;

    @Override
    public void load(JsonObject object){
        this.autosaveIntervalSeconds = this.get(object, "autosave_interval", 60);
        this.maxPlayerAmount = this.get(object, "max_players", 100);
        this.worldName = this.get(object, "world_name", "world_server");
    }

    @Override
    public void save(JsonObject object){
        this.set(object, "autosave_interval", this.autosaveIntervalSeconds);
        this.set(object, "max_players", this.maxPlayerAmount);
        this.set(object, "world_name", this.worldName);
    }

    @Override
    public File getSettingsFile(IDataManager manager){
        return manager.getServerSettingsFile();
    }

    @Override
    public String getName(){
        return "Server settings";
    }
}
