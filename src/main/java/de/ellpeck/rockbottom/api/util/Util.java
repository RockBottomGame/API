/*
 * This file ("Util.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public final class Util{

    public static final Random RANDOM = new Random();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final JsonParser JSON_PARSER = new JsonParser();

    public static double distance(double x1, double y1, double x2, double y2){
        return Math.sqrt(distanceSq(x1, y1, x2, y2));
    }

    public static double distanceSq(double x1, double y1, double x2, double y2){
        double dx = x2-x1;
        double dy = y2-y1;
        return (dx*dx)+(dy*dy);
    }

    public static double clamp(double num, double min, double max){
        return Math.max(min, Math.min(max, num));
    }

    public static int clamp(int num, int min, int max){
        return Math.max(min, Math.min(max, num));
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

    public static int toWorldPos(int gridPos){
        return gridPos*Constants.CHUNK_SIZE;
    }

    public static void deleteFolder(File file){
        if(file.isDirectory()){
            for(File child : file.listFiles()){
                deleteFolder(child);
            }
        }
        file.delete();
    }

    public static Entity createEntity(IResourceName name, IWorld world){
        Class<? extends Entity> entityClass = RockBottomAPI.ENTITY_REGISTRY.get(name);

        try{
            return entityClass.getConstructor(IWorld.class).newInstance(world);
        }
        catch(Exception e){
            RockBottomAPI.logger().log(Level.SEVERE, "Couldn't initialize entity with name "+name, e);
            return null;
        }
    }

    public static long getTimeMillis(){
        return System.currentTimeMillis();
    }

    public static boolean createAndOpen(File file){
        if(!file.exists()){
            file.mkdirs();
        }

        try{
            Desktop.getDesktop().open(file);
            return true;
        }
        catch(Exception e){
            RockBottomAPI.logger().log(Level.WARNING, "Couldn't open file "+file, e);
            return false;
        }
    }

    public static boolean openWebsite(String domain){
        try{
            Desktop.getDesktop().browse(new URI(domain));
            return true;
        }
        catch(Exception e){
            RockBottomAPI.logger().log(Level.WARNING, "Couldn't open website "+domain, e);
            return false;
        }
    }

    public static long shiftScramble(long l){
        l ^= (l << 21);
        l ^= (l >> 35);
        l ^= (l << 4);
        return l;
    }

    public static long scrambleSeed(int x, int y){
        return scrambleSeed(x, y, 0L);
    }

    public static long scrambleSeed(int i){
        return scrambleSeed(i, 0L);
    }

    public static long scrambleSeed(int x, int y, long seed){
        return shiftScramble(shiftScramble(x)+Long.rotateLeft(shiftScramble(y), 32))+seed;
    }

    public static long scrambleSeed(int i, long seed){
        return shiftScramble(i)+seed;
    }

    public static double polymax(double a, double b, double div){
        double h = clamp(0.5D+0.5D*(b-a)/div, 0D, 1D);
        return (b*h+a*(1D-h))+div*h*(1D-h);
    }

    public static List<Integer> makeIntList(int start, int end){
        List<Integer> list = new ArrayList<>();
        for(int i = start; i < end; i++){
            list.add(i);
        }
        return list;
    }

    public static boolean isResourceName(String s){
        int index = s.indexOf(Constants.RESOURCE_SEPARATOR);
        return index > 0 && index < s.length()-1;
    }

    public static void sleepSafe(long time){
        try{
            Thread.sleep(time);
        }
        catch(InterruptedException ignored){
        }
    }
}
