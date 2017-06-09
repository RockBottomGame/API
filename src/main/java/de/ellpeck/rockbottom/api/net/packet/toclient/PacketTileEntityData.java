/*
 * This file ("PacketTileEntityData.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 */

package de.ellpeck.rockbottom.api.net.packet.toclient;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class PacketTileEntityData implements IPacket{

    private final ByteBuf tileBuf = Unpooled.buffer();
    private int x;
    private int y;

    public PacketTileEntityData(int x, int y, TileEntity tile){
        this.x = x;
        this.y = y;
        tile.toBuf(this.tileBuf);
    }

    public PacketTileEntityData(){

    }

    @Override
    public void toBuffer(ByteBuf buf) throws IOException{
        buf.writeInt(this.x);
        buf.writeInt(this.y);

        buf.writeInt(this.tileBuf.readableBytes());
        buf.writeBytes(this.tileBuf);
    }

    @Override
    public void fromBuffer(ByteBuf buf) throws IOException{
        this.x = buf.readInt();
        this.y = buf.readInt();

        int readable = buf.readInt();
        buf.readBytes(this.tileBuf, readable);
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context){
        game.scheduleAction(() -> {
            if(game.getWorld() != null){
                TileEntity tile = game.getWorld().getTileEntity(this.x, this.y);
                if(tile != null){
                    tile.fromBuf(this.tileBuf);
                }
                return true;
            }
            else{
                return false;
            }
        });
    }
}
