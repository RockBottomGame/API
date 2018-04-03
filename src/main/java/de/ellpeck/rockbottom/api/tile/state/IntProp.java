/*
 * This file ("IntProp.java") is part of the RockBottomAPI by Ellpeck.
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

import com.google.common.base.Preconditions;

public final class IntProp extends TileProp<Integer>{

    private final int def;
    private final int possibilities;

    public IntProp(String name, int def, int possibilities){
        super(name);
        this.def = def;
        this.possibilities = possibilities;
        Preconditions.checkArgument(this.def >= 0 && this.def < this.possibilities, "The default value of an Integer prop has to be an allowed value too!");
    }

    @Override
    public int getVariants(){
        return this.possibilities;
    }

    @Override
    public Integer getValue(int index){
        return index;
    }

    @Override
    public int getIndex(Integer value){
        return value;
    }

    @Override
    public Integer getDefault(){
        return this.def;
    }
}
