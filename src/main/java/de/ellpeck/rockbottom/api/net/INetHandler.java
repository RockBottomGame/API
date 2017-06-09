/*
 * This file ("INetHandler.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 */

package de.ellpeck.rockbottom.api.net;

import de.ellpeck.rockbottom.api.data.settings.CommandPermissions;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.channel.group.ChannelGroup;

public interface INetHandler{

    boolean isThePlayer(AbstractEntityPlayer player);

    boolean isClient();

    boolean isServer();

    boolean isActive();

    boolean isConnectedToServer();

    ChannelGroup getConnectedClients();

    CommandPermissions getCommandPermissions();

    void sendToServer(IPacket packet);

    void sendToAllPlayers(IWorld world, IPacket packet);

    void sendToAllPlayersExcept(IWorld world, IPacket packet, Entity except);

    void init(String ip, int port, boolean isServer) throws Exception;

    void shutdown();
}
