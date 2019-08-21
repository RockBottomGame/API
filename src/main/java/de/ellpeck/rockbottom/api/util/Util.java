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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

/**
 * This class provides a large variety of helpful utility methods and variables
 * that can be useful in a multitude of circumstances.
 */
public final class Util {

    /**
     * A global random with a randomized seed that can be used for operations
     * that are not based on the world seed. Note that, when doing something
     * like world generation, the world's {@link IWorld#getSeed()} should always
     * be used. For that, there are several utility methods at the bottom of
     * this class to help modify the world seed in a predictable pattern.
     *
     * @see IWorld#getSeed()
     * @see #scrambleSeed(int, long)
     */
    public static final Random RANDOM = new Random();
    /**
     * A global instance of google gson' gson that can be used to serialize and
     * deserialize json objects. This instance has pretty printing turned on,
     * meaning any JSONs that are created with it will look easily readable and
     * will not be a single line.
     */
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    /**
     * A global instance of google gson's json parser that can be used to parse
     * jsons into easily modifyable and queryable json objects.
     */
    public static final JsonParser JSON_PARSER = new JsonParser();

    /**
     * Returns the absolute distance between two points using the Pythagorean
     * theorem. Note that, if you want to just compare distances and don't care
     * about the actual, absolute value of the distance, it is a lot faster to
     * use {@link #distanceSq(double, double, double, double)}.
     *
     * @param x1 The first point's x
     * @param y1 The first point's y
     * @param x2 The second point's x
     * @param y2 The second point's y
     * @return The distance
     * @see #distanceSq(double, double, double, double)
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(distanceSq(x1, y1, x2, y2));
    }

    /**
     * Returns the squared distance between two points using the Pythagorean
     * theorem.
     *
     * @param x1 The first point's x
     * @param y1 The first point's y
     * @param x2 The second point's x
     * @param y2 The second point's y
     * @return The squared distance
     * @see #distance(double, double, double, double)
     */
    public static double distanceSq(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return (dx * dx) + (dy * dy);
    }

    public static double lerp(double pos1, double pos2, double factor) {
        return (1D - factor) * pos1 + factor * pos2;
    }

    /**
     * Clamps a value between a minimum and a maximum value. If the given value
     * is higher than the maximum, the maximum will be returned. If it is lower
     * than the minimum, the minimum will be returned. Else, the actual value
     * will be returned.
     *
     * @param num The number to clamp
     * @param min The minimum
     * @param max The maximum
     * @return The clamped number
     * @see #clamp(int, int, int)
     */
    public static double clamp(double num, double min, double max) {
        return Math.max(min, Math.min(max, num));
    }

    /**
     * Clamps a value between a minimum and a maximum value. If the given value
     * is higher than the maximum, the maximum will be returned. If it is lower
     * than the minimum, the minimum will be returned. Else, the actual value
     * will be returned.
     *
     * @param num The number to clamp
     * @param min The minimum
     * @param max The maximum
     * @return The clamped number
     * @see #clamp(double, double, double)
     */
    public static int clamp(int num, int min, int max) {
        return Math.max(min, Math.min(max, num));
    }

    /**
     * This is a faster implementation of {@link Math#floor(double)}, in that it
     * simply typecasts the given value and then adjusts it accordingly. This
     * results in the integer that is closest to the given value (in the
     * direction of 0) will be returned.
     *
     * @param value The value to floor
     * @return The floored value
     */
    public static int floor(double value) {
        int i = (int) value;
        return value < (double) i ? i - 1 : i;
    }

    /**
     * This is a faster implementation of {@link Math#ceil(double)}, in that it
     * simply typecasts the given value and then adjusts it accordingly. This
     * results in the integer that is closest to the given value (in the
     * direction of negative infinity, if the input is negative, or positive
     * infinity otherwise) will be returned.
     *
     * @param value The value to ceiling
     * @return The ceilinged value
     */
    public static int ceil(double value) {
        int i = (int) value;
        return value > (double) i ? i + 1 : i;
    }

    /**
     * Converts a given position in the world to a chunk grid position. In other
     * words, giving any position in the world to this method will return the
     * position of the chunk that this position would be in.
     *
     * @param worldPos The world position
     * @return The chunk position
     * @see IChunk#getGridX()
     * @see IChunk#getGridY()
     */
    public static int toGridPos(double worldPos) {
        return floor(worldPos / (double) Constants.CHUNK_SIZE);
    }

    /**
     * Converts a given position of a chunk into its world position. In other
     * words, giving any chunk's grid position to this method will return the
     * position of the chunk in the world.
     *
     * @param gridPos The chunk grid position
     * @return The world position
     */
    public static int toWorldPos(int gridPos) {
        return gridPos * Constants.CHUNK_SIZE;
    }

    /**
     * A helper method to recursively delete a folder and all of its contents.
     * Just calling {@link File#delete()} will fail if it is a folder that
     * contains other files, so this method clears all of the files in a folder
     * first before deleting it.
     *
     * @param file The folder to delete
     */
    public static void deleteFolder(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteFolder(child);
            }
        }
        file.delete();
    }

    /**
     * Creates an entity from its entity registry name and with the given
     * world.
     *
     * @param name  The registry name of the entity
     * @param world The world that the entity should be in
     * @return The newly created entity
     * @see Registries#ENTITY_REGISTRY
     */
    public static Entity createEntity(ResourceName name, IWorld world) {
        Class<? extends Entity> entityClass = Registries.ENTITY_REGISTRY.get(name);

        try {
            return entityClass.getConstructor(IWorld.class).newInstance(world);
        } catch (Exception e) {
            RockBottomAPI.logger().log(Level.SEVERE, "Couldn't initialize entity with name " + name, e);
            return null;
        }
    }

    /**
     * Gets the system time in milliseconds. This is merely to shorten the long
     * call to {@link System#currentTimeMillis()} to a more sensible amount of
     * characters.
     *
     * @return The system time in milliseconds
     */
    public static long getTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * Creates a file if it doesn't exist and then opens it with the operating
     * system's default file opening software. This is a utility method as
     * usually, just using {@link Desktop#open(File)} would cause an exception
     * if the folder does not exist
     *
     * @param file The file to open
     * @return True if it worked, false if some exception was thrown
     */
    public static boolean createAndOpen(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            Desktop.getDesktop().open(file);
            return true;
        } catch (Exception e) {
            RockBottomAPI.logger().log(Level.WARNING, "Couldn't open file " + file, e);
            return false;
        }
    }

    /**
     * Opens a website in the operating system's default browser. This is a
     * utility method as {@link Desktop#browse(URI)} can throw an exception.
     *
     * @param domain The domain to open
     * @return True if it worked, false if some exception was thrown
     */
    public static boolean openWebsite(String domain) {
        try {
            Desktop.getDesktop().browse(new URI(domain));
            return true;
        } catch (Exception e) {
            RockBottomAPI.logger().log(Level.WARNING, "Couldn't open website " + domain, e);
            return false;
        }
    }

    /**
     * Scrambles a seed in a predictable, always-equal, but seemingly random
     * way, making the output seem like a completely different number. This can
     * be used for world generation when you need a different seed that,
     * nevertheless, should still be influenced by the world's seed. To scramble
     * a seed based on some other value, see {@link #scrambleSeed(int, long)}.
     *
     * @param l The long to scramble
     * @return The scrambled long
     * @see IWorld#getSeed()
     * @see #scrambleSeed(int, long)
     */
    public static long shiftScramble(long l) {
        l ^= (l << 21);
        l ^= (l >> 35);
        l ^= (l << 4);
        return l;
    }

    /**
     * Scrambles an x and a y value together into a seed that can be used for
     * world gen. Note that this is not influenced by another seed. Obviously,
     * these values do not have to be actual positions in the world, but can
     * just be two arbitrary numbers.
     *
     * @param x The x value
     * @param y The y value
     * @return The seed
     */
    public static long scrambleSeed(int x, int y) {
        return scrambleSeed(x, y, 0L);
    }

    /**
     * Scrambles a single value into a seed that can be used for world gen. Note
     * that this is not influenced by another seed.
     *
     * @param i The value
     * @return The seed
     */
    public static long scrambleSeed(int i) {
        return scrambleSeed(i, 0L);
    }

    /**
     * Scrambles an x and a y value into a seed based on a different seed. This
     * can be used for world gen. Obviously, these values do not have to be
     * actual positions in the world, but can just be two arbitrary numbers.
     *
     * @param x    The x value
     * @param y    The y value
     * @param seed The original seed
     * @return The new seed
     */
    public static long scrambleSeed(int x, int y, long seed) {
        return shiftScramble(shiftScramble(x) + Long.rotateLeft(shiftScramble(y), 32)) + seed;
    }

    /**
     * Scrambles a value into a seed based on a different seed. This can be used
     * for world gen.
     *
     * @param i    The value
     * @param seed The original seed
     * @return The new seed
     */
    public static long scrambleSeed(int i, long seed) {
        return shiftScramble(i) + seed;
    }

    /**
     * Creates a list of integers starting at the first number and ending at the
     * second number. This can be especially useful for creating {@link
     * IFilteredInventory} instances with a lot of slots.
     *
     * @param start The first number (inclusive)
     * @param end   The second number (exclusive)
     * @return A list of integers
     * @see IFilteredInventory#getInputSlots(ItemInstance, Direction)
     * @see IFilteredInventory#getOutputSlots(Direction)
     */
    public static List<Integer> makeIntList(int start, int end) {
        List<Integer> list = new ArrayList<>();
        for (int i = start; i < end; i++) {
            list.add(i);
        }
        return list;
    }

    /**
     * Utility method to figure out if a string is a resource name, meaning if
     * it contains a {@link Constants#RESOURCE_SEPARATOR} that is not at the
     * beginning or end of the string.
     *
     * @param s The string to check
     * @return If the string is a resource name
     */
    public static boolean isResourceName(String s) {
        int index = s.indexOf(Constants.RESOURCE_SEPARATOR);
        return index > 0 && index < s.length() - 1;
    }

    /**
     * Creates an integer representation of a bit vector from an array of booleans
     * Reverse of {@link Util#decodeBitVector(int, int)}
     * @param inputs The bits as booleans
     * @return The int equivalent of ORing the boolean inputs.
     *
     * @see Util#decodeBitVector(int, int)
     */
    public static int encodeBitVector(boolean... inputs) {
        if (inputs.length == 0) return 0;
        int out = 0;
        int max = Math.min(inputs.length, 32);
        for (int i = 0; i < max; i++) {
            if (inputs[i])
                out += 1 << i;
        }
        return out;
    }

    /**
     * Separates a bit vector into an array of booleans whose are true if the bit is 1.
     * Reverse of {@link Util#encodeBitVector(boolean...)}
     * @param input The input to decode
     * @param size The size of the out array (how many bits to check).
     * @return An array of booleans representing the bits of the input
     *
     * @see Util#encodeBitVector(boolean...)
     */
    public static boolean[] decodeBitVector(int input, int size) {
        int max = Math.min(size, 32);
        boolean[] out = new boolean[max];
        for (int i = 0; i < max; i++) {
            out[i] = ((input >> i) & 1) == 1;
        }
        return out;
    }

    /**
     * Causes the current thread to sleep by the given amount of milliseconds,
     * ignoring any interruptions that might be thrown. This is a helper method
     * to reduce the amount of space that a try-catch statement takes up in
     * code.
     *
     * @param time The time to sleep for
     */
    public static void sleepSafe(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {
        }
    }
}
