/*
 * This file ("GameContent.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api;

import com.google.common.base.Preconditions;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;
import de.ellpeck.rockbottom.api.construction.resource.ResInfo;
import de.ellpeck.rockbottom.api.effect.IEffect;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.TileMeta;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;

/**
 * This class houses all of the game's default content like all the {@link
 * Tile}, {@link Item}, {@link Biome}, {@link IEffect} and {@link
 * IResourceRegistry} entry objects as static variables that can be accessed
 * from anywhere.
 */
public final class GameContent {

    /*
        ---TILES---
     */
    public static final Tile TILE_AIR = getTile("air");
    public static final Tile TILE_SOIL = getTile("soil").addEffectiveTool(ToolProperty.SHOVEL, 0).setForceDrop().setMaxAmount(100);
    public static final Tile TILE_GRASS = getTile("grass").addEffectiveTool(ToolProperty.SHOVEL, 0).setForceDrop().setMaxAmount(100);
    public static final Tile TILE_STONE = getTile("stone").setHardness(5F).addEffectiveTool(ToolProperty.PICKAXE, 0).setMaxAmount(50);
    public static final TileMeta TILE_GRASS_TUFT = (TileMeta) getTile("grass_tuft").setHardness(0F).setForceDrop().setMaxAmount(150);
    public static final Tile TILE_LOG = getTile("log").setHardness(3F).addEffectiveTool(ToolProperty.AXE, 0).setMaxAmount(75).setForceDrop();
    public static final Tile TILE_LEAVES = getTile("leaves").setHardness(0.5F).setForceDrop().setMaxAmount(150);
    public static final TileMeta TILE_FLOWER = (TileMeta) getTile("flower").setHardness(0F).setForceDrop().setMaxAmount(150);
    public static final Tile TILE_PEBBLES = getTile("pebbles").setHardness(0F).setForceDrop().setMaxAmount(200);
    public static final Tile TILE_SAND = getTile("sand").setHardness(0.75F).addEffectiveTool(ToolProperty.SHOVEL, 0).setForceDrop().setMaxAmount(100);
    public static final Tile TILE_SANDSTONE = getTile("sandstone").setHardness(4F).addEffectiveTool(ToolProperty.PICKAXE, 0).setMaxAmount(75);
    public static final Tile TILE_COAL = getTile("coal").setHardness(6F).addEffectiveTool(ToolProperty.PICKAXE, 1).setMaxAmount(45);
    public static final Tile TILE_TORCH = getTile("torch").setHardness(0F).setForceDrop().setMaxAmount(50);
    public static final Tile TILE_LADDER = getTile("ladder").setHardness(1.5F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(100);
    public static final Tile TILE_CHEST = getTile("chest").setHardness(4F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(5);
    public static final Tile TILE_SIGN = getTile("sign").addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(100);
    public static final Tile TILE_SAPLING = getTile("sapling").setHardness(0F).setForceDrop().setMaxAmount(150);
    public static final TileLiquid TILE_WATER = (TileLiquid) getTile("water");
    public static final TileMeta TILE_WOOD_BOARDS = (TileMeta) getTile("wood_boards").setHardness(2F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(75);
    public static final Tile TILE_WOOD_DOOR = getTile("wood_door").setHardness(4F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(5);
    public static final Tile TILE_WOOD_DOOR_OLD = getTile("wood_door_old").setHardness(3.5F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(5);
    public static final Tile TILE_REMAINS_GOO = getTile("remains_goo");
    public static final Tile TILE_GRASS_TORCH = getTile("torch_grass").setHardness(0F).setForceDrop().setMaxAmount(50);
    public static final Tile TILE_COPPER = getTile("copper").setHardness(10F).addEffectiveTool(ToolProperty.PICKAXE, 2).setMaxAmount(35);
    public static final Tile TILE_SPINNING_WHEEL = getTile("spinning_wheel").setHardness(7F).addEffectiveTool(ToolProperty.PICKAXE, 1).setMaxAmount(1);
    public static final Tile TILE_SIMPLE_FURNACE = getTile("simple_furnace").setHardness(15F).addEffectiveTool(ToolProperty.PICKAXE, 2).setMaxAmount(1);
    public static final Tile TILE_CONSTRUCTION_TABLE = getTile("construction_table").setHardness(15F).addEffectiveTool(ToolProperty.PICKAXE, 2).setMaxAmount(1);
    public static final Tile TILE_SMITHING_TABLE = getTile("smithing_table").setHardness(15F).addEffectiveTool(ToolProperty.PICKAXE, 2).setMaxAmount(1);
    public static final Tile TILE_SNOW = getTile("snow").setHardness(0.75F).addEffectiveTool(ToolProperty.SHOVEL, 0).setMaxAmount(200);
    public static final TileMeta TILE_CAVE_MUSHROOM = (TileMeta) getTile("cave_mushroom").setHardness(0.25F).setMaxAmount(50).setForceDrop();
    public static final Tile TILE_STARDROP = getTile("stardrop").setHardness(0.75F).setMaxAmount(10).setForceDrop();
    public static final Tile TILE_IRON_LAMP = getTile("lamp_iron").setHardness(10F).addEffectiveTool(ToolProperty.PICKAXE, 8).setMaxAmount(20).setForceDrop();
    public static final Tile TILE_MORTAR = getTile("mortar").setHardness(3F).addEffectiveTool(ToolProperty.PICKAXE, 0).setMaxAmount(5);
    public static final Tile TILE_SOIL_TILLED = getTile("soil_tilled").setHardness(0.8F).addEffectiveTool(ToolProperty.SHOVEL, 0).setForceDrop();
    public static final Tile TILE_CORN = getTile("corn").setHardness(2F).setForceDrop().setMaxAmount(25);
    public static final Tile TILE_COTTON = getTile("cotton").setHardness(2F).setForceDrop().setMaxAmount(25);
    public static final Tile TILE_GLASS = getTile("glass").setHardness(3F).setMaxAmount(75);
    public static final Tile TILE_PLATFORM = getTile("platform").setHardness(0.5F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(150);

    /*
        ---ITEMS---
     */
    public static final Item ITEM_BRITTLE_PICKAXE = getItem("brittle_pickaxe");
    public static final Item ITEM_BRITTLE_AXE = getItem("brittle_axe");
    public static final Item ITEM_BRITTLE_SHOVEL = getItem("brittle_shovel");
    public static final Item ITEM_BRITTLE_SWORD = getItem("brittle_sword");
    public static final Item ITEM_WRENCH = getItem("wrench");
    public static final Item ITEM_SAW = getItem("saw");
    public static final Item ITEM_HAMMER = getItem("hammer");
    public static final Item ITEM_MALLET = getItem("mallet");
    public static final Item ITEM_CHISEL = getItem("chisel");
    public static final Item ITEM_FIREWORK = getItem("firework").setMaxAmount(25);
    public static final Item ITEM_STAT_NOTE = getItem("start_note").setMaxAmount(1);
    public static final Item ITEM_PLANT_FIBER = getItem("plant_fiber").setMaxAmount(150);
    public static final Item ITEM_YARN = getItem("yarn").setMaxAmount(30);
    public static final Item ITEM_TWIG = getItem("twig").setMaxAmount(150);
    public static final Item ITEM_STICK = getItem("stick").setMaxAmount(150);
    public static final Item ITEM_STONE_PICKAXE = getItem("stone_pickaxe");
    public static final Item ITEM_STONE_AXE = getItem("stone_axe");
    public static final Item ITEM_STONE_SHOVEL = getItem("stone_shovel");
    public static final Item ITEM_STONE_SWORD = getItem("stone_sword");
    public static final Item ITEM_COPPER_CANISTER = getItem("copper_canister").setMaxAmount(15);
    public static final Item ITEM_COPPER_INGOT = getItem("copper_ingot").setMaxAmount(50);
    public static final Item ITEM_COPPER_PICKAXE = getItem("copper_pickaxe");
    public static final Item ITEM_COPPER_AXE = getItem("copper_axe");
    public static final Item ITEM_COPPER_SHOVEL = getItem("copper_shovel");
    public static final Item ITEM_COPPER_SWORD = getItem("copper_sword");
    public static final Item ITEM_RECIPE_NOTE = getItem("recipe_note").setMaxAmount(1);
    public static final Item ITEM_BOWL = getItem("bowl").setMaxAmount(1);
    public static final Item ITEM_PESTLE = getItem("pestle").setMaxAmount(1);
    public static final Item ITEM_MUSH = getItem("mush").setMaxAmount(20);
    public static final Item ITEM_WOOD_BOOMERANG = getItem("wood_boomerang");
    public static final Item ITEM_SIMPLE_HOE = getItem("simple_hoe");

    /*
        ---RESOURCE REGISTRY ENTRIES---
     */
    public static final String RES_SOIL = res().addResources("soil", TILE_SOIL);
    public static final String RES_GRASS = res().addResources("grass", TILE_GRASS);
    public static final String RES_STONE = res().addResources("stone", TILE_STONE);
    public static final String RES_WOOD_RAW = res().addResources("wood_raw", TILE_LOG);
    public static final String RES_LEAVES = res().addResources("leaves", TILE_LEAVES);
    public static final String RES_PEBBLES = res().addResources("pebbles", TILE_PEBBLES);
    public static final String RES_SAND = res().addResources("sand", TILE_SAND);
    public static final String RES_COAL = res().addResources("coal", TILE_COAL);
    public static final String RES_SAPLING = res().addResources("sapling", TILE_SAPLING);
    public static final String RES_WOOD_PROCESSED = res().addResources("wood_processed", TILE_WOOD_BOARDS, 0, TILE_WOOD_BOARDS.metaProp.getVariants() - 1);
    public static final String RES_PLANT_FIBER = res().addResources("plant_fiber", ITEM_PLANT_FIBER);
    public static final String RES_STICK = res().addResources("stick", new ResInfo(ITEM_TWIG), new ResInfo(ITEM_STICK));
    public static final String RES_COPPER_RAW = res().addResources("copper_raw", TILE_COPPER);
    public static final String RES_COPPER_PROCESSED = res().addResources("copper_processed", ITEM_COPPER_INGOT);

    /*
        ---BIOMES---
     */
    public static final Biome BIOME_SKY = getBiome("sky");
    public static final Biome BIOME_GRASSLAND = getBiome("grassland");
    public static final Biome BIOME_DESERT = getBiome("desert");
    public static final Biome BIOME_UNDERGROUND = getBiome("underground");
    public static final Biome BIOME_COLD_GRASSLAND = getBiome("cold_grassland");

    /*
        ---BIOME LEVELS---
     */
    public static final BiomeLevel BIOME_LEVEL_SKY = getBiomeLevel("sky");
    public static final BiomeLevel BIOME_LEVEL_SURFACE = getBiomeLevel("surface");
    public static final BiomeLevel BIOME_LEVEL_UNDERGROUND = getBiomeLevel("underground");

    /*
        ---ENTITY EFFECTS---
     */
    public static final IEffect EFFECT_SPEED = getEffect("speed");
    public static final IEffect EFFECT_JUMP_HEIGHT = getEffect("jump_height");
    public static final IEffect EFFECT_RANGE = getEffect("range");
    public static final IEffect EFFECT_PICKUP_RANGE = getEffect("pickup_range");

    @ApiInternal
    private static Biome getBiome(String name) {
        return get(name, Registries.BIOME_REGISTRY);
    }

    @ApiInternal
    private static Item getItem(String name) {
        return get(name, Registries.ITEM_REGISTRY);
    }

    @ApiInternal
    private static Tile getTile(String name) {
        return get(name, Registries.TILE_REGISTRY);
    }

    @ApiInternal
    private static IEffect getEffect(String name) {
        return get(name, Registries.EFFECT_REGISTRY);
    }

    @ApiInternal
    private static BiomeLevel getBiomeLevel(String name) {
        return get(name, Registries.BIOME_LEVEL_REGISTRY);
    }

    @ApiInternal
    private static <T> T get(String name, NameRegistry<T> registry) {
        ResourceName res = ResourceName.intern(name);
        return Preconditions.checkNotNull(registry.get(res), "Object with name " + res + " was not found in registry " + registry + "! This is probably due to GameContent being accessed before the game has initialized!");
    }

    @ApiInternal
    private static IResourceRegistry res() {
        return RockBottomAPI.getResourceRegistry();
    }
}
