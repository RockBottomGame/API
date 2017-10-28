/*
 * This file ("IItemRenderer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.render.item;

import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public interface IItemRenderer<T extends Item>{

    void render(IGameInstance game, IAssetManager manager, IGraphics g, T item, ItemInstance instance, float x, float y, float scale, int filter);

    default void renderOnMouseCursor(IGameInstance game, IAssetManager manager, IGraphics g, T item, ItemInstance instance, float x, float y, float scale, int filter, boolean isInPlayerRange){

    }

    default void renderHolding(IGameInstance game, IAssetManager manager, IGraphics g, T item, ItemInstance instance, AbstractEntityPlayer player, float x, float y, float rotation, float scale, int filter, boolean mirrored){
        g.pushMatrix();
        g.translate(x, y);
        g.rotate(rotation);

        if(mirrored){
            g.scale(-1F, 1F);
            g.translate(-scale, 0F);
        }

        this.render(game, manager, g, item, instance, 0F, 0F, scale*0.5F, filter);
        g.popMatrix();
    }

    default JsonElement getAdditionalTextureData(IGameInstance game, IAssetManager manager, IGraphics g, T item, ItemInstance instance, AbstractEntityPlayer player, String name){
        return null;
    }
}
