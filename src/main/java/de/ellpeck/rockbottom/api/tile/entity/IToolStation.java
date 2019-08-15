package de.ellpeck.rockbottom.api.tile.entity;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.ConstructionTool;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;

// TODO 0.4 Write description for modders on how to use this.
public interface IToolStation {

	/**
	 * Damages the specified tool in the table if it is found
	 * @param tool Tool to be damaged
	 * @param simulate Should we only check for the existence of the tool?
	 * @return True if the tool exists
	 */
	default boolean damageTool(ConstructionTool tool, boolean simulate) {
		if (tool == null || tool.tool == null) return true;
		int toolSlot = getToolSlot(tool.tool);
		ItemInstance toolItem;
		if (toolSlot != -1 && (toolItem = getTileInventory().get(toolSlot)) != null) {
			if (!simulate && !RockBottomAPI.getNet().isClient()) {
				toolItem.getItem().takeDamage(toolItem, tool.usage);
				getTileInventory().notifyChange(toolSlot);
			}
			return true;
		}
		return false;
	}

	int getToolSlot(Item tool);

	IFilteredInventory getTileInventory();
}
