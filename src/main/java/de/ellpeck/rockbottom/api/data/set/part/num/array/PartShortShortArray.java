/*
 * This file ("PartShortShortArray.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api.data.set.part.num.array;

import de.ellpeck.rockbottom.api.data.set.part.BasicDataPart;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.Arrays;

public class PartShortShortArray extends BasicDataPart<short[][]>{

    public PartShortShortArray(String name){
        super(name);
    }

    public PartShortShortArray(String name, short[][] data){
        super(name, data);
    }

    @Override
    public void write(DataOutput stream) throws Exception{
        stream.writeInt(this.data.length);

        for(short[] array : this.data){
            stream.writeInt(array.length);

            for(int b : array){
                stream.writeShort(b);
            }
        }
    }

    @Override
    public void read(DataInput stream) throws Exception{
        int amount = stream.readInt();
        this.data = new short[amount][];

        for(int i = 0; i < amount; i++){
            int innerAmount = stream.readInt();
            this.data[i] = new short[innerAmount];

            for(int j = 0; j < innerAmount; j++){
                this.data[i][j] = stream.readShort();
            }
        }
    }

    @Override
    public String toString(){
        return Arrays.deepToString(this.data);
    }
}
