/*
 * This file ("Inventory.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.inventory;

import com.google.common.collect.ImmutableList;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.part.PartDataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inventory extends AbstractInventory {

    protected final ItemInstance[] slots;

    public Inventory(int slotAmount) {
        this.slots = new ItemInstance[slotAmount];
    }

    @Override
    public void set(int id, ItemInstance instance) {
        this.slots[id] = instance;
        this.notifyChange(id);
    }

    @Override
    public ItemInstance get(int id) {
        return this.slots[id];
    }

    @Override
    public int getSlotAmount() {
        return this.slots.length;
    }

    public void save(DataSet set) {
        List<PartDataSet> list = new ArrayList<>();
        for (ItemInstance slot : this.slots) {
            DataSet subset = new DataSet();
            if (slot != null) {
                slot.save(subset);
            }
            list.add(new PartDataSet(subset));
        }
        set.addList("items", list);
    }

    public void load(DataSet set) {
        List<PartDataSet> list = set.getList("items");
        for (int i = 0; i < list.size(); i++) {
            DataSet subset = list.get(i).get();
            if (!subset.isEmpty()) {
                this.slots[i] = ItemInstance.load(subset);
            } else {
                this.slots[i] = null;
            }
        }
    }

    @Override
    public void clear() {
        Arrays.fill(this.slots, null);
    }
}
