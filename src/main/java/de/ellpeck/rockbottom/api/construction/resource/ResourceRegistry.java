/*
 * This file ("ResourceRegistry.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.construction.resource;

import org.newdawn.slick.util.Log;

import java.util.*;

public final class ResourceRegistry{

    public static final String COAL = "coal";
    public static final String WOOD_BOARDS = "wood_boards";
    public static final String WOOD_LOG = "wood_logs";
    public static final String LEAVES = "leaves";
    public static final String RAW_STONE = "raw_stone";
    public static final String PROCESSED_STONE = "processed_stone";
    public static final String RAW_COPPER = "raw_copper";
    public static final String PARTLY_PROCESSED_COPPER = "partly_processed_copper";
    public static final String PROCESSED_COPPER = "processed_copper";
    public static final String SLAG = "slag";

    private static final Map<String, List<ResInfo>> RESOURCES = new HashMap<>();
    private static final Map<ResInfo, List<String>> RESOURCE_NAMES = new HashMap<>();

    public static void addResources(String name, ResInfo... resources){
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

        Log.info("Registered resources "+Arrays.toString(resources)+" for resource name "+name);
    }

    public static List<ResInfo> getResources(String name){
        List<ResInfo> resources = RESOURCES.get(name);
        return resources == null ? Collections.emptyList() : Collections.unmodifiableList(resources);
    }

    public static List<String> getNames(ResInfo resource){
        List<String> names = RESOURCE_NAMES.get(resource);
        return names == null ? Collections.emptyList() : Collections.unmodifiableList(names);
    }
}
