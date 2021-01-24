/*
 * This file ("Biome.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.world.gen.biome;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;
import java.util.Random;

public abstract class Biome {

    protected final ResourceName name;

    public Biome(ResourceName name) {
        this.name = name;
    }

    public abstract TileState getState(IWorld world, IChunk chunk, int x, int y, TileLayer layer, INoiseGen noise, int surfaceHeight);

    public abstract List<BiomeLevel> getGenerationLevels(IWorld world);

    public abstract int getWeight(IWorld world);

    public long getBiomeSeed(IWorld world) {
        return world.getSeed();
    }

    public ResourceName getName() {
        return this.name;
    }

    public Biome register() {
        Registries.BIOME_REGISTRY.register(this.getName(), this);
        return this;
    }

    public boolean hasGrasslandDecoration() {
        return false;
    }

    public float getFlowerChance() {
        return 0F;
    }

    public float getPebbleChance() {
        return 0F;
    }

    /**
     * Renders the background of the biome. Called during the sky rendering of the world.
     * Returning false will cause the default sky rendering to not happen.
     * @param game The game instance.
     * @param manager The asset manager.
     * @param g The renderer.
     * @param world The world.
     * @param player The client player.
     * @param width The width of the screen in the world {@link IRenderer#getWidthInWorld()}.
     * @param height The height of the screen in the world {@link IRenderer#getHeightInWorld()}.
     * @return false if you want the default sky rendering to not happen, true otherwise.
     */
    public boolean renderBackground(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, AbstractPlayerEntity player, double width, double height) {
        return true;
    }

    /**
     * Renders the foreground of the biome. This renders in front of all entities and tiles.
     * @param game The game instance.
     * @param manager The asset manager.
     * @param g The renderer.
     * @param world The world.
     * @param player The client player.
     * @param scale The world scale {@link IRenderer#getWorldScale()}.
     */
    public void renderForeground(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, AbstractPlayerEntity player, float scale) {

    }

    /**
     * The sky color to render the background with.
     * This is done before {@link Biome#renderBackground}.
     * @param defaultColor The color which would be rendered by default.
     * @return The new sky color to be renderer.
     */
    public int getSkyColor(int defaultColor) {
        return defaultColor;
    }

    public boolean canTreeGrow(IWorld world, IChunk chunk, int x, int y) {
        return false;
    }

    public TileState getFillerTile(IWorld world, IChunk chunk, int x, int y) {
        return GameContent.TILE_SOIL.getDefState();
    }

    public Biome getVariationToGenerate(IWorld world, int x, int y, int surfaceHeight, Random random) {
        return this;
    }

    public boolean hasUndergroundFeatures(IWorld world, IChunk chunk) {
        return false;
    }

    public float getLakeChance(IWorld world, IChunk chunk) {
        return 0.5F;
    }

    public boolean shouldGenerateInWorld(IWorld world) {
        return true;
    }
}
