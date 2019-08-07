/*
 * This file ("ComponentIngredient.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui.component.construction;

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public class ComponentIngredient extends GuiComponent {

    private static final ResourceName RES = ResourceName.intern("gui.compendium.ingredient_background");
    private static final ResourceName RES_NONE = ResourceName.intern("gui.compendium.ingredient_background_none");

    private final boolean hasItem;
    private final List<ItemInstance> inputs;

    public ComponentIngredient(Gui gui, boolean hasItem, List<ItemInstance> inputs) {
        super(gui, 0, 0, 14, 14);
        this.hasItem = hasItem;
        this.inputs = inputs;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        if (!this.inputs.isEmpty()) {
            ItemInstance input = this.getInput(game);
            manager.getTexture(RES).draw(x, y, this.width, this.height);
            g.renderItemInGui(game, manager, input, x + 2, y + 2, 1.0F, this.hasItem ? Colors.WHITE : Colors.multiplyA(Colors.WHITE, 0.35F));
        } else {
            manager.getTexture(RES_NONE).draw(x, y, this.width, this.height);
        }
    }

    @Override
    public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        if (!this.inputs.isEmpty() && this.isMouseOver(game)) {
            ItemInstance instance = this.getInput(game);
            g.drawHoverInfoAtMouse(game, manager, false, 200, instance.getDisplayName());
        }
    }

    protected ItemInstance getInput(IGameInstance game) {
        return this.inputs.get((game.getTotalTicks() / Constants.TARGET_TPS) % this.inputs.size());
    }

    @Override
    public boolean shouldDoFingerCursor(IGameInstance game) {
        return false;
    }

    @Override
    public ResourceName getName() {
        return ResourceName.intern("ingredient");
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y) {
        return this.isMouseOver(game);
    }
}