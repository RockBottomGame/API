/*
 * This file ("IJsonSettings.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.IDataManager;

import java.io.File;

public interface IJsonSettings {

    void load(JsonObject object);

    void save(JsonObject object);

    File getSettingsFile(IDataManager manager);

    String getName();

    default void save() {
        RockBottomAPI.getGame().getDataManager().saveSettings(this);
    }

    default void load() {
        RockBottomAPI.getGame().getDataManager().loadSettings(this);
    }

    default void set(JsonObject object, String name, String val) {
        object.addProperty(name, val);
    }

    default void set(JsonObject object, String name, Character val) {
        object.addProperty(name, val);
    }

    default void set(JsonObject object, String name, Boolean val) {
        object.addProperty(name, val);
    }

    default void set(JsonObject object, String name, Number val) {
        object.addProperty(name, val);
    }

    default int get(JsonObject object, String name, int def) {
        return object.has(name) ? object.get(name).getAsInt() : def;
    }

    default boolean get(JsonObject object, String name, boolean def) {
        return object.has(name) ? object.get(name).getAsBoolean() : def;
    }

    default float get(JsonObject object, String name, float def) {
        return object.has(name) ? object.get(name).getAsFloat() : def;
    }

    default String get(JsonObject object, String name, String def) {
        return object.has(name) ? object.get(name).getAsString() : def;
    }
}
