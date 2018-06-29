/*
 * This file ("IResourceRegistry.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.construction.resource;

import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;

import java.util.List;
import java.util.Set;

public interface IResourceRegistry {

    String addResources(String name, Item item);

    String addResources(String name, Item item, int meta);

    String addResources(String name, Item item, int lowestMeta, int highestMeta);

    String addResources(String name, Tile tile);

    String addResources(String name, Tile tile, int meta);

    String addResources(String name, Tile tile, int lowestMeta, int highestMeta);

    String addResources(String name, ItemInstance instance);

    String addResources(String name, ResInfo... resources);

    List<ResInfo> getResources(String name);

    List<String> getNames(ItemInstance instance);

    List<String> getNames(ResInfo resource);

    Set<ResInfo> getAllResources();

    Set<String> getAllResourceNames();
}
