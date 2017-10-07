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
import de.ellpeck.rockbottom.api.data.set.part.DataPart;
import de.ellpeck.rockbottom.api.data.settings.Keybind;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.knowledge.Information;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.gui.IMainMenuTheme;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.mod.IModLoader;
import de.ellpeck.rockbottom.api.net.INetHandler;
import de.ellpeck.rockbottom.api.net.chat.Command;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.util.reg.IndexRegistry;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class RockBottomAPI{

    public static final String VERSION = "0.1.13";

    public static final NameRegistry<Tile> TILE_REGISTRY = new NameRegistry<>("tile_registry");
    public static final NameRegistry<Item> ITEM_REGISTRY = new NameRegistry<>("item_registry");
    public static final NameRegistry<Class<? extends Entity>> ENTITY_REGISTRY = new NameRegistry<>("entity_registry");
    public static final IndexRegistry<Class<? extends DataPart>> PART_REGISTRY = new IndexRegistry<>("part_registry", Byte.MAX_VALUE);
    public static final IndexRegistry<Class<? extends IPacket>> PACKET_REGISTRY = new IndexRegistry<>("packet_registry", Byte.MAX_VALUE);
    public static final NameRegistry<Command> COMMAND_REGISTRY = new NameRegistry<>("command_registry");
    public static final NameRegistry<IRecipe> ALL_CONSTRUCTION_RECIPES = new NameRegistry<>("all_recipe_registry");
    public static final NameRegistry<BasicRecipe> MANUAL_CONSTRUCTION_RECIPES = new NameRegistry<>("manual_recipe_registry");
    public static final NameRegistry<Class<? extends IWorldGenerator>> WORLD_GENERATORS = new NameRegistry<>("world_generator_registry");
    public static final NameRegistry<Biome> BIOME_REGISTRY = new NameRegistry<>("biome_registry");
    public static final NameRegistry<TileState> TILE_STATE_REGISTRY = new NameRegistry<>("tile_state_registry");
    public static final NameRegistry<TileLayer> TILE_LAYER_REGISTRY = new NameRegistry<>("tile_layer_registry");
    public static final NameRegistry<Keybind> KEYBIND_REGISTRY = new NameRegistry<>("keybind_registry");
    public static final IndexRegistry<Class<? extends ChatComponent>> CHAT_COMPONENT_REGISTRY = new IndexRegistry<>("chat_component_registry", Byte.MAX_VALUE);
    public static final List<IMainMenuTheme> MAIN_MENU_THEMES = new ArrayList<>();
    public static final NameRegistry<Class<? extends Information>> INFORMATION_REGISTRY = new NameRegistry<>("information_registry");
    public static final NameRegistry<IAssetLoader> ASSET_LOADER_REGISTRY = new NameRegistry<>("asset_loader_registry");

    private static Internals internals;

    public static IApiHandler getApiHandler(){
        return internals.getApi();
    }

    public static INetHandler getNet(){
        return internals.getNet();
    }

    public static IEventHandler getEventHandler(){
        return internals.getEvent();
    }

    public static IGameInstance getGame(){
        return internals.getGame();
    }

    public static IModLoader getModLoader(){
        return internals.getMod();
    }

    public static IResourceName createInternalRes(String resource){
        return createRes(getGame(), resource);
    }

    public static IResourceName createRes(IMod mod, String resource){
        return getModLoader().createResourceName(mod, resource);
    }

    public static IResourceName createRes(String combined){
        return getModLoader().createResourceName(combined);
    }

    public static Logger createLogger(String name){
        return getApiHandler().createLogger(name);
    }

    public static Logger logger(){
        return getApiHandler().logger();
    }

    public static void setInternals(Internals intern){
        if(internals == null){
            internals = intern;
        }
        else{
            throw new RuntimeException("Mod tried to modify internal handlers - This is not allowed!");
        }
    }
}

