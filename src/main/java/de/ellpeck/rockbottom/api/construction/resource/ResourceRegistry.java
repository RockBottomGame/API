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

import java.util.*;

public final class ResourceRegistry{

    private static final Map<String, List<ResInfo>> RESOURCES = new HashMap<>();
    private static final Map<ResInfo, List<String>> RESOURCE_NAMES = new HashMap<>();

    public static String addResources(String name, ResInfo... resources){
        List<ResInfo> resList = RESOURCES.computeIfAbsent(name, k -> new ArrayList<>());

        for(ResInfo res : resources){
            if(!resList.contains(res)){
                resList.add(res);
            }

            List<String> nameList = RESOURCE_NAMES.computeIfAbsent(res, k -> new ArrayList<>());
            if(!nameList.contains(name)){
                nameList.add(name);
            }
        }

        RockBottomAPI.logger().config("Registered resources "+Arrays.toString(resources)+" for resource name "+name);
        return name;
    }

    public static List<ResInfo> getResources(String name){
        List<ResInfo> resources = RESOURCES.get(name);
        return resources == null ? Collections.emptyList() : Collections.unmodifiableList(resources);
    }

    public static List<String> getNames(ResInfo resource){
        List<String> names = RESOURCE_NAMES.get(resource);
        return names == null ? Collections.emptyList() : Collections.unmodifiableList(names);
    }

    public static Set<ResInfo> getAllResources(){
        return Collections.unmodifiableSet(RESOURCE_NAMES.keySet());
    }

    public static Set<String> getAllResourceNames(){
        return Collections.unmodifiableSet(RESOURCES.keySet());
    }
}
