/*
 * This file ("BasicRecipe.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api.construction;

import de.ellpeck.rockbottom.api.construction.IRecipe;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BasicRecipe implements IRecipe{

    private final List<ItemInstance> inputs;
    private final List<ItemInstance> outputs;

    public BasicRecipe(ItemInstance output, ItemInstance... inputs){
        this.inputs = Arrays.asList(inputs);
        this.outputs = Collections.singletonList(output);
    }

    @Override
    public List<ItemInstance> getInputs(){
        return this.inputs;
    }

    @Override
    public List<ItemInstance> getOutputs(){
        return this.outputs;
    }
}
