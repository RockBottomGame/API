/*
 * This file ("IndexRegistry.java") is part of the RockBottomAPI by Ellpeck.
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

public class IndexRegistry<T> implements IRegistry<Integer, T> {

    protected final int max;
    protected final ResourceName name;
    protected final boolean canUnregister;
    protected final BiMap<Integer, T> map = HashBiMap.create();
    protected final BiMap<Integer, T> unmodifiableMap;

    public IndexRegistry(ResourceName name, int max, boolean canUnregister) {
        this.name = name;
        this.max = max;
        this.canUnregister = canUnregister;
        this.unmodifiableMap = Maps.unmodifiableBiMap(this.map);
    }

    @Override
    public void register(Integer id, T value) {
        Preconditions.checkArgument(id >= 0 && id <= this.max, "Tried registering " + value + " with id " + id + " which is less than 0 or greater than max " + this.max + " in registry " + this);
        Preconditions.checkArgument(!this.map.containsKey(id), "Cannot register " + value + " with id " + id + " twice into registry " + this);

        this.map.put(id, value);
        RockBottomAPI.logger().config("Registered " + value + " with id " + id + " into registry " + this);
    }

    public void registerNextFree(T value) {
        this.register(this.getNextFreeId(), value);
    }

    @Override
    public T get(Integer id) {
        if (id > this.max) {
            RockBottomAPI.logger().warning("Tried getting value of " + id + " for registry " + this + " which is greater than max " + this.max);
            return null;
        } else {
            return this.map.get(id);
        }
    }

    @Override
    public Integer getId(T value) {
        return this.map.inverse().getOrDefault(value, -1);
    }

    public int getNextFreeId() {
        for (int i = 0; i <= this.max; i++) {
            if (!this.map.containsKey(i)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSize() {
        return this.map.size();
    }

    @Override
    public void unregister(Integer id) {
        if (this.canUnregister) {
            this.map.remove(id);
            RockBottomAPI.logger().config("Unregistered " + id + " from registry " + this);
        } else {
            throw new UnsupportedOperationException("Unregistering from registry " + this + " is disallowed");
        }
    }

    @Override
    public BiMap<Integer, T> getUnmodifiable() {
        return this.unmodifiableMap;
    }

    @Override
    public Set<Integer> keySet() {
        return this.unmodifiableMap.keySet();
    }

    @Override
    public Set<T> values() {
        return this.unmodifiableMap.values();
    }

    @Override
    public Set<Map.Entry<Integer, T>> entrySet() {
        return this.unmodifiableMap.entrySet();
    }

    @Override
    public ResourceName getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }
}
