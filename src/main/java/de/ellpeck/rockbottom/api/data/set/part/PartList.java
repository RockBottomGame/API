package de.ellpeck.rockbottom.api.data.set.part;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.RockBottomAPI;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.ArrayList;
import java.util.List;

public class PartList extends BasicDataPart<List<? extends DataPart>> {

    public static final IPartFactory<PartList> FACTORY = new IPartFactory<PartList>() {
        @Override
        public PartList parse(JsonElement element) throws Exception {
            if (element.isJsonArray()) {
                List<DataPart> parts = new ArrayList<>();
                for (JsonElement e : element.getAsJsonArray()) {
                    parts.add(RockBottomAPI.getApiHandler().readDataPart(e));
                }
                return new PartList(parts);
            } else {
                return null;
            }
        }

        @Override
        public PartList parse(DataInput stream) throws Exception {
            List<DataPart> parts = new ArrayList<>();
            int size = stream.readInt();
            if (size > 0) {
                IPartFactory factory = RockBottomAPI.PART_REGISTRY.get((int) stream.readByte());
                for (int i = 0; i < size; i++) {
                    parts.add(factory.parse(stream));
                }
            }
            return new PartList(parts);
        }
    };

    public PartList(List<? extends DataPart> data) {
        super(data);
    }

    @Override
    public void write(DataOutput stream) throws Exception {
        stream.writeInt(this.data.size());
        for (int i = 0; i < this.data.size(); i++) {
            DataPart part = this.data.get(i);
            if (i == 0) {
                stream.writeByte(RockBottomAPI.PART_REGISTRY.getId(part.getFactory()));
            }
            part.write(stream);
        }
    }

    @Override
    public JsonElement write() throws Exception {
        JsonArray array = new JsonArray();
        for (DataPart part : this.data) {
            array.add(part.write());
        }
        return array;
    }

    @Override
    public IPartFactory<? extends DataPart<List<? extends DataPart>>> getFactory() {
        return FACTORY;
    }
}
