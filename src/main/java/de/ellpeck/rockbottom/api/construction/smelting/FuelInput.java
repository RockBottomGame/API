/*
 * This file ("FuelInput.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class FuelInput{

    private final ResourceName name;
    private final IUseInfo fuel;
    private final int fuelTime;

    public FuelInput(IUseInfo fuel, int fuelTime){
        this(fuel.getItems().get(0).getItem().getName(), fuel, fuelTime);
    }

    public FuelInput(ResourceName name, IUseInfo fuel, int fuelTime){
        this.name = name;
        this.fuel = fuel;
        this.fuelTime = fuelTime;
    }

    public IUseInfo getFuel(){
        return this.fuel;
    }

    public int getFuelTime(){
        return this.fuelTime;
    }

    public ResourceName getName(){
        return this.name;
    }

    public FuelInput register(){
        RockBottomAPI.FUEL_REGISTRY.register(this.getName(), this);
        return this;
    }

    public static int getFuelTime(ItemInstance instance){
        for(FuelInput input : RockBottomAPI.FUEL_REGISTRY.values()){
            if(input.getFuel().containsItem(instance)){
                return input.fuelTime;
            }
        }
        return 0;
    }
}
