/*
 * This file ("ImageBuffer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.assets.texture;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageBuffer{

    private final int width;
    private final int height;

    private final byte[] rawData;

    public ImageBuffer(int width, int height){
        this.width = width;
        this.height = height;
        this.rawData = new byte[width*height*4];
    }

    public ByteBuffer getRGBA(){
        ByteBuffer buffer = BufferUtils.createByteBuffer(this.rawData.length);
        buffer.put(this.rawData);
        buffer.flip();
        return buffer;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void setRGBA(int x, int y, int r, int g, int b, int a){
        int offset = ((x+(y*this.width))*4);

        if(ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN){
            this.rawData[offset] = (byte)b;
            this.rawData[offset+1] = (byte)g;
            this.rawData[offset+2] = (byte)r;
            this.rawData[offset+3] = (byte)a;
        }
        else{
            this.rawData[offset] = (byte)r;
            this.rawData[offset+1] = (byte)g;
            this.rawData[offset+2] = (byte)b;
            this.rawData[offset+3] = (byte)a;
        }
    }
}

