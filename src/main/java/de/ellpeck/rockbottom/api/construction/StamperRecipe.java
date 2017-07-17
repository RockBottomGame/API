/*
 * This file ("StamperRecipe.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.item.ItemInstance;

public class StamperRecipe{

    private final ItemInstance input;
    private final ItemInstance[] outputs;

    public StamperRecipe(ItemInstance input, ItemInstance... outputs){
        this.input = input;
        this.outputs = outputs;
    }

    public ItemInstance getInput(){
        return this.input;
    }

    public ItemInstance[] getOutputs(){
        return this.outputs;
    }
}
