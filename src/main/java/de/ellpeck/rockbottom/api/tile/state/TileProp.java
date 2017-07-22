/*
 * This file ("TileProp.java") is part of the RockBottomAPI by Ellpeck.
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

public abstract class TileProp<T extends Comparable> implements Comparable<TileProp>{

    protected final String name;

    public TileProp(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public abstract int getVariants();

    public abstract T makeValue(int variant);

    public abstract int getVariant(T value);

    public abstract T getDefault();

    @Override
    public int compareTo(TileProp o){
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        else if(o instanceof TileProp<?>){
            TileProp<?> tileProp = (TileProp<?>)o;
            return this.name.equals(tileProp.name);
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }
}
