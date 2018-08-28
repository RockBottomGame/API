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

import com.google.common.base.Preconditions;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;
import de.ellpeck.rockbottom.api.content.pack.IContentPackLoader;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.internal.IInternalHooks;
import de.ellpeck.rockbottom.api.internal.Internals;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.mod.IModLoader;
import de.ellpeck.rockbottom.api.net.INetHandler;
import de.ellpeck.rockbottom.api.util.ApiInternal;

import java.util.logging.Logger;

/**
 * The main API class. Use this to access data related to the game's internal
 * workings such as the {@link IApiHandler}. See {@link Registries} for a list
 * of all of the registries in the game. See {@link GameContent} for a list of
 * all the content in the game.
 */
public final class RockBottomAPI {

    /**
     * The current API version equal to the version in the build.gradle file.
     */
    public static final String VERSION = "0.4.3";

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
    public static IApiHandler getApiHandler() {
        return internals.getApi();
    }

    /**
     * Returns the {@link INetHandler} object initialized by the game on
     * startup. The net handler contains a set of methods to deal with
     * multiplayer and networking.
     *
     * @return The net handler
     */
    public static INetHandler getNet() {
        return internals.getNet();
    }

    /**
     * Returns the {@link IEventHandler} object initialized by the game on
     * startup. The event handler contains a set of methods to deal with
     * subscribing, firing and listening to events.
     *
     * @return The event handler
     */
    public static IEventHandler getEventHandler() {
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
    public static IGameInstance getGame() {
        return internals.getGame();
    }

    /**
     * Returns the {@link IModLoader} object initialized by the game on startup.
     * The mod loader contains a set of methods to deal with mod interaction and
     * loading and finding mods.
     *
     * @return The mod loader
     */
    public static IModLoader getModLoader() {
        return internals.getMod();
    }

    /**
     * Returns the {@link IContentPackLoader} object initialized by the game on
     * startup. The content pack loader contains a set of methods to deal with
     * content pack interaction and loading, finding and organizing content
     * packs.
     *
     * @return The content pack loader
     */
    public static IContentPackLoader getContentPackLoader() {
        return internals.getContent();
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
    public static IResourceRegistry getResourceRegistry() {
        return internals.getResource();
    }

    /**
     * Returns the {@link IInternalHooks} object initialized by the game on
     * startup. This is an api internal interface used to access methods outside
     * of the API. None of the api internal hooks should be used by mods.
     *
     * @return The internal hooks
     */
    public static IInternalHooks getInternalHooks() {
        return internals.getHooks();
    }

    /**
     * Convenience call to {@link IApiHandler#createLogger(String)} that creates
     * a {@link Logger} with a specified name.
     *
     * @param name The logger's display name
     * @return The logger
     */
    public static Logger createLogger(String name) {
        return getApiHandler().createLogger(name);
    }

    /**
     * Convenience call to {@link IInternalHooks#logger()} that returns the
     * game's internal logger
     *
     * @return The game's logger
     */
    @ApiInternal
    public static Logger logger() {
        return getInternalHooks().logger();
    }

    /**
     * This is an internal method that sets all of the important game-related
     * constants like the {@link IGameInstance} or {@link IApiHandler}. Do not
     * call this method as it will throw an {@link IllegalStateException} at all
     * circumstances
     *
     * @param intern The internals
     */
    @ApiInternal
    public static void setInternals(Internals intern) {
        Preconditions.checkState(internals == null, "Mod tried to modify internal handlers - This is not allowed!");
        internals = intern;
    }
}

