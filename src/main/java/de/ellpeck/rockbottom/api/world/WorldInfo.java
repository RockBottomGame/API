/*
 * This file ("WorldInfo.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame>.
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

package de.ellpeck.rockbottom.api.world;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import io.netty.buffer.ByteBuf;

import java.io.File;

public class WorldInfo{

    private final File dataFile;

    public long seed;
    public int totalTimeInWorld;
    public int currentWorldTime = 3000;

    public WorldInfo(File worldDirectory){
        this.dataFile = new File(worldDirectory, "world_info.dat");
    }

    public static boolean exists(File directory){
        return new File(directory, "world_info.dat").exists();
    }

    public static long lastModified(File directory){
        return new File(directory, "world_info.dat").lastModified();
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
