/*
 * This file ("StateCollection.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.tile.Tile;

import java.util.Map;
import java.util.TreeMap;

public class StateHandler{

    private final TileState defaultState;

    public StateHandler(Tile tile){
        TileProp[] props = tile.getProperties();

        Map<TileProp, Comparable> defMap = new TreeMap<>();
        for(TileProp prop : props){
            defMap.put(prop, prop.getDefault());
        }
        this.defaultState = new TileState(tile, defMap);
    }

    public TileState getDefault(){
        return this.defaultState;
    }
}
