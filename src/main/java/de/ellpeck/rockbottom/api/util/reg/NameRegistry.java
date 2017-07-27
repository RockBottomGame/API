/*
 * This file ("NameRegistry.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.util.reg;

import org.newdawn.slick.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class NameRegistry<T> implements IRegistry<IResourceName, T>{

    protected final String name;
    protected final Map<IResourceName, T> map;

    public NameRegistry(String name, Map<IResourceName, T> map){
        this.name = name;
        this.map = map;
    }

    public NameRegistry(String name){
        this(name, new HashMap<>());
    }

    @Override
    public void register(IResourceName name, T value){
        if(name == null || name.isEmpty()){
            throw new IndexOutOfBoundsException("Tried registering "+value+" with name "+name+" which is invalid into registry "+this);
        }
        if(this.map.containsKey(name)){
            throw new RuntimeException("Cannot register "+value+" with name "+name+" twice into registry "+this);
        }

        this.map.put(name, value);

        Log.info("Registered "+value+" with name "+name+" into registry "+this);
    }

    @Override
    public T get(IResourceName name){
        if(name == null || name.isEmpty()){
            Log.warn("Tried getting value of "+name+" for registry "+this+" which is invalid");
            return null;
        }
        else{
            return this.map.get(name);
        }
    }

    @Override
    public IResourceName getId(T value){
        for(Entry<IResourceName, T> entry : this.map.entrySet()){
            if(value.equals(entry.getValue())){
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public int getSize(){
        return this.map.size();
    }

    @Override
    public Map<IResourceName, T> getUnmodifiable(){
        return Collections.unmodifiableMap(this.map);
    }

    @Override
    public String toString(){
        return this.name;
    }
}
