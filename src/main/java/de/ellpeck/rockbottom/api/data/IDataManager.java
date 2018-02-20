/*
 * This file ("IDataManager.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.settings.IJsonSettings;
import de.ellpeck.rockbottom.api.data.settings.IPropSettings;

import java.io.File;

public interface IDataManager{

    File getGameDir();

    File getModsDir();

    File getWorldsDir();

    File getScreenshotDir();

    File getGameDataFile();

    File getSettingsFile();

    File getCommandPermsFile();

    File getWhitelistFile();

    File getBlacklistFile();

    File getModSettingsFile();

    File getPlayerDesignFile();

    File getModConfigFolder();

    DataSet getGameInfo();

    /**
     * @deprecated Use {@link #loadSettings(IJsonSettings)} instead. If your
     * settings class still extends {@link IPropSettings} in addition to {@link
     * IJsonSettings}, then it will attempt to load the setting in both ways as
     * a way of backward compatibility.
     */
    @Deprecated
    void loadPropSettings(IPropSettings settings);

    /**
     * @deprecated Use {@link #saveSettings(IJsonSettings)} instead
     */
    @Deprecated
    void savePropSettings(IPropSettings settings);

    void loadSettings(IJsonSettings settings);

    void saveSettings(IJsonSettings settings);
}
