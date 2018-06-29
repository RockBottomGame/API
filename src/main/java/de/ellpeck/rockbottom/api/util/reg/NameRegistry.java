/*
 * This file ("NameRegistry.java") is part of the RockBottomAPI by Ellpeck.
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

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import de.ellpeck.rockbottom.api.RockBottomAPI;

import java.util.Map;
import java.util.Set;

public class NameRegistry<T> implements IRegistry<ResourceName, T> {

    protected final String name;
    protected final boolean canUnregister;
    protected final BiMap<ResourceName, T> map = HashBiMap.create();
    protected final BiMap<ResourceName, T> unmodifiableMap;

    public NameRegistry(String name, boolean canUnregister) {
        this.name = name;
        this.canUnregister = canUnregister;
        this.unmodifiableMap = Maps.unmodifiableBiMap(this.map);
    }

    @Override
    public void register(ResourceName name, T value) {
        Preconditions.checkArgument(name != null, "Tried registering " + value + " with name " + name + " which is invalid into registry " + this);
        Preconditions.checkArgument(!this.map.containsKey(name), "Cannot register " + value + " with name " + name + " twice into registry " + this);

        this.map.put(name, value);
        RockBottomAPI.logger().config("Registered " + value + " with name " + name + " into registry " + this);
    }

    @Override
    public T get(ResourceName name) {
        if (name == null) {
            RockBottomAPI.logger().warning("Tried getting value of " + name + " for registry " + this + " which is invalid");
            return null;
        } else {
            return this.map.get(name);
        }
    }

    @Override
    public ResourceName getId(T value) {
        return this.map.inverse().get(value);
    }

    @Override
    public int getSize() {
        return this.map.size();
    }

    @Override
    public void unregister(ResourceName name) {
        if (this.canUnregister) {
            this.map.remove(name);
            RockBottomAPI.logger().config("Unregistered " + name + " from registry " + this);
        } else {
            throw new UnsupportedOperationException("Unregistering from registry " + this + " is disallowed");
        }
    }

    @Override
    public BiMap<ResourceName, T> getUnmodifiable() {
        return this.unmodifiableMap;
    }

    @Override
    public Set<ResourceName> keySet() {
        return this.unmodifiableMap.keySet();
    }

    @Override
    public Set<T> values() {
        return this.unmodifiableMap.values();
    }

    @Override
    public Set<Map.Entry<ResourceName, T>> entrySet() {
        return this.unmodifiableMap.entrySet();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
