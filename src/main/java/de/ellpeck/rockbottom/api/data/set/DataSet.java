/*
 * This file ("DataSet.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data.set;

import de.ellpeck.rockbottom.api.data.set.part.*;
import de.ellpeck.rockbottom.api.data.set.part.num.*;
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartByteArray;
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartIntArray;
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartShortArray;

import java.util.UUID;

public final class DataSet extends AbstractDataSet {

    public int getInt(String key) {
        return this.getPartContent(key, PartInt.class, 0);
    }

    public void addInt(String key, int i) {
        this.addPart(key, new PartInt(i));
    }

    public long getLong(String key) {
        return this.getPartContent(key, PartLong.class, 0L);
    }

    public void addLong(String key, long l) {
        this.addPart(key, new PartLong(l));
    }

    public float getFloat(String key) {
        return this.getPartContent(key, PartFloat.class, 0F);
    }

    public void addFloat(String key, float f) {
        this.addPart(key, new PartFloat(f));
    }

    public double getDouble(String key) {
        return this.getPartContent(key, PartDouble.class, 0D);
    }

    public void addDouble(String key, double d) {
        this.addPart(key, new PartDouble(d));
    }

    public DataSet getDataSet(String key) {
        return this.getPartContent(key, PartDataSet.class, new DataSet());
    }

    public void addDataSet(String key, DataSet set) {
        this.addPart(key, new PartDataSet(set));
    }

    public ModBasedDataSet getModBasedDataSet(String key) {
        return this.getPartContent(key, PartModBasedDataSet.class, new ModBasedDataSet());
    }

    public void addModBasedDataSet(String key, ModBasedDataSet set) {
        this.addPart(key, new PartModBasedDataSet(set));
    }

    public byte[] getByteArray(String key, int defaultSize) {
        return this.getPartContent(key, PartByteArray.class, new byte[defaultSize]);
    }

    public void addByteArray(String key, byte[] array) {
        this.addPart(key, new PartByteArray(array));
    }

    public int[] getIntArray(String key, int defaultSize) {
        return this.getPartContent(key, PartIntArray.class, new int[defaultSize]);
    }

    public void addIntArray(String key, int[] array) {
        this.addPart(key, new PartIntArray(array));
    }

    public short[] getShortArray(String key, int defaultSize) {
        return this.getPartContent(key, PartShortArray.class, new short[defaultSize]);
    }

    public void addShortArray(String key, short[] array) {
        this.addPart(key, new PartShortArray(array));
    }

    public UUID getUniqueId(String key) {
        return this.getPartContent(key, PartUniqueId.class, null);
    }

    public void addUniqueId(String key, UUID id) {
        this.addPart(key, new PartUniqueId(id));
    }

    public byte getByte(String key) {
        return this.getPartContent(key, PartByte.class, (byte) 0);
    }

    public void addByte(String key, byte b) {
        this.addPart(key, new PartByte(b));
    }

    public short getShort(String key) {
        return this.getPartContent(key, PartShort.class, (short) 0);
    }

    public void addShort(String key, short s) {
        this.addPart(key, new PartShort(s));
    }

    public boolean getBoolean(String key) {
        return this.getPartContent(key, PartBoolean.class, false);
    }

    public void addBoolean(String key, boolean s) {
        this.addPart(key, new PartBoolean(s));
    }

    public String getString(String key) {
        return this.getPartContent(key, PartString.class, null);
    }

    public void addString(String key, String s) {
        this.addPart(key, new PartString(s));
    }

    public DataSet copy() {
        DataSet set = new DataSet();
        set.data.putAll(this.data);
        return set;
    }
}
