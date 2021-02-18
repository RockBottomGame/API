/*
 * This file ("ConstructionRecipe.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.construction.compendium;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ConstructComponent;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConstructionRecipe extends PlayerCompendiumRecipe {

    public static final ResourceName ID = ResourceName.intern("recipe");

    public static ConstructionRecipe forName(ResourceName name) {
        return Registries.CONSTRUCTION_RECIPES.get(name);
    }

    protected final List<IUseInfo> inputs;
    protected final List<ItemInstance> outputs;
    protected final boolean manualOnly;

    public ConstructionRecipe(ResourceName name, List<IUseInfo> inputs, List<ItemInstance> outputs, boolean manualOnly, boolean isKnowledge, float skillReward) {
        super(name, isKnowledge, skillReward);
        this.inputs = inputs;
        this.outputs = outputs;
        this.manualOnly = manualOnly;
    }

    public ConstructionRecipe(ResourceName name, boolean isKnowledge, float skillReward, ItemInstance output, IUseInfo... inputs) {
        this(name, Arrays.asList(inputs), Collections.singletonList(output), false, isKnowledge, skillReward);
    }

    public ConstructionRecipe(boolean isKnowledge, float skillReward, ItemInstance output, IUseInfo... inputs) {
        this(output.getItem().getName(), isKnowledge, skillReward, output, inputs);
    }

    @Override
    public List<IUseInfo> getInputs() {
        return this.inputs;
    }

    @Override
    public List<ItemInstance> getOutputs() {
        return this.outputs;
    }

    @Override
    public ConstructComponent getConstructButton(Gui gui, AbstractPlayerEntity player, TileEntity machine, boolean canConstruct) {
        return new ConstructComponent(gui, this, canConstruct, () -> {
            RockBottomAPI.getApiHandler().defaultConstruct(player, this, machine);
            return true;
        });
    }

    public boolean showInConstructionTable() {
        return !this.manualOnly;
    }

    public ConstructionRecipe registerManual() {
        Registries.MANUAL_CONSTRUCTION_RECIPES.register(this.getName(), this);
        return this;
    }

    public ConstructionRecipe registerConstructionTable() {
        Registries.CONSTRUCTION_TABLE_RECIPES.register(this.getName(), this);
        return this;
    }

    @Override
    public String toString() {
        return "ConstructionRecipe{" +
                "infoName=" + this.infoName +
                ", isKnowledge=" + this.isKnowledge +
                ", inputs=" + this.inputs +
                ", outputs=" + this.outputs +
                ", skillReward=" + this.skillReward +
                '}';
    }
}
