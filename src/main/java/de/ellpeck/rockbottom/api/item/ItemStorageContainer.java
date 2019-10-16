package de.ellpeck.rockbottom.api.item;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.gui.container.ImmovableSlot;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.gui.container.RestrictedSlot;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import org.lwjgl.glfw.GLFW;

public class ItemStorageContainer extends ItemBasic {

    protected final int maxStorage;
    protected final int containerWidth;

    public ItemStorageContainer(ResourceName name, int maxStorage) {
        this(name, maxStorage, maxStorage);
    }

    public ItemStorageContainer(ResourceName name, int maxStorage, int containerWidth) {
        super(name);
        this.setMaxAmount(1);
        this.maxStorage = maxStorage;
        this.containerWidth = containerWidth;
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        this.openStorageContainer(player, instance);
        return true;
    }

    @Override
    public double getMaxInteractionDistance(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        return Double.MAX_VALUE;
    }

    public int getMaxStorage() {
        return this.maxStorage;
    }

    public int getContainerWidth() {
        return containerWidth;
    }

    public int getContainerHeight() {
        return 1 + this.getMaxStorage() / this.getContainerWidth();
    }

    protected void openStorageContainer(AbstractEntityPlayer player, ItemInstance instance) {
        Inventory itemInventory = getItemInventory(instance);
        RockBottomAPI.getApiHandler().openExtendedPlayerInventory(
                player,
                itemInventory,
                this.getContainerWidth(),
                (inventory) -> setItemInventory(instance, (Inventory) inventory),
                ((inventory, slot, x, y) -> {
                    if (inventory == player.getInv() && slot == player.getSelectedSlot()) {
                        return new ImmovableSlot(inventory, slot, x, y);
                    }
                    if (inventory == itemInventory) {
                        return new RestrictedSlot(inventory, slot, x, y, inst -> !(inst.getItem() instanceof ItemStorageContainer));
                    }
                    return new ContainerSlot(inventory, slot, x, y);
                })
        );
    }

    /**
     * @param instance The item instance.
     * @return The inventory with the items from the instance,
     * or null if instance is not instance of {@link ItemStorageContainer}.
     */
    public static Inventory getItemInventory(ItemInstance instance) {
        if (!(instance.getItem() instanceof ItemStorageContainer)) {
            return null;
        }
        ItemStorageContainer item = (ItemStorageContainer) instance.getItem();
        Inventory inv = new Inventory(item.maxStorage);
        ModBasedDataSet itemData = instance.getAdditionalData();
        if (itemData != null) {
            DataSet invData = itemData.getDataSet(ResourceName.intern("inv"));
            if (!invData.isEmpty()) {
                inv.load(invData);
            }
        }
        return inv;
    }

    /**
     * @param instance  The item instance.
     * @param inventory The inventory to set
     * @return The inventory with the items from the instance,
     * or null if instance is not instance of {@link ItemStorageContainer}.
     */
    public static void setItemInventory(ItemInstance instance, Inventory inventory) {
        if (!(instance.getItem() instanceof ItemStorageContainer)) {
            return;
        }
        DataSet invData = new DataSet();
        inventory.save(invData);
        ModBasedDataSet itemData = instance.getOrCreateAdditionalData();
        itemData.addDataSet(ResourceName.intern("inv"), invData);
    }
}
