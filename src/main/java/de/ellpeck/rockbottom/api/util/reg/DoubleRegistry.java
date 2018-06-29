/*
 * This file ("DoubleRegistry.java") is part of the RockBottomAPI by Ellpeck.
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

public class DoubleRegistry<T> extends IndexRegistry<T> {

    private final NameRegistry<Integer> nameRegistry;

    public DoubleRegistry(String name, int max, boolean canUnregister) {
        super(name, max, canUnregister);
        this.nameRegistry = new NameRegistry(name + "_named", canUnregister);
    }

    public void register(ResourceName name, Integer id, T value) {
        super.register(id, value);
        this.nameRegistry.register(name, id);
    }

    public void unregister(ResourceName name, Integer id) {
        Preconditions.checkArgument(id.equals(this.nameRegistry.get(name)), "Can't unregister name " + name + " and id " + id + " that weren't registered as a matching pair from registry " + this);

        super.unregister(id);
        this.nameRegistry.unregister(name);
    }

    public T get(ResourceName name) {
        return this.get(this.nameRegistry.get(name));
    }

    public ResourceName getName(T value) {
        return this.nameRegistry.getId(this.getId(value));
    }

    @Override
    public void register(Integer id, T value) {
        throw new UnsupportedOperationException("Can't register to double registry " + this + " using just the integer key");
    }

    @Override
    public void unregister(Integer id) {
        throw new UnsupportedOperationException("Can't unregister from double registry " + this + " using just the integer key");
    }
}
