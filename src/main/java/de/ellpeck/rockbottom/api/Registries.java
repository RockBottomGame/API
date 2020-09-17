/*
 * This file ("Registries.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.assets.IAssetLoader;
import de.ellpeck.rockbottom.api.construction.compendium.CompendiumCategory;
import de.ellpeck.rockbottom.api.construction.compendium.ICompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.compendium.ICriteria;
import de.ellpeck.rockbottom.api.construction.compendium.ConstructionRecipe;
import de.ellpeck.rockbottom.api.construction.compendium.MortarRecipe;
import de.ellpeck.rockbottom.api.construction.compendium.SmithingRecipe;
import de.ellpeck.rockbottom.api.construction.smelting.CombinerRecipe;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.construction.smelting.SmeltingRecipe;
import de.ellpeck.rockbottom.api.content.IContentLoader;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.part.DataPart;
import de.ellpeck.rockbottom.api.data.set.part.IPartFactory;
import de.ellpeck.rockbottom.api.data.settings.Keybind;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.effect.IEffect;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.entity.player.knowledge.Information;
import de.ellpeck.rockbottom.api.entity.player.statistics.IStatistics;
import de.ellpeck.rockbottom.api.entity.player.statistics.StatisticInitializer;
import de.ellpeck.rockbottom.api.entity.spawn.SpawnBehavior;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.gui.IMainMenuTheme;
import de.ellpeck.rockbottom.api.gui.ISpecialCursor;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.net.chat.Command;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.*;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.SubWorldInitializer;
import de.ellpeck.rockbottom.api.world.gen.IStructure;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

/**
 * This class houses all of the game's default {@link IRegistry} objects. If you
 * want to access a registry without directly accessing its object, use {@link
 * #get(ResourceName, Class)}.
 */
public final class Registries {

    /**
     * The registry containing all persistent {@link IRegistry} objects. Use
     * {@link IRegistry#register()} to register your own registry. This is
     * useful both for outputting information on how full registries are, but
     * also for mod interaction: Other mods can register to your registries
     * without needing their object directly simply by getting them from this
     * registry. To get a registry by name, use {@link #get(ResourceName,
     * Class)}.
     */
    @ApiInternal
    public static final NameRegistry<IRegistry> REGISTRIES = new NameRegistry<>(ResourceName.intern("registries"), false);
    /**
     * The registry for all {@link Tile} objects. Use {@link Tile#register()} to
     * register it.
     */
    @ApiInternal
    public static final NameRegistry<Tile> TILE_REGISTRY = new NameRegistry<>(ResourceName.intern("tile_registry"), false).register();
    /**
     * The registry for all {@link Item} objects. Use {@link Item#register()} to
     * register it.
     */
    @ApiInternal
    public static final NameRegistry<Item> ITEM_REGISTRY = new NameRegistry<>(ResourceName.intern("item_registry"), false).register();
    /**
     * The registry for all {@link Entity} types. The {@link ResourceName}
     * refers to the way the entity will be saved to disk when it is present in
     * the world. Note that, to be able to be loaded back in when the game is
     * started, an {@link Entity} needs to have a constructor containing only an
     * {@link IWorld}.
     */
    public static final NameRegistry<Entity.IFactory> ENTITY_REGISTRY = new NameRegistry<>(ResourceName.intern("entity_registry"), false).register();
    /**
     * The registry for all {@link DataPart} types. Note that this registry
     * should not be registered into without caution, as the index should not be
     * automatically determined due to it having to persist on disk when a
     * {@link DataSet} is stored. Either use an index that most likely no other
     * mods will use, create a configuration file to be able to change it or
     * only use existing {@link DataPart}s.
     */
    @ApiInternal
    public static final IndexRegistry<IPartFactory> PART_REGISTRY = new IndexRegistry<>(ResourceName.intern("part_registry"), Byte.MAX_VALUE, false).register();
    /**
     * The registry for all {@link IPacket} types. To register into this
     * registry, you can use {@link IndexRegistry#getNextFreeId()} to determine
     * an id for the packet, or directly register it using {@link
     * IndexRegistry#registerNextFree(Object)}.
     */
    public static final IndexRegistry<Class<? extends IPacket>> PACKET_REGISTRY = new IndexRegistry<>(ResourceName.intern("packet_registry"), Byte.MAX_VALUE, false).register();
    /**
     * The registry for all {@link Command} objects. Use {@link
     * Command#register()} to register it.
     */
    public static final NameRegistry<Command> COMMAND_REGISTRY = new NameRegistry<>(ResourceName.intern("command_registry"), true).register();
    /**
     * The registry for all {@link ICriteria} objects. Use
     * {link ICriteria#register()} to register your criteria.
     * These can be used in recipe json files.
     */
    public static final NameRegistry<ICriteria> CRITERIA_REGISTRY = new NameRegistry<>(ResourceName.intern("criteria_registry"), false).register();
    /**
     * The registry for all {@link ICompendiumRecipe} objects that are
     * in the game. If your mod includes its own registry for recipes,
     * consider making it a {@link ParentedNameRegistry} and using this
     * as the parent.
     */
    public static final NameRegistry<ICompendiumRecipe> ALL_RECIPES = new NameRegistry<>(ResourceName.intern("all_recipes_registry"), true).register();
    /**
     * // TODO 0.4
     * A parent for Manual and Construction Table recipes.
     */
    @ApiInternal
    public static final ParentedNameRegistry<ConstructionRecipe> CONSTRUCTION_RECIPES = new ParentedNameRegistry<>(ResourceName.intern("construction_recipe_registry"), true, ALL_RECIPES).register();
    /**
     * The recipe for all {@link ConstructionRecipe} objects which require
     * a tool to be crafted. These show up in a separate tab to manual recipes
     * in the Compendium and can be crafted with the use of a tool in the
     * ConstructionTable. Use {@link ConstructionRecipe#registerConstructionTable()}
     * to register.
     */
    @ApiInternal
    public static final ParentedNameRegistry<ConstructionRecipe> CONSTRUCTION_TABLE_RECIPES = new ParentedNameRegistry<>(ResourceName.intern("construction_table_recipe_registry"), true, CONSTRUCTION_RECIPES).register();
    /**
     * The registry for all {@link ConstructionRecipe} objects that are
     * displayed on the left side of the player's inventory. Use {@link
     * ConstructionRecipe#registerManual()} to register recipes into this
     * registry. If you want to get a recipe instance by its name, use {@link
     * ConstructionRecipe#forName(ResourceName)}.
     */
    @ApiInternal
    public static final ParentedNameRegistry<ConstructionRecipe> MANUAL_CONSTRUCTION_RECIPES = new ParentedNameRegistry<>(ResourceName.intern("manual_construction_recipe_registry"), true, CONSTRUCTION_RECIPES).register();
    /**
     * The registry for all {@link MortarRecipe} objects that can be processed
     * in a mortar. To register something into this registry, please use  {@link
     * MortarRecipe#register()}. If you want to get a recipe instance by its
     * name, use {@link MortarRecipe#forName(ResourceName)}.
     */
    @ApiInternal
    public static final ParentedNameRegistry<MortarRecipe> MORTAR_RECIPES = new ParentedNameRegistry<>(ResourceName.intern("mortar_recipe_registry"), true, ALL_RECIPES).register();
    /**
     * The registry for all {@link SmithingRecipe} objects that can be processed
     * in a mortar. To register something into this registry, please use  {@link
     * SmithingRecipe#register()}. If you want to get a recipe instance by its
     * name, use {@link MortarRecipe#forName(ResourceName)}.
     */
    @ApiInternal
    public static final ParentedNameRegistry<SmithingRecipe> SMITHING_RECIPES = new ParentedNameRegistry<>(ResourceName.intern("smithing_recipe_registry"), true, ALL_RECIPES).register();

    /**
     * The registry for all {@link IWorldGenerator} types. The {@link
     * ResourceName} is used to save a generator to disk if it is a retroactive
     * generator ({@link IWorldGenerator#generatesRetroactively()}) to mark that
     * it has already generated in a certain chunk. However, a registry name
     * needs to be supplied regardless if the generator is retroactive. Note
     * that, to instantiate a world generator, it needs to contain a default
     * constructor.
     */
    public static final NameRegistry<IWorldGenerator.IFactory> WORLD_GENERATORS = new NameRegistry<>(ResourceName.intern("world_generator_registry"), true).register();
    /**
     * The registry for all {@link Biome} objects. Use {@link Biome#register()}
     * to register biomes into this registry.
     */
    @ApiInternal
    public static final NameRegistry<Biome> BIOME_REGISTRY = new NameRegistry<>(ResourceName.intern("biome_registry"), false).register();
    /**
     * The registry for all {@link BiomeLevel} objects. Use {@link
     * BiomeLevel#register()} to register biomes into this registry.
     */
    @ApiInternal
    public static final NameRegistry<BiomeLevel> BIOME_LEVEL_REGISTRY = new NameRegistry<>(ResourceName.intern("biome_level_registry"), false).register();
    /**
     * The registry for all {@link IStructure} objects. Use the contents.json
     * file to create a new structure and have it loaded through the content
     * manager. If you want to get a structure by its name, use {@link
     * IStructure#forName(ResourceName)} or if you want to get multiple
     * structures with similar names, use {@link IStructure#forNamePart(String)}.
     */
    @ApiInternal
    public static final NameRegistry<IStructure> STRUCTURE_REGISTRY = new NameRegistry<>(ResourceName.intern("structure_registry"), true).register();
    /**
     * The registry for all {@link TileState} objects. This registry
     * automatically has objects registered into it by registering the
     * associated {@link Tile} objects using {@link Tile#register()}. Do not
     * manually register anything in it.
     */
    @ApiInternal
    public static final NameRegistry<TileState> TILE_STATE_REGISTRY = new NameRegistry<>(ResourceName.intern("tile_state_registry"), false).register();
    /**
     * The registry for all {@link TileLayer} objects. Use {@link
     * TileLayer#register()} to register a layer into this registry.
     */
    @ApiInternal
    public static final NameRegistry<TileLayer> TILE_LAYER_REGISTRY = new NameRegistry<>(ResourceName.intern("tile_layer_registry"), true).register();
    /**
     * The registry for all {@link Keybind} objects. Use {@link
     * Keybind#register()} to register a keybind into this registry. This needs
     * to be done before {@link IMod#preInit(IGameInstance, IApiHandler,
     * IEventHandler)} as otherwise, the keybind's current value will not be
     * loaded from the {@link Settings} file automatically.
     */
    @ApiInternal
    public static final NameRegistry<Keybind> KEYBIND_REGISTRY = new NameRegistry<>(ResourceName.intern("keybind_registry"), true).register();
    /**
     * The registry for all {@link ChatComponent} objects. Use {@link
     * IndexRegistry#getNextFreeId()} to determine the component's id. When
     * registering custom chat components, note that, to send their information
     * between the server and the client, they need to contain a default
     * constructor.
     */
    public static final IndexRegistry<Class<? extends ChatComponent>> CHAT_COMPONENT_REGISTRY = new IndexRegistry<>(ResourceName.intern("chat_component_registry"), Byte.MAX_VALUE, false).register();
    /**
     * The list of all {@link IMainMenuTheme} objects. Adding themes into this
     * list will give them a chance to be randomly chosen when the title screen
     * of the game is loaded up. Do not clear this list as it will crash the
     * game on startup.
     */
    public static final IndexRegistry<IMainMenuTheme> MAIN_MENU_THEMES = new IndexRegistry<>(ResourceName.intern("main_menu_theme_registry"), Integer.MAX_VALUE, true).register();
    /**
     * The registry for all {@link Information} types. Note that, to save and
     * load information from and to disk, they need to contain a constructor
     * that takes an {@link ResourceName} similar to the one that {@link
     * Information} provides.
     */
    public static final NameRegistry<Information.IFactory> INFORMATION_REGISTRY = new NameRegistry<>(ResourceName.intern("information_registry"), false).register();
    /**
     * The registry for all {@link IAssetLoader} objects. To register one into
     * this registry, use {@link IAssetLoader#register()}.
     */
    @ApiInternal
    public static final NameRegistry<IAssetLoader> ASSET_LOADER_REGISTRY = new NameRegistry<>(ResourceName.intern("asset_loader_registry"), false).register();
    /**
     * The registry for all {@link IContentLoader} objects. To register one into
     * this registry, use {@link IContentLoader#register()}.
     */
    @ApiInternal
    public static final NameRegistry<IContentLoader> CONTENT_LOADER_REGISTRY = new NameRegistry<>(ResourceName.intern("content_loader_registry"), false).register();
    /**
     * The list of all {@link ISpecialCursor} instance. Adding cursors into this
     * list will allow them to be displayed in place of the regular cursor when
     * their condition is met.
     */
    public static final IndexRegistry<ISpecialCursor> SPECIAL_CURSORS = new IndexRegistry<>(ResourceName.intern("special_cursor_registry"), Integer.MAX_VALUE, true).register();
    /**
     * The registry for all {@link IEffect} objects that can be applied to any
     * entity. To register something into this registry, use {@link
     * IEffect#register()}.
     */
    @ApiInternal
    public static final NameRegistry<IEffect> EFFECT_REGISTRY = new NameRegistry<>(ResourceName.intern("effect_registry"), false).register();
    /**
     * The registry for all {@link StatisticInitializer} objects that track
     * certain things in the game. To registry something into this registry, use
     * {@link StatisticInitializer#register()}. To get a statistic and/or modify
     * its value, use {@link IStatistics} which you can obtain from any {@link
     * AbstractEntityPlayer}.
     */
    @ApiInternal
    public static final NameRegistry<StatisticInitializer> STATISTICS_REGISTRY = new NameRegistry<>(ResourceName.intern("statistics_registry"), false).register();
    /**
     * The registry for all {@link FuelInput} objects that determine which kinds
     * of items burn for how long as furnace fuel. To register something into
     * this registry, please use {@link FuelInput#register()}.
     */
    @ApiInternal
    public static final NameRegistry<FuelInput> FUEL_REGISTRY = new NameRegistry<>(ResourceName.intern("fuel_registry"), true).register();
    /**
     * The registry for all {@link SmeltingRecipe} objects that can be smelted
     * in any kind of furnace. To register something into this registry, please
     * use {@link SmeltingRecipe#register()}.
     */
    @ApiInternal
    public static final NameRegistry<SmeltingRecipe> SMELTING_REGISTRY = new NameRegistry<>(ResourceName.intern("smelting_registry"), true).register();
    /**
     * The registry for all {@link CombinerRecipe} objects that can be combined
     * in any kind of combiner. To register something into this registry, please
     * use {@link CombinerRecipe#register()}.
     */
    @ApiInternal
    public static final NameRegistry<CombinerRecipe> COMBINER_REGISTRY = new NameRegistry<>(ResourceName.intern("combiner_registry"), true).register();
    /**
     * The registry for all {@link SpawnBehavior} objects that determine how and
     * where entities can randomly appear in the world. To register something
     * into this registry, please use {@link SpawnBehavior#register()}.
     */
    @ApiInternal
    public static final NameRegistry<SpawnBehavior> SPAWN_BEHAVIOR_REGISTRY = new NameRegistry<>(ResourceName.intern("spawn_behavior_registry"), true).register();
    /**
     * The registry for all {@link ToolProperty} objects that determine what
     * kind of tool an item is and what level it is. Use {@link
     * ToolProperty#register()} to register a property.
     */
    @ApiInternal
    public static final NameRegistry<ToolProperty> TOOL_PROPERTY_REGISTRY = new NameRegistry<>(ResourceName.intern("tool_property_registry"), false).register();
    /**
     * The registry for all {@link SubWorldInitializer} objects that can be used
     * to initialize custom sub worlds for the main world. To register a sub
     * world initializer, please use {@link SubWorldInitializer#register()}.
     */
    @ApiInternal
    public static final NameRegistry<SubWorldInitializer> SUB_WORLD_INITIALIZER_REGISTRY = new NameRegistry<>(ResourceName.intern("sub_world_initializer_registry"), true).register();
    /**
     * The registry for all {@link CompendiumCategory} objects that allow
     * modders to add a custom tab to the compendium in the inventory, and an
     * easy way to list recipes and their descriptions with it. Use {@link
     * CompendiumCategory#register()} to register a category.
     */
    @ApiInternal
    public static final NameRegistry<CompendiumCategory> COMPENDIUM_CATEGORY_REGISTRY = new NameRegistry<>(ResourceName.intern("compendium_category_registry"), true).register();

    public static <T extends IRegistry> T get(ResourceName name, Class<T> type) {
        IRegistry reg = get(name);
        if (reg != null && type.isAssignableFrom(reg.getClass())) {
            return (T) reg;
        } else {
            return null;
        }
    }

    public static IRegistry get(ResourceName name) {
        return REGISTRIES.get(name);
    }
}
