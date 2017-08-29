/*
 * This file ("IMod.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.mod;

import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.gui.Gui;

/**
 * This is the class that describes a mod main class
 * <br> To make a mod, make any non-final class with a default constructor extend it.
 * The mod loader will then find it inside of the compiled mod jar and add it to the list of mods
 */
public interface IMod{

    /**
     * @return The display name of the mod
     */
    String getDisplayName();

    /**
     * @return The mod's unique string id. Needs to be all lowercase and cannot contain spaces
     */
    String getId();

    /**
     * @return The current version of the mod
     */
    String getVersion();

    /**
     * Returns the location in the jar file that mod-related {@link de.ellpeck.rockbottom.api.assets.IAsset}s
     * are stored (images, sounds, language files etc.)
     * <br> The location must contain a file named "assets.json" containg a mapping
     * of all the asset names to their locations in the jar file
     *
     * @return The location
     */
    String getResourceLocation();

    /**
     * @return The mod's description to be displayed in the mod info {@link de.ellpeck.rockbottom.api.gui.Gui}
     * on the title screen
     */
    default String getDescription(){
        return "";
    }

    /**
     * Returns the priority with which this mod will be sorted in the list of mods
     * <br>Higher values mean that it will be closer to the start of the list
     * <br>RockBottom has a priority of {@link Integer#MAX_VALUE} to always be loaded first
     *
     * @return The priority
     */
    default int getSortingPriority(){
        return 0;
    }

    /**
     * If desired, returns a class for a {@link Gui} that is opened from a button that is
     * displayed in the mods menu on the title screen of the game automatically
     * <br> Gui class must have one-argument constructor taking a {@link Gui} parent
     *
     * @return The gui class
     */
    default Class<? extends Gui> getModGuiClass(){
        return null;
    }

    /**
     * @return If this mod can be disabled from being loaded next time the game starts in the mods menu on the title screen
     */
    default boolean isDisableable(){
        return true;
    }

    default boolean isRequiredOnClient(){
        return true;
    }

    default boolean isRequiredOnServer(){
        return false;
    }

    default boolean isCompatibleWithModVersion(String version){
        return version.equals(this.getVersion());
    }

    default void prePreInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    /**
     * Override this method to do any actions that should happen before the initialization of the base game
     *
     * @param game         The current game instance
     * @param apiHandler   The API handler
     * @param eventHandler The event handler
     */
    default void preInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    /**
     * Override this method to do any actions that should happen during the initialization of the base game
     *
     * @param game         The current game instance
     * @param apiHandler   The API handler
     * @param eventHandler The event handler
     */
    default void init(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    /**
     * Override this method to do any actions that should happen after the initialization of the base game
     *
     * @param game         The current game instance
     * @param apiHandler   The API handler
     * @param eventHandler The event handler
     */
    default void postInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    default void postPostInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    /**
     * Override this method to do any actions involving registering assets
     * <br> This method is not called on the dedicated server
     *
     * @param game         The current game instance
     * @param assetManager The asset manager
     * @param apiHandler   The API handler
     */
    default void initAssets(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler){

    }
}
