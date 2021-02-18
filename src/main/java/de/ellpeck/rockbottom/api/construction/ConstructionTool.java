package de.ellpeck.rockbottom.api.construction;

import de.ellpeck.rockbottom.api.item.ToolProperty;

public class ConstructionTool {
    public final ToolProperty type;
    public final int usage;

    public ConstructionTool(ToolProperty type, int usage) {
        this.type = type;
        this.usage = usage;
    }
}
