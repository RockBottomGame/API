/*
 * This file ("AbstractDataSet.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data.set;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.part.DataPart;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractDataSet implements Iterable<Map.Entry<String, DataPart>> {

    protected final Map<String, DataPart> data = new HashMap<>();
    protected final Map<String, DataPart> dataUnmodifiable = Collections.unmodifiableMap(this.data);

    public void addPart(String name, DataPart part) {
        this.data.put(name, part);
    }

    public boolean hasKey(String key) {
        return this.data.containsKey(key);
    }

    public DataPart remove(String key) {
        return this.data.remove(key);
    }

    public void clear() {
        this.data.clear();
    }

    public int size() {
        return this.data.size();
    }

    public <T> T getPartContent(String key, Class<? extends DataPart<T>> typeClass, T defaultValue) {
        DataPart part = this.data.get(key);

        if (part != null && part.getClass() == typeClass) {
            T result = (T) part.get();
            if (result != null) {
                return result;
            }
        }

        return defaultValue;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }

    public Map<String, DataPart> getData() {
        return this.dataUnmodifiable;
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public Iterator<Map.Entry<String, DataPart>> iterator() {
        return this.dataUnmodifiable.entrySet().iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        AbstractDataSet dataSet = (AbstractDataSet) o;
        return this.data.equals(dataSet.data);
    }

    @Override
    public int hashCode() {
        return this.data.hashCode();
    }

    public void write(File file) {
        RockBottomAPI.getApiHandler().writeDataSet(this, file, false);
    }

    public void read(File file) {
        RockBottomAPI.getApiHandler().readDataSet(this, file, false);
    }

    public void writeAsJson(File file) {
        RockBottomAPI.getApiHandler().writeDataSet(this, file, true);
    }

    public void readAsJson(File file) {
        RockBottomAPI.getApiHandler().readDataSet(this, file, true);
    }
}
