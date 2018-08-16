/*
 * This file ("IStructure.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.world.gen;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.content.IContent;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IStructure extends IContent {

    ResourceName ID = ResourceName.intern("structure");

    static IStructure forName(ResourceName name) {
        return Registries.STRUCTURE_REGISTRY.get(name);
    }

    static List<IStructure> forNamePart(String part) {
        List<IStructure> list = new ArrayList<>();
        for (Map.Entry<ResourceName, IStructure> entry : Registries.STRUCTURE_REGISTRY.entrySet()) {
            if (entry.getKey().toString().contains(part)) {
                list.add(entry.getValue());
            }
        }
        return list;
    }

    int getWidth();

    int getHeight();

    Set<TileLayer> getInvolvedLayers();

    TileState getTile(int x, int y);

    TileState getTile(TileLayer layer, int x, int y);
}
