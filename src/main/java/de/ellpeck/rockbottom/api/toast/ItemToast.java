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

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.util.Colors;

import java.util.List;

public class ItemToast extends BasicToast {

    protected final List<ItemInstance> items;
    protected final List<ChatComponent> descriptions;
    private final int startTick;

    public ItemToast(List<ItemInstance> items, ChatComponent title, List<ChatComponent> descriptions, int displayTime) {
        super(title, descriptions.get(0), displayTime);
        this.items = items;
        this.descriptions = descriptions;
        this.startTick = RockBottomAPI.getGame().getTotalTicks();
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, float x, float y) {
        float width = this.getWidth();
        float height = this.getHeight();

        g.addFilledRect(x, y, width, height, GuiComponent.getElementColor());
        g.addEmptyRect(x, y, width, height, GuiComponent.getElementOutlineColor());

        float textX = x;
        float textWidth = width;


        int id = 0;
        if (this.items != null && !this.items.isEmpty()) {
            id = ((game.getTotalTicks() - startTick) / Constants.TARGET_TPS) % this.items.size();
            ItemInstance instance = items.get(id);
            if (instance != null) {
                float size = height - 2;

                textX += size + 1;
                textWidth -= size + 1;

                Item item = instance.getItem();

                item.getRenderer().render(game, manager, g, item, instance, x + 1, y + 1, 12f / 12f * size, Colors.WHITE);
            }

            if (this.descriptions.size() != this.items.size()) id = 0;
        }

        IFont font = manager.getFont();
        font.drawAutoScaledString(textX + 1, y + 1, this.getTitle().getDisplayWithChildren(game, manager), 0.3F, (int) textWidth - 2, Colors.WHITE, Colors.BLACK, false, false);
        font.drawSplitString(textX + 1, y + 8, FormattingCode.LIGHT_GRAY + descriptions.get(id).getDisplayWithChildren(game, manager), 0.25F, (int) textWidth - 2);
    }
}
