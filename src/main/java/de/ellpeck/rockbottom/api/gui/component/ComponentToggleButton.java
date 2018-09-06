/*
 * This file ("ComponentToggleButton.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.function.Supplier;

public class ComponentToggleButton extends ComponentButton {

    private final IMod locDomain;
    private final String locKey;
    private boolean isToggled;

    public ComponentToggleButton(Gui gui, int x, int y, int sizeX, int sizeY, boolean defaultState, Supplier<Boolean> supplier, String locKey, String... hover) {
        this(gui, x, y, sizeX, sizeY, defaultState, supplier, RockBottomAPI.getGame(), locKey, hover);
    }

    public ComponentToggleButton(Gui gui, int x, int y, int sizeX, int sizeY, boolean defaultState, Supplier<Boolean> supplier, IMod locDomain, String locKey, String... hover) {
        super(gui, x, y, sizeX, sizeY, supplier, null, hover);
        this.isToggled = defaultState;
        this.locDomain = locDomain;
        this.locKey = locKey;
    }

    @Override
    protected String getText() {
        String resource = this.locKey + (this.isToggled ? "_toggled" : "");
        return RockBottomAPI.getGame().getAssetManager().localize(new ResourceName(this.locDomain, resource));
    }

    @Override
    public boolean onPressed(IGameInstance game) {
        this.isToggled = !this.isToggled;
        return false;
    }

    @Override
    public ResourceName getName() {
        return ResourceName.intern("toggle_button");
    }
}
