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

import com.google.common.base.Charsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.part.*;
import de.ellpeck.rockbottom.api.data.set.part.num.*;
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartByteArray;
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartIntArray;
import de.ellpeck.rockbottom.api.data.set.part.num.array.PartShortArray;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public abstract class AbstractDataSet{

    protected final Map<String, DataPart> data = new HashMap<>();
    protected final Map<String, DataPart> dataUnmodifiable = Collections.unmodifiableMap(this.data);

    static{
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("int"), 0, PartInt.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("float"), 1, PartFloat.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("double"), 2, PartDouble.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("int_array"), 3, PartIntArray.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("short_array"), 4, PartShortArray.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("byte_array"), 5, PartByteArray.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("data_set"), 6, PartDataSet.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("long"), 7, PartLong.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("uuid"), 8, PartUniqueId.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("byte"), 9, PartByte.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("short"), 10, PartShort.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("boolean"), 11, PartBoolean.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("string"), 12, PartString.class);
        RockBottomAPI.PART_REGISTRY.register(ResourceName.intern("mod_data_set"), 13, PartModBasedDataSet.class);
    }

    public void addPart(DataPart part){
        this.data.put(part.getName(), part);
    }

    public boolean hasKey(String key){
        return this.data.containsKey(key);
    }

    public DataPart remove(String key){
        return this.data.remove(key);
    }

    public void clear(){
        this.data.clear();
    }

    public int size(){
        return this.data.size();
    }

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

    @Override
    public String toString(){
        return this.data.toString();
    }

    public Map<String, DataPart> getData(){
        return this.dataUnmodifiable;
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

    /**
     * Writes a data set to the given file either as binary or as a json. Use
     * {@link #writeSafe(File, boolean)} for automatic exception handling.
     *
     * @param file   The file to write to
     * @param asJson Wether it should be stored as json
     */
    public void write(File file, boolean asJson) throws Exception{
        if(!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        if(asJson){
            JsonObject object = new JsonObject();
            this.write(object);

            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);
            Util.GSON.toJson(object, writer);
            writer.close();
        }
        else{
            DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));
            this.write(stream);
            stream.close();
        }
    }

    /**
     * @param file   The file to write to
     * @param asJson Wether it should be stored as json
     *
     * @see #write(JsonObject)
     */
    public void writeSafe(File file, boolean asJson){
        try{
            this.write(file, asJson);
        }
        catch(Exception e){
            RockBottomAPI.logger().log(Level.SEVERE, "Exception saving a data set to disk!", e);
        }
    }

    /**
     * Reads a data set from the given file either as binary or as a json and
     * stores the data in the set. Use {@link #readSafe(File, boolean)} for
     * automatic exception handling.
     *
     * @param file   The file to read from
     * @param asJson Wether or not it should be stored as json
     */
    public void read(File file, boolean asJson) throws Exception{
        if(!this.isEmpty()){
            this.clear();
        }

        if(file.exists()){
            if(asJson){
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file), Charsets.UTF_8);
                JsonObject object = Util.JSON_PARSER.parse(reader).getAsJsonObject();
                reader.close();

                this.read(object);
            }
            else{
                DataInputStream stream = new DataInputStream(new FileInputStream(file));
                this.read(stream);
                stream.close();
            }
        }
    }

    /**
     * @param file   The file to read from
     * @param asJson Wether or not it should be stored as json
     *
     * @see #read(JsonObject)
     */
    public void readSafe(File file, boolean asJson){
        try{
            this.read(file, asJson);
        }
        catch(Exception e){
            RockBottomAPI.logger().log(Level.SEVERE, "Exception loading a data set from disk!", e);
        }
    }

    /**
     * Writes a data set directly to a data output of any kind, throwing an
     * exception if something fails.
     *
     * @param stream The output to write to
     *
     * @throws Exception if writing fails for some reason
     */
    public void write(DataOutput stream) throws Exception{
        stream.writeInt(this.size());

        for(DataPart part : this.data.values()){
            this.writePart(stream, part);
        }
    }

    /**
     * Reads a data set directly from a data input of any kind, throwing an
     * exception if something fails.
     *
     * @param stream The input to read from
     *
     * @throws Exception if reading fails for some reason
     */
    public void read(DataInput stream) throws Exception{
        int amount = stream.readInt();

        for(int i = 0; i < amount; i++){
            DataPart part = this.readPart(stream);
            this.addPart(part);
        }
    }

    /**
     * Writes a data set directly to a json object head, throwing an exception
     * if something fails.
     *
     * @param main The json object to write to
     *
     * @throws Exception if writing fails for some reason
     */
    public void write(JsonObject main) throws Exception{
        JsonArray data = new JsonArray();
        for(DataPart part : this.data.values()){
            this.writePart(data, part);
        }
        main.add("data", data);
    }

    /**
     * Reads a data set directly from a json object head, throwing an exception
     * if something fails.
     *
     * @param main The json object to read from
     *
     * @throws Exception if reading fails for some reason
     */
    public void read(JsonObject main) throws Exception{
        JsonArray data = main.get("data").getAsJsonArray();
        for(int i = 0; i < data.size(); i++){
            DataPart part = this.readPart(data, i);
            this.addPart(part);
        }
    }

    private void writePart(DataOutput stream, DataPart part) throws Exception{
        stream.writeByte(RockBottomAPI.PART_REGISTRY.getId(part.getClass()));
        stream.writeUTF(part.getName());
        part.write(stream);
    }

    private DataPart readPart(DataInput stream) throws Exception{
        int id = stream.readByte();
        String name = stream.readUTF();

        Class<? extends DataPart> partClass = RockBottomAPI.PART_REGISTRY.get(id);
        DataPart part = partClass.getConstructor(String.class).newInstance(name);
        part.read(stream);

        return part;
    }

    private void writePart(JsonArray array, DataPart part) throws Exception{
        JsonObject object = new JsonObject();
        object.addProperty("type", RockBottomAPI.PART_REGISTRY.getName(part.getClass()).toString());
        object.addProperty("name", part.getName());
        object.add("data", part.write());
        array.add(object);
    }

    private DataPart readPart(JsonArray array, int i) throws Exception{
        JsonObject object = array.get(i).getAsJsonObject();
        ResourceName type = new ResourceName(object.get("type").getAsString());
        String name = object.get("name").getAsString();
        JsonElement data = object.get("data");

        Class<? extends DataPart> partClass = RockBottomAPI.PART_REGISTRY.get(type);
        DataPart part = partClass.getConstructor(String.class).newInstance(name);
        part.read(data);

        return part;
    }
}
