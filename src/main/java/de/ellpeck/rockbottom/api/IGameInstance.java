/*
 * This file ("IGameInstance.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.IDataManager;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.settings.IPropSettings;
import de.ellpeck.rockbottom.api.data.settings.Keybind;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.entity.player.IInteractionManager;
import de.ellpeck.rockbottom.api.gui.IGuiManager;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.net.chat.IChatLog;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.render.IPlayerDesign;
import de.ellpeck.rockbottom.api.toast.IToaster;
import de.ellpeck.rockbottom.api.toast.Toast;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.world.DynamicRegistryInfo;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.WorldInfo;

import java.io.File;
import java.net.URLClassLoader;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * The game instance that can be used to interact with various game-related
 * values and acces things like {@link IGraphics} and {@link IAssetManager}.
 * Notice that this instance represents both the server and the client, however
 * some methods that are not implemented on the dedicated server (like graphics
 * and rendering methods) will throw an {@link UnsupportedOperationException}.
 * To access any methods from this class, use {@link RockBottomAPI#getGame()}.
 */
public interface IGameInstance extends IMod{

    @ApiInternal
    void startWorld(File worldFile, WorldInfo info, boolean isNewlyCreated);

    @ApiInternal
    void joinWorld(DataSet playerSet, WorldInfo info, DynamicRegistryInfo regInfo);

    @ApiInternal
    void quitWorld();

    @ApiInternal
    void openIngameMenu();

    <T> void enqueueAction(BiConsumer<IGameInstance, T> action, T object, Predicate<IGameInstance> condition);

    <T> void enqueueAction(BiConsumer<IGameInstance, T> action, T object);

    /**
     * Gets the {@link IDataManager} of the current game instance. This can be
     * used for accessing locations on disk like the save folder or the game
     * folder itself. Additionally, it can save {@link IPropSettings}.
     *
     * @return The data manager
     */
    IDataManager getDataManager();

    /**
     * Gets the {@link Settings} of the current game instance. These store all
     * of the values that can be edited in the settings gui.
     *
     * @return The settings
     */
    Settings getSettings();

    /**
     * Gets the {@link AbstractEntityPlayer} of the current game instance. Note
     * that this is not implemented on the dedicated server.
     *
     * @return The player
     *
     * @throws UnsupportedOperationException on the dedicated server
     */
    AbstractEntityPlayer getPlayer();

    /**
     * Gets the {@link IGuiManager} of the current game instance. This can be
     * used to open Guis and fade the screen in and out. Note that this is not
     * implemented on the dedicated server.
     *
     * @return The gui manager
     *
     * @throws UnsupportedOperationException on the dedicated server
     */
    IGuiManager getGuiManager();

    /**
     * Gets the {@link IInteractionManager} of the current game instance. This
     * can be used to access the positions in the world that the player is
     * currently breaking. Note that this is not implemented on the dedicated
     * server.
     *
     * @return The interaction manager
     *
     * @throws UnsupportedOperationException on the dedicated server
     */
    IInteractionManager getInteractionManager();

    /**
     * Gets the {@link IChatLog} of the current game instance. This can be used
     * to write messages and commands into the in-game chat.
     *
     * @return The chat log
     */
    IChatLog getChatLog();

    /**
     * Gets the {@link IWorld} of the current game instance. In the title
     * screen, this value is unassigned.
     *
     * @return The world
     */
    IWorld getWorld();

    /**
     * Gets the {@link IAssetManager} of the current game instance. This can be
     * used to get graphics, sounds and various other assets. Note that this is
     * not implemented on the dedicated server.
     *
     * @return The asset manager
     *
     * @throws UnsupportedOperationException on the dedicated server
     */
    IAssetManager getAssetManager();

    /**
     * Gets the {@link IGraphics} context of the current game instance. This can
     * be used to draw various shapes and interact with the OpenGL context.
     *
     * @return The graphics context
     */
    IGraphics getGraphics();

    /**
     * Gets the {@link IParticleManager} of the current game instance. This can
     * be used to spawn and render particles. Note that this is not implemented
     * on the dedicated server.
     *
     * @return The particle manager
     *
     * @throws UnsupportedOperationException on the dedicated server
     */
    IParticleManager getParticleManager();

    /**
     * Gets the {@link UUID} of the current game instance. This will be applied
     * to the player as soon as a world starts. Note that the dedicated server
     * does not have a unique id.
     *
     * @return The game's unique id
     *
     * @throws UnsupportedOperationException on the dedicated server
     */
    UUID getUniqueId();

    @ApiInternal
    int getTpsAverage();

    @ApiInternal
    int getFpsAverage();

    /**
     * Gets the {@link URLClassLoader} that the game is using to load its own
     * classes and classes from jars out of the mods folder.
     *
     * @return The class loader
     */
    URLClassLoader getClassLoader();

    @ApiInternal
    void setFullscreen(boolean fullscreen);

    /**
     * Gets the total amount of ticks the game has been running for. Note that,
     * when closing down the game and opening it back up, this value will be
     * reset.
     *
     * @return The total ticks
     */
    int getTotalTicks();

    /**
     * Gets the {@link IPlayerDesign} that defines what the player looks like.
     * Note that this is not implemented on the dedicated server.
     *
     * @return The player design
     *
     * @throws UnsupportedOperationException on the dedicated server
     */
    IPlayerDesign getPlayerDesign();

    @ApiInternal
    void setPlayerDesign(String jsonString);

    /**
     * Gets if the game is currently in dedicated server mode. This will be true
     * when "--server" is appended to the launch arguments.
     *
     * @return If the instance is a dedicated server.
     */
    boolean isDedicatedServer();

    @ApiInternal
    void setUniqueId(UUID id);

    /**
     * Gets the {@link IInputHandler} of the current game instance. This is a
     * class that allows for the polling of inputs. Note that, when using {@link
     * Keybind}, {@link Keybind#isDown()} and {@link Keybind#isPressed()} should
     * be used instead. Note that this is not implemented on the dedicated
     * server.
     *
     * @return The input manager
     *
     * @throws UnsupportedOperationException on the dedicated server
     */
    IInputHandler getInput();

    ISoundHandler getSound();

    @ApiInternal
    void exit();

    /**
     * Gets the {@link IToaster} of the current game instance. This is the
     * system that displays {@link Toast} objects, which are the little pop-up
     * messages in the top left. Note that this is not implemented on the
     * dedicated server.
     *
     * @return The toaster
     *
     * @throws UnsupportedOperationException on the dedicated server
     */
    IToaster getToaster();
}
