/*
 * This file ("MortarRecipe.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.helper.InventoryHelper;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public class MortarRecipe extends PlayerCompendiumRecipe {

    public static final ResourceName ID = ResourceName.intern("mortar");

    protected final List<IUseInfo> inputs;
    protected final List<ItemInstance> output;
    protected final int time;

    public MortarRecipe(ResourceName name, List<IUseInfo> inputs, List<ItemInstance> output, int time, boolean isKnowledge, float skillReward) {
        super(name, isKnowledge, skillReward);
        this.inputs = inputs;
        this.output = output;
        this.time = time;
    }

    public static MortarRecipe forName(ResourceName name) {
        return Registries.MORTAR_RECIPES.get(name);
    }

    public static MortarRecipe getRecipe(AbstractPlayerEntity player, IInventory inv, TileEntity mortar) {
        for (MortarRecipe recipe : Registries.MORTAR_RECIPES.values()) {
            if (recipe.canConstruct(player, inv, inv, mortar, InventoryHelper.collectItems(inv))) {
                return recipe;
            }
        }
        return null;
    }

    public int getTime() {
        return this.time;
    }

    // TODO: implement handleRecipe instead of this to make it consistent with other recipes
    public void construct(AbstractPlayerEntity player, IInventory inventory, TileEntity machine, int amount) {
		RockBottomAPI.getApiHandler().construct(player, inventory, inventory, this, machine, amount, InventoryHelper.collectItems(inventory), skillReward);
	}

	public MortarRecipe register() {
		Registries.MORTAR_RECIPES.register(this.getName(), this);
		return this;
	}

    @Override
    public List<IUseInfo> getInputs() {
        return this.inputs;
    }

    @Override
    public List<ItemInstance> getOutputs() {
        return this.output;
    }
}
