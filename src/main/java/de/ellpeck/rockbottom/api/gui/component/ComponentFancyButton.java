/*
 * This file ("ComponentFancyButton.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.function.Supplier;

public class ComponentFancyButton extends ComponentButton {

    protected final ResourceName texture;

    public ComponentFancyButton(Gui gui, int x, int y, int sizeX, int sizeY, Supplier<Boolean> supplier, ResourceName texture, String... hover) {
        super(gui, x, y, sizeX, sizeY, supplier, null, hover);
        this.texture = texture;
    }

    protected ResourceName getTexture() {
        return this.texture;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        super.render(game, manager, g, x, y);

        manager.getTexture(this.getTexture()).draw(x, y, this.width, this.height);
    }

    @Override
    public ResourceName getName() {
        return ResourceName.intern("fancy_button");
    }
}
