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
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.UUID;

public class ModBasedDataSet extends AbstractDataSet{

    @Override
    public void addPart(DataPart part){
        Preconditions.checkArgument(Util.isResourceName(part.getName()), "The name "+part.getName()+" of data part "+part+" which is being added to mod based data set "+this+" is not a valid resource name!");
        super.addPart(part);
    }

    public int getInt(IResourceName key){
        return this.getPartContent(key.toString(), PartInt.class, 0);
    }

    public void addInt(IResourceName key, int i){
        this.addPart(new PartInt(key.toString(), i));
    }

    public long getLong(IResourceName key){
        return this.getPartContent(key.toString(), PartLong.class, 0L);
    }

    public void addLong(IResourceName key, long l){
        this.addPart(new PartLong(key.toString(), l));
    }

    public float getFloat(IResourceName key){
        return this.getPartContent(key.toString(), PartFloat.class, 0F);
    }

    public void addFloat(IResourceName key, float f){
        this.addPart(new PartFloat(key.toString(), f));
    }

    public double getDouble(IResourceName key){
        return this.getPartContent(key.toString(), PartDouble.class, 0D);
    }

    public void addDouble(IResourceName key, double d){
        this.addPart(new PartDouble(key.toString(), d));
    }

    public DataSet getDataSet(IResourceName key){
        return this.getPartContent(key.toString(), PartDataSet.class, new DataSet());
    }

    public void addDataSet(IResourceName key, DataSet set){
        this.addPart(new PartDataSet(key.toString(), set));
    }

    public ModBasedDataSet getModBasedDataSet(IResourceName key){
        return this.getPartContent(key.toString(), PartModBasedDataSet.class, new ModBasedDataSet());
    }

    public void addModBasedDataSet(IResourceName key, ModBasedDataSet set){
        this.addPart(new PartModBasedDataSet(key.toString(), set));
    }

    public byte[] getByteArray(IResourceName key, int defaultSize){
        return this.getPartContent(key.toString(), PartByteArray.class, new byte[defaultSize]);
    }

    public void addByteArray(IResourceName key, byte[] array){
        this.addPart(new PartByteArray(key.toString(), array));
    }

    public int[] getIntArray(IResourceName key, int defaultSize){
        return this.getPartContent(key.toString(), PartIntArray.class, new int[defaultSize]);
    }

    public void addIntArray(IResourceName key, int[] array){
        this.addPart(new PartIntArray(key.toString(), array));
    }

    public short[] getShortArray(IResourceName key, int defaultSize){
        return this.getPartContent(key.toString(), PartShortArray.class, new short[defaultSize]);
    }

    public void addShortArray(IResourceName key, short[] array){
        this.addPart(new PartShortArray(key.toString(), array));
    }

    public UUID getUniqueId(IResourceName key){
        return this.getPartContent(key.toString(), PartUniqueId.class, null);
    }

    public void addUniqueId(IResourceName key, UUID id){
        this.addPart(new PartUniqueId(key.toString(), id));
    }

    public byte getByte(IResourceName key){
        return this.getPartContent(key.toString(), PartByte.class, (byte)0);
    }

    public void addByte(IResourceName key, byte b){
        this.addPart(new PartByte(key.toString(), b));
    }

    public short getShort(IResourceName key){
        return this.getPartContent(key.toString(), PartShort.class, (short)0);
    }

    public void addShort(IResourceName key, short s){
        this.addPart(new PartShort(key.toString(), s));
    }

    public boolean getBoolean(IResourceName key){
        return this.getPartContent(key.toString(), PartBoolean.class, false);
    }

    public void addBoolean(IResourceName key, boolean s){
        this.addPart(new PartBoolean(key.toString(), s));
    }

    public String getString(IResourceName key){
        return this.getPartContent(key.toString(), PartString.class, null);
    }

    public void addString(IResourceName key, String s){
        this.addPart(new PartString(key.toString(), s));
    }

    public ModBasedDataSet copy(){
        ModBasedDataSet set = new ModBasedDataSet();
        set.data.putAll(this.data);
        return set;
    }
}
