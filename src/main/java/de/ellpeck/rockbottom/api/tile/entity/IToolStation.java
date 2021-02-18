package de.ellpeck.rockbottom.api.tile.entity;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.ConstructionTool;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolProperty;

import java.util.*;

/**
 * Implement this if you want your tile entity to contain and use construction tools similar to {@link de.ellpeck.rockbottom.api.GameContent.Tiles#CONSTRUCTION_TABLE} or {@link de.ellpeck.rockbottom.api.GameContent.Tiles#SMITHING_TABLE}
 */
public interface IToolStation {

    List<ItemInstance> getTools();

    default List<ConstructionTool> getMissingTools(List<ConstructionTool> requiredTools) {
        List<ConstructionTool> missingTools = new ArrayList<>();
        Set<ToolProperty> properties = new HashSet<>();
        for (ItemInstance inst : this.getTools()) {
            if (inst != null) {
                properties.addAll(inst.getItem().getToolProperties(inst).keySet());
            }
        }
        for (ConstructionTool tool : requiredTools) {
            if (!properties.contains(tool.type)) {
                missingTools.add(tool);
            }
        }
        return missingTools;
    }

    /**
     * Attempts to get the tool item from the tile given the required tool property.
     * @param tool The {@link ToolProperty} to get.
     * @return The item in the tile. Null if the item doesn't exist.
     */
	ItemInstance getTool(ToolProperty tool);

    /**
     * Attempts to insert the given {@link ItemInstance} into the station.
     * @param tool The item to try insert
     * @return The remaining item after insertion, or {@param tool} if it couldn't be inserted.
     */
    ItemInstance insertTool(ItemInstance tool);

    /**
     * Damages the specified tool in the table if it is found
     * @param tool Tool to be damaged
     */
    default void damageTool(ConstructionTool tool) {
        if (tool == null || tool.type == null) return;
        ItemInstance toolItem = this.getTool(tool.type);
        if (toolItem != null) {
            if (!RockBottomAPI.getNet().isClient()) {
                int itemSlot = this.getTileInventory().getItemIndex(toolItem);
                ItemInstance damagedTool = toolItem.getItem().takeDamage(toolItem, tool.usage);
                if (damagedTool == null || damagedTool.equals(toolItem)) {
                    this.getTileInventory().set(itemSlot, damagedTool);
                }
                this.getTileInventory().notifyChange(itemSlot);
            }
        }
    }

	IFilteredInventory getTileInventory();
}
