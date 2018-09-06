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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.render.tile;

import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.TileMeta;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileMetaRenderer<T extends TileMeta> implements ITileRenderer<T> {

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        this.getTexture(manager, tile, state.get(tile.metaProp)).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IRenderer g, T tile, ItemInstance instance, float x, float y, float scale, int filter) {
        this.getTexture(manager, tile, instance.getMeta()).draw(x, y, scale, scale, filter);
    }

    @Override
    public void renderInMainMenuBackground(IGameInstance game, IAssetManager manager, IRenderer g, T tile, TileState state, float x, float y, float scale) {
        this.getTexture(manager, tile, state.get(tile.metaProp)).getPositionalVariation((int) x, (int) y).draw(x, y, scale, scale);
    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, T tile, TileState state) {
        return this.getTexture(manager, tile, state.get(tile.metaProp));
    }

    protected ITexture getTexture(IAssetManager manager, T tile, int meta) {
        if (meta >= 0 && meta < tile.subResourceNames.size()) {
            return manager.getTexture(tile.subResourceNames.get(meta));
        } else {
            return manager.getMissingTexture();
        }
    }

    @Override
    public JsonElement getAdditionalTextureData(IGameInstance game, IAssetManager manager, IRenderer g, T tile, ItemInstance instance, AbstractEntityPlayer player, String name) {
        return this.getTexture(manager, tile, instance.getMeta()).getAdditionalData(name);
    }
}
