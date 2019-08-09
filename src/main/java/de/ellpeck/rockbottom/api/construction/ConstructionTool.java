package de.ellpeck.rockbottom.api.construction;

import de.ellpeck.rockbottom.api.item.Item;

public class ConstructionTool {
    public final Item tool;
    public final int durability;

    public ConstructionTool(Item tool, int durability) {
        this.tool = tool;
        this.durability = durability;
    }
}
