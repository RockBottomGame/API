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

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.StaticTileProps;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.construction.compendium.PlayerCompendiumRecipe;
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
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.*;

public class Tile {

    public static final BoundBox DEFAULT_BOUNDS = new BoundBox(0, 0, 1, 1);

    public static final BoundBox TOP_LEFT = new BoundBox(0, 0.5d, 0.5d, 1);
    public static final BoundBox TOP_RIGHT = new BoundBox(0.5d, 0.5d, 1, 1);
    public static final BoundBox BOTTOM_LEFT = new BoundBox(0, 0, 0.5d, 0.5d);
    public static final BoundBox BOTTOM_RIGHT = new BoundBox(0.5d, 0, 1, 0.5d);

    public static final BoundBox[] CHISEL_BOUNDS = new BoundBox[]{
            TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    };

    private static final ResourceName SOUND_GENERIC_TILE = ResourceName.intern("tiles.generic_tile");
    private static final ResourceName LOC_ADVANCED = ResourceName.intern("info.advanced_info");
    private static final ResourceName LOC_LAYER = ResourceName.intern("info.layer_placement");

    protected final ResourceName name;
    private final IStateHandler stateHandler = RockBottomAPI.getInternalHooks().makeStateHandler(this);
    protected Map<ToolProperty, Integer> effectiveToolProps = new HashMap<>();
    protected boolean forceDrop;
    protected float hardness = 1F;
    protected boolean isChiselable;

    public Tile(ResourceName name) {
        this.name = name;
    }

    public ITileRenderer getRenderer() {
        return null;
    }

    public BoundBox getActualBoundBox(IWorld world, TileState state, int x, int y, TileLayer layer) {
    	BoundBox box = getBoundBox(world, state, x, y, layer);
    	if (box == null) {
    		box = BoundBox.NULL_BOUNDS;
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
        if (this.isPlatform())
            return this.getPlatformBounds(world, x, y, layer, state, object, objectBox, objectBoxMotion);
    
        if (layer == TileLayer.MAIN && this.isChiseled(world, x, y, layer, state))
            return getChiselBoundBoxes(world, x, y);

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

    public boolean isPlatform() {
        return false;
    }

    public List<BoundBox> getPlatformBounds(IWorld world, int x, int y, TileLayer layer, TileState state, MovableWorldObject object, BoundBox objectBox, BoundBox objectBoxMotion) {
        if (!(this instanceof MultiTile) || (state.get(((MultiTile) this).propSubY) == ((MultiTile) this).getHeight() - 1))
            return RockBottomAPI.getApiHandler().getDefaultPlatformBounds(world, x, y, layer, 1, 1, state, object, objectBox);
        return Collections.emptyList();
    }
  
    protected List<BoundBox> getChiselBoundBoxes(IWorld world, int x, int y) {
        List<BoundBox> boxes = new ArrayList<>();
        boolean[] chiseledCorners = Util.decodeBitVector(world.getState(x, y).get(StaticTileProps.CHISEL_STATE), 4);
        for (int i = 0; i < CHISEL_BOUNDS.length; i++) {
            if (!chiseledCorners[i]) {
                boxes.add(CHISEL_BOUNDS[i].copy().add(x, y));
            }
        }
        return boxes;
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

    /**
     * Attempts to chisel a corner of the tile based on the cursor position.
     * Only works if the tile is {@link Tile#isChiselable()}
     * @param world The {@link IWorld} the tile is in.
     * @param x The x position of the tile
     * @param y The y position of the tile
     * @param state The {@link TileState} of the tile
     * @param mouseX The x position of the cursor
     * @param mouseY The y position of the cursor
     * @return True if the tile has been chiseled, false otherwise.
     */
    public boolean chisel(IWorld world, int x, int y, TileState state, double mouseX, double mouseY) {
        if (!isChiselable())
            return false;

        mouseX -= x;
        mouseY -= y;

        int corner = (Util.floor((1-mouseY) * 2f) << 1) + Util.floor(mouseX * 2f);
        return chiselCorner(world, x, y, state, corner);
    }

    // Chisels a specific corner.
    // The corner param is representing the bit of the corner from StaticTileProps.CHISEL_STATE
    private boolean chiselCorner(IWorld world, int x, int y, TileState state, int corner) {
        int prop = state.get(StaticTileProps.CHISEL_STATE);
        boolean[] chiseledCorners = Util.decodeBitVector(prop, 4);

        if (chiseledCorners[corner])
            return false;

        prop += 1 << corner;
        if (!world.isClient()) {
            if (prop == 0b1111) {
                world.setState(x, y, GameContent.TILE_AIR.getDefState());
            } else {
                world.setState(x, y, state.prop(StaticTileProps.CHISEL_STATE, prop));
            }
        }
        return true;
    }

    public void onDestroyed(IWorld world, int x, int y, Entity destroyer, TileLayer layer, boolean shouldDrop) {
        List<ItemInstance> drops = new ArrayList<>();

        if (shouldDrop && !world.isClient()) {
            if (!(destroyer instanceof AbstractEntityPlayer && ((AbstractEntityPlayer) destroyer).getGameMode().isCreative()))
                drops.addAll(this.getDrops(world, x, y, layer, destroyer));
        }

        if (RockBottomAPI.getEventHandler().fireEvent(new TileDropsEvent(this, drops, world, x, y, layer, destroyer)) != EventResult.CANCELLED) {
            if (!drops.isEmpty()) {
                for (ItemInstance inst : drops) {
                    AbstractEntityItem.spawn(world, inst, x + 0.5, y + 0.5, Util.RANDOM.nextGaussian() * 0.1, Util.RANDOM.nextGaussian() * 0.1);
                }
            }
        }

        if (!world.isClient() && shouldDrop && destroyer instanceof AbstractEntityPlayer) {
            List<PlayerCompendiumRecipe> recipes = RockBottomAPI.getInternalHooks().getRecipesToLearnFrom(this);
            ((AbstractEntityPlayer)destroyer).getKnowledge().teachRecipes(recipes);
        }
    }

    public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer) {
        if (this.isChiseled(world, x, y, layer, world.getState(layer, x, y)))
            return Collections.emptyList();

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
        return this.isFullTile() && !this.isChiseled(world, x, y, layer, world.getState(layer, x, y));
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

    public boolean isChiselable() {
        return this.isChiselable;
    }

    /**
     * Call this to make your tile chiselable. Make sure you do this before you call the {@link Tile#register()} method.
     * @return The tile
     */
    public Tile setChiselable() {
        this.isChiselable = true;
        this.addProps(StaticTileProps.CHISEL_STATE);
        return this;
    }

    public boolean isChiseled(IWorld world, int x, int y, TileLayer layer, TileState state) {
        if (layer == TileLayer.MAIN && state.getTile().isChiselable()) {
            return state.get(StaticTileProps.CHISEL_STATE) > 0;
        }
        return false;
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

    public boolean hasProp(TileProp prop) {
        return this.getProps().contains(prop);
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

    public boolean canLiquidSpread(IWorld world, int x, int y, TileLiquid liquid, Direction dir) {
        if (!this.isChiseled(world, x, y, TileLayer.MAIN, world.getState(x, y)))
            return !this.isFullTile();

        TileState state = world.getState(x, y);
        int prop = state.get(StaticTileProps.CHISEL_STATE);
        boolean[] chiseledCorners = Util.decodeBitVector(prop, 4);

        switch (dir) {
            case UP: return chiseledCorners[0] || chiseledCorners[1];
            case DOWN: return chiseledCorners[2] || chiseledCorners[3];
            case LEFT: return chiseledCorners[0] || chiseledCorners[2];
            case RIGHT: return chiseledCorners[1] || chiseledCorners[3];
            case NONE: return true;
            default: return false;
        }
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
