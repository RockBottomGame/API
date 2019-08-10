package de.ellpeck.rockbottom.api.construction;

import de.ellpeck.rockbottom.api.item.Item;

public class ConstructionTool {
    public final Item tool;
    public final int usage;

    public ConstructionTool(Item tool, int usage) {
        this.tool = tool;
        this.usage = usage;
    }
}
