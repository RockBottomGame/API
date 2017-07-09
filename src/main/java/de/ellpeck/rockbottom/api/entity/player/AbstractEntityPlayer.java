/*
 * This file ("AbstractEntityPlayer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.entity.player;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.EntityLiving;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInvChangeCallback;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.net.chat.ICommandSender;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.render.IPlayerDesign;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import org.newdawn.slick.Color;

import java.util.List;

public abstract class AbstractEntityPlayer extends EntityLiving implements IInvChangeCallback, ICommandSender{

    public AbstractEntityPlayer(IWorld world){
        super(world);
    }

    public abstract void openGuiContainer(Gui gui, ItemContainer container);

    public abstract void openContainer(ItemContainer container);

    public abstract void closeContainer();

    public abstract ItemContainer getContainer();

    public abstract void resetAndSpawn(IGameInstance game);

    public abstract void sendPacket(IPacket packet);

    public abstract boolean move(int type);

    public abstract void onChunkLoaded(IChunk chunk);

    public abstract void onChunkUnloaded(IChunk chunk);

    public abstract List<IChunk> getChunksInRange();

    public abstract int getCommandLevel();

    public abstract ItemContainer getInvContainer();

    public abstract Inventory getInv();

    public abstract int getSelectedSlot();

    public abstract void setSelectedSlot(int slot);

    public abstract String getChatColorFormat();

    public abstract String getName();

    public abstract Color getColor();

    public abstract IPlayerDesign getDesign();
}
