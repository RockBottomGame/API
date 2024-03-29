package de.ellpeck.rockbottom.api.render.tile;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.StaticTileProps;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.PlatformTile;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

@SuppressWarnings("unchecked")
public class PlatformTileRenderer extends DefaultTileRenderer<PlatformTile> {

    public PlatformTileRenderer(ResourceName texture) {
        super(texture);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer renderer, IWorld world, PlatformTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {

        if (state.get(StaticTileProps.HAS_LADDER)) {
            GameContent.Tiles.LADDER.getRenderer().render(game, manager, renderer, world, GameContent.Tiles.LADDER, GameContent.Tiles.LADDER.getDefState(), x, y, layer, renderX, renderY, scale, light);
        }
        ITexture texture = manager.getTexture(this.texture).getPositionalVariation(x, y);

        Tile left = GameContent.Tiles.AIR;
        Tile right = GameContent.Tiles.AIR;

        if (world.isPosLoaded(x - 1, y)) {
            left = world.getState(x - 1, y).getTile();
        }

        if (world.isPosLoaded(x + 1, y)) {
            right = world.getState(x + 1, y).getTile();
        }

        boolean leftFullTile = left.isFullTile();
        boolean rightFullTile = right.isFullTile();
        boolean leftPlatform = left instanceof PlatformTile;
        boolean rightPlatform = right instanceof PlatformTile;

        // The row and column to draw from the texture and whether to flip it in X.
        int row = 0;
        int col = 1;
        boolean flip = false;

        // I did nice visualisation of this.
        // |                is a full tile
        // .- OR - OR -.    is any  platform (left side and right side attached to full tile respectively, middle no full tile attachment)
        // .= OR = OR =.    is this platform (left side and right side attached to full tile respectively, middle no full tile attachment)

        if (leftFullTile && rightFullTile) { //  |.=.|
            col = 2;
        } else if (leftFullTile || rightFullTile) { //  |.=--  OR  --=.|
            col = 0;
            if (leftFullTile) {
                row = rightPlatform ? 1 : 0; //  |.=-- else |.=
            } else {
                row = leftPlatform ? 1 : 0;  //  --=.| else =.|
                flip = true;
            }
        } else if (!(leftPlatform && rightPlatform)) { //  |.--=  OR  =--.|  OR  =  NOT  |.---=---.|
            if (!leftPlatform && !rightPlatform) {  //  =
                row = 1;
                col = 2;
            } else {    //  |.--=  OR  =--.|
                row = 1;
                flip = rightPlatform;   //  rightPlatform ? =--.| else |.--=
            }
        }

        texture.draw(renderX + (flip ? scale : 0), renderY, renderX + (flip ? 0 : scale), renderY + scale, col * 12, row * 12, col * 12 + 12, row * 12 + 12, light);
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, IRenderer renderer, PlatformTile tile, ItemInstance instance, float x, float y, float scale, int filter, boolean mirrored) {
        manager.getTexture(this.texture).draw(x, y + scale/2, x + scale, y + scale + scale/2, 24, 0, 36, 12, filter);
    }
}
