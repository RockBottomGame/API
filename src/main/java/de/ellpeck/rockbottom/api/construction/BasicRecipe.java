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
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BasicRecipe implements IRecipe{

    private final IResourceName name;
    private final List<IUseInfo> inputs;
    private final List<ItemInstance> outputs;

    public BasicRecipe(ItemInstance output, IUseInfo... inputs){
        this(output.getItem().getName(), output, inputs);
    }

    public BasicRecipe(IResourceName name, ItemInstance output, IUseInfo... inputs){
        this.name = name;
        this.inputs = Arrays.asList(inputs);
        this.outputs = Collections.singletonList(output);
    }

    @Override
    public List<IUseInfo> getInputs(){
        return this.inputs;
    }

    @Override
    public List<ItemInstance> getOutputs(){
        return this.outputs;
    }

    @Override
    public boolean isKnown(AbstractEntityPlayer player){
        return true;
    }

    @Override
    public boolean shouldDisplayIngredient(AbstractEntityPlayer player, IUseInfo info){
        return true;
    }

    @Override
    public boolean shouldDisplayOutput(AbstractEntityPlayer player, ItemInstance output){
        return true;
    }

    @Override
    public IResourceName getName(){
        return this.name;
    }

    public BasicRecipe registerManual(){
        return this.register(RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES);
    }

    public BasicRecipe register(NameRegistry<BasicRecipe> registry){
        RockBottomAPI.ALL_CONSTRUCTION_RECIPES.register(this.getName(), this);
        registry.register(this.getName(), this);
        return this;
    }
}
