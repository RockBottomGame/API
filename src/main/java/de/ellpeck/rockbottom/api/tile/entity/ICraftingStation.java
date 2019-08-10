package de.ellpeck.rockbottom.api.tile.entity;

import de.ellpeck.rockbottom.api.construction.ConstructionTool;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public interface ICraftingStation {

	/**
	 * Damages the specified tool in the table if it is found
	 * @param tool Tool to be damaged
	 * @param simulate Should we only check for the existence of the tool?
	 * @return True if the tool exists
	 */
	default boolean damageTool(ConstructionTool tool, boolean simulate) {
		ItemInstance toolItem;
		if (tool != null && (toolItem = getTool(tool.tool)) != null) {
			if (!simulate) {
				toolItem.getItem().takeDamage(toolItem, tool.usage);
			}
			return true;
		}
		return tool == null || tool.tool == null;
	}

	ItemInstance getTool(Item tool);
}
