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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.net;

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.channel.group.ChannelGroup;

import java.util.UUID;

/**
 * This class handles all of the multiplayer and {@link IPacket} managing. You
 * can receive an instance of this class using {@link RockBottomAPI#getNet()}.
 */
public interface INetHandler {

    /**
     * Returns true if the passed entity is the current local player. Note that
     * this will always return false on the dedicated server as it does not have
     * a local player.
     *
     * @param entity The entity to query
     * @return If the entity is the local player
     * @see IGameInstance#isDedicatedServer()
     * @see IWorld#isLocalPlayer(Entity)
     */
    boolean isThePlayer(Entity entity);

    /**
     * Returns true if the game is currently in client mode, meaning it houses a
     * player that is currently connected to a local or dedicated server that a
     * different player is hosting. Note that, if a player opens a local server
     * using the ingame menu, this will not be true.
     *
     * @return If the game is a client currently
     * @see IWorld#isClient()
     */
    boolean isClient();

    /**
     * Returns true if the game is currently in server mode, meaning it acts as
     * a server that other players can join. Note that this will both be true
     * for the dedicated server and the local server hosted from within the
     * ingame menu.
     *
     * @return If the game is a server currently
     * @see IGameInstance#isDedicatedServer()
     * @see IWorld#isServer()
     */
    boolean isServer();

    /**
     * Returns true fi the game is currently either in server or client mode.
     *
     * @return If the net handler is active
     * @see #isClient()
     * @see #isServer()
     */
    boolean isActive();

    /**
     * Returns true if the game is currently in client mode and there is an
     * open, working connection to the server. This is rather useless for
     * modders as client-mode will automatically be terminated whenever this
     * method does not return true.
     *
     * @return If the game is connected to a server
     * @see #isClient()
     */
    boolean isConnectedToServer();

    @ApiInternal
    ChannelGroup getConnectedClients();

    /**
     * Sends an {@link IPacket} directly to the server where it will be
     * handled.
     *
     * @param packet The packet to send
     */
    void sendToServer(IPacket packet);

    /**
     * Sends an {@link IPacket} to all the players on the server, meaning that
     * this packet will be send to a list of clients and then handled there.
     *
     * @param world  The world
     * @param packet The packet to send
     */
    void sendToAllPlayers(IWorld world, IPacket packet);

    /**
     * Sends an {@link IPacket} to all of the players on the server except one.
     * This can be useful for if a packet is being sent from a player that
     * already has a certain bit of information, possibly from having sent it to
     * the server prior.
     *
     * @param world  The world
     * @param packet The packet to send
     * @param except The player that the packet should not be sent to
     */
    void sendToAllPlayersExcept(IWorld world, IPacket packet, Entity except);

    /**
     * Sends an {@link IPacket} to all the players in the current world, meaning
     * that this packet will be send to a list of clients and then handled
     * there.
     *
     * @param world  The world
     * @param packet The packet to send
     */
    void sendToAllPlayersInWorld(IWorld world, IPacket packet);

    /**
     * Sends an {@link IPacket} to all of the players in the current world
     * except one. This can be useful for if a packet is being sent from a
     * player that already has a certain bit of information, possibly from
     * having sent it to the server prior.
     *
     * @param world  The world
     * @param packet The packet to send
     * @param except The player that the packet should not be sent to
     */
    void sendToAllPlayersInWorldExcept(IWorld world, IPacket packet, Entity except);

    /**
     * Sends an {@link IPacket} to all of the players in the current world
     * within a certain radius of a certain point. This can be useful for thigns
     * like machines or entity or tile-based actions where a player that is too
     * far away to see the change does not need to receive the packet.
     *
     * @param world  The world
     * @param packet The packet to send
     * @param x      The center x
     * @param y      The center y
     * @param radius The radius around the center
     */
    void sendToAllPlayersAround(IWorld world, IPacket packet, double x, double y, double radius);

    /**
     * Sends an {@link IPacket} to all of the players in the current world
     * within a certain radius of a certain point, except for a single player.
     *
     * @param world  The world
     * @param packet The packet to send
     * @param x      The center x
     * @param y      The center y
     * @param radius The radius around the center
     * @param except The player that the packet should not be sent to
     */
    void sendToAllPlayersAroundExcept(IWorld world, IPacket packet, double x, double y, double radius, Entity except);

    /**
     * Sends an {@link IPacket} to all of the players in the current world that
     * have the given position in their list of loaded chunks. What this means
     * is that the packet will only be sent to those players that are close
     * enough to the position for the chunk that contains it to be loaded on
     * their end. This can greatly increase server performance for things that
     * happen in the world and for which players that don't have a given
     * position loaded also do not have to receive the information.
     *
     * @param world  The world
     * @param packet The packet to send
     * @param x      The position x
     * @param y      The position y
     */
    void sendToAllPlayersWithLoadedPos(IWorld world, IPacket packet, double x, double y);

    /**
     * Sends an {@link IPacket} to all of the players in the current world that
     * have the given position in their list of loaded chunks, except for a
     * certain player.
     *
     * @param world  The world
     * @param packet The packet to send
     * @param x      The position x
     * @param y      The position y
     * @param except The player that the packet should not be sent to
     */
    void sendToAllPlayersWithLoadedPosExcept(IWorld world, IPacket packet, double x, double y, Entity except);

    /**
     * Returns the command level of any player. This can be seen as a sort of
     * permission to check how much access to commands any player has.
     *
     * @param player The player
     * @return The player's command level
     * @see Constants#ADMIN_PERMISSION
     */
    int getCommandLevel(AbstractEntityPlayer player);

    /**
     * Sets the command level of any player to a certain value.
     *
     * @param player The player
     * @param level  The command level
     * @see #getCommandLevel(AbstractEntityPlayer)
     * @see Constants#ADMIN_PERMISSION
     */
    void setCommandLevel(AbstractEntityPlayer player, int level);

    /**
     * Sets the command level of any player's unique id to a certain value.
     *
     * @param id    The player's id
     * @param level The command level
     * @see #getCommandLevel(AbstractEntityPlayer)
     * @see Constants#ADMIN_PERMISSION
     */
    void setCommandLevel(UUID id, int level);

    /**
     * Puts a certain player's unique id onto the whitelist of the server.
     *
     * @param id The id to whitelist
     */
    void whitelist(UUID id);

    /**
     * Removes a certain player's unique id from the whitelist of the server.
     *
     * @param id The id to remove
     */
    void removeWhitelist(UUID id);

    /**
     * Returns true if the given player unique id is on the whitelist.
     *
     * @param id The id to query
     * @return If it is whitelisted
     */
    boolean isWhitelisted(UUID id);

    /**
     * Returns true if the whitelist is enabled. If it is not enabled, there
     * will still be certain players on the whitelist, however, who is and who
     * isn't will take no effect on their permissions and wether or not they are
     * allowed to play on the server.
     *
     * @return If the whitelist is enabled
     */
    boolean isWhitelistEnabled();

    /**
     * Sets the whitelist to be disabled or enabled
     *
     * @param enabled The value
     * @see #isWhitelistEnabled()
     */
    void enableWhitelist(boolean enabled);

    /**
     * Adds a certain player's unique id to the blacklist, meaning they will not
     * be allowed to play on the server anymore. Optionally, you can supply a
     * reason that will be displayed to the player when they try to join the
     * server.
     *
     * @param id     The id
     * @param reason The reason for being on the blacklist
     */
    void blacklist(UUID id, String reason);

    /**
     * Gets the reason for any player's unique id to be on the blacklist. If the
     * player's id is not blacklisted, then this method will return null. It
     * will also return null if there is no reason.
     *
     * @param id The id
     * @return The reson for being on the blacklist
     */
    String getBlacklistReason(UUID id);

    /**
     * Removes a certain player's unique id from the blacklist
     *
     * @param id The id to remove
     * @see #blacklist(UUID, String)
     */
    void removeBlacklist(UUID id);

    /**
     * Returns true if a certain player's unique id is blacklisted currently
     *
     * @param id The id to query
     * @return If it is blacklisted
     */
    boolean isBlacklisted(UUID id);

    @ApiInternal
    void init(String ip, int port, boolean isServer) throws Exception;

    @ApiInternal
    void shutdown();

    @ApiInternal
    void saveServerSettings();
}
