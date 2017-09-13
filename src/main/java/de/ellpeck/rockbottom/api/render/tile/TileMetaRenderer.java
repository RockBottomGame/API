package de.ellpeck.rockbottom.api.render.tile;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.tex.Texture;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.TileMeta;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class TileMetaRenderer implements ITileRenderer<TileMeta>{

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, TileMeta tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light){
        this.getTexture(manager, tile, state.get(tile.metaProp)).drawWithLight(renderX, renderY, scale, scale, light);
    }

    @Override
    public void renderItem(IGameInstance game, IAssetManager manager, Graphics g, TileMeta tile, ItemInstance instance, float x, float y, float scale, Color filter){
        this.getTexture(manager, tile, instance.getMeta()).draw(x, y, scale, scale, filter);
    }

    @Override
    public Texture getParticleTexture(IGameInstance game, IAssetManager manager, Graphics g, TileMeta tile, TileState state){
        return this.getTexture(manager, tile, state.get(tile.metaProp));
    }

    private Texture getTexture(IAssetManager manager, TileMeta tile, int meta){
        if(meta >= 0 && meta < tile.subResourceNames.size()){
            return manager.getTexture(tile.subResourceNames.get(meta));
        }
        else{
            return manager.getMissingTexture();
        }
    }
}
