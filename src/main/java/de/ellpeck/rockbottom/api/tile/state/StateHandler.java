/*
 * This file ("StateHandler.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.tile.state;

import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.*;

@ApiInternal
public class StateHandler{

    private final Tile tile;
    private final List<TileProp> properties = new ArrayList<>();

    private TileState defaultState;
    private boolean hasInit;

    public StateHandler(Tile tile){
        this.tile = tile;
    }

    public void init(){
        if(!this.hasInit){
            this.hasInit = true;

            Map<String, Comparable> defMap = new TreeMap<>();
            for(TileProp prop : this.getProps()){
                Comparable def = prop.getDefault();
                int index = prop.getIndex(def);

                if(index >= 0 && index < prop.getVariants()){
                    defMap.put(prop.getName(), def);
                }
                else{
                    throw new IllegalArgumentException();
                }
            }

            IResourceName defName = TileState.generateName(this.tile, defMap);
            if(this.tile.hasState(defName, defMap)){
                this.defaultState = new TileState(defName, this.tile, defMap);
            }
            else{
                throw new RuntimeException("Tile "+this.tile+" is disallowing its default state from being generated! This is disallowed!");
            }
        }
        else{
            throw new RuntimeException("Cannot initialize state handler for tile "+this.tile+" twice!");
        }
    }

    public void addProp(TileProp prop){
        if(!this.hasInit){
            if(!this.properties.contains(prop)){
                this.properties.add(prop);
            }
            else{
                throw new IllegalArgumentException("Cannot add prop "+prop+" to state handler for tile "+this.tile+" twice!");
            }
        }
        else{
            throw new RuntimeException("Cannot add prop "+prop+" to state handler for tile "+this.tile+" after it has been initialized!");
        }
    }

    public List<TileProp> getProps(){
        return Collections.unmodifiableList(this.properties);
    }

    public TileState getDefault(){
        if(!this.hasInit){
            throw new RuntimeException("Tried to access default state for tile "+this.tile+" without its state handler being initialized!");
        }

        return this.defaultState;
    }
}
