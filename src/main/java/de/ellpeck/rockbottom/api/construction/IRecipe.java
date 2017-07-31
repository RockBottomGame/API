/*
 * This file ("IRecipe.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.construction.resource.ResourceUsageInfo;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.List;

public interface IRecipe{

    static boolean matchesInv(IRecipe recipe, IInventory inventory){
        for(ResourceUsageInfo info : recipe.getInputs()){
            if(!inventory.containsResource(info)){
                return false;
            }
        }
        return true;
    }

    List<ResourceUsageInfo> getInputs();

    List<ItemInstance> getOutputs();
}
