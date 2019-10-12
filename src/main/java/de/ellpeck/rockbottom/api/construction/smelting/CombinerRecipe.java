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
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CombinerRecipe extends BasicCompendiumRecipe {

    public static final ResourceName ID = ResourceName.intern("combiner");

    private final IUseInfo input1;
    private final IUseInfo input2;
    private final ItemInstance output;
    private final int time;

    public CombinerRecipe(IUseInfo input1, IUseInfo input2, ItemInstance output, int time) {
        this(output.getItem().getName(), input1, input2, output, time);
    }

    public CombinerRecipe(ResourceName name, IUseInfo input1, IUseInfo input2, ItemInstance output, int time) {
        super(name);
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.time = time;
    }

    public static CombinerRecipe forName(ResourceName name) {
    	return Registries.COMBINER_REGISTRY.get(name);
	}

    public static CombinerRecipe forInput(ItemInstance input) {
        for (CombinerRecipe recipe : Registries.COMBINER_REGISTRY.values()) {
            if (recipe.getInput1().containsItem(input) || recipe.getInput2().containsItem(input)) {
                return recipe;
            }
        }
        return null;
    }

    public static CombinerRecipe forInputs(ItemInstance input1, ItemInstance input2) {
        for (CombinerRecipe recipe : Registries.COMBINER_REGISTRY.values()) {
            IUseInfo i1 = recipe.getInput1();
            IUseInfo i2 = recipe.getInput2();
            if (i1.containsItem(input1) && i2.containsItem(input2) ||
                    i1.containsItem(input2) && i2.containsItem(input1)) {
                return recipe;
            }
        }
        return null;
    }

    public IUseInfo getInput1() {
        return input1;
    }

    public IUseInfo getInput2() {
        return input2;
    }

    public ItemInstance getOutput() {
        return this.output;
    }

    public int getTime() {
        return this.time;
    }

    @Override
    public boolean isKnown(AbstractEntityPlayer player) {
        return true;
    }

    @Override
    public List<IUseInfo> getInputs() {
        return Arrays.asList(this.getInput1(), this.getInput2());
    }

    @Override
    public List<ItemInstance> getOutputs() {
        return Collections.singletonList(this.getOutput());
    }

    public CombinerRecipe register() {
        Registries.COMBINER_REGISTRY.register(this.getName(), this);
        return this;
    }
}
