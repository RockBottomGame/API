/*
 * This file ("EnumProp.java") is part of the RockBottomAPI by Ellpeck.
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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.tile.state;

import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class EnumProp<T extends Enum<T>> extends TileProp<T> {

    private final T def;
    private final List<T> allowedValues;

    public EnumProp(String name, T def, Class<? extends T> enumClass) {
        this(name, def, enumClass.getEnumConstants());
    }

    public EnumProp(String name, T def, T... allowedValues) {
        this(name, def, Arrays.asList(allowedValues));
    }

    public EnumProp(String name, T def, List<T> allowedValues) {
        super(name);
        this.def = def;
        this.allowedValues = Collections.unmodifiableList(allowedValues);

        Preconditions.checkArgument(this.allowedValues.contains(this.def), "The default value for an Enum property has to be an allowed value as well!");
    }

    @Override
    public int getVariants() {
        return this.allowedValues.size();
    }

    @Override
    public T getValue(int index) {
        return this.allowedValues.get(index);
    }

    @Override
    public int getIndex(T value) {
        return this.allowedValues.indexOf(value);
    }

    @Override
    public T getDefault() {
        return this.def;
    }
}
