/*
 * This file ("BasicDataPart.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data.set.part;

public abstract class BasicDataPart<T> extends DataPart<T>{

    protected final T data;

    public BasicDataPart(String name, T data){
        super(name);
        this.data = data;
    }

    @Override
    public T get(){
        return this.data;
    }

    @Override
    public String toString(){
        return this.data.toString();
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        else if(o instanceof BasicDataPart){
            BasicDataPart that = (BasicDataPart)o;
            return this.name.equals(that.name) && (this.data != null ? this.data.equals(that.data) : that.data == null);
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        int result = this.name.hashCode();
        result = 31*result+(this.data != null ? this.data.hashCode() : 0);
        return result;
    }
}
