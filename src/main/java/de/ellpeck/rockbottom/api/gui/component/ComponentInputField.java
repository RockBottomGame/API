/*
 * This file ("ComponentInputField.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.function.Consumer;

public class ComponentInputField extends GuiComponent {

    public final boolean renderBox;
    public final boolean selectable;
    public final int maxLength;
    public final boolean displaxMaxLength;
    public final Consumer<String> consumer;
    private String text = "";
    private boolean isSelected;
    private boolean censored;

    public ComponentInputField(Gui gui, int x, int y, int sizeX, int sizeY, boolean renderBox, boolean selectable, boolean defaultActive, int maxLength, boolean displayMaxLength) {
        this(gui, x, y, sizeX, sizeY, renderBox, selectable, defaultActive, maxLength, displayMaxLength, null);
    }

    public ComponentInputField(Gui gui, int x, int y, int sizeX, int sizeY, boolean renderBox, boolean selectable, boolean defaultActive, int maxLength, boolean displayMaxLength, Consumer<String> consumer) {
        super(gui, x, y, sizeX, sizeY);
        this.renderBox = renderBox;
        this.selectable = selectable;
        this.isSelected = defaultActive;
        this.maxLength = maxLength;
        this.displaxMaxLength = displayMaxLength;
        this.consumer = consumer;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public boolean isCensored() {
        return this.censored;
    }

    public ComponentInputField setCensored(boolean censored) {
        this.censored = censored;
        return this;
    }

    @Override
    public boolean onKeyPressed(IGameInstance game, int button) {
        return RockBottomAPI.getInternalHooks().doInputFieldKeyPress(game, button, this);
    }

    @Override
    public boolean onCharInput(IGameInstance game, int codePoint, char[] characters) {
        return RockBottomAPI.getInternalHooks().doInputFieldCharInput(game, characters, this);

    }

    @Override
    public ResourceName getName() {
        return ResourceName.intern("input_field");
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
        if (this.consumer != null) {
            this.consumer.accept(text);
        }
    }

    public String getDisplayText() {
        return this.getText();
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        RockBottomAPI.getInternalHooks().doInputFieldRender(game, manager, g, x, y, this);
    }

    public void append(String text) {
        if (this.text.length() + text.length() <= this.maxLength) {
            this.setText(this.text + text);
        }
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y) {
        if (Settings.KEY_GUI_ACTION_1.isKey(button)) {
            if (this.selectable) {
                this.isSelected = this.isMouseOverPrioritized(game);
            }
        } else if (Settings.KEY_GUI_ACTION_2.isKey(button)) {
            if (this.isMouseOverPrioritized(game)) {
                this.setText("");

                if (this.selectable) {
                    this.isSelected = true;
                }
            } else if (this.selectable) {
                this.isSelected = false;
            }
        }
        return false;
    }

    @Override
    public boolean canCloseWithInvKey() {
        return !this.isSelected || !this.isActive();
    }
}
