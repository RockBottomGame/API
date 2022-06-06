/*
 * This file ("DefaultTileRenderer.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.render.tile;

import com.google.gson.JsonElement;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.StaticTileProps;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundingBox;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import static de.ellpeck.rockbottom.api.assets.texture.ITexture.*;

public class DefaultTileRenderer<T extends Tile> implements ITileRenderer<T> {

    public ResourceName texture;
    public ResourceName itemTexture;

    public DefaultTileRenderer(ResourceName texture) {
        this(texture, false);
    }

    public DefaultTileRenderer(ResourceName texture, boolean hasCustomItemTexture) {
        this.texture = texture.addPrefix("tiles.");
        this.itemTexture = hasCustomItemTexture ? this.texture.addSuffix(".item") : this.texture;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer renderer, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        if (!tile.isChiseled(world, x, y, layer, state))
            manager.getTexture(this.getTextureForState(world, layer, x, y, state)).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);

    }

    @Override
    public void renderInForeground(IGameInstance game, IAssetManager manager, IRenderer renderer, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        if (tile.isChiseled(world, x, y, layer, state))
            this.renderChiseled(game, manager, renderer, world, tile, state, x, y, layer, renderX, renderY, scale, light);
        else if (tile.isChiselable() && layer == TileLayer.MAIN)
            this.renderChiselHighlight(game, renderer, null, x, y, renderX, renderY, scale);

    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IRenderer renderer, T tile, ItemInstance instance, float x, float y, float scale, int filter, boolean mirrored) {
        manager.getTexture(this.getTextureForItem(tile, instance)).draw(x, y, scale, scale, filter);
    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer renderer, T tile, TileState state) {
        return manager.getTexture(this.getTextureForState(null, TileLayer.MAIN, 0, 0, state));
    }

    @Override
    public void renderInMainMenuBackground(IGameInstance game, IAssetManager manager, IRenderer renderer, T tile, TileState state, float x, float y, float scale) {
        manager.getTexture(this.getTextureForState(null, TileLayer.MAIN, (int) x, (int) y, state)).getPositionalVariation((int) x, (int) y).draw(x, y, scale, scale);
    }

    protected void renderChiseled(IGameInstance game, IAssetManager manager, IRenderer renderer, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {

        ITexture texture = manager.getTexture(this.getTextureForState(world, layer, x, y, state)).getPositionalVariation(x, y);
        int prop = state.get(StaticTileProps.CHISEL_STATE);

        boolean[] chiseledCorners = Util.decodeBitVector(prop, 4);

        for (int i = 0; i < Tile.CHISEL_BOUNDS.length; i++) {
            if (!chiseledCorners[i]) {
                BoundingBox box = Tile.CHISEL_BOUNDS[i];
                float minX = (float) box.getMinX();
                float minY = (float) box.getMinY();
                float maxX = (float) box.getMaxX();
                float maxY = (float) box.getMaxY();

                int[] newLights = this.getChiseledLight(light, i);

                texture.draw(renderX + minX * scale, renderY + (1 - minY) * scale, renderX + maxX * scale, renderY + (1 - maxY) * scale, minX * 12, (1 - minY) * 12, maxX * 12, (1 - maxY) * 12, newLights);
                this.renderChiselHighlight(game, renderer, box, x, y, renderX, renderY, scale);
            }
        }
    }

    protected void renderChiselHighlight(IGameInstance game, IRenderer renderer, BoundingBox box, int x, int y, float renderX, float renderY, float scale) {
        AbstractPlayerEntity player = game.getPlayer();
        ItemInstance held = game.getPlayer().getInv().get(game.getPlayer().getSelectedSlot());
        if (held == null) {
            return;
        }

        int tileX = Util.floor(renderer.getMousedTileX());
        int tileY = Util.floor(renderer.getMousedTileY());
        if (!player.isInRange(tileX, tileY, player.getMaxInteractionDistance(player.world, renderer.getMousedTileX(), renderer.getMousedTileY(), player)))
            return;

        double mouseX = renderer.getMousedTileX() - x;
        double mouseY = renderer.getMousedTileY() - y;

        if (mouseX < 0 || mouseX >= 1 || mouseY < 0 || mouseY >= 1)
            return;

        if (box == null) {
            int corner = (Util.floor((1-mouseY) * 2f) << 1) + Util.floor(mouseX * 2f);
            box = Tile.CHISEL_BOUNDS[corner];
        }

        double tileMouseX = renderer.getMousedTileX();
        double tileMouseY = renderer.getMousedTileY();

        float minX = (float) box.getMinX();
        float minY = (float) box.getMinY();

        if (held.getItem().hasToolProperty(held, ToolProperty.CHISEL) && box.copy().add(x, y).contains(tileMouseX, tileMouseY)) {
            renderer.addEmptyRect(renderX + minX * scale, renderY + (0.5f - minY) * scale, 0.5f * scale, 0.5f * scale, Colors.WHITE);
        }
    }

    @Override
    public JsonElement getAdditionalTextureData(IGameInstance game, IAssetManager manager, IRenderer renderer, T tile, ItemInstance instance, AbstractPlayerEntity player, String name) {
        return manager.getTexture(this.getTextureForItem(tile, instance)).getAdditionalData(name);
    }

    public ResourceName getTextureForState(IWorld world, TileLayer layer, int x, int y, TileState state) {
        return this.texture;
    }

    public ResourceName getTextureForItem(T tile, ItemInstance item) {
        return this.itemTexture;
    }

    protected int[] getChiseledLight(int[] light, int corner) {
        if (corner > 3)
            return light;
        int[] newLight = new int[4];
        int centerLight = Colors.lerp(Colors.lerp(light[TOP_LEFT], light[TOP_RIGHT], 0.5f), Colors.lerp(light[BOTTOM_LEFT], light[BOTTOM_RIGHT], 0.5f), 0.5f);
        int leftLight = Colors.lerp(light[TOP_LEFT], light[BOTTOM_LEFT], 0.5f);
        int rightLight = Colors.lerp(light[BOTTOM_RIGHT], light[TOP_RIGHT], 0.5f);
        int topLight = Colors.lerp(light[TOP_LEFT], light[TOP_RIGHT], 0.5f);
        int bottomLight = Colors.lerp(light[BOTTOM_LEFT], light[BOTTOM_RIGHT], 0.5f);
        if (corner == 0) {
            newLight[TOP_LEFT] = light[TOP_LEFT];
            newLight[TOP_RIGHT] = topLight;
            newLight[BOTTOM_LEFT] = leftLight;
            newLight[BOTTOM_RIGHT] = centerLight;
        } else if (corner == 1) {
            newLight[TOP_LEFT] = topLight;
            newLight[TOP_RIGHT] = light[TOP_RIGHT];
            newLight[BOTTOM_LEFT] = centerLight;
            newLight[BOTTOM_RIGHT] = rightLight;
        } else if (corner == 2) {
            newLight[TOP_LEFT] = leftLight;
            newLight[TOP_RIGHT] = centerLight;
            newLight[BOTTOM_LEFT] = light[BOTTOM_LEFT];
            newLight[BOTTOM_RIGHT] = bottomLight;
        } else if (corner == 3) {
            newLight[TOP_LEFT] = centerLight;
            newLight[TOP_RIGHT] = rightLight;
            newLight[BOTTOM_LEFT] = bottomLight;
            newLight[BOTTOM_RIGHT] = light[BOTTOM_RIGHT];
        }

        return newLight;
    }
}
