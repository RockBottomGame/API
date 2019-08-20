/*
 * This file ("Item.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame/>.
 * View information on the project at <https://rockbottom.ellpeck.de/>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 *
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.item;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Item {

    protected final ResourceName name;
    protected final ResourceName unlocName;

    protected int maxAmount = 999;

    public Item(ResourceName name) {
        this.name = name;
        this.unlocName = this.name.addPrefix("item.");
    }

    public IItemRenderer getRenderer() {
        return null;
    }

    public Item register() {
        Registries.ITEM_REGISTRY.register(this.getName(), this);
        return this;
    }

    public int getMaxAmount() {
        return this.maxAmount;
    }

    public Item setMaxAmount(int amount) {
        this.maxAmount = amount;
        return this;
    }

    public ResourceName getName() {
        return this.name;
    }

    public ResourceName getUnlocalizedName(ItemInstance instance) {
        return this.unlocName;
    }

    public String getLocalizedName(ItemInstance instance) {
        return RockBottomAPI.getGame().getAssetManager().localize(this.getUnlocalizedName(instance));
    }

    /**
     * Allows items to add to the tooltip
     * @param manager       The asset manager
     * @param instance      The item that is being described
     * @param desc          The item description. You can add to this list or replace it.
     * @param isAdvanced    If the player is holding SHIFT to view advanced info
     * @param isRealItem    Does the player actually own this item or is it a preview in a GUI?
     *                      Used by tools to display durability descriptors only on real items.
     */
    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced, boolean isRealItem) {
        desc.add(instance.getDisplayName());
    }

    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        return false;
    }

    public boolean onInteractWithDestKey(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        return false;
    }

    public int getInteractionPriority(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        return 100;
    }

    @Override
    public String toString() {
        return this.getName().toString();
    }

    public int getDespawnTime(ItemInstance instance) {
        return 24000;
    }

    /**
     * @param instance the item instance
     * @return if the instance data is sensitive
     *
     * @deprecated This method is now unused - items will always be data
     * sensitive
     */
    @Deprecated
    public boolean isDataSensitive(ItemInstance instance) {
        return false;
    }

    public Map<ToolProperty, Integer> getToolProperties(ItemInstance instance) {
        return Collections.emptyMap();
    }

    public boolean hasToolProperty(ItemInstance instance, ToolProperty property) {
        return this.getToolProperties(instance).containsKey(property);
    }

    public float getMiningSpeed(IWorld world, int x, int y, TileLayer layer, Tile tile, boolean isRightTool, ItemInstance instance) {
        return 1F;
    }

    public void onTileBroken(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player, Tile tile, ItemInstance instance) {

    }

    public int getHighestPossibleMeta() {
        return 0;
    }

    public double getMaxInteractionDistance(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        return player.getRange();
    }

    public boolean canHoldButtonToAttack(IWorld world, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        return false;
    }

    public List<Entity> getCustomAttackableEntities(IWorld world, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        return null;
    }

    public boolean onEntityAttack(IWorld world, double mouseX, double mouseY, AbstractEntityPlayer player, Entity entity, ItemInstance instance) {
        return true;
    }

    public int getAttackDamage(IWorld world, Entity entity, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        return 5;
    }

    public int getAttackCooldown(IWorld world, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        return 40;
    }

    public boolean attacksMultipleEntities(IWorld world, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
        return false;
    }

    public boolean useMetaAsDurability() {
        return false;
    }

    public void takeDamage(ItemInstance instance, AbstractEntityPlayer player, int amount) {
        if (this.useMetaAsDurability()) {
            ItemInstance left = this.takeDamage(instance, amount);
            player.getInv().set(player.getSelectedSlot(), left);
            if (left == null) {
                RockBottomAPI.getInternalHooks().onToolBroken(player.world, player, instance);
            }
        }
    }

    public ItemInstance takeDamage(ItemInstance instance, int amount) {
        if (this.useMetaAsDurability()) {
            int meta = instance.getMeta();
            if (meta + amount <= this.getHighestPossibleMeta()) {
                instance.setMeta(meta + amount);
                return instance;
            } else {
                return null;
            }
        } else {
            return instance;
        }
    }
}
