/*
 * This file ("IndexRegistry.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.util.reg;

import com.google.common.base.Preconditions;

public class IndexRegistry<U> extends AbstractRegistry<Integer, U> {

    protected final int max;

    public IndexRegistry(ResourceName name, int max, boolean canUnregister) {
        super(name, canUnregister);
        this.max = max;
    }

    @Override
    public void register(Integer key, U value) {
        Preconditions.checkArgument(key >= 0 && key <= this.max, "Tried registering " + value + " with id " + key + " which is less than 0 or greater than max " + this.max + " in registry " + this);
        super.register(key, value);
    }

    public void registerNextFree(U value) {
        this.register(this.getNextFreeId(), value);
    }

    @Override
    public Integer getId(U value) {
        Integer id = super.getId(value);
        return id == null ? -1 : id;
    }

    public int getNextFreeId() {
        for (int i = 0; i <= this.max; i++) {
            if (!this.map.containsKey(i)) {
                return i;
            }
        }
        return -1;
    }
}
