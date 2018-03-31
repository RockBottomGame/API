/*
 * This file ("IResourceName.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.util.reg;

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.mod.IMod;

/**
 * A resource name that defines the name of a resource and the domain that it is
 * created from. A resource name is structured in the following way: The domain,
 * followed by {@link Constants#RESOURCE_SEPARATOR}, followed by the actual
 * resource name. New resource names can be created using {@link
 * RockBottomAPI#createRes(IMod, String)} or {@link RockBottomAPI#createRes(String)}.
 * Creating a custom implementation of this class is discouraged.
 */
public interface IResourceName extends Comparable<IResourceName>{

    /**
     * Returns the domain of this resource name.
     *
     * @return The domain
     *
     * @see #getResourceName()
     * @see Constants#RESOURCE_SEPARATOR
     */
    String getDomain();

    /**
     * Returns the name of this resource
     *
     * @return The name
     *
     * @see #getDomain()
     * @see Constants#RESOURCE_SEPARATOR
     */
    String getResourceName();

    /**
     * Returns a new resource name with the given prefix added to the front of
     * the resource. For example, the resource name rockbottom/stone with the
     * prefix dirty_ added to it would result in a new resource name
     * rockbottom/dirty_stone, meaning the domain of the resource name stays
     * unaffected by this action.
     *
     * @param prefix The prefix to add
     *
     * @return A new name with the prefix added
     */
    IResourceName addPrefix(String prefix);

    /**
     * Returns a new resource name with the given suffix added to the end of the
     * resource. For example, the resource name rockbottom/stone with the suffix
     * _pickaxe added to it would result in a new resource name
     * rockbottom/stone_pickaxe, meaning the domain of the resource name stays
     * unaffected by this action.
     *
     * @param suffix The suffix to add
     *
     * @return A new name with the suffix added
     */
    IResourceName addSuffix(String suffix);
}
