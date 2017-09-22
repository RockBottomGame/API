/*
 * This file ("DynamicRegistryInfo.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.world;

import de.ellpeck.rockbottom.api.util.reg.NameToIndexInfo;
import io.netty.buffer.ByteBuf;

public class DynamicRegistryInfo{

    private NameToIndexInfo tileRegInfo;
    private NameToIndexInfo biomeRegInfo;

    public DynamicRegistryInfo(){
    }

    public DynamicRegistryInfo(NameToIndexInfo tileRegInfo, NameToIndexInfo biomeRegInfo){
        this.tileRegInfo = tileRegInfo;
        this.biomeRegInfo = biomeRegInfo;
    }

    public NameToIndexInfo getTiles(){
        return this.tileRegInfo;
    }

    public NameToIndexInfo getBiomes(){
        return this.biomeRegInfo;
    }

    public void fromBuffer(ByteBuf buf){
        this.tileRegInfo = new NameToIndexInfo("tile_reg_client_world", null, Integer.MAX_VALUE);
        this.tileRegInfo.fromBuffer(buf);

        this.biomeRegInfo = new NameToIndexInfo("biome_reg_client_world", null, Short.MAX_VALUE);
        this.biomeRegInfo.fromBuffer(buf);
    }

    public void toBuffer(ByteBuf buf){
        this.tileRegInfo.toBuffer(buf);
        this.biomeRegInfo.toBuffer(buf);
    }
}
