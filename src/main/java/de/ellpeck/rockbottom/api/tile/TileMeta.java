package de.ellpeck.rockbottom.api.tile;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.render.tile.TileMetaRenderer;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileMeta extends TileBasic{

    public final List<IResourceName> subResourceNames = new ArrayList<>();
    public final List<IResourceName> subUnlocNames = new ArrayList<>();
    public IntProp metaProp;

    public TileMeta(IResourceName name){
        super(name);
        this.addSubTile(name);
    }

    @Override
    protected ITileRenderer createRenderer(IResourceName name){
        return new TileMetaRenderer();
    }

    @Override
    public Tile register(){
        this.metaProp = new IntProp("meta", 0, Math.max(this.subUnlocNames.size(), this.subResourceNames.size()));
        this.addProps(this.metaProp);

        return super.register();
    }

    public TileMeta addSubTile(IResourceName name){
        this.subResourceNames.add(name.addPrefix("tiles."));
        this.subUnlocNames.add(name.addPrefix("item."));
        return this;
    }

    @Override
    protected ItemTile createItemTile(){
        return new ItemTile(this.getName()){
            @Override
            public IResourceName getUnlocalizedName(ItemInstance instance){
                int meta = instance.getMeta();

                if(meta >= 0 && TileMeta.this.subUnlocNames.size() > meta){
                    return TileMeta.this.subUnlocNames.get(meta);
                }
                else{
                    return super.getUnlocalizedName(instance);
                }
            }

            @Override
            public int getHighestPossibleMeta(){
                return Math.max(TileMeta.this.subUnlocNames.size(), TileMeta.this.subResourceNames.size())-1;
            }
        };
    }

    @Override
    public TileState getPlacementState(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer){
        return this.getDefState().prop(this.metaProp, instance.getMeta());
    }

    @Override
    public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer){
        return Collections.singletonList(new ItemInstance(this, 1, world.getState(layer, x, y).get(this.metaProp)));
    }
}
