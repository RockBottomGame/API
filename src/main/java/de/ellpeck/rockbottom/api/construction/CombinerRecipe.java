/*
 * This file ("CombinerRecipe.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame>.
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

import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class CombinerRecipe{

    private final ResUseInfo inputOne;
    private final ResUseInfo inputTwo;
    private final ItemInstance output;
    private final int time;

    public CombinerRecipe(ItemInstance output, ResUseInfo inputOne, ResUseInfo inputTwo, int time){
        this.inputOne = inputOne;
        this.inputTwo = inputTwo;
        this.output = output;
        this.time = time;
    }

    public ResUseInfo getInputOne(){
        return this.inputOne;
    }

    public ResUseInfo getInputTwo(){
        return this.inputTwo;
    }

    public ItemInstance getOutput(){
        return this.output;
    }

    public int getTime(){
        return this.time;
    }
}
