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

import java.util.Objects;

public abstract class BasicDataPart<T> extends DataPart<T> {

    protected final T data;

    public BasicDataPart(T data) {
        this.data = data;
    }

    @Override
    public T get() {
        return this.data;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + this.data.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return Objects.equals(this.data, ((BasicDataPart) o).data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.data);
    }
}
