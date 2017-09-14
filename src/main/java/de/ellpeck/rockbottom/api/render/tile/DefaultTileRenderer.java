/*
 * This file ("DefaultTileRenderer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.render.tile;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.tex.ITexture;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class DefaultTileRenderer<T extends Tile> implements ITileRenderer<T>{

    public final IResourceName texture;

    public DefaultTileRenderer(IResourceName texture){
        this.texture = texture.addPrefix("tiles.");
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light){
        manager.getTexture(this.texture).draw(renderX, renderY, scale, scale, light);
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IGraphics g, T tile, ItemInstance instance, float x, float y, float scale, int filter){
        manager.getTexture(this.texture).draw(x, y, scale, scale, filter);
    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IGraphics g, T tile, TileState state){
        return manager.getTexture(this.texture);
    }

    @Override
    public void renderInMainMenuBackground(IGameInstance game, IAssetManager manager, IGraphics g, T tile, TileState state, float x, float y, float scale){
        manager.getTexture(this.texture).draw(x, y, scale, scale);
    }
}
