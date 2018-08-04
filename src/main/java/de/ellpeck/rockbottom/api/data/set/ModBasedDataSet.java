/*
 * This file ("ModBasedDataSet.java") is part of the RockBottomAPI by Ellpeck.
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

import com.google.common.base.Preconditions;
import de.ellpeck.rockbottom.api.data.set.part.*;
import de.ellpeck.rockbottom.api.data.set.part.num.*;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;
import java.util.UUID;

public final class ModBasedDataSet extends AbstractDataSet {

    @Override
    public void addPart(String name, DataPart part) {
        Preconditions.checkArgument(Util.isResourceName(name), "The name " + name + " of data part " + part + " which is being added to mod based data set " + this + " is not a valid resource name!");
        super.addPart(name, part);
    }

    public int getInt(ResourceName key) {
        return this.getPartContent(key.toString(), PartInt.class, 0);
    }

    public void addInt(ResourceName key, int i) {
        this.addPart(key.toString(), new PartInt(i));
    }

    public long getLong(ResourceName key) {
        return this.getPartContent(key.toString(), PartLong.class, 0L);
    }

    public void addLong(ResourceName key, long l) {
        this.addPart(key.toString(), new PartLong(l));
    }

    public float getFloat(ResourceName key) {
        return this.getPartContent(key.toString(), PartFloat.class, 0F);
    }

    public void addFloat(ResourceName key, float f) {
        this.addPart(key.toString(), new PartFloat(f));
    }

    public double getDouble(ResourceName key) {
        return this.getPartContent(key.toString(), PartDouble.class, 0D);
    }

    public void addDouble(ResourceName key, double d) {
        this.addPart(key.toString(), new PartDouble(d));
    }

    public DataSet getDataSet(ResourceName key) {
        return this.getPartContent(key.toString(), PartDataSet.class, new DataSet());
    }

    public void addDataSet(ResourceName key, DataSet set) {
        this.addPart(key.toString(), new PartDataSet(set));
    }

    public ModBasedDataSet getModBasedDataSet(ResourceName key) {
        return this.getPartContent(key.toString(), PartModBasedDataSet.class, new ModBasedDataSet());
    }

    public void addModBasedDataSet(ResourceName key, ModBasedDataSet set) {
        this.addPart(key.toString(), new PartModBasedDataSet(set));
    }

    public UUID getUniqueId(ResourceName key) {
        return this.getPartContent(key.toString(), PartUniqueId.class, null);
    }

    public void addUniqueId(ResourceName key, UUID id) {
        this.addPart(key.toString(), new PartUniqueId(id));
    }

    public byte getByte(ResourceName key) {
        return this.getPartContent(key.toString(), PartByte.class, (byte) 0);
    }

    public void addByte(ResourceName key, byte b) {
        this.addPart(key.toString(), new PartByte(b));
    }

    public short getShort(ResourceName key) {
        return this.getPartContent(key.toString(), PartShort.class, (short) 0);
    }

    public void addShort(ResourceName key, short s) {
        this.addPart(key.toString(), new PartShort(s));
    }

    public boolean getBoolean(ResourceName key) {
        return this.getPartContent(key.toString(), PartBoolean.class, false);
    }

    public void addBoolean(ResourceName key, boolean s) {
        this.addPart(key.toString(), new PartBoolean(s));
    }

    public String getString(ResourceName key) {
        return this.getPartContent(key.toString(), PartString.class, null);
    }

    public void addString(ResourceName key, String s) {
        this.addPart(key.toString(), new PartString(s));
    }

    public <T extends DataPart> List<T> getList(String key) {
        return (List<T>) this.getPartContent(key, PartList.class, null);
    }

    public void addList(String key, List<DataPart> list) {
        this.addPart(key, new PartList(list));
    }

    public ModBasedDataSet copy() {
        ModBasedDataSet set = new ModBasedDataSet();
        set.data.putAll(this.data);
        return set;
    }
}
