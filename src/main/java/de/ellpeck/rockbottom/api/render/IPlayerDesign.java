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
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IPlayerDesign{

    IResourceName EYES = RockBottomAPI.createInternalRes("player.base.eyes");

    List<IResourceName> BASE = Arrays.asList(
            RockBottomAPI.createInternalRes("player.base.male_skin_1"),
            RockBottomAPI.createInternalRes("player.base.male_skin_2"),
            RockBottomAPI.createInternalRes("player.base.male_skin_3"),
            RockBottomAPI.createInternalRes("player.base.male_skin_4"),
            RockBottomAPI.createInternalRes("player.base.male_skin_5")
    );

    List<IResourceName> SHIRT = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("player.shirt.1"),
            RockBottomAPI.createInternalRes("player.shirt.2"),
            RockBottomAPI.createInternalRes("player.shirt.3"),
            RockBottomAPI.createInternalRes("player.shirt.4"),
            null
    ));

    List<IResourceName> ARMS = Arrays.asList(
            RockBottomAPI.createInternalRes("player.arm.skin_1"),
            RockBottomAPI.createInternalRes("player.arm.skin_2"),
            RockBottomAPI.createInternalRes("player.arm.skin_3"),
            RockBottomAPI.createInternalRes("player.arm.skin_4"),
            RockBottomAPI.createInternalRes("player.arm.skin_5")
    );

    List<IResourceName> SLEEVES = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("player.sleeve.1"),
            RockBottomAPI.createInternalRes("player.sleeve.2"),
            RockBottomAPI.createInternalRes("player.sleeve.3"),
            RockBottomAPI.createInternalRes("player.sleeve.4"),
            null
    ));

    List<IResourceName> PANTS = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("player.pants.1"),
            RockBottomAPI.createInternalRes("player.pants.2"),
            null
    ));

    List<IResourceName> FOOTWEAR = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("player.footwear.boots"),
            RockBottomAPI.createInternalRes("player.footwear.flip_flops"),
            RockBottomAPI.createInternalRes("player.footwear.shoes_1"),
            RockBottomAPI.createInternalRes("player.footwear.shoes_2"),
            RockBottomAPI.createInternalRes("player.footwear.slippers"),
            null
    ));

    List<IResourceName> HAIR = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("player.hair.1"),
            RockBottomAPI.createInternalRes("player.hair.2"),
            RockBottomAPI.createInternalRes("player.hair.3"),
            null
    ));

    List<IResourceName> ACCESSORIES = new ArrayList<>(Arrays.asList(
            null,
            RockBottomAPI.createInternalRes("player.accessory.monocle_1"),
            RockBottomAPI.createInternalRes("player.accessory.monocle_2"),
            RockBottomAPI.createInternalRes("player.accessory.sunglasses_1"),
            RockBottomAPI.createInternalRes("player.accessory.sunglasses_2"),
            RockBottomAPI.createInternalRes("player.accessory.sunglasses_3"),
            RockBottomAPI.createInternalRes("player.accessory.glasses_1"),
            RockBottomAPI.createInternalRes("player.accessory.glasses_2")
    ));

    List<IResourceName> EYEBROWS = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("player.eyebrows.1"),
            RockBottomAPI.createInternalRes("player.eyebrows.2"),
            RockBottomAPI.createInternalRes("player.eyebrows.3"),
            RockBottomAPI.createInternalRes("player.eyebrows.4"),
            RockBottomAPI.createInternalRes("player.eyebrows.5"),
            RockBottomAPI.createInternalRes("player.eyebrows.6"),
            null
    ));

    List<IResourceName> MOUTH = new ArrayList<>(Arrays.asList(
            null,
            RockBottomAPI.createInternalRes("player.mouth.1"),
            RockBottomAPI.createInternalRes("player.mouth.2"),
            RockBottomAPI.createInternalRes("player.mouth.3"),
            RockBottomAPI.createInternalRes("player.mouth.4"),
            RockBottomAPI.createInternalRes("player.mouth.5"),
            RockBottomAPI.createInternalRes("player.mouth.6"),
            RockBottomAPI.createInternalRes("player.mouth.7"),
            RockBottomAPI.createInternalRes("player.mouth.8"),
            RockBottomAPI.createInternalRes("player.mouth.9"),
            RockBottomAPI.createInternalRes("player.mouth.10")
    ));

    void saveToFile();

    void loadFromFile();

    void save(DataSet set);

    void load(DataSet set);

    Color getFavoriteColor();

    void setFavoriteColor(Color color);

    String getName();

    void setName(String name);

    int getBase();

    Color getEyeColor();

    int getShirt();

    Color getShirtColor();

    int getSleeves();

    Color getSleevesColor();

    int getPants();

    Color getPantsColor();

    int getFootwear();

    Color getFootwearColor();

    int getHair();

    Color getHairColor();

    int getAccessory();

    int getEyebrows();

    int getMouth();

    Color getEyebrowsColor();

    void setBase(int base);

    void setEyeColor(Color eyeColor);

    void setShirt(int shirt);

    void setShirtColor(Color shirtColor);

    void setSleeves(int sleeves);

    void setSleevesColor(Color sleevesColor);

    void setPants(int pants);

    void setPantsColor(Color pantsColor);

    void setFootwear(int footwear);

    void setFootwearColor(Color footwearColor);

    void setHair(int hair);

    void setHairColor(Color hairColor);

    void setAccessory(int accessory);

    void setEyebrows(int eyebrows);

    void setMouth(int mouth);

    void setEyebrowsColor(Color eyebrowsColor);
}
