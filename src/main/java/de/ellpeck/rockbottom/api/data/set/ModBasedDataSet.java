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
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartByteArray;
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartIntArray;
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartShortArray;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.UUID;

public final class ModBasedDataSet extends AbstractDataSet{

    @Override
    public void addPart(DataPart part){
        Preconditions.checkArgument(Util.isResourceName(part.getName()), "The name "+part.getName()+" of data part "+part+" which is being added to mod based data set "+this+" is not a valid resource name!");
        super.addPart(part);
    }

    public int getInt(ResourceName key){
        return this.getPartContent(key.toString(), PartInt.class, 0);
    }

    public void addInt(ResourceName key, int i){
        this.addPart(new PartInt(key.toString(), i));
    }

    public long getLong(ResourceName key){
        return this.getPartContent(key.toString(), PartLong.class, 0L);
    }

    public void addLong(ResourceName key, long l){
        this.addPart(new PartLong(key.toString(), l));
    }

    public float getFloat(ResourceName key){
        return this.getPartContent(key.toString(), PartFloat.class, 0F);
    }

    public void addFloat(ResourceName key, float f){
        this.addPart(new PartFloat(key.toString(), f));
    }

    public double getDouble(ResourceName key){
        return this.getPartContent(key.toString(), PartDouble.class, 0D);
    }

    public void addDouble(ResourceName key, double d){
        this.addPart(new PartDouble(key.toString(), d));
    }

    public DataSet getDataSet(ResourceName key){
        return this.getPartContent(key.toString(), PartDataSet.class, new DataSet());
    }

    public void addDataSet(ResourceName key, DataSet set){
        this.addPart(new PartDataSet(key.toString(), set));
    }

    public ModBasedDataSet getModBasedDataSet(ResourceName key){
        return this.getPartContent(key.toString(), PartModBasedDataSet.class, new ModBasedDataSet());
    }

    public void addModBasedDataSet(ResourceName key, ModBasedDataSet set){
        this.addPart(new PartModBasedDataSet(key.toString(), set));
    }

    public byte[] getByteArray(ResourceName key, int defaultSize){
        return this.getPartContent(key.toString(), PartByteArray.class, new byte[defaultSize]);
    }

    public void addByteArray(ResourceName key, byte[] array){
        this.addPart(new PartByteArray(key.toString(), array));
    }

    public int[] getIntArray(ResourceName key, int defaultSize){
        return this.getPartContent(key.toString(), PartIntArray.class, new int[defaultSize]);
    }

    public void addIntArray(ResourceName key, int[] array){
        this.addPart(new PartIntArray(key.toString(), array));
    }

    public short[] getShortArray(ResourceName key, int defaultSize){
        return this.getPartContent(key.toString(), PartShortArray.class, new short[defaultSize]);
    }

    public void addShortArray(ResourceName key, short[] array){
        this.addPart(new PartShortArray(key.toString(), array));
    }

    public UUID getUniqueId(ResourceName key){
        return this.getPartContent(key.toString(), PartUniqueId.class, null);
    }

    public void addUniqueId(ResourceName key, UUID id){
        this.addPart(new PartUniqueId(key.toString(), id));
    }

    public byte getByte(ResourceName key){
        return this.getPartContent(key.toString(), PartByte.class, (byte)0);
    }

    public void addByte(ResourceName key, byte b){
        this.addPart(new PartByte(key.toString(), b));
    }

    public short getShort(ResourceName key){
        return this.getPartContent(key.toString(), PartShort.class, (short)0);
    }

    public void addShort(ResourceName key, short s){
        this.addPart(new PartShort(key.toString(), s));
    }

    public boolean getBoolean(ResourceName key){
        return this.getPartContent(key.toString(), PartBoolean.class, false);
    }

    public void addBoolean(ResourceName key, boolean s){
        this.addPart(new PartBoolean(key.toString(), s));
    }

    public String getString(ResourceName key){
        return this.getPartContent(key.toString(), PartString.class, null);
    }

    public void addString(ResourceName key, String s){
        this.addPart(new PartString(key.toString(), s));
    }

    public ModBasedDataSet copy(){
        ModBasedDataSet set = new ModBasedDataSet();
        set.data.putAll(this.data);
        return set;
    }
}
