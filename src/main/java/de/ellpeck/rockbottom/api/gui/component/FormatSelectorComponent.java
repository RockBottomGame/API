/*
 * This file ("ComponentFormatSelector.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;
import java.util.List;

public class FormatSelectorComponent extends ButtonComponent {

    private final InputFieldComponent[] inputFields;
    private SelectorMenuComponent menu;

    public FormatSelectorComponent(Gui gui, int x, int y, InputFieldComponent... inputFields) {
        super(gui, x, y, 16, 16, null, "Aa");
        this.inputFields = inputFields;
    }

    public void openMenu() {
        int width = 86;
        int height = 72;

        this.menu = new SelectorMenuComponent(this.gui, this.x + this.width / 2 - width / 2, this.y - height - 2, width, height, this.inputFields);
        this.gui.getComponents().add(this.menu);

        this.gui.sortComponents();
    }

    public void closeMenu() {
        this.gui.getComponents().remove(this.menu);
        this.menu.onRemoved();
        this.menu = null;

        this.gui.sortComponents();
    }

    public boolean isMenuOpen() {
        return this.menu != null;
    }

    @Override
    public boolean onKeyPressed(IGameInstance game, int button) {
        if (this.menu != null) {
            if (Settings.KEY_MENU.isKey(button)) {
                this.closeMenu();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y) {
        if (this.isMouseOver(game)) {
            if (Settings.KEY_GUI_ACTION_1.isKey(button)) {
                if (this.menu == null) {
                    this.openMenu();
                    return true;
                }
            }
        }

        if (this.menu != null && !this.menu.isMouseOver(game)) {
            this.closeMenu();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ResourceName getName() {
        return ResourceName.intern("format_selector_button");
    }

    @Override
    public int getPriority() {
        return this.menu != null ? 1000 : super.getPriority();
    }

    private static class SelectorMenuComponent extends GuiComponent {

        private final List<GuiComponent> subComponents = new ArrayList<>();

        public SelectorMenuComponent(Gui gui, int x, int y, int width, int height, InputFieldComponent[] inputFields) {
            super(gui, x, y, width, height);


            int buttonX = 2;
            int buttonY = 2;
            for (FormattingCode code : FormattingCode.getDefaultCodes().values()) {
                this.subComponents.add(new ButtonComponent(gui, x + buttonX, y + buttonY, 12, 12, () -> {
                    String codeStrg = code.toString();
                    InputFieldComponent field = null;

                    if (inputFields.length <= 1) {
                        field = inputFields[0];
                    } else {
                        for (InputFieldComponent f : inputFields) {
                            if (f.isSelected()) {
                                field = f;
                                break;
                            }
                        }
                    }

                    if (field != null && !field.getText().endsWith(codeStrg)) {
                        field.append(codeStrg);
                        return true;
                    } else {
                        return false;
                    }
                }, code + "Aa", RockBottomAPI.getGame().getAssetManager().localize(ResourceName.intern("info.format." + code)), "Code: " + code.toString().replaceAll("&", "<&>")) {
                    @Override
                    public int getPriority() {
                        return 2000;
                    }
                });

                buttonX += 14;
                if (buttonX >= width) {
                    buttonX = 2;
                    buttonY += 14;
                }
            }

            this.gui.getComponents().addAll(this.subComponents);
        }

        public void onRemoved() {
            this.gui.getComponents().removeAll(this.subComponents);
        }

        @Override
        public ResourceName getName() {
            return ResourceName.intern("format_selector_menu");
        }

        @Override
        public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
            g.addFilledRect(x, y, this.width, this.height, Colors.setA(Colors.BLACK, 0.65F));
            g.addEmptyRect(x, y, this.width, this.height, Colors.BLACK);
        }

        @Override
        public int getPriority() {
            return 1500;
        }
    }
}
