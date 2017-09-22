/*
 * This file ("ModSettings.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data.settings;

import de.ellpeck.rockbottom.api.data.IDataManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ModSettings implements IPropSettings{

    private final List<String> disabledMods = new ArrayList<>();

    @Override
    public void load(Properties props){
        this.disabledMods.clear();

        for(String key : props.stringPropertyNames()){
            if("disabled".equals(props.getProperty(key))){
                this.disabledMods.add(key);
            }
        }
    }

    @Override
    public void save(Properties props){
        for(String id : this.disabledMods){
            props.setProperty(id, "disabled");
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
        return this.disabledMods.contains(modid);
    }

    public void setDisabled(String modid, boolean disabled){
        if(disabled){
            this.disabledMods.add(modid);
        }
        else{
            this.disabledMods.remove(modid);
        }
    }
}
