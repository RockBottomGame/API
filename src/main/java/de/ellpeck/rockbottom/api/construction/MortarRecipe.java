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

package de.ellpeck.rockbottom.api.construction;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.content.IContent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class MortarRecipe implements IContent {

    public static final ResourceName ID = ResourceName.intern("mortar");

    private final ResourceName name;
    private final IUseInfo[] input;
    private final ItemInstance[] output;
    private final int time;

    public MortarRecipe(ResourceName name, IUseInfo[] input, ItemInstance[] output, int time) {
        this.name = name;
        this.input = input;
        this.output = output;
        this.time = time;
    }

    public static MortarRecipe forInput(ItemInstance[] inputs) {
        for (MortarRecipe recipe : Registries.MORTAR_REGISTRY.values()) {
            int found = 0;
            for (IUseInfo input : recipe.getInput()) {
                if (input != null) {
                    for (ItemInstance instance : inputs) {
                        if (instance != null && input.containsItem(instance)) {
                            found++;
                            break;
                        }
                    }
                }
            }
            if (found >= recipe.getInput().length) {
                return recipe;
            }
        }
        return null;
    }

    public IUseInfo[] getInput() {
        return this.input;
    }

    public ItemInstance[] getOutput() {
        return this.output;
    }

    public int getTime() {
        return this.time;
    }

    public ResourceName getName() {
        return this.name;
    }

    public MortarRecipe register() {
        Registries.MORTAR_REGISTRY.register(this.getName(), this);
        return this;
    }
}
