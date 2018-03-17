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

public class DoubleRegistry<T> extends IndexRegistry<T>{

    private final NameRegistry<Integer> nameRegistry;

    public DoubleRegistry(String name, int max, boolean canUnregister){
        super(name, max, canUnregister);
        this.nameRegistry = new NameRegistry(name+"_named", canUnregister);
    }

    public void register(IResourceName name, Integer id, T value){
        super.register(id, value);
        this.nameRegistry.register(name, id);
    }

    public void unregister(IResourceName name, Integer id){
        super.unregister(id);

        Preconditions.checkArgument(id.equals(this.nameRegistry.get(name)), "Can't unregister name "+name+" and id "+id+" that weren't registered as a matching pair from registry "+this);
        this.nameRegistry.unregister(name);
    }

    public T get(IResourceName name){
        return this.get(this.nameRegistry.get(name));
    }

    public IResourceName getName(T value){
        return this.nameRegistry.getId(this.getId(value));
    }

    @Override
    public void register(Integer id, T value){
        throw new UnsupportedOperationException("Can't register to double registry "+this.name+" using just the integer key");
    }

    @Override
    public void unregister(Integer id){
        throw new UnsupportedOperationException("Can't unregister from double registry "+this.name+" using just the integer key");
    }
}
