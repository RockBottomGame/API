/*
 * This file ("ItemTileRenderer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.render.item;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ItemTileRenderer implements IItemRenderer<ItemTile>{

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, ItemTile item, ItemInstance instance, float x, float y, float scale, Color filter){
        Tile tile = item.getTile();
        if(tile != null){
            ITileRenderer renderer = tile.getRenderer();
            if(renderer != null){
                renderer.renderItem(game, manager, g, tile, instance, x, y, scale, filter);
            }
        }
    }

    @Override
    public void renderOnMouseCursor(IGameInstance game, IAssetManager manager, Graphics g, ItemTile item, ItemInstance instance, float x, float y, float scale, Color filter, boolean isInPlayerRange){
        if(isInPlayerRange){
            this.render(game, manager, g, item, instance, x+0.1F*scale, y, scale*0.75F, filter);
        }
    }
}
