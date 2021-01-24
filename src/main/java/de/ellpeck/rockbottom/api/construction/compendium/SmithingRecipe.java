package de.ellpeck.rockbottom.api.construction.compendium;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.ConstructionTool;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ConstructComponent;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IToolStation;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;
import java.util.function.Function;

public class SmithingRecipe extends PlayerCompendiumRecipe {

	public static final ResourceName ID = ResourceName.intern("smithing");

	private List<IUseInfo> inputs;
	private List<ItemInstance> outputs;
	private int usage;

	private ConstructionTool tool;

	public SmithingRecipe(ResourceName name, List<IUseInfo> inputs, List<ItemInstance> outputs, boolean isKnowledge, float skillReward) {
	    this(name, inputs, outputs, isKnowledge, skillReward, 1);
    }

	public SmithingRecipe(ResourceName name, List<IUseInfo> inputs, List<ItemInstance> outputs, boolean isKnowledge, float skillReward, int usage) {
		super(name, isKnowledge, skillReward);
		this.inputs = inputs;
		this.outputs = outputs;
		this.usage = usage;

		this.tool = new ConstructionTool(GameContent.ITEM_HAMMER, usage);
	}

	private boolean hasTools(TileEntity machine) {
		if (machine == null) return false;
        IToolStation station = (IToolStation) machine;
        return station.damageTool(this.tool, true);
    }

	@Override
	public ConstructComponent getConstructButton(Gui gui, AbstractPlayerEntity player, TileEntity machine, boolean canConstruct) {
		return new ConstructComponent(gui, this, hasTools(machine), canConstruct, machine == null ? null : () -> {
		    if (hasTools(machine) && canConstruct) {
                this.playerConstruct(player, machine, 1);
                return true;
            }
            return false;
		});
	}

	@Override
	public boolean handleRecipe(AbstractPlayerEntity player, Inventory inputInventory, Inventory outputInventory, TileEntity machine, List<IUseInfo> recipeInputs, List<ItemInstance> actualInputs, Function<List<ItemInstance>, List<ItemInstance>> outputGetter, float skillReward) {
        IToolStation station = (IToolStation) machine;
        if (machine.getTileInventory().get(station.getToolSlot(tool.tool)) != null) {
            return ((IToolStation) machine).damageTool(this.tool, true) && ((IToolStation) machine).damageTool(this.tool, false);
        }
        return false;
	}

	public SmithingRecipe register() {
		Registries.SMITHING_RECIPES.register(this.getName(), this);
		return this;
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
	public String toString() {
		return "SmithingRecipe{" +
				"infoName=" + infoName +
				", isKnowledge=" + isKnowledge +
				", recipeInputs=" + inputs +
				", outputs=" + outputs +
				", skillReward=" + skillReward +
				'}';
	}
}
