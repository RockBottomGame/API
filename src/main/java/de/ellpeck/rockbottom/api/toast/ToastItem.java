/*
 * This file ("ToastBasic.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.toast;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public class ToastItem extends ToastBasic {

    protected final List<ItemInstance> items;

    public ToastItem(List<ItemInstance> items, ChatComponent title, ChatComponent description, int displayTime) {
        super(title, description, displayTime);
        this.items = items;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, float x, float y) {
        float width = this.getWidth();
        float height = this.getHeight();

        g.addFilledRect(x, y, width, height, GuiComponent.getElementColor());
        g.addEmptyRect(x, y, width, height, GuiComponent.getElementOutlineColor());

        float textX = x;
        float textWidth = width;

        if (this.items != null && !this.items.isEmpty()) {
            ItemInstance instance = items.get((game.getTotalTicks() * 40) % items.size());
            if (instance != null) {
                float size = height - 2;

                textX += size + 1;
                textWidth -= size + 1;

                Item item = instance.getItem();

                item.getRenderer().render(game, manager, g, item, instance, x + 1, y + 1, 12f / 12f * size, Colors.WHITE);
            }
        }

        IFont font = manager.getFont();
        font.drawAutoScaledString(textX + 1, y + 1, this.getTitle().getDisplayWithChildren(game, manager), 0.3F, (int) textWidth - 2, Colors.WHITE, Colors.BLACK, false, false);
        font.drawSplitString(textX + 1, y + 8, FormattingCode.LIGHT_GRAY + this.getDescription().getDisplayWithChildren(game, manager), 0.25F, (int) textWidth - 2);
    }
}
