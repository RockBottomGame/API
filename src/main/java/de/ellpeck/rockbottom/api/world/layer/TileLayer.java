/*
 * This file ("TileLayer.java") is part of the RockBottomAPI by Ellpeck.
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
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api.world.layer;

import com.google.common.base.Preconditions;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TileLayer {

    public static final TileLayer MAIN = new TileLayer(ResourceName.intern("main"), 0).register();
    public static final TileLayer LIQUIDS = new TileLayer(ResourceName.intern("liquids"), -5).register();
    public static final TileLayer BACKGROUND = new TileLayer(ResourceName.intern("background"), -10).register();

    private static List<TileLayer> allLayers;
    private static List<TileLayer> layersByIntPrio;
    private static List<TileLayer> layersByRenderPrio;

    private final ResourceName name;
    private final int renderPriority;
    private final int interactionPriority;

    private int assignedIndex = -1;

    public TileLayer(ResourceName name, int priority) {
        this(name, priority, priority);
    }

    public TileLayer(ResourceName name, int renderPriority, int interactionPriority) {
        this.name = name;
        this.renderPriority = renderPriority;
        this.interactionPriority = interactionPriority;
    }

    @ApiInternal
    public static void init() {
        allLayers = makeList(Comparator.comparing(TileLayer::getName));
        for (int i = 0; i < allLayers.size(); i++) {
            allLayers.get(i).assignedIndex = i;
        }

        layersByIntPrio = makeList(Comparator.comparingInt(TileLayer::getInteractionPriority).reversed());
        layersByRenderPrio = makeList(Comparator.comparingInt(TileLayer::getRenderPriority).reversed());

        RockBottomAPI.logger().info("Sorting a total of " + allLayers.size() + " tile layers");
    }

    private static List<TileLayer> makeList(Comparator comparator) {
        List<TileLayer> list = new ArrayList<>(RockBottomAPI.TILE_LAYER_REGISTRY.values());
        list.sort(comparator);
        return Collections.unmodifiableList(list);
    }

    public static List<TileLayer> getAllLayers() {
        return allLayers;
    }

    public static List<TileLayer> getLayersByInteractionPrio() {
        return layersByIntPrio;
    }

    public static List<TileLayer> getLayersByRenderPrio() {
        return layersByRenderPrio;
    }

    public ResourceName getName() {
        return this.name;
    }

    public int getRenderPriority() {
        return this.renderPriority;
    }

    public int getInteractionPriority() {
        return this.interactionPriority;
    }

    public boolean canEditLayer(IGameInstance game, AbstractEntityPlayer player) {
        return Settings.KEY_BACKGROUND.isDown() ? this == BACKGROUND : (this == MAIN || this == LIQUIDS);
    }

    public float getRenderLightModifier() {
        return this == BACKGROUND ? 0.5F : 1F;
    }

    public boolean forceForegroundRender() {
        return false;
    }

    public boolean isVisible(IGameInstance game, AbstractEntityPlayer player, IChunk chunk, int x, int y, boolean isRenderingForeground) {
        return true;
    }

    public boolean canTileBeInLayer(IWorld world, int x, int y, Tile tile) {
        return tile.isAir() || (this == LIQUIDS) == tile.isLiquid();
    }

    public boolean canHoldTileEntities() {
        return this == MAIN || this == BACKGROUND;
    }

    public boolean canCollide(MovableWorldObject object) {
        return this == MAIN;
    }

    public TileLayer register() {
        RockBottomAPI.TILE_LAYER_REGISTRY.register(this.getName(), this);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof TileLayer) {
            TileLayer tileLayer = (TileLayer) o;
            return this.name.equals(tileLayer.name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return this.getName().toString();
    }

    public int index() {
        Preconditions.checkState(this.assignedIndex >= 0, "Cannot access layer index before layer list has been initialized!");
        return this.assignedIndex;
    }
}
