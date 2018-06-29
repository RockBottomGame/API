/*
 * This file ("ComponentMessageBox.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;
import java.util.function.Supplier;

public class ComponentMessageBox extends GuiComponent {

    private final List<String> message;
    private final float scale;
    private final boolean hasBackground;
    private final boolean appearsSlowly;
    private final int letterAmount;
    private final Supplier<Boolean> supplier;
    private float letterCounter;

    public ComponentMessageBox(Gui gui, int x, int y, int width, int height, String message, float scale, boolean hasBackground, boolean appearsSlowly, Supplier<Boolean> supplier) {
        super(gui, x, y, width, height);
        this.scale = scale;
        this.hasBackground = hasBackground;
        this.appearsSlowly = appearsSlowly;
        this.supplier = supplier;

        IFont font = RockBottomAPI.getGame().getAssetManager().getFont();
        this.message = font.splitTextToLength(width - 4, scale, true, message);
        this.letterAmount = font.removeFormatting(message).length();
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        if (this.hasBackground) {
            g.addFilledRect(x, y, this.width, this.height, this.isMouseOverPrioritized(game) ? getElementColor() : getUnselectedElementColor());
            g.addEmptyRect(x, y, this.width, this.height, getElementOutlineColor());
        }

        IFont font = manager.getFont();
        float height = font.getHeight(this.scale);
        float yAdd = 0F;
        int accum = 0;

        for (String s : this.message) {
            if (this.appearsSlowly) {
                int sLength = font.removeFormatting(s).length();
                if (this.letterCounter >= accum + sLength) {
                    font.drawString(x + 2, y + 2 + yAdd, s, this.scale);
                    accum += sLength;
                } else {
                    font.drawCutOffString(x + 2, y + 2 + yAdd, s, this.scale, (int) this.letterCounter - accum, false, true);
                    break;
                }
            } else {
                font.drawString(x + 2, y + 2 + yAdd, s, this.scale);
            }

            yAdd += height;
        }

        String[] info = this.letterCounter < this.letterAmount ? new String[]{".  ", ".. ", "...", ""} : new String[]{"->", "->", "", ""};
        String used = info[(game.getTotalTicks() / 10) % info.length];
        font.drawString(x + this.width - font.getWidth(used, 0.3F) - 2, y + this.height - 8, used, 0.3F);
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y) {
        if (Settings.KEY_GUI_ACTION_1.isKey(button)) {
            if (this.letterCounter < this.letterAmount) {
                this.letterCounter = this.letterAmount;
                return true;
            } else {
                if (this.supplier.get()) {
                    this.gui.getComponents().remove(this);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update(IGameInstance game) {
        if (this.letterCounter < this.letterAmount) {
            this.letterCounter += game.getSettings().textSpeed;
        }
    }

    @Override
    public ResourceName getName() {
        return ResourceName.intern("message_box");
    }
}
