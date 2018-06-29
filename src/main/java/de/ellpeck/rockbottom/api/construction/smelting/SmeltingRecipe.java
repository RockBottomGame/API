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

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.content.IContent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class SmeltingRecipe implements IContent {

    public static final ResourceName ID = ResourceName.intern("smelting");

    private final ResourceName name;
    private final IUseInfo input;
    private final ItemInstance output;
    private final int time;

    public SmeltingRecipe(IUseInfo input, ItemInstance output, int time) {
        this(output.getItem().getName(), input, output, time);
    }

    public SmeltingRecipe(ResourceName name, IUseInfo input, ItemInstance output, int time) {
        this.name = name;
        this.input = input;
        this.output = output;
        this.time = time;
    }

    public static SmeltingRecipe forInput(ItemInstance input) {
        for (SmeltingRecipe recipe : RockBottomAPI.SMELTING_REGISTRY.values()) {
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

    public ResourceName getName() {
        return this.name;
    }

    public SmeltingRecipe register() {
        RockBottomAPI.SMELTING_REGISTRY.register(this.getName(), this);
        return this;
    }
}
