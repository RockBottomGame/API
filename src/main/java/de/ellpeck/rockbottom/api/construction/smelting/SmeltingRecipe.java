/*
 * This file ("SmeltingRecipe.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.construction.smelting;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.compendium.BasicCompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Collections;
import java.util.List;

public class SmeltingRecipe extends BasicCompendiumRecipe {

    public static final ResourceName ID = ResourceName.intern("smelting");

    private final IUseInfo input;
    private final ItemInstance output;
    private final int time;

    public SmeltingRecipe(IUseInfo input, ItemInstance output, int time) {
        this(output.getItem().getName(), input, output, time);
    }

    public SmeltingRecipe(ResourceName name, IUseInfo input, ItemInstance output, int time) {
        super(name);
        this.input = input;
        this.output = output;
        this.time = time;
    }

    public static SmeltingRecipe forName(ResourceName name) {
    	return Registries.SMELTING_REGISTRY.get(name);
	}

    public static SmeltingRecipe forInput(ItemInstance input) {
        for (SmeltingRecipe recipe : Registries.SMELTING_REGISTRY.values()) {
            if (recipe.getInput().containsItem(input)) {
                return recipe;
            }
        }
        return null;
    }

    public IUseInfo getInput() {
        return this.input;
    }

    public ItemInstance getOutput() {
        return this.output;
    }

    public int getTime() {
        return this.time;
    }

    @Override
    public boolean isKnown(AbstractPlayerEntity player) {
        return true;
    }

    @Override
    public List<IUseInfo> getInputs() {
        return Collections.singletonList(this.getInput());
    }

    @Override
    public List<ItemInstance> getOutputs() {
        return Collections.singletonList(this.getOutput());
    }

    public SmeltingRecipe register() {
        Registries.SMELTING_REGISTRY.register(this.getName(), this);
        return this;
    }
}
