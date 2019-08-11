package de.ellpeck.rockbottom.api.construction.compendium;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;
import java.util.function.Function;

public abstract class PlayerCompendiumRecipe extends BasicCompendiumRecipe {

	protected final boolean isKnowledge;
	protected final float skillReward;

	public PlayerCompendiumRecipe(ResourceName name, boolean isKnowledge, float skillReward) {
		super(name);
		this.isKnowledge = isKnowledge;
		this.skillReward = skillReward;
	}

	/**
	 * Called during construction with the machine used to construct the recipe.
	 * Provides the same parameters as the ConstructEvent directly to the recipe.
	 * @return True if the construction should continue
	 */
	public boolean handleMachine(AbstractEntityPlayer player, Inventory inputInventory, Inventory outputInventory, TileEntity machine, int amount, List<IUseInfo> inputs, Function<List<ItemInstance>, List<ItemInstance>> outputGetter, float skillReward) {
		return true;
	}

	public void playerConstruct(AbstractEntityPlayer player, TileEntity machine, int amount) {
		Inventory inv = player.getInv();
		List<ItemInstance> remains = RockBottomAPI.getApiHandler().construct(player, inv, inv, this, machine, amount, this.getActualInputs(inv), items -> this.getActualOutputs(inv, inv, items), this.getSkillReward());
		for (ItemInstance instance : remains) {
			AbstractEntityItem.spawn(player.world, instance, player.getX(), player.getY(), 0F, 0F);
		}
	}


	public ResourceName getKnowledgeInformationName() {
		return this.infoName;
	}

	public boolean isKnowledge() {
		return this.isKnowledge;
	}

	@Override
	public boolean isKnown(AbstractEntityPlayer player) {
		return !isKnowledge || !player.world.isStoryMode() || player.getKnowledge().knowsRecipe(this);
	}

	public float getSkillReward() {
		return this.skillReward;
	}
}
