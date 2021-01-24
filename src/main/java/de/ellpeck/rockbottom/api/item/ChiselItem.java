package de.ellpeck.rockbottom.api.item;

import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ChiselItem extends ToolItem {

    public ChiselItem(ResourceName name, float miningSpeed, int durability, ToolProperty property, int level) {
        super(name, miningSpeed, durability, property, level);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractPlayerEntity player, ItemInstance instance) {
        if (layer != TileLayer.MAIN)
            return false;

        TileState state = world.getState(x, y);
        if (state.getTile().chisel(world, x, y, state, mouseX, mouseY)) {
            if (!world.isClient()) {
                this.takeDamage(instance, 1);
            }
            return true;
        }
        return false;
    }
}
