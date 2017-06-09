/*
 * This file ("WorldInfo.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 */

package de.ellpeck.rockbottom.api.world;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import io.netty.buffer.ByteBuf;

import java.io.File;

public class WorldInfo{

    private final File dataFile;

    public long seed;
    public int totalTimeInWorld;
    public int currentWorldTime;

    public WorldInfo(File worldDirectory){
        this.dataFile = new File(worldDirectory, "world_info.dat");
    }

    public void load(){
        DataSet dataSet = new DataSet();
        dataSet.read(this.dataFile);

        this.seed = dataSet.getLong("seed");
        this.totalTimeInWorld = dataSet.getInt("total_time");
        this.currentWorldTime = dataSet.getInt("curr_time");
    }

    public void save(){
        DataSet dataSet = new DataSet();
        dataSet.addLong("seed", this.seed);
        dataSet.addInt("total_time", this.totalTimeInWorld);
        dataSet.addInt("curr_time", this.currentWorldTime);
        dataSet.write(this.dataFile);
    }

    public void toBuffer(ByteBuf buf){
        buf.writeLong(this.seed);
        buf.writeInt(this.totalTimeInWorld);
        buf.writeInt(this.currentWorldTime);
    }

    public void fromBuffer(ByteBuf buf){
        this.seed = buf.readLong();
        this.totalTimeInWorld = buf.readInt();
        this.currentWorldTime = buf.readInt();
    }
}
