/*
 * This file ("Pos2.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.util;

public final class Pos2{

    private int x;
    private int y;

    public Pos2(){
        this(0, 0);
    }

    public Pos2(int x, int y){
        this.set(x, y);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Pos2 set(int x, int y){
        this.x = x;
        this.y = y;

        return this;
    }

    public Pos2 add(int x, int y){
        return this.set(this.x+x, this.y+y);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || this.getClass() != o.getClass()){
            return false;
        }

        Pos2 pos2 = (Pos2)o;
        return this.x == pos2.x && this.y == pos2.y;
    }

    @Override
    public int hashCode(){
        int result = this.x;
        result = 31*result+this.y;
        return result;
    }

    @Override
    public String toString(){
        return "{"+this.x+", "+this.y+'}';
    }
}
