/*
 * This file ("Pos3.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.util;

public class Pos3{

    private int x;
    private int y;
    private int z;

    public Pos3(){
        this(0, 0, 0);
    }

    public Pos3(int x, int y, int z){
        this.set(x, y, z);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getZ(){
        return this.z;
    }

    public Pos3 set(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || this.getClass() != o.getClass()){
            return false;
        }

        Pos3 pos3 = (Pos3)o;
        return this.x == pos3.x && this.y == pos3.y && this.z == pos3.z;
    }

    @Override
    public int hashCode(){
        int result = this.x;
        result = 31*result+this.y;
        result = 31*result+this.z;
        return result;
    }

    @Override
    public String toString(){
        return "{"+this.x+", "+this.y+", "+this.z+"}";
    }
}
