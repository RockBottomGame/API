/*
 * This file ("ItemToolRenderer.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolItem;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ToolItemRenderer extends DefaultItemRenderer<ToolItem> {

    public ToolItemRenderer(ResourceName texture) {
        super(texture);
    }

    @Override
    public void renderOnMouseCursor(IGameInstance game, IAssetManager manager, IRenderer renderer, ToolItem item, ItemInstance instance, float x, float y, float scale, int filter, boolean isInPlayerRange) {
        if (isInPlayerRange) {
            this.render(game, manager, renderer, item, instance, x, y, scale, filter, false);
        }
    }
}
