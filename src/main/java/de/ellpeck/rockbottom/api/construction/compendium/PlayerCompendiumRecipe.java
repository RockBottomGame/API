package de.ellpeck.rockbottom.api.construction.compendium;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.AbstractItemEntity;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
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
	 * @param player The player
	 * @param inputInventory the input inventory
	 * @param outputInventory the output inventory
	 * @param machine the machine
	 * @param recipeInputs the inputs as dictated from the recipe
     * @param ingredients the actual item instances provided from the inventory slots
	 * @param outputGetter the output
	 * @param skillReward the skill reward
	 * @return True if the construction should continue
	 */
	public boolean handleRecipe(AbstractPlayerEntity player, Inventory inputInventory, Inventory outputInventory, TileEntity machine, List<IUseInfo> recipeInputs, List<ItemInstance> ingredients, Function<List<ItemInstance>, List<ItemInstance>> outputGetter, float skillReward) {
		return true;
	}

	public void playerConstruct(AbstractPlayerEntity player, TileEntity machine, int amount) {
		Inventory inv = player.getInv();
		List<ItemInstance> remains = RockBottomAPI.getApiHandler().construct(player, inv, inv, this, machine, amount, this.getActualInputs(inv), null, items -> this.getActualOutputs(inv, inv, items), this.getSkillReward());
		for (ItemInstance instance : remains) {
			AbstractItemEntity.spawn(player.world, instance, player.getX(), player.getY(), 0F, 0F);
		}
	}

	public ResourceName getKnowledgeInformationName() {
		return this.infoName;
	}

	public boolean isKnowledge() {
		return this.isKnowledge;
	}

	@Override
	public boolean isKnown(AbstractPlayerEntity player) {
		return !isKnowledge || !player.world.isStoryMode() || player.getKnowledge().knowsRecipe(this);
	}

	public float getSkillReward() {
		return this.skillReward;
	}
}
