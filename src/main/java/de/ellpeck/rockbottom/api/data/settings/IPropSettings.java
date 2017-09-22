/*
 * This file ("IPropSettings.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame>.
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
import java.util.Properties;

public interface IPropSettings{

    void load(Properties props);

    void save(Properties props);

    File getFile(IDataManager manager);

    String getName();

    default <T> void setProp(Properties props, String name, T val){
        props.setProperty(name, String.valueOf(val));
    }

    default int getProp(Properties props, String name, int def){
        return Integer.parseInt(props.getProperty(name, String.valueOf(def)));
    }

    default boolean getProp(Properties props, String name, boolean def){
        return Boolean.parseBoolean(props.getProperty(name, String.valueOf(def)));
    }

    default float getProp(Properties props, String name, float def){
        return Float.parseFloat(props.getProperty(name, String.valueOf(def)));
    }

    default String getProp(Properties props, String name, String def){
        return props.getProperty(name, def);
    }
}
