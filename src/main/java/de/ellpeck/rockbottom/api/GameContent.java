/*
 * This file ("GameContent.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api;

import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;

public final class GameContent{

    public static final Tile TILE_AIR = getTile("air");
    public static final Tile TILE_DIRT = getTile("dirt");
    public static final Tile TILE_STONE = getTile("stone");
    public static final Tile TILE_GRASS = getTile("grass");
    public static final Tile TILE_WOOD_BOARDS = getTile("wood_boards");
    public static final Tile TILE_TORCH = getTile("torch");
    public static final Tile TILE_CHEST = getTile("chest");
    public static final Tile TILE_LOG = getTile("log");
    public static final Tile TILE_LEAVES = getTile("leaves");
    public static final Tile TILE_COAL_ORE = getTile("coal_ore");
    public static final Tile TILE_COPPER_ORE = getTile("copper_ore");
    public static final Tile TILE_SMELTER = getTile("smelter");
    public static final Tile TILE_SEPARATOR = getTile("separator");
    public static final Tile TILE_SAPLING = getTile("sapling");
    public static final Tile TILE_LADDER = getTile("ladder");
    public static final Tile TILE_GLOW_ORE = getTile("glow_ore");
    public static final Tile TILE_STAMPER = getTile("stamper");
    public static final Tile TILE_HARDENED_STONE = getTile("hardened_stone");
    public static final Tile TILE_DOOR = getTile("door");
    public static final Tile TILE_CONSTRUCTION_TABLE = getTile("construction_table");
    public static final Tile TILE_LAMP = getTile("lamp");

    public static final Item ITEM_SUPER_TOOL = getItem("super_tool");
    public static final Item ITEM_WOOD_PICK = getItem("wood_pickaxe");
    public static final Item ITEM_STONE_PICKAXE = getItem("stone_pickaxe");
    public static final Item ITEM_STONE_AXE = getItem("stone_axe");
    public static final Item ITEM_STONE_SHOVEL = getItem("stone_shovel");
    public static final Item ITEM_COAL = getItem("coal");
    public static final Item ITEM_COPPER_CLUSTER = getItem("copper_cluster");
    public static final Item ITEM_COPPER_GRIT = getItem("copper_grit");
    public static final Item ITEM_COPPER_INGOT = getItem("copper_ingot");
    public static final Item ITEM_COPPER_PICKAXE = getItem("copper_pickaxe");
    public static final Item ITEM_COPPER_AXE = getItem("copper_axe");
    public static final Item ITEM_COPPER_SHOVEL = getItem("copper_shovel");
    public static final Item ITEM_SLAG = getItem("slag");
    public static final Item ITEM_GLOW_CLUSTER = getItem("glow_cluster");

    public static final Biome BIOME_SKY = getBiome("sky");
    public static final Biome BIOME_GRASSLAND = getBiome("grassland");

    private static Biome getBiome(String name){
        return get(name, RockBottomAPI.BIOME_REGISTRY);
    }

    private static Item getItem(String name){
        return get(name, RockBottomAPI.ITEM_REGISTRY);
    }

    private static Tile getTile(String name){
        return get(name, RockBottomAPI.TILE_REGISTRY);
    }

    private static <T> T get(String name, NameRegistry<T> registry){
        IResourceName res = RockBottomAPI.createInternalRes(name);
        T thing = registry.get(res);

        if(thing == null){
            throw new IllegalStateException("Object with name "+res+" was not found in registry "+registry+"! This is probably due to GameContent being accessed before the game has initialized!");
        }
        else{
            return thing;
        }
    }
}
