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
import de.ellpeck.rockbottom.api.construction.compendium.BasicCompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentConstruct;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConstructionRecipe extends BasicCompendiumRecipe {

    public static final ResourceName ID = ResourceName.intern("recipe");

    protected final ResourceName infoName;
    protected final List<IUseInfo> inputs;
    protected final List<ItemInstance> outputs;
    protected final Item tool;
    protected final float skillReward;

    public ConstructionRecipe(ResourceName name, Item tool, List<IUseInfo> inputs, List<ItemInstance> outputs, float skillReward) {
        super(name);
        this.infoName = name.addPrefix("recipe_");
        this.inputs = inputs;
        this.outputs = outputs;
        this.tool = tool;
        this.skillReward = skillReward;
    }

    public ConstructionRecipe(ResourceName name, Item tool, float skillReward, ItemInstance output, IUseInfo... inputs) {
        this(name, tool, Arrays.asList(inputs), Collections.singletonList(output), skillReward);
    }

    public ConstructionRecipe(Item tool, float skillReward, ItemInstance output, IUseInfo... inputs) {
        this(output.getItem().getName(), tool, skillReward, output, inputs);
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

    public Item getTool() {
        return tool;
    }

    public boolean usesTool() {
        return tool == null;
    }

    public float getSkillReward() {
        return this.skillReward;
    }

    public void playerConstruct(AbstractEntityPlayer player, int amount) {
        Inventory inv = player.getInv();
        List<ItemInstance> remains = RockBottomAPI.getApiHandler().construct(player, inv, inv, this, amount, this.getActualInputs(inv), items -> this.getActualOutputs(inv, inv, items), this.getSkillReward());
        for (ItemInstance instance : remains) {
            AbstractEntityItem.spawn(player.world, instance, player.getX(), player.getY(), 0F, 0F);
        }
    }

    @Override
    public ComponentConstruct getConstructButton(Gui gui, AbstractEntityPlayer player, boolean canConstruct) {
        return new ComponentConstruct(gui, this, canConstruct, () -> {
            RockBottomAPI.getInternalHooks().defaultConstruct(player, this);
            return true;
        });
    }

    public ConstructionRecipe registerManual() {
        if (tool != null) {
            RockBottomAPI.logger().warning("Registered manual recipe " + getName() + " with tool " + getTool() + "! This should be marked as a construction table recipe.");
        }
        Registries.MANUAL_CONSTRUCTION_RECIPES.register(this.getName(), this);
        return this;
    }

    public ConstructionRecipe registerConstructionTable() {
        if (tool == null) {
            RockBottomAPI.logger().warning("Registered construction table recipe " + getName() + " with no tool! This should be marked as a manual recipe.");
        }
        Registries.CONSTRUCTION_TABLE_RECIPES.register(this.getName(), this);
        return this;
    }

    public ResourceName getKnowledgeInformationName() {
        return this.infoName;
    }
}
