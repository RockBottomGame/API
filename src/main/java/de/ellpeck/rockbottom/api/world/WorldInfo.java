/*
 * This file ("WorldInfo.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.world;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import io.netty.buffer.ByteBuf;

import java.io.File;
import java.util.UUID;

public class WorldInfo{

    private static final String NAME = "world_info.dat";
    @ApiInternal
    private final File dataFile;

    public long seed;
    public int totalTimeInWorld;
    public int currentWorldTime = 3000;
    public boolean storyMode = true;
    public UUID lastPlayerId;

    @ApiInternal
    public WorldInfo(File worldDirectory){
        this.dataFile = new File(worldDirectory, NAME);
    }

    public static boolean exists(File directory){
        return new File(directory, NAME).exists();
    }

    public static long lastModified(File directory){
        return new File(directory, NAME).lastModified();
    }

    @ApiInternal
    public void load(){
        DataSet dataSet = new DataSet();
        dataSet.readSafe(this.dataFile, false);

        this.seed = dataSet.getLong("seed");
        this.totalTimeInWorld = dataSet.getInt("total_time");
        this.currentWorldTime = dataSet.getInt("curr_time");
        //TODO Remove this legacy compat check eventually
        if(dataSet.hasKey("story_mode")){
            this.storyMode = dataSet.getBoolean("story_mode");
        }
        this.lastPlayerId = dataSet.getUniqueId("last_player");
    }

    @ApiInternal
    public void save(){
        DataSet dataSet = new DataSet();

        dataSet.addLong("seed", this.seed);
        dataSet.addInt("total_time", this.totalTimeInWorld);
        dataSet.addInt("curr_time", this.currentWorldTime);
        dataSet.addBoolean("story_mode", this.storyMode);
        if(this.lastPlayerId != null){
            dataSet.addUniqueId("last_player", this.lastPlayerId);
        }

        dataSet.writeSafe(this.dataFile, false);
    }

    @ApiInternal
    public void toBuffer(ByteBuf buf){
        buf.writeLong(this.seed);
        buf.writeInt(this.totalTimeInWorld);
        buf.writeInt(this.currentWorldTime);
        buf.writeBoolean(this.storyMode);
    }

    @ApiInternal
    public void fromBuffer(ByteBuf buf){
        this.seed = buf.readLong();
        this.totalTimeInWorld = buf.readInt();
        this.currentWorldTime = buf.readInt();
        this.storyMode = buf.readBoolean();
    }
}
