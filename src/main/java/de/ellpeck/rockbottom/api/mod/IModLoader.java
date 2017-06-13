/*
 * This file ("IModLoader.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api.mod;

import de.ellpeck.rockbottom.api.data.settings.ModSettings;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.io.File;
import java.util.List;

public interface IModLoader{

    void loadModsFromDir(File dir);

    void sortMods();

    void preInit();

    void init();

    void postInit();

    IMod getMod(String id);

    IResourceName createResourceName(IMod mod, String resource);

    IResourceName createResourceName(String combined);

    List<IMod> getAllMods();

    List<IMod> getActiveMods();

    List<IMod> getDisabledMods();

    ModSettings getModSettings();
}
