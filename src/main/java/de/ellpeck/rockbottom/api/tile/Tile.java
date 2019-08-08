/*
 * This file ("Tile.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.tile;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.impl.TileDropsEvent;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.IStateHandler;
import de.ellpeck.rockbottom.api.tile.state.TileProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.*;

public class Tile {

    public static final BoundBox DEFAULT_BOUNDS = new BoundBox(0, 0, 1, 1);
    private static final ResourceName SOUND_GENERIC_TILE = ResourceName.intern("tiles.generic_tile");
    private static final ResourceName LOC_ADVANCED = ResourceName.intern("info.advanced_info");
    private static final ResourceName LOC_LAYER = ResourceName.intern("info.layer_placement");

    protected final ResourceName name;
    private final IStateHandler stateHandler = RockBottomAPI.getInternalHooks().makeStateHandler(this);
    protected Map<ToolProperty, Integer> effectiveToolProps = new HashMap<>();
    protected boolean forceDrop;
    protected float hardness = 1F;

    public Tile(ResourceName name) {
        this.name = name;
    }

    public ITileRenderer getRenderer() {
        return null;
    }

    public BoundBox getActualBoundBox(IWorld world, TileState state, int x, int y, TileLayer layer) {
    	BoundBox box = getBoundBox(world, state, x, y, layer);
    	if (box == null) {
    		box = new BoundBox();
		}
		return box;
	}

    public BoundBox getBoundBox(IWorld world, TileState state, int x, int y, TileLayer layer) {
		return DEFAULT_BOUNDS;
	}

    @Deprecated
    public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer) {
        return this.getBoundBox(world, world.getState(x, y), x, y, layer);
    }

	public List<BoundBox> getBoundBoxes(IWorld world, TileState state, int x, int y, TileLayer layer, MovableWorldObject object, BoundBox objectBox, BoundBox objectBoxMotion) {
		BoundBox box = this.getBoundBox(world, state, x, y, layer);

		if (box != null && !box.isEmpty()) {
			return Collections.singletonList(box.copy().add(x, y));
		} else {
			return Collections.emptyList();
		}
	}

	@Deprecated
    public List<BoundBox> getBoundBoxes(IWorld world, int x, int y, TileLayer layer, MovableWorldObject object, BoundBox objectBox, BoundBox objectBoxMotion) {
        BoundBox box = this.getBoundBox(world, x, y, layer);

        if (box != null && !box.isEmpty()) {
            return Collections.singletonList(box.copy().add(x, y));
        } else {
            return Collections.emptyList();
        }
    }

    public boolean canBreak(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player, boolean isRightTool) {
        return true;
    }

    public boolean canPlace(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player) {
        return true;
    }

    public boolean canPlaceInLayer(TileLayer layer) {
        return layer == TileLayer.MAIN || layer == TileLayer.BACKGROUND;
    }

    public Tile register() {
        Registries.TILE_REGISTRY.register(this.getName(), this);

        if (this.hasItem()) {
            this.createItemTile().register();
        }

        this.stateHandler.init();

        return this;
    }

    protected ItemTile createItemTile() {
        return new ItemTile(this.getName());
    }

    protected boolean hasItem() {
        return true;
    }

    public Item getItem() {
        if (this.hasItem()) {
            return Registries.ITEM_REGISTRY.get(this.getName());
        } else {
            return null;
        }
    }

    public void onRemoved(IWorld world, int x, int y, TileLayer layer) {
        if (!world.isClient() && this.canProvideTileEntity()) {
            TileEntity tile = world.getTileEntity(x, y);
            if (tile != null) {
                IFilteredInventory inv = tile.getTileInventory();
                if (inv != null) {
                    tile.dropInventory(inv);
                }
            }
        }
    }

    public void onAdded(IWorld world, int x, int y, TileLayer layer) {

    }

    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        return false;
    }

    public boolean onInteractWithBreakKey(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        return false;
    }

    public int getInteractionPriority(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        return 0;
    }

    public boolean canReplace(IWorld world, int x, int y, TileLayer layer) {
        return false;
    }

    public boolean doesSustainLeaves(IWorld world, int x, int y, TileLayer layer) {
        return false;
    }

    public void onDestroyed(IWorld world, int x, int y, Entity destroyer, TileLayer layer, boolean shouldDrop) {
        List<ItemInstance> drops = new ArrayList<>();

        if (shouldDrop && !world.isClient()) {
            drops.addAll(this.getDrops(world, x, y, layer, destroyer));
        }

        if (RockBottomAPI.getEventHandler().fireEvent(new TileDropsEvent(this, drops, world, x, y, layer, destroyer)) != EventResult.CANCELLED) {
            if (!drops.isEmpty()) {
                for (ItemInstance inst : drops) {
                    AbstractEntityItem.spawn(world, inst, x + 0.5, y + 0.5, Util.RANDOM.nextGaussian() * 0.1, Util.RANDOM.nextGaussian() * 0.1);
                }
            }
        }
    }

    public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer) {
        List<ItemInstance> drops = new ArrayList<>();

        Item item = this.getItem();
        if (item != null) {
            drops.add(new ItemInstance(item));
        }

        return drops;
    }

    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return null;
    }

    public boolean canProvideTileEntity() {
        return false;
    }

    public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer) {
        if (!world.isClient() && !this.canStay(world, x, y, layer, changedX, changedY, changedLayer)) {
            this.doBreak(world, x, y, layer, null, false, true);
        }
    }

    public boolean canStay(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer) {
        return true;
    }

    public boolean isFullTile() {
        return true;
    }

    public boolean obscuresBackground(IWorld world, int x, int y, TileLayer layer) {
        return this.isFullTile();
    }

    public void updateRandomly(IWorld world, int x, int y, TileLayer layer) {

    }

    public void updateRandomlyInPlayerView(IWorld world, int x, int y, TileLayer layer, TileState state, IParticleManager manager) {

    }

    public void doBreak(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer breaker, boolean isRightTool, boolean allowDrop) {
        if (!world.isClient()) {
            world.destroyTile(x, y, layer, breaker, allowDrop && (this.forceDrop || isRightTool));
        }
    }

    public void doPlace(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer) {
        if (!world.isClient()) {
            world.setState(layer, x, y, this.getPlacementState(world, x, y, layer, instance, placer));
        }
    }

    public TileState getPlacementState(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer) {
        return this.getDefState();
    }

    public float getHardness(IWorld world, int x, int y, TileLayer layer) {
        return this.hardness;
    }

    public Tile setHardness(float hardness) {
        this.hardness = hardness;
        return this;
    }

    public Tile setForceDrop() {
        this.forceDrop = true;
        return this;
    }

    public boolean isToolEffective(IWorld world, int x, int y, TileLayer layer, ToolProperty property, int level) {
        for (Map.Entry<ToolProperty, Integer> entry : this.effectiveToolProps.entrySet()) {
            if (entry.getKey() == property && level >= entry.getValue()) {
                return true;
            }
        }
        return false;
    }

    public Tile addEffectiveTool(ToolProperty property, int level) {
        this.effectiveToolProps.put(property, level);
        return this;
    }

    public int getLight(IWorld world, int x, int y, TileLayer layer) {
        return 0;
    }

    public float getTranslucentModifier(IWorld world, int x, int y, TileLayer layer, boolean skylight) {
        if (!this.isFullTile() && skylight) {
            return 1F;
        } else {
            return layer == TileLayer.BACKGROUND ? 0.9F : 0.8F;
        }
    }

    public boolean isAir() {
        return false;
    }

    public ResourceName getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.getName().toString();
    }

    public void onScheduledUpdate(IWorld world, int x, int y, TileLayer layer, int scheduledMeta) {

    }

    public boolean canClimb(IWorld world, int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes, Entity entity) {
        return false;
    }

    public void onCollideWithEntity(IWorld world, int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes, Entity entity) {

    }

    public void onIntersectWithEntity(IWorld world, int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes, Entity entity) {

    }

    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced) {
        for (TileLayer layer : TileLayer.getLayersByInteractionPrio()) {
            if (this.canPlaceInLayer(layer)) {
                if (isAdvanced) {
                    desc.add(FormattingCode.LIGHT_GRAY + manager.localize(LOC_LAYER, manager.localize(layer.getName().addPrefix("layer."))));
                } else {
                    desc.add(FormattingCode.GRAY + manager.localize(LOC_ADVANCED, Settings.KEY_ADVANCED_INFO.getDisplayName()));
                    break;
                }
            }
        }
    }

    public boolean canGrassSpreadTo(IWorld world, int x, int y, TileLayer layer) {
        return false;
    }

    public boolean canKeepPlants(IWorld world, int x, int y, TileLayer layer) {
        return false;
    }

    public boolean canKeepFarmablePlants(IWorld world, int x, int y, TileLayer layer) {
        return false;
    }

    public TileState getDefState() {
        return this.stateHandler.getDefault();
    }

    public Tile addProps(TileProp... props) {
        for (TileProp prop : props) {
            this.stateHandler.addProp(prop);
        }
        return this;
    }

    public List<TileProp> getProps() {
        return this.stateHandler.getProps();
    }

    public boolean hasState(ResourceName name, Map<String, Comparable> props) {
        return true;
    }

    public ResourceName getBreakSound(IWorld world, int x, int y, TileLayer layer, Entity destroyer) {
        return SOUND_GENERIC_TILE;
    }

    public ResourceName getPlaceSound(IWorld world, int x, int y, TileLayer layer, Entity placer, TileState state) {
        return SOUND_GENERIC_TILE;
    }

    public double getMaxInteractionDistance(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        return player.getRange();
    }

    public boolean hasSolidSurface(IWorld world, int x, int y, TileLayer layer) {
        return this.isFullTile();
    }

    public boolean canLiquidSpreadInto(IWorld world, int x, int y, TileLiquid liquid) {
        return !this.isFullTile();
    }

    public boolean isLiquid() {
        return false;
    }

    public boolean canSwim(IWorld world, int x, int y, TileLayer layer, Entity entity) {
        return false;
    }

    public Tile setMaxAmount(int amount) {
        Item item = this.getItem();
        if (item != null) {
            item.setMaxAmount(amount);
        }
        return this;
    }

    public boolean factorsIntoHeightMap(IWorld world, int x, int y, TileLayer layer) {
        return (this.isFullTile() || this.isLiquid()) && !this.isAir();
    }

    public boolean makesGrassSnowy(IWorld world, int x, int y, TileLayer layer) {
        return false;
    }
}
