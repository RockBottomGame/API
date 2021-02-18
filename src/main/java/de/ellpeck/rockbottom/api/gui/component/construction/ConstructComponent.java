/*
 * This file ("ComponentConstruct.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.construction.ConstructionTool;
import de.ellpeck.rockbottom.api.construction.compendium.ICompendiumRecipe;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ConstructComponent extends GuiComponent {

    private final ICompendiumRecipe recipe;
    public final List<ConstructionTool> missingTools;
    private final boolean hasIngredients;
    private final Supplier<Boolean> onClick;

    // TODO Provide which tools are missing to display to the user
    public ConstructComponent(Gui gui, ICompendiumRecipe recipe, boolean hasIngredients, Supplier<Boolean> onClick) {
        super(gui, 94, 17, 30, 30);
        this.recipe = recipe;
        this.missingTools = new ArrayList<>();
        this.hasIngredients = hasIngredients;
        this.onClick = onClick;
    }

    public ConstructComponent(Gui gui, ICompendiumRecipe recipe, List<ConstructionTool> missingTools, boolean hasIngredients, Supplier<Boolean> onClick) {
        super(gui, 94, 17, 30, 30);
        this.recipe = recipe;
        this.missingTools = missingTools;
        this.hasIngredients = hasIngredients;
        this.onClick = onClick;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        if (this.recipe != null) {
            g.renderItemInGui(game, manager, this.getOutput(game), x + 5, y + 5, 2.0F, Colors.WHITE, true, false);
        }
    }

    @Override
    public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        if (this.isMouseOver(game)) {
            ItemInstance instance = this.getOutput(game);

            List<String> info = new ArrayList<>();
            instance.getItem().describeItem(manager, instance, info, Settings.KEY_ADVANCED_INFO.isDown(), false);
            if (this.onClick != null) {
                if (info.size() > 1) {
                    info.add("");
                }

                if (this.hasIngredients && this.missingTools.isEmpty()) {
                    info.add(manager.localize(ResourceName.intern("info.click_to_construct")));
                } else {
                    if (!this.hasIngredients) {
                        info.add(manager.localize(ResourceName.intern("info.missing_items")));
                    }

                }

                this.recipe.fillRecipeInfo(this.gui, game, manager, info, instance, this);

            }
            g.drawHoverInfoAtMouse(game, manager, true, 200, info);
        }
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y) {
        if (Settings.KEY_GUI_ACTION_1.isKey(button) && this.isMouseOver(game)) {
            if (this.onClick != null) {
                return this.onClick.get();
            }
        }
        return false;
    }

    protected ItemInstance getOutput(IGameInstance game) {
        List<ItemInstance> outputs = this.recipe.getOutputs();
        return outputs.get((game.getTotalTicks() / Constants.TARGET_TPS) % outputs.size());
    }

    @Override
    public boolean shouldDoFingerCursor(IGameInstance game) {
        return this.onClick != null;
    }

    @Override
    public ResourceName getName() {
        return ResourceName.intern("construct");
    }
}
