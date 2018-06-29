/*
 * This file ("ResourceName.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.util.reg;

import com.google.common.base.Preconditions;
import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.Util;

/**
 * A resource name that defines the name of a resource and the domain that it is
 * created from. A resource name is structured in the following way: The domain,
 * followed by {@link Constants#RESOURCE_SEPARATOR}, followed by the actual
 * resource name.
 * <p>
 * The game's internal resource names use the {@link IGameInstance} instance
 * from {@link RockBottomAPI#getGame()} as the domain. Using this in mods is
 * highly discouraged and, if anything, should only be used to access existing
 * internal resources, not create new ones.
 */
public final class ResourceName implements Comparable<ResourceName> {

    private final String domain;
    private final String resourceName;

    /**
     * Creates a resource name with the given {@link IMod} as the domain.
     *
     * @param mod          The mod domain
     * @param resourceName The resource name
     */
    public ResourceName(IMod mod, String resourceName) {
        this(mod.getId(), resourceName);
    }

    /**
     * Creates a resource name from a combined string. If the string does not
     * contain the {@link Constants#RESOURCE_SEPARATOR}, then it will throw an
     * {@link IllegalArgumentException}.
     *
     * @param combined The combined resource string
     * @see #toString()
     */
    public ResourceName(String combined) {
        Preconditions.checkArgument(Util.isResourceName(combined), "Cannot create a resource name from combined string " + combined);

        String[] split = combined.split(Constants.RESOURCE_SEPARATOR, 2);
        this.domain = split[0];
        this.resourceName = split[1];
    }

    private ResourceName(String domain, String resourceName) {
        this.domain = domain;
        this.resourceName = resourceName;
    }

    /**
     * Creates an internal resource name that uses the {@link IGameInstance}
     * instance from {@link RockBottomAPI#getGame()} as the domain. Using this
     * in mods is highly discouraged and, if anything, should only be used to
     * access existing internal resources, not create new ones.
     *
     * @param resourceName The name of the resource
     * @return A new internal resource name
     */
    @ApiInternal
    public static ResourceName intern(String resourceName) {
        return new ResourceName(RockBottomAPI.getGame(), resourceName);
    }

    /**
     * Returns the domain of this resource name.
     *
     * @return The domain
     * @see #getResourceName()
     * @see Constants#RESOURCE_SEPARATOR
     */
    public String getDomain() {
        return this.domain;
    }

    /**
     * Returns the name of this resource
     *
     * @return The name
     * @see #getDomain()
     * @see Constants#RESOURCE_SEPARATOR
     */
    public String getResourceName() {
        return this.resourceName;
    }

    /**
     * Returns a new resource name with the given prefix added to the front of
     * the resource. For example, the resource name rockbottom/stone with the
     * prefix dirty_ added to it would result in a new resource name
     * rockbottom/dirty_stone, meaning the domain of the resource name stays
     * unaffected by this action.
     *
     * @param prefix The prefix to add
     * @return A new name with the prefix added
     */
    public ResourceName addPrefix(String prefix) {
        return new ResourceName(this.domain, prefix + this.resourceName);
    }

    /**
     * Returns a new resource name with the given suffix added to the end of the
     * resource. For example, the resource name rockbottom/stone with the suffix
     * _pickaxe added to it would result in a new resource name
     * rockbottom/stone_pickaxe, meaning the domain of the resource name stays
     * unaffected by this action.
     *
     * @param suffix The suffix to add
     * @return A new name with the suffix added
     */
    public ResourceName addSuffix(String suffix) {
        return new ResourceName(this.domain, this.resourceName + suffix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        ResourceName name = (ResourceName) o;
        return this.domain.equals(name.domain) && this.resourceName.equals(name.resourceName);
    }

    @Override
    public int hashCode() {
        int result = this.domain.hashCode();
        result = 31 * result + this.resourceName.hashCode();
        return result;
    }

    @Override
    public int compareTo(ResourceName o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public String toString() {
        return this.getDomain() + Constants.RESOURCE_SEPARATOR + this.getResourceName();
    }
}
