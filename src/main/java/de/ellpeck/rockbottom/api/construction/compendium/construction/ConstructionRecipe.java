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

package de.ellpeck.rockbottom.api.construction.compendium.construction;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.ConstructionTool;
import de.ellpeck.rockbottom.api.construction.compendium.PlayerCompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentConstruct;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IToolStation;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ConstructionRecipe extends PlayerCompendiumRecipe {

    public static final ResourceName ID = ResourceName.intern("recipe");

    protected final List<IUseInfo> inputs;
    protected final List<ItemInstance> outputs;
    protected final List<ConstructionTool> tools;
    protected final boolean manualOnly;

    public ConstructionRecipe(ResourceName name, List<ConstructionTool> tools, List<IUseInfo> inputs, List<ItemInstance> outputs, boolean manualOnly, boolean isKnowledge, float skillReward) {
        super(name, isKnowledge, skillReward);
        this.inputs = inputs;
        this.outputs = outputs;
        this.tools = tools;
        this.manualOnly = manualOnly;
    }

    public ConstructionRecipe(ResourceName name, List<ConstructionTool> tools, boolean isKnowledge, float skillReward, ItemInstance output, IUseInfo... inputs) {
        this(name, tools, Arrays.asList(inputs), Collections.singletonList(output), false, isKnowledge, skillReward);
    }

    public ConstructionRecipe(List<ConstructionTool> tools, boolean isKnowledge, float skillReward, ItemInstance output, IUseInfo... inputs) {
        this(output.getItem().getName(), tools, isKnowledge, skillReward, output, inputs);
    }

    public static ConstructionRecipe forName(ResourceName name) {
        return Registries.CONSTRUCTION_RECIPES.get(name);
    }

    @Override
    public List<IUseInfo> getInputs() {
        return this.inputs;
    }

    @Override
    public List<ItemInstance> getOutputs() {
        return this.outputs;
    }

    public List<ConstructionTool> getTools() {
        return tools;
    }

    public boolean usesTools() {
        return tools != null && tools.size() > 0;
    }

    public boolean canUseTools(IToolStation machine) {
        if (usesTools()) {
        	if (machine == null) return false;
            for (ConstructionTool tool : tools) {
                if (!machine.damageTool(tool, true)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ComponentConstruct getConstructButton(Gui gui, AbstractEntityPlayer player, TileEntity machine, boolean canConstruct) {
        return new ComponentConstruct(gui, this, canUseTools((IToolStation)machine), canConstruct, usesTools() && machine == null ? null : () -> {
            RockBottomAPI.getInternalHooks().defaultConstruct(player, this, machine);
            return true;
        });
    }

    @Override
    public boolean handleMachine(AbstractEntityPlayer player, Inventory inputInventory, Inventory outputInventory, TileEntity machine, int amount, List<IUseInfo> inputs, Function<List<ItemInstance>, List<ItemInstance>> outputGetter, float skillReward) {
        if (usesTools()) {
            if (!canUseTools((IToolStation)machine)) {
                return false;
            }
            for (ConstructionTool tool : tools) {
				((IToolStation)machine).damageTool(tool, false);
            }
        }
        return true;
    }

    public boolean showInConstructionTable() {
        return !this.manualOnly;
    }

    public ConstructionRecipe registerManual() {
        if (tools != null && tools.size() > 0) {
            RockBottomAPI.logger().warning("Registered manual recipe " + getName() + " with " + getTools().size() + "tools! This should be marked as a construction table recipe.");
        }
        Registries.MANUAL_CONSTRUCTION_RECIPES.register(this.getName(), this);
        return this;
    }

    public ConstructionRecipe registerConstructionTable() {
        if (tools == null || tools.isEmpty()) {
            RockBottomAPI.logger().warning("Registered construction table recipe " + getName() + " with no tools! This should be marked as a manual recipe.");
        }
        Registries.CONSTRUCTION_TABLE_RECIPES.register(this.getName(), this);
        return this;
    }
}
