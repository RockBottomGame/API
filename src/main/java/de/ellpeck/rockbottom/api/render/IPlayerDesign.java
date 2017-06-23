/*
 * This file ("IPlayerDesign.java") is part of the RockBottomAPI by Ellpeck.
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
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IPlayerDesign{

    List<IResourceName> BASE = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("male_white")
    ));
    List<IResourceName> SHIRT = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("basic")
    ));
    List<IResourceName> ARMS = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("hanging_white")
    ));
    List<IResourceName> SLEEVES = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("hanging_basic")
    ));
    List<IResourceName> PANTS = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("null")
    ));
    List<IResourceName> BOOTS = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("basic")
    ));
    List<IResourceName> HAIR = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("null")
    ));
    List<IResourceName> ACCESSORY = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("sunglasses")
    ));
    List<List<IResourceName>> LAYERS = Arrays.asList(BASE, SHIRT, ARMS, SLEEVES, PANTS, BOOTS, HAIR, ACCESSORY);
    List<String> LAYER_NAMES = Arrays.asList("base", "shirt", "arm", "sleeve", "pants", "boot", "hair", "accessory");

    void setAnimation(int layer);

    void save(DataSet set);

    void load(DataSet set);

    void toBuf(ByteBuf buf);

    void fromBuf(ByteBuf buf);

    int[] getIndices();

    Animation[] getAnimations();

    Color getColor();

    void setColor(Color color);

    String getName();

    void setName(String name);
}
