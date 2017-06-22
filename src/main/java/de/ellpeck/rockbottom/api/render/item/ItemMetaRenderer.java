/*
 * This file ("ItemMetaRenderer.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.item.ItemMeta;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ItemMetaRenderer extends DefaultItemRenderer<ItemMeta>{

    public ItemMetaRenderer(IResourceName texture){
        super(texture);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, ItemMeta item, ItemInstance instance, float x, float y, float scale, Color filter){
        int meta = instance.getMeta();

        if(meta >= 0 && item.subResourceNames.size() > meta){
            manager.getTexture(item.subResourceNames.get(meta)).draw(x, y, 1F*scale, 1F*scale, filter);
        }
        else{
            super.render(game, manager, g, item, instance, x, y, scale, filter);
        }
    }
}
