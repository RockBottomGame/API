/*
 * This file ("ContainerSlot.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui.container;

import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ContainerSlot {

    public final IInventory inventory;
    public final int slot;

    public final int x;
    public final int y;

    public ContainerSlot(IInventory inventory, int slot, int x, int y) {
        this.inventory = inventory;
        this.slot = slot;
        this.x = x;
        this.y = y;
    }

    public boolean canPlace(ItemInstance instance) {
        return true;
    }

    public boolean canRemove(int amount) {
        return true;
    }

    public void set(ItemInstance instance) {
        this.inventory.set(this.slot, instance);
    }

    public ItemInstance get() {
        return this.inventory.get(this.slot);
    }

    public ComponentSlot getGraphicalSlot(GuiContainer gui, int index, int xOffset, int yOffset) {
        return new ComponentSlot(gui, this, index, xOffset + this.x, yOffset + this.y);
    }
}
