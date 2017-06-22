/*
 * This file ("ItemEntityRenderer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.render.entity;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.EntityItem;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ItemEntityRenderer implements IEntityRenderer<EntityItem>{

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, EntityItem entity, float x, float y, Color light){
        Item item = entity.item.getItem();
        IItemRenderer renderer = item.getRenderer();
        if(renderer != null){
            float bob = (float)Math.sin(entity.ticksExisted/20D%(2*Math.PI))*0.1F;
            renderer.render(game, manager, g, item, entity.item, x-0.25F, y+bob-0.45F, 0.5F, light);
        }
    }
}
