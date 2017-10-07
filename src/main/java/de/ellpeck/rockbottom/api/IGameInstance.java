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
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.entity.player.IInteractionManager;
import de.ellpeck.rockbottom.api.gui.IGuiManager;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.net.chat.IChatLog;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.render.IPlayerDesign;
import de.ellpeck.rockbottom.api.toast.IToaster;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.NameToIndexInfo;
import de.ellpeck.rockbottom.api.world.DynamicRegistryInfo;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.WorldInfo;
import org.newdawn.slick.Input;

import java.io.File;
import java.net.URLClassLoader;
import java.util.UUID;
import java.util.function.Supplier;

public interface IGameInstance extends IMod{

    @ApiInternal
    void startWorld(File worldFile, WorldInfo info);

    @ApiInternal
    void joinWorld(DataSet playerSet, WorldInfo info, DynamicRegistryInfo regInfo);

    @ApiInternal
    void quitWorld();

    @ApiInternal
    void openIngameMenu();

    void scheduleAction(Supplier<Boolean> action);

    @ApiInternal
    void calcScales();

    float getDisplayRatio();

    float getGuiScale();

    float getWorldScale();

    float getWidthInWorld();

    float getHeightInWorld();

    float getWidthInGui();

    float getHeightInGui();

    float getMouseInGuiX();

    float getMouseInGuiY();

    IDataManager getDataManager();

    Settings getSettings();

    AbstractEntityPlayer getPlayer();

    IGuiManager getGuiManager();

    IInteractionManager getInteractionManager();

    IChatLog getChatLog();

    IWorld getWorld();

    IAssetManager getAssetManager();

    IGraphics getGraphics();

    IParticleManager getParticleManager();

    UUID getUniqueId();

    boolean isDebug();

    boolean isLightDebug();

    boolean isItemInfoDebug();

    boolean isChunkBorderDebug();

    @ApiInternal
    int getTpsAverage();

    @ApiInternal
    int getFpsAverage();

    URLClassLoader getClassLoader();

    @ApiInternal
    void setFullscreen(boolean fullscreen);

    int getTotalTicks();

    IPlayerDesign getPlayerDesign();

    boolean isDedicatedServer();

    @ApiInternal
    void setUniqueId(UUID id);

    Input getInput();

    @ApiInternal
    void exit();

    IToaster getToaster();
}
