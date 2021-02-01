package de.ellpeck.rockbottom.api.construction;

import de.ellpeck.rockbottom.api.item.Item;

public class ConstructionTool {
    public final Item item;
    public final int usage;

    public ConstructionTool(Item item, int usage) {
        this.item = item;
        this.usage = usage;
    }
}
