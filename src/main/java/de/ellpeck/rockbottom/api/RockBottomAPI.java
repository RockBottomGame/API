/*
 * This file ("RockBottomAPI.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.SeparatorRecipe;
import de.ellpeck.rockbottom.api.construction.SmelterRecipe;
import de.ellpeck.rockbottom.api.construction.StamperRecipe;
import de.ellpeck.rockbottom.api.construction.resource.ResourceInfo;
import de.ellpeck.rockbottom.api.data.set.part.DataPart;
import de.ellpeck.rockbottom.api.data.settings.Keybind;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.mod.IModLoader;
import de.ellpeck.rockbottom.api.net.INetHandler;
import de.ellpeck.rockbottom.api.net.chat.Command;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.util.reg.IndexRegistry;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;

import java.util.*;

/**
 * The main API class
 * <br> Use this to access important game values
 */
public final class RockBottomAPI{

    /**
     * The current API version
     */
    public static final String VERSION = "0.0.17";

    /**
     * The registry for {@link Tile}
     * <br> Use {@link Tile#register()} to register your tiles
     */
    public static final NameRegistry<Tile> TILE_REGISTRY = new NameRegistry<>("tile_registry");
    /**
     * The registry for {@link Item}
     * <br> Use {@link Item#register()} to register your items
     */
    public static final NameRegistry<Item> ITEM_REGISTRY = new NameRegistry<>("item_registry");
    /**
     * The registry for {@link Entity}
     * <br> Use this to register custom entity types
     */
    public static final NameRegistry<Class<? extends Entity>> ENTITY_REGISTRY = new NameRegistry<>("entity_registry");
    /**
     * The registry for {@link de.ellpeck.rockbottom.api.data.set.DataSet} parts
     * <br> DO NOT use {@link IndexRegistry#getNextFreeId} to register a custom part
     * and try not to register new {@link DataPart} types in general
     */
    public static final IndexRegistry<Class<? extends DataPart>> PART_REGISTRY = new IndexRegistry<>("part_registry", Byte.MAX_VALUE);
    /**
     * The registry for {@link IPacket}
     * <br> Use {@link IndexRegistry#getNextFreeId()} to register a custom packet
     */
    public static final IndexRegistry<Class<? extends IPacket>> PACKET_REGISTRY = new IndexRegistry<>("packet_registry", Byte.MAX_VALUE);
    /**
     * The registry for {@link Command}
     * <br> Use this to register custom commands
     */
    public static final Map<String, Command> COMMAND_REGISTRY = new HashMap<>();
    /**
     * The registry for {@link BasicRecipe}
     * <br> Use this to register construction recipes that can be used in the inventory {@link de.ellpeck.rockbottom.api.gui.Gui}
     */
    public static final List<BasicRecipe> MANUAL_CONSTRUCTION_RECIPES = new ArrayList<>();
    public static final List<BasicRecipe> CONSTRUCTION_TABLE_RECIPES = new ArrayList<>();
    public static final List<StamperRecipe> STAMPER_RECIPES = new ArrayList<>();
    /**
     * The registry for {@link ResourceInfo}s that can be used in machines as a fuel.
     * The {@link Integer} specified is the amount of time the fuel will burn for
     * <br> Use this to register custom fuels
     */
    public static final Map<ResourceInfo, Integer> FUEL_REGISTRY = new HashMap<>();
    /**
     * The registry for {@link SmelterRecipe}
     * <br> Use this to register recipes for the smelter
     */
    public static final List<SmelterRecipe> SMELTER_RECIPES = new ArrayList<>();
    /**
     * The registry for {@link SeparatorRecipe}
     * <br> Use this to register recipes for the separator
     */
    public static final List<SeparatorRecipe> SEPARATOR_RECIPES = new ArrayList<>();
    /**
     * The registry for {@link IWorldGenerator}
     * <br> Use this to register custom world generators
     * <br> Every world generator class needs to have an empty constructor to be able
     * to be initialized for the appropriate world
     */
    public static final List<Class<? extends IWorldGenerator>> WORLD_GENERATORS = new ArrayList<>();
    /**
     * The registry for {@link Biome}
     * <br> Use this to register custom biomes
     */
    public static final NameRegistry<Biome> BIOME_REGISTRY = new NameRegistry<>("biome_registry");
    /**
     * The registry for {@link TileState}
     * <br> DO NOT manually register states into this registry, it is populated automatically
     */
    public static final NameRegistry<TileState> TILE_STATE_REGISTRY = new NameRegistry<>("tile_state_registry");
    /**
     * The registry for {@link Keybind}
     * <br> Use this to register custom keybinds
     */
    public static final NameRegistry<Keybind> KEYBIND_REGISTRY = new NameRegistry<>("keybind_registry", new TreeMap<>());

    private static IApiHandler apiHandler;
    private static INetHandler netHandler;
    private static IEventHandler eventHandler;
    private static IGameInstance gameInstance;
    private static IModLoader modLoader;

    /**
     * See {@link IApiHandler} for more information
     *
     * @return The {@link IApiHandler}
     */
    public static IApiHandler getApiHandler(){
        return apiHandler;
    }

    /**
     * See {@link INetHandler} for more information
     *
     * @return The {@link INetHandler}
     */
    public static INetHandler getNet(){
        return netHandler;
    }

    /**
     * See {@link IEventHandler} for more information
     *
     * @return The {@link IEventHandler}
     */
    public static IEventHandler getEventHandler(){
        return eventHandler;
    }

    /**
     * See {@link IGameInstance} for more information
     *
     * @return The {@link IGameInstance}
     */
    public static IGameInstance getGame(){
        return gameInstance;
    }

    /**
     * See {@link IModLoader} for more information
     *
     * @return The {@link IModLoader}
     */
    public static IModLoader getModLoader(){
        return modLoader;
    }

    /**
     * Creates an internal {@link IResourceName} to be used for vanilla game related resources
     *
     * @param resource The name of the resource to create
     * @return The {@link IResourceName}
     */
    public static IResourceName createInternalRes(String resource){
        return createRes(gameInstance, resource);
    }

    /**
     * Creates an {@link IResourceName} to be used by the specified {@link IMod}
     *
     * @param mod      The mod to use this resource name
     * @param resource The name of the resource to create
     * @return The {@link IResourceName}
     */
    public static IResourceName createRes(IMod mod, String resource){
        return modLoader.createResourceName(mod, resource);
    }

    /**
     * Creates an {@link IResourceName} out of a combined string of the mod id
     * and the resource itself
     *
     * @param combined The combined name
     * @return The parsed {@link IResourceName}
     * @throws IllegalArgumentException if the specified string cannot be parsed as an {@link IResourceName}
     */
    public static IResourceName createRes(String combined){
        return modLoader.createResourceName(combined);
    }

    /**
     * For game internal use only
     */
    public static void setApiHandler(IApiHandler api){
        if(apiHandler == null){
            apiHandler = api;
        }
    }

    /**
     * For game internal use only
     */
    public static void setNetHandler(INetHandler net){
        if(netHandler == null){
            netHandler = net;
        }
    }

    /**
     * For game internal use only
     */
    public static void setEventHandler(IEventHandler event){
        if(eventHandler == null){
            eventHandler = event;
        }
    }

    /**
     * For game internal use only
     */
    public static void setGameInstance(IGameInstance game){
        if(gameInstance == null){
            gameInstance = game;
        }
    }

    /**
     * For game internal use only
     */
    public static void setModLoader(IModLoader mod){
        if(modLoader == null){
            modLoader = mod;
        }
    }

    public static int getFuelValue(ItemInstance instance){
        for(Map.Entry<ResourceInfo, Integer> entry : FUEL_REGISTRY.entrySet()){
            if(entry.getKey().containsItem(instance)){
                return entry.getValue();
            }
        }
        return 0;
    }

    public static SmelterRecipe getSmelterRecipe(ItemInstance input){
        for(SmelterRecipe recipe : SMELTER_RECIPES){
            if(recipe.getInput().containsItem(input)){
                return recipe;
            }
        }
        return null;
    }

    public static SeparatorRecipe getSeparatorRecipe(ItemInstance input){
        for(SeparatorRecipe recipe : SEPARATOR_RECIPES){
            if(recipe.getInput().containsItem(input)){
                return recipe;
            }
        }
        return null;
    }

    public static StamperRecipe getStamperRecipe(ItemInstance input){
        for(StamperRecipe recipe : STAMPER_RECIPES){
            if(recipe.getInput().containsItem(input)){
                return recipe;
            }
        }
        return null;
    }
}

