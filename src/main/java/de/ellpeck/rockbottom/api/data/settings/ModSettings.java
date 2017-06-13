/*
 * This file ("ModSettings.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 */

package de.ellpeck.rockbottom.api.data.settings;

import de.ellpeck.rockbottom.api.data.IDataManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ModSettings implements IPropSettings{

    private final Map<String, Boolean> disabledMods = new HashMap<>();

    @Override
    public void load(Properties props){
        this.disabledMods.clear();

        for(String key : props.stringPropertyNames()){
            boolean disabled = Boolean.parseBoolean(props.getProperty(key));
            this.disabledMods.put(key, disabled);
        }
    }

    @Override
    public void save(Properties props){
        for(Map.Entry<String, Boolean> entry : this.disabledMods.entrySet()){
            props.setProperty(entry.getKey(), entry.getValue().toString());
        }
    }

    @Override
    public File getFile(IDataManager manager){
        return manager.getModSettingsFile();
    }

    @Override
    public String getName(){
        return "Mod settings";
    }

    public boolean isDisabled(String modid){
        return this.disabledMods.containsKey(modid);
    }

    public void setDisabled(String modid, boolean disabled){
        this.disabledMods.put(modid, disabled);
    }
}
