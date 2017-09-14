/*
 * This file ("MultiTileRenderer.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

import java.util.HashMap;
import java.util.Map;

public class MultiTileRenderer<T extends MultiTile> extends DefaultTileRenderer<T>{

    protected final IResourceName texItem;
    protected final Map<Pos2, IResourceName> textures = new HashMap<>();

    public MultiTileRenderer(IResourceName texture, MultiTile tile){
        super(texture);
        this.texItem = this.texture.addSuffix(".item");

        for(int x = 0; x < tile.getWidth(); x++){
            for(int y = 0; y < tile.getHeight(); y++){
                if(tile.isStructurePart(x, y)){
                    this.textures.put(new Pos2(x, y), this.texture.addSuffix("."+x+"."+y));
                }
            }
        }
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light){
        Pos2 innerCoord = tile.getInnerCoord(state);
        manager.getTexture(this.textures.get(innerCoord)).draw(renderX, renderY, scale, scale, light);
    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IGraphics g, T tile, TileState state){
        Pos2 innerCoord = tile.getInnerCoord(state);
        return manager.getTexture(this.textures.get(innerCoord));
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IGraphics g, T tile, ItemInstance instance, float x, float y, float scale, int filter){
        manager.getTexture(this.texItem).draw(x, y, scale, scale, filter);
    }
}
