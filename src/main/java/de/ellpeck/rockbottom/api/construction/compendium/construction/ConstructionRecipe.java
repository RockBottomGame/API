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
import de.ellpeck.rockbottom.api.construction.compendium.BasicCompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentConstruct;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ConstructionRecipe extends BasicCompendiumRecipe {

    public static final ResourceName ID = ResourceName.intern("recipe");

    protected final ResourceName infoName;
    protected final List<IUseInfo> inputs;
    protected final List<ItemInstance> outputs;
    protected final List<ConstructionTool> tools;
    protected final float skillReward;

    public ConstructionRecipe(ResourceName name, List<ConstructionTool> tools, List<IUseInfo> inputs, List<ItemInstance> outputs, float skillReward) {
        super(name);
        this.infoName = name.addPrefix("recipe_");
        this.inputs = inputs;
        this.outputs = outputs;
        this.tools = tools;
        this.skillReward = skillReward;
    }

    public ConstructionRecipe(ResourceName name, List<ConstructionTool> tools, float skillReward, ItemInstance output, IUseInfo... inputs) {
        this(name, tools, Arrays.asList(inputs), Collections.singletonList(output), skillReward);
    }

    public ConstructionRecipe(List<ConstructionTool> tools, float skillReward, ItemInstance output, IUseInfo... inputs) {
        this(output.getItem().getName(), tools, skillReward, output, inputs);
    }

    public static ConstructionRecipe forName(ResourceName name) {
        return Registries.MANUAL_CONSTRUCTION_RECIPES.get(name);
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
    public boolean isKnown(AbstractEntityPlayer player) {
        return true;
    }

    public List<ConstructionTool> getTools() {
        return tools;
    }

    public boolean usesTools() {
        return tools != null && tools.size() > 0;
    }

    public float getSkillReward() {
        return this.skillReward;
    }

    public void playerConstruct(AbstractEntityPlayer player, TileEntity machine, int amount) {
        Inventory inv = player.getInv();
        List<ItemInstance> remains = RockBottomAPI.getApiHandler().construct(player, inv, inv, this, machine, amount, this.getActualInputs(inv), items -> this.getActualOutputs(inv, inv, items), this.getSkillReward());
        for (ItemInstance instance : remains) {
            AbstractEntityItem.spawn(player.world, instance, player.getX(), player.getY(), 0F, 0F);
        }
    }

    public boolean canUseTools(TileEntity machine) {
        if (usesTools()) {
            for (ConstructionTool tool : tools) {
                if (!RockBottomAPI.getInternalHooks().useConstructionTableTool(machine, tool, true)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ComponentConstruct getConstructButton(Gui gui, AbstractEntityPlayer player, TileEntity machine, boolean canConstruct) {

        return new ComponentConstruct(gui, this, canUseTools(machine), canConstruct, () -> {
            RockBottomAPI.getInternalHooks().defaultConstruct(player, this, machine);
            return true;
        });
    }

    @Override
    public boolean handleMachine(AbstractEntityPlayer player, Inventory inputInventory, Inventory outputInventory, TileEntity machine, int amount, List<IUseInfo> inputs, Function<List<ItemInstance>, List<ItemInstance>> outputGetter, float skillReward) {
        if (usesTools()) {
            if (!canUseTools(machine)) {
                return false;
            }
            for (ConstructionTool tool : tools) {
                RockBottomAPI.getInternalHooks().useConstructionTableTool(machine, tool, false);
            }
        }
        return true;
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

    public ResourceName getKnowledgeInformationName() {
        return this.infoName;
    }
}
