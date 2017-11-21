/*
 * This file ("IPlayerDesign.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.render;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IPlayerDesign{

    @ApiInternal
    IResourceName EYES = RockBottomAPI.createInternalRes("player.base.eyes");

    @ApiInternal
    List<IResourceName> BASE = Arrays.asList(
            RockBottomAPI.createInternalRes("player.base.1"),
            RockBottomAPI.createInternalRes("player.base.2"),
            RockBottomAPI.createInternalRes("player.base.3"),
            RockBottomAPI.createInternalRes("player.base.4"),
            RockBottomAPI.createInternalRes("player.base.5")
    );

    List<IResourceName> SHIRT = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("player.shirt.1"),
            RockBottomAPI.createInternalRes("player.shirt.2"),
            RockBottomAPI.createInternalRes("player.shirt.3"),
            RockBottomAPI.createInternalRes("player.shirt.4"),
            null
    ));

    @ApiInternal
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
            RockBottomAPI.createInternalRes("player.footwear.1"),
            RockBottomAPI.createInternalRes("player.footwear.2"),
            RockBottomAPI.createInternalRes("player.footwear.3"),
            RockBottomAPI.createInternalRes("player.footwear.4"),
            RockBottomAPI.createInternalRes("player.footwear.5"),
            null
    ));

    List<IResourceName> HAIR = new ArrayList<>(Arrays.asList(
            RockBottomAPI.createInternalRes("player.hair.1"),
            RockBottomAPI.createInternalRes("player.hair.2"),
            RockBottomAPI.createInternalRes("player.hair.3"),
            RockBottomAPI.createInternalRes("player.hair.4"),
            RockBottomAPI.createInternalRes("player.hair.5"),
            RockBottomAPI.createInternalRes("player.hair.6"),
            RockBottomAPI.createInternalRes("player.hair.7"),
            RockBottomAPI.createInternalRes("player.hair.8"),
            RockBottomAPI.createInternalRes("player.hair.9"),
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
            RockBottomAPI.createInternalRes("player.accessory.glasses_2"),
            RockBottomAPI.createInternalRes("player.accessory.glasses_3"),
            RockBottomAPI.createInternalRes("player.accessory.eyeliner")
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

    List<IResourceName> BEARD = new ArrayList<>(Arrays.asList(
            null,
            RockBottomAPI.createInternalRes("player.beard.1"),
            RockBottomAPI.createInternalRes("player.beard.2")
    ));

    int getFavoriteColor();

    @ApiInternal
    void setFavoriteColor(int color);

    String getName();

    @ApiInternal
    void setName(String name);

    int getBase();

    @ApiInternal
    void setBase(int base);

    int getEyeColor();

    @ApiInternal
    void setEyeColor(int eyeColor);

    int getShirt();

    @ApiInternal
    void setShirt(int shirt);

    int getShirtColor();

    @ApiInternal
    void setShirtColor(int shirtColor);

    int getSleeves();

    @ApiInternal
    void setSleeves(int sleeves);

    int getSleevesColor();

    @ApiInternal
    void setSleevesColor(int sleevesColor);

    int getPants();

    @ApiInternal
    void setPants(int pants);

    int getPantsColor();

    @ApiInternal
    void setPantsColor(int pantsColor);

    int getFootwear();

    @ApiInternal
    void setFootwear(int footwear);

    int getFootwearColor();

    @ApiInternal
    void setFootwearColor(int footwearColor);

    int getHair();

    @ApiInternal
    void setHair(int hair);

    int getHairColor();

    @ApiInternal
    void setHairColor(int hairColor);

    int getAccessory();

    @ApiInternal
    void setAccessory(int accessory);

    int getEyebrows();

    @ApiInternal
    void setEyebrows(int eyebrows);

    int getMouth();

    @ApiInternal
    void setMouth(int mouth);

    int getBeard();

    @ApiInternal
    void setBeard(int beard);

    int getBeardColor();

    @ApiInternal
    void setBeardColor(int beardColor);

    int getEyebrowsColor();

    @ApiInternal
    void setEyebrowsColor(int eyebrowsColor);

    boolean isFemale();

    @ApiInternal
    void setFemale(boolean female);
}
