/*
 * This file ("AbstractEntityPlayer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.entity.player;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.EntityLiving;
import de.ellpeck.rockbottom.api.entity.player.knowledge.IKnowledgeManager;
import de.ellpeck.rockbottom.api.entity.player.statistics.IStatistics;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.chat.ICommandSender;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.render.IPlayerDesign;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.util.List;

public abstract class AbstractEntityPlayer extends EntityLiving implements ICommandSender {

    public AbstractEntityPlayer(IWorld world) {
        super(world);
    }

    public abstract boolean openGui(Gui gui);

    public abstract boolean openGuiContainer(Gui gui, ItemContainer container);

    public abstract boolean openContainer(ItemContainer container);

    public abstract boolean closeContainer();

    public abstract ItemContainer getContainer();

    @ApiInternal
    public abstract void resetAndSpawn(IGameInstance game);

    public abstract void sendPacket(IPacket packet);

    @ApiInternal
    public abstract boolean move(int type);

    @ApiInternal
    public abstract void onChunkLoaded(IChunk chunk);

    @ApiInternal
    public abstract void onChunkUnloaded(IChunk chunk);

    @ApiInternal
    public abstract List<IChunk> getChunksInRange();

    @Override
    public abstract int getCommandLevel();

    public abstract ItemContainer getInvContainer();

    public abstract Inventory getInv();

    public abstract int getSelectedSlot();

    @ApiInternal
    public abstract void setSelectedSlot(int slot);

    public abstract ItemInstance getSelectedItem();

    @Override
    public abstract String getChatColorFormat();

    @Override
    public abstract String getName();

    public abstract int getColor();

    public abstract IPlayerDesign getDesign();

    public abstract boolean isInRange(double x, double y, double maxDistance);

    public abstract IKnowledgeManager getKnowledge();

    public abstract IStatistics getStatistics();

    public abstract double getMoveSpeed();

    public abstract double getClimbSpeed();

    public abstract double getJumpHeight();

    public abstract double getRange();

    public abstract double getPickupRange();

    public abstract boolean isLocalPlayer();

    public abstract void gainSkill(float percentage);

    public abstract float getSkillPercentage();

    public abstract int getSkillPoints();

    public abstract int takeSkillPoints(int points);

    @ApiInternal
    public abstract void setSkill(float percentage, int points);

    public abstract GameMode getGameMode();

    public abstract void setGameMode(GameMode gamemode);

}
