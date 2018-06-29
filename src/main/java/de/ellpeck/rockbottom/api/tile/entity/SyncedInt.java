/*
 * This file ("SyncedInt.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.tile.entity;

import de.ellpeck.rockbottom.api.data.set.DataSet;

public final class SyncedInt {

    private final String name;
    private int value;
    private int lastValue;

    public SyncedInt(String name) {
        this.name = name;
    }

    public boolean needsSync() {
        return this.value != this.lastValue;
    }

    public void onSync() {
        this.lastValue = this.value;
    }

    public void set(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }

    public void add(int amount) {
        this.value += amount;
    }

    public void remove(int amount) {
        this.value -= amount;
    }

    public void save(DataSet set) {
        set.addInt(this.name, this.value);
    }

    public void load(DataSet set) {
        this.value = set.getInt(this.name);
    }
}
