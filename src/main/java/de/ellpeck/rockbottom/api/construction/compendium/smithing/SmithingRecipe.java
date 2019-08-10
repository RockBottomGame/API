package de.ellpeck.rockbottom.api.construction.compendium.smithing;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.compendium.PlayerCompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public class SmithingRecipe extends PlayerCompendiumRecipe {

	private List<IUseInfo> inputs;
	private List<ItemInstance> outputs;
	private boolean isKnowledge;

	public SmithingRecipe(ResourceName name, List<IUseInfo> inputs, List<ItemInstance> outputs, boolean isKnowledge, float skillReward) {
		super(name, skillReward);
		this.isKnowledge = isKnowledge;
		this.inputs = inputs;
		this.outputs = outputs;
	}

	public SmithingRecipe register() {
		Registries.SMITHING_RECIPES.register(this.getName(), this);
		return this;
	}

	@Override
	public boolean isKnown(AbstractEntityPlayer player) {
		return !player.world.isStoryMode() || (this.isKnowledge /*&& player.getKnowledge().knowsRecipe(this)*/);
	}

	@Override
	public List<IUseInfo> getInputs() {
		return this.inputs;
	}

	@Override
	public List<ItemInstance> getOutputs() {
		return this.outputs;
	}
}
