/*
 * This file ("ItemContainer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui.container;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemContainer{

    public final IInventory[] containedInventories;
    public final AbstractEntityPlayer player;
    private final List<ContainerSlot> slots = new ArrayList<>();

    public ItemContainer(AbstractEntityPlayer player, IInventory... containedInventories){
        this.player = player;
        this.containedInventories = containedInventories;
    }

    public ContainerSlot getSlot(int id){
        return this.slots.get(id);
    }

    public int getSlotAmount(){
        return this.slots.size();
    }

    public void addSlot(ContainerSlot slot){
        for(IInventory inv : this.containedInventories){
            if(inv == slot.inventory){
                this.slots.add(slot);
                return;
            }
        }

        RockBottomAPI.logger().warning("Tried adding slot "+slot+" with inventory "+slot.inventory+" to container "+this+" that doesn't contain it!");
    }

    public int getIndexForInvSlot(IInventory inv, int id){
        for(int i = 0; i < this.slots.size(); i++){
            ContainerSlot slot = this.slots.get(i);
            if(slot.inventory == inv && slot.slot == id){
                return i;
            }
        }
        return -1;
    }

    protected void addSlotGrid(IInventory inventory, int start, int end, int xStart, int yStart, int width){
        int x = xStart;
        int y = yStart;
        for(int i = start; i < end; i++){
            this.addSlot(new ContainerSlot(inventory, i, x, y));

            x += 20;
            if((i+1)%width == 0){
                y += 20;
                x = xStart;
            }
        }
    }

    protected void addPlayerInventory(AbstractEntityPlayer player, int x, int y){
        this.addSlotGrid(player.getInv(), 0, 8, x, y, 8);
        this.addSlotGrid(player.getInv(), 8, player.getInv().getSlotAmount(), x, y+25, 8);
    }

    public void onOpened(){

    }

    public void onClosed(){

    }

    @ApiInternal
    public int getUnboundId(){
        return -1;
    }

    public abstract IResourceName getName();
}
