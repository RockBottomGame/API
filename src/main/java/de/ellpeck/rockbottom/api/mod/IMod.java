/*
 * This file ("IMod.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.mod;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAsset;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.stitcher.ITextureStitcher;
import de.ellpeck.rockbottom.api.content.IContent;
import de.ellpeck.rockbottom.api.data.settings.ModConfig;
import de.ellpeck.rockbottom.api.data.settings.ServerSettings;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.effect.IEffect;
import de.ellpeck.rockbottom.api.entity.player.IInteractionManager;
import de.ellpeck.rockbottom.api.entity.player.statistics.Statistic;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.IGuiManager;
import de.ellpeck.rockbottom.api.gui.ISpecialCursor;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.net.chat.Command;
import de.ellpeck.rockbottom.api.net.chat.IChatLog;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.toast.IToaster;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

/**
 * This is the base interface for any mod. When creating a mod, you should have
 * a class that implements this interface, making it automatically get loaded
 * and initialized when the mod's compiled jar is in the mods folder.
 * <p>
 * Notice that, when a mod can be disabled ({@link #isDisableable()}), the mod's
 * main method will still be initialized, but none of its initializing lifecycle
 * methods will be called. For this reason, you shouldn't statically initialize
 * any of your tiles, items or functionalities outside of the designated
 * initializing lifecycle methods.
 * <p>
 * For more information on how to create mods, check out the <a
 * href="https://github.com/RockBottomGame/Modding">modding example and tutorial
 * repository</a>.
 */
public interface IMod {

    /**
     * @return The mod's display name. This is the name that will be displayed
     * in the mods menu. It can contain spaces, capitalization and special
     * characters.
     */
    String getDisplayName();

    /**
     * @return The mod's unique id. It needs to be all lowercase, contain no
     * spaces and be unique, meaning that no other mods that are currently
     * installed are allowed to have the same id. Make sure that your mod id
     * isn't too short or too simple, for this very reason.
     */
    String getId();

    /**
     * @return The version of the mod. This doesn't follow any specific naming
     * or versioning scheme, you can use any style you want. This will not be
     * checked against anything or used in any way other than to display the
     * version to the user as the provided string.
     */
    String getVersion();

    /**
     * Returns the location that resources are stored in, starting in any of the
     * root folders that are marked as source (meaning they will be at the root
     * of the compiled mod jar). The folder specified here needs to contain an
     * assets.json file that contains information on all of the assets that need
     * to be loaded and used during the game.
     * <p>
     * For example, if your directory structure is {@code
     * /assets/mymod/assets.json}, then this method should return {@code
     * assets/mymod}, meaning there should be no leading or trailing slash.
     *
     * @return The resource location
     */
    String getResourceLocation();

    /**
     * Returns the location that content is stored in, starting in any of the
     * root folder that are marked as source (meaning they will be at the root
     * of the compiled mod jar). The folder specified here needs to contain a
     * content.json file that contains ifnormation on all of the content
     * (recipes, structures etc) that need to be loaded and used during the
     * game.
     * <p>
     * For example, if your directory structure is {@code
     * /assets/content/mymod/content.json}, then this method should return
     * {@code  assets/content/mymod}, meaning there should be no leading or
     * trailing slash.
     *
     * @return The content location
     */
    default String getContentLocation() {
        return "";
    }

    /**
     * @return A short description of the mod to be displayed to the user in the
     * mods gui
     */
    default String getDescription() {
        return "";
    }

    /**
     * @return A list of names of the people that have worked on the mod. This
     * list will be displayed, each entry seperated by a comma, to the user in
     * the mods gui.
     */
    default String[] getAuthors() {
        return new String[0];
    }

    /**
     * Gets the priority of how to sort the mod in the list of mods, meaning
     * this changes the order in which mods will be loaded. The higher the
     * priority, the sooner a mod will be loaded. For instance, if there are two
     * installed mods like {@code mod1} with priority {@code 1000} and {@code
     * mod2} with priority {@code 2000}, then the latter will be loaded first,
     * meaning all of its initialization lifecycle methods will be called before
     * mod1's.
     * <p>
     * Note that Rock Bottom has a sorting priority of {@link
     * Integer#MAX_VALUE}.
     *
     * @return The sorting priority
     */
    default int getSortingPriority() {
        return 0;
    }

    /**
     * Gets a class that can be initialized by the mods gui to open a special
     * new gui where the mod can have its own information or settings. This
     * class needs to contain a constructor that takes a single Gui which acts
     * as the parent. When this gui is initialized, it will then be passed the
     * currently opened mods gui.
     *
     * @return A gui class
     */
    default Class<? extends Gui> getModGuiClass() {
        return null;
    }

    /**
     * Gets an optional {@link ModConfig} instance that you can use to save and
     * load settings for your mod that the player will be able to change. The
     * {@link ModConfig} class automatically picks a location for your settings
     * to be, along with automatically loading them from disk before this mod's
     * {@link #preInit(IGameInstance, IApiHandler, IEventHandler)} phase. If you
     * want to change the settings in game, say, using the {@link
     * #getModGuiClass()}, you will still have to call {@link ModConfig#save()}
     * for them to be saved to file.
     * <p>
     * Keep in mind that the config returned does actually have to be cached as
     * a class variable for loading and saving to properly function.
     *
     * @return A config for the mod
     */
    default ModConfig getModConfig() {
        return null;
    }

    /**
     * Return true on this method if you want your mod to be able to be disabled
     * from the mods menu or the mod settings file. Note that disabling a mod
     * will still make its main class (the one that extends this interface) be
     * initialized on startup, but have none of its intializiation lifecycle
     * events be called. For that reason, you should never initialize anything
     * statically in your mod's main class if you return true on this method,
     * and instead always use the initialization lifecycle events.
     *
     * @return If the mod should be disableable
     */
    default boolean isDisableable() {
        return true;
    }

    /**
     * Return true on this method if you want the mod to be needed on the client
     * when trying to join a server. If this method returns false, then the mod
     * is serverside only, meaning that players that do not have it installed on
     * their clients can join the server without being kicked. Note that this
     * can cause desynchronizations and crashes with mods that add tiles or
     * items, and this setting should only be changed if a mod does not add
     * anything that contributes to clientside behavior.
     *
     * @return If the mod should be required on the client
     */
    default boolean isRequiredOnClient() {
        return true;
    }

    /**
     * Return true on this method if you want the mod to be needed on the server
     * when trying to join it. If this method returns false, then a player that
     * has this mod installed can join a server without the mod on it. If this
     * mod adds tiles or items, then they will, naturally, not be available to
     * the player on that server.
     *
     * @return If the mod should be required on the server
     */
    default boolean isRequiredOnServer() {
        return false;
    }

    /**
     * Return something other than the default on this method if you want a
     * client to be able to join a server (or vice versa) that has the same mod,
     * but with a different version, installed. The parameter passed to this
     * method is the version that the client trying to join the server has
     * installed.
     *
     * @param version The version that the client has
     * @return If the client version and the current version are compatible
     */
    default boolean isCompatibleWithModVersion(String version) {
        return version.equals(this.getVersion());
    }

    /*
        The following methods are the initialization lifecycle methods. They are called,
        in order of their naming and appearance here, for every mod in the order of their
        sorting priority. Each of these methods lists in their documentation which things
        are initialized in them by the base game (and in which order). Note that, because
        of how the priority system works, the base game's individual lifecycle method will
        always be called before any mods' ones.
        It is the modder's decision which things they initialize in which method, however,
        some things might only be able to be initialized in a certain order.
     */

    /**
     * This method is called before pre initialization. The base game does not
     * initialize anything in this method.
     *
     * @param game         The game instance
     * @param apiHandler   The api handler instance
     * @param eventHandler The event handler instance
     */
    default void prePreInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {

    }

    /**
     * This method is called as pre initialization. The base game initializes
     * the following things in this method: All threads the game uses, the
     * {@link Settings} (or {@link ServerSettings} on the dedicated server), the
     * {@link IAssetManager} and the {@link IRenderer}.
     *
     * @param game         The game instance
     * @param apiHandler   The api handler instance
     * @param eventHandler The event handler instance
     */
    default void preInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {

    }

    /**
     * This method is called as the main initialization phase. The base game
     * initializes the following things in this method: The {@link GameContent},
     * meaning all {@link Item}, {@link Tile}, {@link Biome} and {@link IEffect}
     * instances along with all {@link IWorldGenerator} classes.
     *
     * @param game         The game instance
     * @param apiHandler   The api handler instance
     * @param eventHandler The event handler instance
     */
    default void init(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {

    }

    /**
     * This method is called after the initialization. The base game initializes
     * the following things in this method: All {@link TileLayer} objects and
     * their (final!) sorting, the loading of all of the {@link IContent} files
     * from the {@link #getContentLocation()}, the {@link IChatLog}, the {@link
     * IGuiManager}, the {@link IInteractionManager}, the {@link
     * IParticleManager}, and lastly the {@link IToaster}.
     *
     * @param game         The game instance
     * @param apiHandler   The api handler instance
     * @param eventHandler The event handler instance
     */
    default void postInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {

    }

    /**
     * This method is called after post initialization. The base game
     * initializes the following things in this method: The base game's {@link
     * Statistic} instances and all of its {@link Command} instances.
     *
     * @param game         The game instance
     * @param apiHandler   The api handler instance
     * @param eventHandler The event handler instance
     */
    default void postPostInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {

    }

    /**
     * This method will not be called on the dedicated server, meaning this is
     * the place to load and initialize assets and rendering-related operatios
     * of any kind. This method is called before the game's {@link
     * IAssetManager} initializes any assets, and especially before the {@link
     * ITextureStitcher} stitches the textures into one.
     *
     * @param game         The game instance
     * @param assetManager The asset manager instance
     * @param apiHandler   The api handler instance
     */
    default void preInitAssets(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler) {

    }

    /**
     * This method will not be called on the dedicated server, meaning this is
     * the place to load and initialize assets and rendering-related operatios
     * of any kind. This method is called after the game's {@link IAssetManager}
     * initializes all of the {@link IAsset} instances from the {@link
     * #getResourceLocation()}, stitches the textures together using the {@link
     * ITextureStitcher} and also after the game loads its own {@link
     * ISpecialCursor} objects.
     *
     * @param game         The game instance
     * @param assetManager The asset manager instance
     * @param apiHandler   The api handler instance
     */
    default void initAssets(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler) {

    }

    /**
     * This method will not be called on the dedicated server, meaning this is
     * the place to load and initialize assets and rendering-related operatios
     * of any kind. This method is called after all of the assets have been
     * initialized and finalized, especially all of the fallback assets have
     * been initialized, but before the {@link IAssetManager} gets locked,
     * meaning assets can still be added to it at this point.
     *
     * @param game         The game instance
     * @param assetManager The asset manager instance
     * @param apiHandler   The api handler instance
     */
    default void postInitAssets(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler) {

    }
}
