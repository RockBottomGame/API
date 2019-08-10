/*
 * This file ("ComponentPolaroid.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.construction.compendium.ICompendiumRecipe;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;
import java.util.List;

public class ComponentPolaroid extends GuiComponent {
    public static final ResourceName DEFAULT_TEX = ResourceName.intern("gui.compendium.item_background");

    private final ResourceName tex;
    private final ResourceName texHighlighted;
    private final ResourceName texSelected;
    public final ICompendiumRecipe recipe;
    public final boolean canConstruct;
    public boolean isSelected;

    public ComponentPolaroid(Gui gui, ICompendiumRecipe recipe, boolean canConstruct, ResourceName tex, ResourceName texHighlighted, ResourceName texSelected) {
        super(gui, 0, 0, 18, 20);
        this.recipe = recipe;
        this.canConstruct = canConstruct;
        this.tex = tex;
        this.texHighlighted = texHighlighted;
        this.texSelected = texSelected;
    }

	public ComponentPolaroid(Gui gui, ICompendiumRecipe recipe, boolean canConstruct, ResourceName tex) {
		super(gui, 0, 0, 18, 20);
		this.recipe = recipe;
		this.canConstruct = canConstruct;
		this.tex = tex;
		this.texHighlighted = tex.addSuffix(".highlighted");
		this.texSelected = tex.addSuffix(".selected");
	}

    public ComponentPolaroid(Gui gui, ICompendiumRecipe recipe, boolean canConstruct) {
        this(gui, recipe, canConstruct, DEFAULT_TEX);
    }

    public static ComponentPolaroid getUnknown(Gui gui, ResourceName tex) {
    	return new ComponentPolaroid(gui, null, false, tex);
	}

    @Override
    public ResourceName getName() {
        return ResourceName.intern("polaroid");
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        ResourceName res = this.recipe != null && this.isSelected ? this.texSelected : (this.isMouseOverPrioritized(game) ? this.texHighlighted : this.tex);
        manager.getTexture(res).draw(x, y, this.width, this.height);

        if (this.recipe != null) {
            g.renderItemInGui(game, manager, this.getOutput(game), x + 4, y + 4, 1.0F, this.canConstruct ? Colors.WHITE : Colors.multiplyA(Colors.WHITE, 0.35F), false, false);
        } else {
            manager.getFont().drawString(x + 6, y + 5, "?", 0, 1, 0.5F, Colors.DARK_GRAY, Colors.NO_COLOR);
        }
    }

    @Override
    public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        if (this.isMouseOver(game)) {
            if (this.recipe != null) {
                ItemInstance instance = this.getOutput(game);
                List<String> info = new ArrayList<>();
                instance.getItem().describeItem(manager, instance, info, Settings.KEY_ADVANCED_INFO.isDown());
                g.drawHoverInfoAtMouse(game, manager, true, 200, info);
            } else {
                g.drawHoverInfoAtMouse(game, manager, false, 200, manager.localize(ResourceName.intern("info.unknown_recipe")));
            }
        }
    }

    protected ItemInstance getOutput(IGameInstance game) {
        List<ItemInstance> outputs = this.recipe.getOutputs();
        return outputs.get((game.getTotalTicks() / Constants.TARGET_TPS) % outputs.size());
    }

    @Override
    public boolean shouldDoFingerCursor(IGameInstance game) {
        return super.shouldDoFingerCursor(game) && this.recipe != null;
    }
}
