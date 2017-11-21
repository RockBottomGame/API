/*
 * This file ("TileMetaRenderer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.render.tile;

import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.ITexture;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.TileMeta;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileMetaRenderer implements ITileRenderer<TileMeta>{

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, TileMeta tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light){
        this.getTexture(manager, tile, state.get(tile.metaProp)).draw(renderX, renderY, scale, scale, light);
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IGraphics g, TileMeta tile, ItemInstance instance, float x, float y, float scale, int filter){
        this.getTexture(manager, tile, instance.getMeta()).draw(x, y, scale, scale, filter);
    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IGraphics g, TileMeta tile, TileState state){
        return this.getTexture(manager, tile, state.get(tile.metaProp));
    }

    @Override
    public JsonElement getAdditionalTextureData(IGameInstance game, IAssetManager manager, IGraphics g, TileMeta tile, ItemInstance instance, AbstractEntityPlayer player, String name){
        return this.getTexture(manager, tile, instance.getMeta()).getAdditionalData(name);
    }

    private ITexture getTexture(IAssetManager manager, TileMeta tile, int meta){
        if(meta >= 0 && meta < tile.subResourceNames.size()){
            return manager.getTexture(tile.subResourceNames.get(meta));
        }
        else{
            return manager.getMissingTexture();
        }
    }
}
