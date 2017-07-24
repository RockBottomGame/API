/*
 * This file ("TileState.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.tile.state;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TileState{

    private final Tile tile;
    private final IResourceName name;
    private final Map<TileProp, Comparable> properties;
    private final Table<TileProp, Comparable, TileState> subStates = TreeBasedTable.create();

    private TileState(IResourceName name, Tile tile, Map<TileProp, Comparable> properties){
        this.tile = tile;
        this.properties = properties;
        this.name = name;

        RockBottomAPI.TILE_STATE_REGISTRY.register(name, this);

        for(TileProp prop : tile.getProperties()){
            for(int i = 0; i < prop.getVariants(); i++){
                Comparable value = prop.getValue(i);
                if(!properties.get(prop).equals(value)){
                    Map<TileProp, Comparable> subProps = new TreeMap<>(properties);
                    subProps.put(prop, value);

                    IResourceName subName = generateName(tile, subProps);

                    TileState state = RockBottomAPI.TILE_STATE_REGISTRY.get(subName);
                    if(state == null){
                        state = new TileState(subName, tile, subProps);
                    }

                    this.subStates.put(prop, value, state);
                }
            }
        }
    }

    public TileState(Tile tile, Map<TileProp, Comparable> properties){
        this(generateName(tile, properties), tile, properties);
    }

    private static IResourceName generateName(Tile tile, Map<TileProp, Comparable> properties){
        String suffix = "";

        if(!properties.isEmpty()){
            suffix += ";";

            Iterator<Entry<TileProp, Comparable>> iterator = properties.entrySet().iterator();
            while(iterator.hasNext()){
                Entry<TileProp, Comparable> entry = iterator.next();

                String append = entry.getKey().getName()+"@"+entry.getValue();
                if(iterator.hasNext()){
                    append += ",";
                }

                suffix += append;
            }
        }

        return tile.getName().addSuffix(suffix);
    }

    public IResourceName getName(){
        return this.name;
    }

    public <T extends Comparable> TileState prop(TileProp<T> prop, T value){
        if(value.equals(this.get(prop))){
            return this;
        }
        else{
            TileState state = this.subStates.get(prop, value);
            if(state == null){
                throw new IllegalArgumentException("The tile "+this.tile.getName()+" does not have property "+prop+" with value "+value);
            }
            else{
                return state;
            }
        }
    }

    public <T extends Comparable> TileState cycleProp(TileProp<T> prop){
        int index = prop.getIndex(this.get(prop))+1;
        if(index >= prop.getVariants()){
            index = 0;
        }
        return this.prop(prop, prop.getValue(index));
    }

    public <T extends Comparable> T get(TileProp<T> prop){
        Comparable value = this.properties.get(prop);
        if(value == null){
            throw new IllegalArgumentException("The tile "+this.tile.getName()+" does not support property "+prop);
        }
        else{
            return (T)value;
        }
    }

    public TileState overrideProps(TileState other, TileProp... props){
        if(other.tile != this.tile){
            throw new IllegalArgumentException("Cannot override properties of state "+this+" with "+other+" because they are not the same tile");
        }
        else{
            TileState overriden = this;
            for(TileProp prop : props){
                overriden = overriden.prop(prop, other.get(prop));
            }
            return overriden;
        }
    }

    public Tile getTile(){
        return this.tile;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        else if(o instanceof TileState){
            TileState tileState = (TileState)o;
            return this.tile.equals(tileState.tile) && this.properties.equals(tileState.properties);
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        int result = this.tile.hashCode();
        result = 31*result+this.properties.hashCode();
        return result;
    }

    @Override
    public String toString(){
        return this.tile.getName()+"@"+this.properties;
    }
}
