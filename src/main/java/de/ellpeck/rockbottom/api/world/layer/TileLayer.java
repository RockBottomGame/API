package de.ellpeck.rockbottom.api.world.layer;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TileLayer{

    public static final TileLayer MAIN = new TileLayer(RockBottomAPI.createInternalRes("main")).register();
    public static final TileLayer BACKGROUND = new TileLayer(RockBottomAPI.createInternalRes("background")).register();

    private static List<TileLayer> allLayers;

    private final IResourceName name;

    public TileLayer(IResourceName name){
        this.name = name;
    }

    public IResourceName getName(){
        return this.name;
    }

    public TileLayer register(){
        RockBottomAPI.TILE_LAYER_REGISTRY.register(this.getName(), this);
        return this;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        else if(o instanceof TileLayer){
            TileLayer tileLayer = (TileLayer)o;
            return this.name.equals(tileLayer.name);
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }

    public int sessionIndex(){
        return getAllLayers().indexOf(this);
    }

    public static List<TileLayer> getAllLayers(){
        if(allLayers == null || allLayers.size() != RockBottomAPI.TILE_LAYER_REGISTRY.getSize()){
            List<TileLayer> list = new ArrayList<>(RockBottomAPI.TILE_LAYER_REGISTRY.getUnmodifiable().values());
            list.sort(Comparator.comparing(TileLayer::getName));
            allLayers = Collections.unmodifiableList(list);
        }

        return allLayers;
    }
}
