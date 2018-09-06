/*
 * This file ("ItemTileRenderer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.render.item;

import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ItemTileRenderer implements IItemRenderer<ItemTile> {

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, ItemTile item, ItemInstance instance, float x, float y, float scale, int filter) {
        Tile tile = item.getTile();
        if (tile != null) {
            ITileRenderer renderer = tile.getRenderer();
            if (renderer != null) {
                renderer.renderItem(game, manager, g, tile, instance, x, y, scale, filter);
            }
        }
    }

    @Override
    public void renderOnMouseCursor(IGameInstance game, IAssetManager manager, IRenderer g, ItemTile item, ItemInstance instance, float x, float y, float scale, int filter, boolean isInPlayerRange) {
        if (isInPlayerRange) {
            this.render(game, manager, g, item, instance, x + 0.1F * scale, y, scale * 0.75F, filter);
        }
    }

    @Override
    public JsonElement getAdditionalTextureData(IGameInstance game, IAssetManager manager, IRenderer g, ItemTile item, ItemInstance instance, AbstractEntityPlayer player, String name) {
        Tile tile = item.getTile();
        if (tile != null) {
            ITileRenderer renderer = tile.getRenderer();
            if (renderer != null) {
                return renderer.getAdditionalTextureData(game, manager, g, tile, instance, player, name);
            }
        }
        return null;
    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, ItemTile item, ItemInstance instance) {
        Tile tile = item.getTile();
        if (tile != null) {
            ITileRenderer renderer = tile.getRenderer();
            if (renderer != null) {
                return renderer.getParticleTexture(game, manager, g, tile, tile.getDefState());
            }
        }
        return null;
    }
}
