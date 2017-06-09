/*
 * This file ("IndexRegistry.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 */

package de.ellpeck.rockbottom.api.util.reg;

import org.newdawn.slick.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class IndexRegistry<T> implements IRegistry<Integer, T>{

    protected final int max;
    protected final String name;
    protected final Map<Integer, T> map = new HashMap<>();

    public IndexRegistry(String name, int max){
        this.name = name;
        this.max = max;
    }

    @Override
    public void register(Integer id, T value){
        if(id < 0 || id > this.max){
            throw new IndexOutOfBoundsException("Tried registering "+value+" with id "+id+" which is less than 0 or greater than max "+this.max+" in registry "+this);
        }
        if(this.map.containsKey(id)){
            throw new RuntimeException("Cannot register "+value+" with id "+id+" twice into registry "+this);
        }

        this.map.put(id, value);

        Log.info("Registered "+value+" with id "+id+" into registry "+this);
    }

    @Override
    public T get(Integer id){
        if(id > this.max){
            Log.warn("Tried getting value of "+id+" for registry "+this+" which is greater than max "+this.max);
            return null;
        }
        else{
            return this.map.get(id);
        }
    }

    @Override
    public Integer getId(T value){
        for(Entry<Integer, T> entry : this.map.entrySet()){
            if(value.equals(entry.getValue())){
                return entry.getKey();
            }
        }
        return -1;
    }

    public int getNextFreeId(){
        for(int i = 0; i <= this.max; i++){
            if(!this.map.containsKey(i)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSize(){
        return this.map.size();
    }

    @Override
    public Map<Integer, T> getUnmodifiable(){
        return Collections.unmodifiableMap(this.map);
    }

    @Override
    public String toString(){
        return this.name;
    }
}
