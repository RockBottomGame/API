package de.ellpeck.rockbottom.api.net;

import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import io.netty.channel.ChannelHandlerContext;

public interface IPacketContext {

    /**
     * Retrieves the channel context passed to the packet handler.
     */
    ChannelHandlerContext getChannelContext();

    /**
     * @return The player on the server that has sent the packet
     *
     * @throws UnsupportedOperationException on the client.
     */
    AbstractPlayerEntity getSender();

}
