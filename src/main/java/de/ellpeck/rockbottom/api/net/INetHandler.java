/*
 * This file ("INetHandler.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.net;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.channel.group.ChannelGroup;

import java.util.UUID;

public interface INetHandler{

    boolean isThePlayer(Entity entity);

    boolean isClient();

    boolean isServer();

    boolean isActive();

    boolean isConnectedToServer();

    @ApiInternal
    ChannelGroup getConnectedClients();

    void sendToServer(IPacket packet);

    void sendToAllPlayers(IWorld world, IPacket packet);

    void sendToAllPlayersExcept(IWorld world, IPacket packet, Entity except);

    void sendToAllPlayersAround(IWorld world, IPacket packet, double x, double y, double radius);

    void sendToAllPlayersAroundExcept(IWorld world, IPacket packet, double x, double y, double radius, Entity except);

    void sendToAllPlayersWithLoadedPos(IWorld world, IPacket packet, double x, double y);

    void sendToAllPlayersWithLoadedPosExcept(IWorld world, IPacket packet, double x, double y, Entity except);

    int getCommandLevel(AbstractEntityPlayer player);

    void setCommandLevel(AbstractEntityPlayer player, int level);

    void setCommandLevel(UUID id, int level);

    void whitelist(UUID id);

    void removeWhitelist(UUID id);

    boolean isWhitelisted(UUID id);

    boolean isWhitelistEnabled();

    void enableWhitelist(boolean enabled);

    void blacklist(UUID id, String reason);

    String getBlacklistReason(UUID id);

    void removeBlacklist(UUID id);

    boolean isBlacklisted(UUID id);

    @ApiInternal
    void init(String ip, int port, boolean isServer) throws Exception;

    @ApiInternal
    void shutdown();

    @ApiInternal
    void saveServerSettings();
}
