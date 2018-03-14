/*
 * This file ("StringProp.java") is part of the RockBottomAPI by Ellpeck.
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringProp extends TileProp<String>{

    private final String def;
    private final List<String> allowedValues;

    public StringProp(String name, String def, String... allowedValues){
        this(name, def, Arrays.asList(allowedValues));
    }

    public StringProp(String name, String def, List<String> allowedValues){
        super(name);
        this.def = def;
        this.allowedValues = Collections.unmodifiableList(allowedValues);

        Preconditions.checkArgument(this.allowedValues.contains(this.def), "The default value of a String property has to be an allowed value too!");
    }

    @Override
    public int getVariants(){
        return this.allowedValues.size();
    }

    @Override
    public String getValue(int index){
        return this.allowedValues.get(index);
    }

    @Override
    public int getIndex(String value){
        return this.allowedValues.indexOf(value);
    }

    @Override
    public String getDefault(){
        return this.def;
    }
}
