package de.ellpeck.rockbottom.api.construction.compendium;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.AbstractItemEntity;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.helper.InventoryHelper;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public abstract class PlayerCompendiumRecipe extends BasicCompendiumRecipe {

	protected final boolean isKnowledge;
	protected final float skillReward;

	public PlayerCompendiumRecipe(ResourceName name, boolean isKnowledge, float skillReward) {
		super(name);
		this.isKnowledge = isKnowledge;
		this.skillReward = skillReward;
	}

	public void playerConstruct(AbstractPlayerEntity player, TileEntity machine, int amount) {
		Inventory inv = player.getInv();
		List<ItemInstance> remains = RockBottomAPI.getApiHandler().construct(player, inv, inv, this, machine, amount, InventoryHelper.collectItems(inv), this.getSkillReward());
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
		return !this.isKnowledge || !player.world.isStoryMode() || player.getKnowledge().knowsRecipe(this);
	}

	public float getSkillReward() {
		return this.skillReward;
	}
}
