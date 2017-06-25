/*
 * This file ("Util.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.util;

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import org.newdawn.slick.Color;
import org.newdawn.slick.util.Log;

import java.io.File;
import java.util.Random;

public final class Util{

    /**
     * An unseeded {@link Random} for general use
     */
    public static final Random RANDOM = new Random();

    public static double distance(double x1, double y1, double x2, double y2){
        return Math.sqrt(distanceSq(x1, y1, x2, y2));
    }

    public static double distanceSq(double x1, double y1, double x2, double y2){
        double dx = x2-x1;
        double dy = y2-y1;
        return (dx*dx)+(dy*dy);
    }

    public static int floor(double value){
        int i = (int)value;
        return value < (double)i ? i-1 : i;
    }

    public static int ceil(double value){
        int i = (int)value;
        return value > (double)i ? i+1 : i;
    }

    public static int toGridPos(double worldPos){
        return floor(worldPos/(double)Constants.CHUNK_SIZE);
    }

    public static int toGridAlignedWorldPos(double worldPos){
        return toGridPos(worldPos)*Constants.CHUNK_SIZE;
    }

    public static int toWorldPos(int gridPos){
        return gridPos*Constants.CHUNK_SIZE;
    }

    public static void deleteFolder(File file) throws Exception{
        if(file.isDirectory()){
            for(File child : file.listFiles()){
                deleteFolder(child);
            }
        }
        file.delete();
    }

    public static Color randomColor(Random rand){
        return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    public static int toIntColor(Color color){
        return (color.getRedByte() << 24)+(color.getGreenByte() << 16)+(color.getBlueByte() << 8)+color.getAlphaByte();
    }

    public static Entity createEntity(IResourceName name, IWorld world){
        Class<? extends Entity> entityClass = RockBottomAPI.ENTITY_REGISTRY.get(name);

        try{
            return entityClass.getConstructor(IWorld.class).newInstance(world);
        }
        catch(Exception e){
            Log.error("Couldn't initialize entity with name "+name, e);
            return null;
        }
    }

    public static String trimString(String s, int length){
        if(s.length() <= length){
            return s;
        }
        else{
            return s.substring(0, length);
        }
    }

}
