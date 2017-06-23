/*
 * This file ("PlayerInfo.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api.render;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.anim.Animation;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerDesign{

    public static final List<IResourceName> BASE = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("male_white")
    ));
    public static final List<IResourceName> SHIRT = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("basic")
    ));
    public static final List<IResourceName> ARMS = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("hanging_white")
    ));
    public static final List<IResourceName> SLEEVES = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("hanging_basic")
    ));
    public static final List<IResourceName> PANTS = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("null")
    ));
    public static final List<IResourceName> BOOTS = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("basic")
    ));
    public static final List<IResourceName> HAIR = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("null")
    ));
    public static final List<IResourceName> ACCESSORY = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("sunglasses")
    ));

    public static final List<List<IResourceName>> LAYERS = Arrays.asList(BASE, SHIRT, ARMS, SLEEVES, PANTS, BOOTS, HAIR, ACCESSORY);
    public static final List<String> LAYER_NAMES = Arrays.asList("base", "shirt", "arm", "sleeve", "pants", "boot", "hair", "accessory");

    public final int[] indices = new int[LAYERS.size()];
    public final Animation[] animations = new Animation[LAYERS.size()];

    public PlayerDesign(){
        this.setAllAnimations();
    }

    public void setAllAnimations(){
        for(int i = 0; i < LAYERS.size(); i++){
            this.setAnimation(i);
        }
    }

    public void setAnimation(int layer){
        IResourceName animName = LAYERS.get(layer).get(this.indices[layer]);
        String layerName = LAYER_NAMES.get(layer);

        IResourceName path = animName.addPrefix("player."+layerName+".");
        this.animations[layer] = RockBottomAPI.getGame().getAssetManager().getAnimation(path);
    }

    public void save(DataSet set){
        for(int i = 0; i < this.indices.length; i++){
            set.addInt("design_layer_"+i, i);
        }
    }

    public void load(DataSet set){
        for(int i = 0; i < this.indices.length; i++){
            int index = set.getInt("design_layer_"+i);

            if(index != this.indices[i]){
                this.indices[i] = index;
                this.setAnimation(i);
            }
        }
    }
}
