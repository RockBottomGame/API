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
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api;

import de.ellpeck.rockbottom.api.effect.IEffect;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.TileMeta;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;

/**
 * This class houses all of the game's default content like all the {@link
 * Tile}, {@link Item}, {@link Biome} objects as static variables that can be
 * accessed from anywhere.
 */
public final class GameContent{

    public static final Tile TILE_AIR = getTile("air");
    public static final Tile TILE_SOIL = getTile("soil").addEffectiveTool(ToolType.SHOVEL, 0).setForceDrop();
    public static final Tile TILE_GRASS = getTile("grass").addEffectiveTool(ToolType.SHOVEL, 0).setForceDrop();
    public static final Tile TILE_STONE = getTile("stone").setHardness(5F).addEffectiveTool(ToolType.PICKAXE, 0);
    public static final TileMeta TILE_GRASS_TUFT = (TileMeta)getTile("grass_tuft").setHardness(0F);
    public static final Tile TILE_LOG = getTile("log").setHardness(3F).addEffectiveTool(ToolType.AXE, 0).setForceDrop();
    public static final Tile TILE_LEAVES = getTile("leaves").setHardness(0.5F).setForceDrop();
    public static final TileMeta TILE_FLOWER = (TileMeta)getTile("flower").setHardness(0F).setForceDrop();
    public static final Tile TILE_PEBBLES = getTile("pebbles").setHardness(0F).setForceDrop();
    public static final Tile TILE_SAND = getTile("sand").setHardness(0.75F).addEffectiveTool(ToolType.SHOVEL, 0).setForceDrop();
    public static final Tile TILE_SANDSTONE = getTile("sandstone").setHardness(4F).addEffectiveTool(ToolType.PICKAXE, 0);
    public static final Tile TILE_COAL = getTile("coal").setHardness(6F).addEffectiveTool(ToolType.PICKAXE, 0);
    public static final Tile TILE_TORCH = getTile("torch").setHardness(0F).setForceDrop();
    public static final Tile TILE_LADDER = getTile("ladder").setHardness(1.5F).addEffectiveTool(ToolType.AXE, 0).setForceDrop();
    public static final Tile TILE_CHEST = getTile("chest").setHardness(4F).addEffectiveTool(ToolType.AXE, 0).setForceDrop();
    public static final Tile TILE_SIGN = getTile("sign").addEffectiveTool(ToolType.AXE, 0).setForceDrop();
    public static final Tile TILE_SAPLING = getTile("sapling").setHardness(0F).setForceDrop();
    public static final Tile TILE_WATER = getTile("water");
    public static final TileMeta WOOD_BOARDS = (TileMeta)getTile("wood_boards").setHardness(2F).addEffectiveTool(ToolType.AXE, 0).setForceDrop();
    public static final Tile TILE_WOOD_DOOR = getTile("wood_door").setHardness(4F).addEffectiveTool(ToolType.AXE, 0).setForceDrop();
    public static final Tile TILE_WOOD_DOOR_OLD = getTile("wood_door_old").setHardness(3.5F).addEffectiveTool(ToolType.AXE, 0).setForceDrop();
    public static final Tile TILE_REMAINS_GOO = getTile("remains_goo");
    public static final Tile TILE_GRASS_TORCH = getTile("torch_grass").setHardness(0F).setForceDrop();

    public static final Item ITEM_BRITTLE_PICKAXE = getItem("brittle_pickaxe");
    public static final Item ITEM_BRITTLE_AXE = getItem("brittle_axe");
    public static final Item ITEM_BRITTLE_SHOVEL = getItem("brittle_shovel");
    public static final Item ITEM_FIREWORK = getItem("firework");
    public static final Item ITEM_STAT_NOTE = getItem("start_note");

    public static final Biome BIOME_SKY = getBiome("sky");
    public static final Biome BIOME_GRASSLAND = getBiome("grassland");
    public static final Biome BIOME_DESERT = getBiome("desert");
    public static final Biome BIOME_UNDERGROUND = getBiome("underground");

    public static final IEffect EFFECT_SPEED = getEffect("speed");
    public static final IEffect EFFECT_JUMP_HEIGHT = getEffect("jump_height");

    @ApiInternal
    private static Biome getBiome(String name){
        return get(name, RockBottomAPI.BIOME_REGISTRY);
    }

    @ApiInternal
    private static Item getItem(String name){
        return get(name, RockBottomAPI.ITEM_REGISTRY);
    }

    @ApiInternal
    private static Tile getTile(String name){
        return get(name, RockBottomAPI.TILE_REGISTRY);
    }

    @ApiInternal
    private static IEffect getEffect(String name){
        return get(name, RockBottomAPI.EFFECT_REGISTRY);
    }

    @ApiInternal
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
