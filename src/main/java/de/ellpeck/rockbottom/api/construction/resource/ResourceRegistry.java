/*
 * This file ("ResourceRegistry.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.construction.resource;

import de.ellpeck.rockbottom.api.RockBottomAPI;

import java.util.List;
import java.util.Set;

/**
 * @deprecated Use {@link RockBottomAPI#getResourceRegistry()} instead
 */
@Deprecated
public final class ResourceRegistry{

    private static final IResourceRegistry R = RockBottomAPI.getResourceRegistry();

    public static String addResources(String name, ResInfo... resources){
        return R.addResources(name, resources);
    }

    public static List<ResInfo> getResources(String name){
        return R.getResources(name);
    }

    public static List<String> getNames(ResInfo resource){
        return R.getNames(resource);
    }

    public static Set<ResInfo> getAllResources(){
        return R.getAllResources();
    }

    public static Set<String> getAllResourceNames(){
        return R.getAllResourceNames();
    }
}
