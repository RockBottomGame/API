package de.ellpeck.rockbottom.api.world.layer;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

public class TileLayer{

    public static final TileLayer MAIN = new TileLayer(RockBottomAPI.createInternalRes("main"), 0, (game, player) -> !Settings.KEY_BACKGROUND.isDown()).register();
    public static final TileLayer BACKGROUND = new TileLayer(RockBottomAPI.createInternalRes("background"), -10, (game, player) -> Settings.KEY_BACKGROUND.isDown()).register();

    private static List<TileLayer> allLayers;

    private final IResourceName name;
    private final int priority;
    private final BiFunction<IGameInstance, AbstractEntityPlayer, Boolean> canEditFunction;

    public TileLayer(IResourceName name, int priority, BiFunction<IGameInstance, AbstractEntityPlayer, Boolean> canEditFunction){
        this.name = name;
        this.priority = priority;
        this.canEditFunction = canEditFunction;
    }

    public IResourceName getName(){
        return this.name;
    }

    public int getPriority(){
        return this.priority;
    }

    public boolean canEditLayer(IGameInstance game, AbstractEntityPlayer player){
        return this.canEditFunction.apply(game, player);
    }

    public float getRenderLightModifier(){
        return this == BACKGROUND ? 0.5F : 1F;
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
            list.sort(Comparator.comparingInt(TileLayer:: getPriority).reversed());
            allLayers = Collections.unmodifiableList(list);
        }

        return allLayers;
    }
}
