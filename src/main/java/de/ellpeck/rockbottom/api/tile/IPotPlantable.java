package de.ellpeck.rockbottom.api.tile;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.TileItem;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.IWorld;

public interface IPotPlantable {

    default float getRenderXOffset(IWorld world, TileState pot, int x, int y, ItemInstance item) {
        return 0;
    }

    float getRenderYOffset(IWorld world, TileState pot, int x, int y, ItemInstance item);

    default float getRenderScale() {
        return 0.75f;
    }

    static boolean isPotPlantable(ItemInstance item) {
        return item.getItem() instanceof IPotPlantable || item.getItem() instanceof TileItem && ((TileItem) item.getItem()).getTile() instanceof IPotPlantable;
    }

    static IPotPlantable getPlantable(ItemInstance item) {
        if (item.getItem() instanceof IPotPlantable) {
            return (IPotPlantable) item.getItem();
        } else if (item.getItem() instanceof TileItem && ((TileItem) item.getItem()).getTile() instanceof IPotPlantable) {
            return (IPotPlantable) ((TileItem) item.getItem()).getTile();
        }

        return null;
    }

}
