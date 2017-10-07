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

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.part.*;
import de.ellpeck.rockbottom.api.data.set.part.num.*;
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartByteArray;
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartIntArray;
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartShortArray;
import de.ellpeck.rockbottom.api.util.ApiInternal;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataSet{

    public final Map<String, DataPart> data = new HashMap<>();

    @ApiInternal
    public void addPart(DataPart part){
        this.data.put(part.getName(), part);
    }

    public boolean hasKey(String key){
        return this.data.containsKey(key);
    }

    @ApiInternal
    public <T> T getPartContent(String key, Class<? extends DataPart<T>> typeClass, T defaultValue){
        DataPart part = this.data.get(key);

        if(part != null && part.getClass() == typeClass){
            T result = (T)part.get();
            if(result != null){
                return result;
            }
        }

        return defaultValue;
    }

    public int getInt(String key){
        return this.getPartContent(key, PartInt.class, 0);
    }

    public void addInt(String key, int i){
        this.addPart(new PartInt(key, i));
    }

    public long getLong(String key){
        return this.getPartContent(key, PartLong.class, 0L);
    }

    public void addLong(String key, long l){
        this.addPart(new PartLong(key, l));
    }

    public float getFloat(String key){
        return this.getPartContent(key, PartFloat.class, 0F);
    }

    public void addFloat(String key, float f){
        this.addPart(new PartFloat(key, f));
    }

    public double getDouble(String key){
        return this.getPartContent(key, PartDouble.class, 0D);
    }

    public void addDouble(String key, double d){
        this.addPart(new PartDouble(key, d));
    }

    public DataSet getDataSet(String key){
        return this.getPartContent(key, PartDataSet.class, new DataSet());
    }

    public void addDataSet(String key, DataSet set){
        this.addPart(new PartDataSet(key, set));
    }

    public byte[] getByteArray(String key, int defaultSize){
        return this.getPartContent(key, PartByteArray.class, new byte[defaultSize]);
    }

    public void addByteArray(String key, byte[] array){
        this.addPart(new PartByteArray(key, array));
    }

    public int[] getIntArray(String key, int defaultSize){
        return this.getPartContent(key, PartIntArray.class, new int[defaultSize]);
    }

    public void addIntArray(String key, int[] array){
        this.addPart(new PartIntArray(key, array));
    }

    public short[] getShortArray(String key, int defaultSize){
        return this.getPartContent(key, PartShortArray.class, new short[defaultSize]);
    }

    public void addShortArray(String key, short[] array){
        this.addPart(new PartShortArray(key, array));
    }

    public UUID getUniqueId(String key){
        return this.getPartContent(key, PartUniqueId.class, null);
    }

    public void addUniqueId(String key, UUID id){
        this.addPart(new PartUniqueId(key, id));
    }

    public byte getByte(String key){
        return this.getPartContent(key, PartByte.class, (byte)0);
    }

    public void addByte(String key, byte b){
        this.addPart(new PartByte(key, b));
    }

    public short getShort(String key){
        return this.getPartContent(key, PartShort.class, (short)0);
    }

    public void addShort(String key, short s){
        this.addPart(new PartShort(key, s));
    }

    public boolean getBoolean(String key){
        return this.getPartContent(key, PartBoolean.class, false);
    }

    public void addBoolean(String key, boolean s){
        this.addPart(new PartBoolean(key, s));
    }

    public String getString(String key){
        return this.getPartContent(key, PartString.class, null);
    }

    public void addString(String key, String s){
        this.addPart(new PartString(key, s));
    }

    public void write(File file){
        RockBottomAPI.getApiHandler().writeDataSet(this, file);
    }

    public void read(File file){
        RockBottomAPI.getApiHandler().readDataSet(this, file);
    }

    @Override
    public String toString(){
        return this.data.toString();
    }

    @ApiInternal
    public Map<String, DataPart> getData(){
        return this.data;
    }

    public boolean isEmpty(){
        return this.data.isEmpty();
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || this.getClass() != o.getClass()){
            return false;
        }

        DataSet dataSet = (DataSet)o;
        return this.data.equals(dataSet.data);
    }

    @Override
    public int hashCode(){
        return this.data.hashCode();
    }

    public DataSet copy(){
        DataSet set = new DataSet();
        set.data.putAll(this.data);
        return set;
    }
}
