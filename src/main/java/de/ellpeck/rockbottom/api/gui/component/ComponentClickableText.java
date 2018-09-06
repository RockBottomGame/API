/*
 * This file ("ComponentClickableText.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.function.Supplier;

public class ComponentClickableText extends GuiComponent {

    private final float scale;
    private final String[] text;
    private final Supplier<Boolean> clickSupplier;

    public ComponentClickableText(Gui gui, int x, int y, float scale, boolean fromRight, Supplier<Boolean> clickSupplier, String... text) {
        super(gui, x, y, 0, 0);
        this.scale = scale;
        this.text = text;
        this.clickSupplier = clickSupplier;

        IFont font = RockBottomAPI.getGame().getAssetManager().getFont();
        for (String s : text) {
            int width = (int) font.getWidth(s, scale);
            if (this.width < width) {
                this.width = width;
            }
        }
        this.height = (int) font.getHeight(scale) * text.length;

        if (fromRight) {
            this.x -= this.width;
        }
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        IFont font = manager.getFont();
        boolean moused = this.isMouseOverPrioritized(game);

        int yOff = 0;
        for (String s : this.getText()) {
            font.drawString(this.x, this.y + yOff, moused ? FormattingCode.UNDERLINED + s : s, this.scale);
            yOff += font.getHeight(this.scale);
        }
    }

    public String[] getText() {
        return this.text;
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y) {
        if (Settings.KEY_GUI_ACTION_1.isKey(button) && this.isMouseOverPrioritized(game)) {
            return this.clickSupplier.get();
        } else {
            return false;
        }
    }

    @Override
    public ResourceName getName() {
        return ResourceName.intern("clickable_text");
    }
}
