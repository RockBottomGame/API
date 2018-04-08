/*
 * This file ("ITileRenderer.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public interface ITileRenderer<T extends Tile>{

    void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light);

    void renderItem(IGameInstance game, IAssetManager manager, IRenderer g, T tile, ItemInstance instance, float x, float y, float scale, int filter);

    ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, T tile, TileState state);

    default void renderInMainMenuBackground(IGameInstance game, IAssetManager manager, IRenderer g, T tile, TileState state, float x, float y, float scale){

    }

    default void renderOnMouseOver(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float mouseX, float mouseY){

    }

    default void renderInForeground(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light){

    }

    default JsonElement getAdditionalTextureData(IGameInstance game, IAssetManager manager, IRenderer g, T tile, ItemInstance instance, AbstractEntityPlayer player, String name){
        return null;
    }

    default ResourceName getRenderShader(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, T tile, TileState state, int x, int y, TileLayer layer){
        return null;
    }
}
