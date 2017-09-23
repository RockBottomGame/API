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

    public static final TileLayer MAIN = new TileLayer(RockBottomAPI.createInternalRes("main"), 0).register();
    public static final TileLayer BACKGROUND = new TileLayer(RockBottomAPI.createInternalRes("background"), -10).register();

    private static List<TileLayer> allLayers;

    private final IResourceName name;
    private final int renderPriority;

    private int assignedIndex = -1;

    public TileLayer(IResourceName name, int renderPriority){
        this.name = name;
        this.renderPriority = renderPriority;
    }

    public IResourceName getName(){
        return this.name;
    }

    public int getRenderPriority(){
        return this.renderPriority;
    }

    public boolean canEditLayer(IGameInstance game, AbstractEntityPlayer player){
        return Settings.KEY_BACKGROUND.isDown() ? this == BACKGROUND : this == MAIN;
    }

    public float getRenderLightModifier(){
        return this == BACKGROUND ? 0.5F : 1F;
    }

    public boolean forceForegroundRender(){
        return false;
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

    public int index(){
        if(this.assignedIndex >= 0){
            return this.assignedIndex;
        }
        else{
            throw new RuntimeException("Cannot access layer index before layer list has been initialized!");
        }
    }

    public static void initLayerList(){
        if(allLayers == null){
            List<TileLayer> list = new ArrayList<>(RockBottomAPI.TILE_LAYER_REGISTRY.getUnmodifiable().values());
            list.sort(Comparator.comparing(TileLayer:: getName));
            allLayers = Collections.unmodifiableList(list);

            for(int i = 0; i < allLayers.size(); i++){
                allLayers.get(i).assignedIndex = i;
            }

            RockBottomAPI.logger().info("Sorting a total of "+allLayers.size()+" tile layers");
        }
        else{
            throw new RuntimeException("Layer list already initialized!");
        }
    }

    public static List<TileLayer> getAllLayers(){
        if(allLayers != null){
            return allLayers;
        }
        else{
            throw new RuntimeException("Cannot access layer list before it has been initialized!");
        }
    }
}
