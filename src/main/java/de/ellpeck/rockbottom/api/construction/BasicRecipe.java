/*
 * This file ("BasicRecipe.java") is part of the RockBottomAPI by Ellpeck.
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
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api.construction;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BasicRecipe implements IRecipe {

    private final ResourceName name;
    private final ResourceName infoName;
    private final List<IUseInfo> inputs;
    private final List<ItemInstance> outputs;

    public BasicRecipe(ResourceName name, List<IUseInfo> inputs, List<ItemInstance> outputs) {
        this.name = name;
        this.infoName = name.addPrefix("recipe_");
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public BasicRecipe(ResourceName name, ItemInstance output, IUseInfo... inputs) {
        this(name, Arrays.asList(inputs), Collections.singletonList(output));
    }

    public BasicRecipe(ItemInstance output, IUseInfo... inputs) {
        this(output.getItem().getName(), output, inputs);
    }

    @Override
    public List<IUseInfo> getInputs() {
        return this.inputs;
    }

    @Override
    public List<ItemInstance> getOutputs() {
        return this.outputs;
    }

    @Override
    public boolean isKnown(AbstractEntityPlayer player) {
        return true;
    }

    @Override
    public ResourceName getName() {
        return this.name;
    }

    @Override
    public ResourceName getKnowledgeInformationName() {
        return this.infoName;
    }

    public BasicRecipe register(NameRegistry<BasicRecipe> registry) {
        registry.register(this.getName(), this);
        return this;
    }

    public BasicRecipe registerManual() {
        return this.register(RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES);
    }
}
