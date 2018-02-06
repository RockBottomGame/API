/*
 * This file ("RockBottomAPI.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.assets.IAssetLoader;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.IRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.part.DataPart;
import de.ellpeck.rockbottom.api.data.settings.Keybind;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.effect.IEffect;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.knowledge.Information;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.gui.IMainMenuTheme;
import de.ellpeck.rockbottom.api.gui.ISpecialCursor;
import de.ellpeck.rockbottom.api.internal.IInternalHooks;
import de.ellpeck.rockbottom.api.internal.Internals;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.mod.IModLoader;
import de.ellpeck.rockbottom.api.net.INetHandler;
import de.ellpeck.rockbottom.api.net.chat.Command;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.IRegistry;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.util.reg.IndexRegistry;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * The main API class. Use this to access all of the {@link IRegistry} objects
 * and other important data.
 */
public final class RockBottomAPI{

    /**
     * The current API version equal to the version in the build.gradle file.
     */
    public static final String VERSION = "0.2.6";

    @ApiInternal
    private static final List<IRegistry> ALL_REGISTRIES = new ArrayList<>();
    /**
     * The registry for all {@link Tile} objects. Use {@link Tile#register()} to
     * register it.
     */
    @ApiInternal
    public static final NameRegistry<Tile> TILE_REGISTRY = new NameRegistry<>("tile_registry").register();
    /**
     * The registry for all {@link Item} objects. Use {@link Item#register()} to
     * register it.
     */
    @ApiInternal
    public static final NameRegistry<Item> ITEM_REGISTRY = new NameRegistry<>("item_registry").register();
    /**
     * The registry for all {@link Entity} types. The {@link IResourceName}
     * refers to the way the entity will be saved to disk when it is present in
     * the world. Note that, to be able to be loaded back in when the game is
     * started, an {@link Entity} needs to have a constructor containing only an
     * {@link IWorld}.
     */
    public static final NameRegistry<Class<? extends Entity>> ENTITY_REGISTRY = new NameRegistry<>("entity_registry").register();
    /**
     * The registry for all {@link DataPart} types. Note that this registry
     * should not be registered into without caution, as the index should not be
     * automatically determined due to it having to persist on disk when a
     * {@link DataSet} is stored. Either use an index that most likely no other
     * mods will use, create a configuration file to be able to change it or
     * only use existing {@link DataPart}s.
     */
    @ApiInternal
    public static final IndexRegistry<Class<? extends DataPart>> PART_REGISTRY = new IndexRegistry<>("part_registry", Byte.MAX_VALUE).register();
    /**
     * The registry for all {@link IPacket} types. To register into this
     * registry, you can use {@link IndexRegistry#getNextFreeId()} to determine
     * an id for the packet.
     */
    public static final IndexRegistry<Class<? extends IPacket>> PACKET_REGISTRY = new IndexRegistry<>("packet_registry", Byte.MAX_VALUE).register();
    /**
     * The registry for all {@link Command} objects. Use {@link
     * Command#register()} to register it.
     */
    public static final NameRegistry<Command> COMMAND_REGISTRY = new NameRegistry<>("command_registry").register();
    /**
     * The registry for all {@link IRecipe} objects that are used to construct
     * items through the input-output system of the construction in the player's
     * inventory. Do not manually register recipes into this registry as this is
     * automatically done using {@link BasicRecipe#register(NameRegistry)}. This
     * is a convenience registry to help with packets and networking.
     */
    @ApiInternal
    public static final NameRegistry<IRecipe> ALL_CONSTRUCTION_RECIPES = new NameRegistry<>("all_recipe_registry").register();
    /**
     * The registry for all {@link BasicRecipe} objects that are displayed on
     * the left side of the player's inventory. Use {@link
     * BasicRecipe#registerManual()} to register recipes into this registry.
     */
    @ApiInternal
    public static final NameRegistry<BasicRecipe> MANUAL_CONSTRUCTION_RECIPES = new NameRegistry<>("manual_recipe_registry").register();
    /**
     * The registry for all {@link IWorldGenerator} types. The {@link
     * IResourceName} is used to save a generator to disk if it is a retroactive
     * generator ({@link IWorldGenerator#generatesRetroactively()}) to mark that
     * it has already generated in a certain chunk. However, a registry name
     * needs to be supplied regardless if the generator is retroactive. Note
     * that, to instantiate a world generator, it needs to contain a default
     * constructor.
     */
    public static final NameRegistry<Class<? extends IWorldGenerator>> WORLD_GENERATORS = new NameRegistry<>("world_generator_registry").register();
    /**
     * The registry for all {@link Biome} objects. Use {@link Biome#register()}
     * to register biomes into this registry.
     */
    @ApiInternal
    public static final NameRegistry<Biome> BIOME_REGISTRY = new NameRegistry<>("biome_registry").register();
    /**
     * The registry for all {@link TileState} objects. This registry
     * automatically has objects registered into it by registering the
     * associated {@link Tile} objects using {@link Tile#register()}. Do not
     * manually register anything in it.
     */
    @ApiInternal
    public static final NameRegistry<TileState> TILE_STATE_REGISTRY = new NameRegistry<>("tile_state_registry").register();
    /**
     * The registry for all {@link TileLayer} objects. Use {@link
     * TileLayer#register()} to register a layer into this registry.
     */
    @ApiInternal
    public static final NameRegistry<TileLayer> TILE_LAYER_REGISTRY = new NameRegistry<>("tile_layer_registry").register();
    /**
     * The registry for all {@link Keybind} objects. Use {@link
     * Keybind#register()} to register a keybind into this registry. This needs
     * to be done before {@link IMod#preInit(IGameInstance, IApiHandler,
     * IEventHandler)} as otherwise, the keybind's current value will not be
     * loaded from the {@link Settings} file automatically.
     */
    @ApiInternal
    public static final NameRegistry<Keybind> KEYBIND_REGISTRY = new NameRegistry<>("keybind_registry").register();
    /**
     * The registry for all {@link ChatComponent} objects. Use {@link
     * IndexRegistry#getNextFreeId()} to determine the component's id. When
     * registering custom chat components, note that, to send their information
     * between the server and the client, they need to contain a default
     * constructor.
     */
    public static final IndexRegistry<Class<? extends ChatComponent>> CHAT_COMPONENT_REGISTRY = new IndexRegistry<>("chat_component_registry", Byte.MAX_VALUE).register();
    /**
     * The list of all {@link IMainMenuTheme} objects. Adding themes into this
     * list will give them a chance to be randomly chosen when the title screen
     * of the game is loaded up. Do not clear this list as it will crash the
     * game on startup.
     */
    public static final IndexRegistry<IMainMenuTheme> MAIN_MENU_THEMES = new IndexRegistry<>("main_menu_theme_registry", Integer.MAX_VALUE).register();
    /**
     * The registry for all {@link Information} types. Note that, to save and
     * load information from and to disk, they need to contain a constructor
     * that takes an {@link IResourceName} similar to the one that {@link
     * Information} provides.
     */
    public static final NameRegistry<Class<? extends Information>> INFORMATION_REGISTRY = new NameRegistry<>("information_registry").register();
    /**
     * The registry for all {@link IAssetLoader} objects. To register one into
     * this registry, use {@link IAssetLoader#register()}.
     */
    @ApiInternal
    public static final NameRegistry<IAssetLoader> ASSET_LOADER_REGISTRY = new NameRegistry<>("asset_loader_registry").register();
    /**
     * The list of all {@link ISpecialCursor} instance. Adding cursors into this
     * list will allow them to be displayed in place of the regular cursor when
     * their condition is met.
     */
    public static final IndexRegistry<ISpecialCursor> SPECIAL_CURSORS = new IndexRegistry<>("special_cursor_registry", Integer.MAX_VALUE).register();
    public static final NameRegistry<IEffect> EFFECT_REGISTRY = new NameRegistry<>("effect_registry").register();

    /**
     * A set of internal references to API classes that are initialized by the
     * game.
     */
    @ApiInternal
    private static Internals internals;

    /**
     * Returns the {@link IApiHandler} object initialized by the game on
     * startup. The API handler contains a set of useful helper methods that can
     * be used to do various things.
     *
     * @return The API handler
     */
    public static IApiHandler getApiHandler(){
        return internals.getApi();
    }

    /**
     * Returns the {@link INetHandler} object initialized by the game on
     * startup. The net handler contains a set of methods to deal with
     * multiplayer and networking.
     *
     * @return The net handler
     */
    public static INetHandler getNet(){
        return internals.getNet();
    }

    /**
     * Returns the {@link IEventHandler} object initialized by the game on
     * startup. The event handler contains a set of methods to deal with
     * subscribing, firing and listening to events.
     *
     * @return The event handler
     */
    public static IEventHandler getEventHandler(){
        return internals.getEvent();
    }

    /**
     * Returns the {@link IGameInstance} object initialized as the game on
     * startup. This is also an instance of the {@link IMod} that the game
     * represents. The game instance contains references to the player, the
     * asset manager, the graphics context and various other things.
     *
     * @return The game instance
     */
    public static IGameInstance getGame(){
        return internals.getGame();
    }

    /**
     * Returns the {@link IModLoader} object initialized by the game on startup.
     * The mod loader contains a set of methods to deal with mod interaction and
     * loading and finding mods.
     *
     * @return The mod loader
     */
    public static IModLoader getModLoader(){
        return internals.getMod();
    }

    /**
     * Returns the {@link IResourceRegistry} object initialized by the game on
     * startup. The resource registry can be used to register resources to be
     * used in construction and various other recipes. The idea of it is that it
     * unifies items to be represented by just a name, so that if, for example,
     * multiple mods add an item representing copper ore, then it can be
     * registered into the resource registry by both mods as "oreCopper" so that
     * recipes can use copper universally.
     *
     * @return The resource registry
     */
    public static IResourceRegistry getResourceRegistry(){
        return internals.getResource();
    }

    /**
     * Returns the {@link IInternalHooks} object initialized by the game on
     * startup. This is an api internal interface used to access methods outside
     * of the API. None of the api internal hooks should be used by mods.
     *
     * @return The internal hooks
     */
    public static IInternalHooks getInternalHooks(){
        return internals.getHooks();
    }

    /**
     * Convenience call to {@link IModLoader#createResourceName(IMod, String)}
     * that creates a resource with {@link IGameInstance} as the holding mod.
     *
     * @param resource The name of the resource
     *
     * @return The resource name bound to the game instance
     */
    @ApiInternal
    public static IResourceName createInternalRes(String resource){
        return createRes(getGame(), resource);
    }

    /**
     * Convenience call to {@link IModLoader#createResourceName(IMod, String)}
     * that creates an {@link IResourceName} bound to the specified {@link
     * IMod}.
     *
     * @param mod      The mod
     * @param resource The resource
     *
     * @return The resource name bound to the mod
     */
    public static IResourceName createRes(IMod mod, String resource){
        return getModLoader().createResourceName(mod, resource);
    }

    /**
     * Convenience call to {@link IModLoader#createResourceName(String)} that
     * creates an {@link IResourceName} from a combined string.
     *
     * @param combined The combined string representing a resource bound to a
     *                 mod
     *
     * @return The resource name bound to the mod
     */
    public static IResourceName createRes(String combined){
        return getModLoader().createResourceName(combined);
    }

    /**
     * Convenience call to {@link IApiHandler#createLogger(String)} that creates
     * a {@link Logger} with a specified name.
     *
     * @param name The logger's display name
     *
     * @return The logger
     */
    public static Logger createLogger(String name){
        return getApiHandler().createLogger(name);
    }

    /**
     * Convenience call to {@link IApiHandler#logger()} that returns the game's
     * internal logger
     *
     * @return The game's logger
     */
    @ApiInternal
    public static Logger logger(){
        return getApiHandler().logger();
    }

    public static void registerRegistry(IRegistry registry){
        if(!ALL_REGISTRIES.contains(registry)){
            ALL_REGISTRIES.add(registry);
        }
        else{
            throw new RuntimeException("Registry "+registry+" was already registered!");
        }
    }

    public static List<IRegistry> getAllRegistries(){
        return Collections.unmodifiableList(ALL_REGISTRIES);
    }

    @ApiInternal
    public static void setInternals(Internals intern){
        if(internals == null){
            internals = intern;
        }
        else{
            throw new RuntimeException("Mod tried to modify internal handlers - This is not allowed!");
        }
    }
}

