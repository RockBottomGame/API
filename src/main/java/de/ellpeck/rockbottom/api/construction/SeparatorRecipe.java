/*
 * This file ("SeparatorRecipe.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.construction.resource.IResUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class SeparatorRecipe{

    private final IResUseInfo input;
    private final ItemInstance output;
    private final ItemInstance byproduct;
    private final int time;
    private final float byproductChance;

    public SeparatorRecipe(ItemInstance output, IResUseInfo input, int time, ItemInstance byproduct, float byproductChance){
        this.input = input;
        this.output = output;
        this.byproduct = byproduct;
        this.time = time;
        this.byproductChance = byproductChance;

        if(this.byproductChance <= 0F || this.byproductChance > 1F){
            throw new IllegalArgumentException("Byproduct chance of separator recipe "+this+" is out of bounds: "+this.byproductChance+", should be between 0 and 1");
        }
    }

    public IResUseInfo getInput(){
        return this.input;
    }

    public ItemInstance getOutput(){
        return this.output;
    }

    public ItemInstance getByproduct(){
        return this.byproduct;
    }

    public int getTime(){
        return this.time;
    }

    public float getByproductChance(){
        return this.byproductChance;
    }
}
