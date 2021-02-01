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
import de.ellpeck.rockbottom.api.tile.LiquidTile;
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
    public static final class Tiles {
        public static final Tile AIR = getTile("air");
        public static final Tile SOIL = getTile("soil").addEffectiveTool(ToolProperty.SHOVEL, 0).setForceDrop().setMaxAmount(100);
        public static final Tile GRASS = getTile("grass").addEffectiveTool(ToolProperty.SHOVEL, 0).setForceDrop().setMaxAmount(100);
        public static final Tile STONE = getTile("stone").setHardness(5F).addEffectiveTool(ToolProperty.PICKAXE, 0).setMaxAmount(50);
        public static final TileMeta GRASS_TUFT = (TileMeta) getTile("grass_tuft").setHardness(0F).setForceDrop().setMaxAmount(150);
        public static final Tile LOG = getTile("log").setHardness(3F).addEffectiveTool(ToolProperty.AXE, 0).setMaxAmount(75).setForceDrop();
        public static final Tile LEAVES = getTile("leaves").setHardness(0.5F).setForceDrop().setMaxAmount(150);
        public static final TileMeta FLOWER = (TileMeta) getTile("flower").setHardness(0F).setForceDrop().setMaxAmount(150);
        public static final Tile PEBBLES = getTile("pebbles").setHardness(0F).setForceDrop().setMaxAmount(200);
        public static final Tile SAND = getTile("sand").setHardness(0.75F).addEffectiveTool(ToolProperty.SHOVEL, 0).setForceDrop().setMaxAmount(100);
        public static final Tile SANDSTONE = getTile("sandstone").setHardness(4F).addEffectiveTool(ToolProperty.PICKAXE, 0).setMaxAmount(75);
        public static final Tile COAL = getTile("coal").setHardness(6F).addEffectiveTool(ToolProperty.PICKAXE, 1).setMaxAmount(45);
        public static final Tile TORCH = getTile("torch").setHardness(0F).setForceDrop().setMaxAmount(50);
        public static final Tile LADDER = getTile("ladder").setHardness(1.5F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(100);
        public static final Tile CHEST = getTile("chest").setHardness(4F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(5);
        public static final Tile SIGN = getTile("sign").addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(100);
        public static final Tile SAPLING = getTile("sapling").setHardness(0F).setForceDrop().setMaxAmount(150);
        public static final LiquidTile WATER = (LiquidTile) getTile("water");
        public static final TileMeta WOOD_BOARDS = (TileMeta) getTile("wood_boards").setHardness(2F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(75);
        public static final Tile WOOD_DOOR = getTile("wood_door").setHardness(4F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(5);
        public static final Tile OLD_WOOD_DOOR = getTile("old_wood_door").setHardness(3.5F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(5);
        public static final Tile GOO_REMAINS = getTile("goo_remains");
        public static final Tile GRASS_TORCH = getTile("grass_torch").setHardness(0F).setForceDrop().setMaxAmount(50);
        public static final Tile COPPER = getTile("copper").setHardness(10F).addEffectiveTool(ToolProperty.PICKAXE, 2).setMaxAmount(35);
        public static final Tile TIN = getTile("tin").setHardness(8F).addEffectiveTool(ToolProperty.PICKAXE, 2).setMaxAmount(35);
        public static final Tile SPINNING_WHEEL = getTile("spinning_wheel").setHardness(7F).addEffectiveTool(ToolProperty.PICKAXE, 1).setMaxAmount(1);
        public static final Tile SIMPLE_FURNACE = getTile("simple_furnace").setHardness(15F).addEffectiveTool(ToolProperty.PICKAXE, 2).setMaxAmount(1);
        public static final Tile COMBINER = getTile("combiner").setHardness(15F).addEffectiveTool(ToolProperty.PICKAXE, 2).setMaxAmount(1);
        public static final Tile CONSTRUCTION_TABLE = getTile("construction_table").setHardness(15F).addEffectiveTool(ToolProperty.PICKAXE, 2).setMaxAmount(1);
        public static final Tile SMITHING_TABLE = getTile("smithing_table").setHardness(15F).addEffectiveTool(ToolProperty.PICKAXE, 2).setMaxAmount(1);
        public static final Tile SNOW = getTile("snow").setHardness(0.75F).addEffectiveTool(ToolProperty.SHOVEL, 0).setMaxAmount(200);
        public static final TileMeta CAVE_MUSHROOM = (TileMeta) getTile("cave_mushroom").setHardness(0.25F).setMaxAmount(50).setForceDrop();
        public static final Tile STARDROP = getTile("stardrop").setHardness(0.75F).setMaxAmount(10).setForceDrop();
        public static final Tile IRON_LAMP = getTile("iron_lamp").setHardness(10F).addEffectiveTool(ToolProperty.PICKAXE, 8).setMaxAmount(20).setForceDrop();
        public static final Tile MORTAR = getTile("mortar").setHardness(3F).addEffectiveTool(ToolProperty.PICKAXE, 0).setMaxAmount(5);
        public static final Tile TILLED_SOIL = getTile("tilled_soil").setHardness(0.8F).addEffectiveTool(ToolProperty.SHOVEL, 0).setForceDrop();
        public static final Tile CORN = getTile("corn").setHardness(2F).setForceDrop().setMaxAmount(25);
        public static final Tile COTTON = getTile("cotton").setHardness(2F).setForceDrop().setMaxAmount(25);
        public static final Tile GLASS = getTile("glass").setHardness(3F).setMaxAmount(75);
        public static final Tile PLATFORM = getTile("platform").setHardness(0.5F).addEffectiveTool(ToolProperty.AXE, 0).setForceDrop().setMaxAmount(150);
        public static final Tile PLANT_ROPE = getTile("plant_rope").setHardness(0).setMaxAmount(150);
    }

    /*
        ---ITEMS---
     */
    public static class Items {
        public static final Item BRITTLE_PICKAXE = getItem("brittle_pickaxe");
        public static final Item BRITTLE_AXE = getItem("brittle_axe");
        public static final Item BRITTLE_SHOVEL = getItem("brittle_shovel");
        public static final Item BRITTLE_SWORD = getItem("brittle_sword");
        public static final Item WRENCH = getItem("wrench");
        public static final Item SAW = getItem("saw");
        public static final Item HAMMER = getItem("hammer");
        public static final Item MALLET = getItem("mallet");
        public static final Item CHISEL = getItem("chisel");
        public static final Item FIREWORK = getItem("firework").setMaxAmount(25);
        public static final Item START_NOTE = getItem("start_note").setMaxAmount(1);
        public static final Item PLANT_FIBER = getItem("plant_fiber").setMaxAmount(150);
        public static final Item YARN = getItem("yarn").setMaxAmount(30);
        public static final Item TWIG = getItem("twig").setMaxAmount(150);
        public static final Item STICK = getItem("stick").setMaxAmount(150);
        public static final Item STONE_PICKAXE = getItem("stone_pickaxe");
        public static final Item STONE_AXE = getItem("stone_axe");
        public static final Item STONE_SHOVEL = getItem("stone_shovel");
        public static final Item STONE_SWORD = getItem("stone_sword");
        public static final Item COPPER_CANISTER = getItem("copper_canister").setMaxAmount(15);
        public static final Item TIN_INGOT = getItem("tin_ingot").setMaxAmount(50);
        public static final Item COPPER_INGOT = getItem("copper_ingot").setMaxAmount(50);
        public static final Item COPPER_PICKAXE = getItem("copper_pickaxe");
        public static final Item COPPER_AXE = getItem("copper_axe");
        public static final Item COPPER_SHOVEL = getItem("copper_shovel");
        public static final Item COPPER_SWORD = getItem("copper_sword");
        public static final Item BRONZE_INGOT = getItem("bronze_ingot").setMaxAmount(50);
        public static final Item BRONZE_PICKAXE = getItem("bronze_pickaxe");
        public static final Item BRONZE_AXE = getItem("bronze_axe");
        public static final Item BRONZE_SHOVEL = getItem("bronze_shovel");
        public static final Item BRONZE_SWORD = getItem("bronze_sword");
        public static final Item BRONZE_CANISTER = getItem("bronze_canister");
        public static final Item RECIPE_NOTE = getItem("recipe_note").setMaxAmount(1);
        public static final Item BOWL = getItem("bowl").setMaxAmount(1);
        public static final Item PESTLE = getItem("pestle").setMaxAmount(1);
        public static final Item MUSH = getItem("mush").setMaxAmount(20);
        public static final Item WOOD_BOOMERANG = getItem("wood_boomerang");
        public static final Item SIMPLE_HOE = getItem("simple_hoe");
    }

    /*
        ---RESOURCE REGISTRY ENTRIES---
     */
    public static final class Resources {
        public static final String SOIL = res().addResources("soil", Tiles.SOIL);
        public static final String GRASS = res().addResources("grass", Tiles.GRASS);
        public static final String STONE = res().addResources("stone", Tiles.STONE);
        public static final String WOOD_RAW = res().addResources("raw_wood", Tiles.LOG);
        public static final String LEAVES = res().addResources("leaves", Tiles.LEAVES);
        public static final String PEBBLES = res().addResources("pebbles", Tiles.PEBBLES);
        public static final String SAND = res().addResources("sand", Tiles.SAND);
        public static final String COAL = res().addResources("coal", Tiles.COAL);
        public static final String SAPLING = res().addResources("sapling", Tiles.SAPLING);
        public static final String WOOD_PROCESSED = res().addResources("processed_wood", Tiles.WOOD_BOARDS, 0, Tiles.WOOD_BOARDS.metaProp.getVariants() - 1);
        public static final String GLASS = res().addResources("glass", Tiles.GLASS);
        public static final String PLANT_FIBER = res().addResources("plant_fiber", Items.PLANT_FIBER);
        public static final String STICK = res().addResources("stick", new ResInfo(Items.TWIG), new ResInfo(Items.STICK));
        public static final String COPPER_RAW = res().addResources("raw_copper", Tiles.COPPER);
        public static final String COPPER_PROCESSED = res().addResources("processed_copper", Items.COPPER_INGOT);
        public static final String TIN_RAW = res().addResources("raw_tin", Tiles.TIN);
        public static final String TIN_PROCESSED = res().addResources("processed_tin", Items.TIN_INGOT);
        public static final String BRONZE_PROCESSED = res().addResources("processed_bronze", Items.BRONZE_INGOT);
    }

    /*
        ---BIOMES---
     */
    public static final class Biomes {
        public static final Biome SKY = getBiome("sky");
        public static final Biome GRASSLAND = getBiome("grassland");
        public static final Biome DESERT = getBiome("desert");
        public static final Biome UNDERGROUND = getBiome("underground");
        public static final Biome COLD_GRASSLAND = getBiome("cold_grassland");
    }

    /*
        ---BIOME LEVELS---
     */
    public static final class BiomeLevels {
        public static final BiomeLevel SKY = getBiomeLevel("sky");
        public static final BiomeLevel SURFACE = getBiomeLevel("surface");
        public static final BiomeLevel UNDERGROUND = getBiomeLevel("underground");
    }

    /*
        ---ENTITY EFFECTS---
     */
    public static final class Effects {
        public static final IEffect SPEED = getEffect("speed");
        public static final IEffect JUMP_HEIGHT = getEffect("jump_height");
        public static final IEffect RANGE = getEffect("range");
        public static final IEffect PICKUP_RANGE = getEffect("pickup_range");
    }

    /*
        ---EMOTIONS---
    */
    public static final class Emotions {
        public static final ResourceName HAPPY = getEmotion("happy");
    }

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
    private static ResourceName getEmotion(String name) {
        return ResourceName.intern("emotions.").addSuffix(name);
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
