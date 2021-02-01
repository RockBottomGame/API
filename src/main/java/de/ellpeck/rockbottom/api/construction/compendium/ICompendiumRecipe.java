/*
 * This file ("ICompendiumRecipe.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.construction.compendium;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.content.IContent;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ConstructComponent;
import de.ellpeck.rockbottom.api.gui.component.construction.IngredientComponent;
import de.ellpeck.rockbottom.api.gui.component.construction.PolaroidComponent;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;
import java.util.List;

public interface ICompendiumRecipe extends IContent {

    ResourceName getName();

    List<IUseInfo> getInputs();

    List<ItemInstance> getOutputs();

    default boolean isKnown(AbstractPlayerEntity player) {
        return true;
    }

    default boolean isKnowledge() {
        return false;
    }

    default boolean canConstruct(IInventory inputInventory, IInventory outputInventory) {
        return RockBottomAPI.getApiHandler().hasItems(inputInventory, this.getActualInputs(inputInventory), 1, null, null);
    }

    static ICompendiumRecipe forName(ResourceName name) {
    	return Registries.ALL_RECIPES.get(name);
	}

    default List<IUseInfo> getActualInputs(IInventory inventory) {
        return this.getInputs();
    }

    default List<ItemInstance> getActualOutputs(IInventory inputInventory, IInventory outputInventory, List<ItemInstance> inputs) {
        return this.getOutputs();
    }

    default List<IngredientComponent> getIngredientButtons(Gui gui, AbstractPlayerEntity player, ResourceName tex) {
        List<IngredientComponent> ingredients = new ArrayList<>();
		for (IUseInfo info : this.getInputs()) {
			ingredients.add(new IngredientComponent(gui, player.getInv().containsResource(info), info.getItems(), tex));
		}
		return ingredients;
    }

    default ConstructComponent getConstructButton(Gui gui, AbstractPlayerEntity player, TileEntity machine, boolean canConstruct) {
        return new ConstructComponent(gui, this, true, canConstruct, null);
    }

    default PolaroidComponent getPolaroidButton(Gui gui, AbstractPlayerEntity player, boolean canConstruct, ResourceName tex) {
        return new PolaroidComponent(gui, this, canConstruct, tex);
    }
}
