/*
 * This file ("ItemMetaRenderer.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.MetaItem;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class MetaItemRenderer extends DefaultItemRenderer<MetaItem> {

    public MetaItemRenderer(ResourceName texture) {
        super(texture);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, MetaItem item, ItemInstance instance, float x, float y, float scale, int filter) {
        int meta = instance.getMeta();

        if (meta >= 0 && item.subResourceNames.size() > meta) {
            manager.getTexture(item.subResourceNames.get(meta)).draw(x, y, 1F * scale, 1F * scale, filter);
        } else {
            super.render(game, manager, g, item, instance, x, y, scale, filter);
        }
    }

    @Override
    public JsonElement getAdditionalTextureData(IGameInstance game, IAssetManager manager, IRenderer g, MetaItem item, ItemInstance instance, AbstractPlayerEntity player, String name) {
        int meta = instance.getMeta();
        if (meta >= 0 && item.subResourceNames.size() > meta) {
            return manager.getTexture(item.subResourceNames.get(meta)).getAdditionalData(name);
        } else {
            return super.getAdditionalTextureData(game, manager, g, item, instance, player, name);
        }
    }
}
