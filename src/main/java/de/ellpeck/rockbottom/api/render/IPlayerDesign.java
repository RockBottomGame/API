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

import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IPlayerDesign{

    @ApiInternal
    ResourceName EYES = ResourceName.intern("player.base.eyes");

    @ApiInternal
    List<ResourceName> BASE = Arrays.asList(
            ResourceName.intern("player.base.1"),
            ResourceName.intern("player.base.2"),
            ResourceName.intern("player.base.3"),
            ResourceName.intern("player.base.4"),
            ResourceName.intern("player.base.5")
    );

    List<ResourceName> SHIRT = new ArrayList<>(Arrays.asList(
            ResourceName.intern("player.shirt.1"),
            ResourceName.intern("player.shirt.2"),
            ResourceName.intern("player.shirt.3"),
            ResourceName.intern("player.shirt.4"),
            null
    ));

    @ApiInternal
    List<ResourceName> ARMS = Arrays.asList(
            ResourceName.intern("player.arm.skin_1"),
            ResourceName.intern("player.arm.skin_2"),
            ResourceName.intern("player.arm.skin_3"),
            ResourceName.intern("player.arm.skin_4"),
            ResourceName.intern("player.arm.skin_5")
    );

    List<ResourceName> SLEEVES = new ArrayList<>(Arrays.asList(
            ResourceName.intern("player.sleeve.1"),
            ResourceName.intern("player.sleeve.2"),
            ResourceName.intern("player.sleeve.3"),
            ResourceName.intern("player.sleeve.4"),
            null
    ));

    List<ResourceName> PANTS = new ArrayList<>(Arrays.asList(
            ResourceName.intern("player.pants.1"),
            ResourceName.intern("player.pants.2"),
            null
    ));

    List<ResourceName> FOOTWEAR = new ArrayList<>(Arrays.asList(
            ResourceName.intern("player.footwear.1"),
            ResourceName.intern("player.footwear.2"),
            ResourceName.intern("player.footwear.3"),
            ResourceName.intern("player.footwear.4"),
            ResourceName.intern("player.footwear.5"),
            null
    ));

    List<ResourceName> HAIR = new ArrayList<>(Arrays.asList(
            ResourceName.intern("player.hair.1"),
            ResourceName.intern("player.hair.2"),
            ResourceName.intern("player.hair.3"),
            ResourceName.intern("player.hair.4"),
            ResourceName.intern("player.hair.5"),
            ResourceName.intern("player.hair.6"),
            ResourceName.intern("player.hair.7"),
            ResourceName.intern("player.hair.8"),
            ResourceName.intern("player.hair.9"),
            null
    ));

    List<ResourceName> ACCESSORIES = new ArrayList<>(Arrays.asList(
            null,
            ResourceName.intern("player.accessory.monocle_1"),
            ResourceName.intern("player.accessory.monocle_2"),
            ResourceName.intern("player.accessory.sunglasses_1"),
            ResourceName.intern("player.accessory.sunglasses_2"),
            ResourceName.intern("player.accessory.sunglasses_3"),
            ResourceName.intern("player.accessory.glasses_1"),
            ResourceName.intern("player.accessory.glasses_2"),
            ResourceName.intern("player.accessory.glasses_3"),
            ResourceName.intern("player.accessory.eyeliner")
    ));

    List<ResourceName> EYEBROWS = new ArrayList<>(Arrays.asList(
            ResourceName.intern("player.eyebrows.1"),
            ResourceName.intern("player.eyebrows.2"),
            ResourceName.intern("player.eyebrows.3"),
            ResourceName.intern("player.eyebrows.4"),
            ResourceName.intern("player.eyebrows.5"),
            ResourceName.intern("player.eyebrows.6"),
            null
    ));

    List<ResourceName> MOUTH = new ArrayList<>(Arrays.asList(
            null,
            ResourceName.intern("player.mouth.1"),
            ResourceName.intern("player.mouth.2"),
            ResourceName.intern("player.mouth.3"),
            ResourceName.intern("player.mouth.4"),
            ResourceName.intern("player.mouth.5"),
            ResourceName.intern("player.mouth.6"),
            ResourceName.intern("player.mouth.7"),
            ResourceName.intern("player.mouth.8"),
            ResourceName.intern("player.mouth.9"),
            ResourceName.intern("player.mouth.10")
    ));

    List<ResourceName> BEARD = new ArrayList<>(Arrays.asList(
            null,
            ResourceName.intern("player.beard.1"),
            ResourceName.intern("player.beard.2")
    ));

    int getFavoriteColor();

    @ApiInternal
    void setFavoriteColor(int color);

    String getName();

    @ApiInternal
    void setName(String name);

    int getBase();

    int getEyeColor();

    int getShirt();

    int getShirtColor();

    int getSleeves();

    int getSleevesColor();

    int getPants();

    int getPantsColor();

    int getFootwear();

    int getFootwearColor();

    int getHair();

    int getHairColor();

    int getAccessory();

    int getEyebrows();

    int getMouth();

    int getBeard();

    int getBeardColor();

    int getEyebrowsColor();

    boolean isFemale();

    @ApiInternal
    void setBase(int base);

    @ApiInternal
    void setEyeColor(int eyeColor);

    @ApiInternal
    void setShirt(int shirt);

    @ApiInternal
    void setShirtColor(int shirtColor);

    @ApiInternal
    void setSleeves(int sleeves);

    @ApiInternal
    void setSleevesColor(int sleevesColor);

    @ApiInternal
    void setPants(int pants);

    @ApiInternal
    void setPantsColor(int pantsColor);

    @ApiInternal
    void setFootwear(int footwear);

    @ApiInternal
    void setFootwearColor(int footwearColor);

    @ApiInternal
    void setHair(int hair);

    @ApiInternal
    void setHairColor(int hairColor);

    @ApiInternal
    void setAccessory(int accessory);

    @ApiInternal
    void setEyebrows(int eyebrows);

    @ApiInternal
    void setMouth(int mouth);

    @ApiInternal
    void setEyebrowsColor(int eyebrowsColor);

    @ApiInternal
    void setBeard(int beard);

    @ApiInternal
    void setBeardColor(int beardColor);

    @ApiInternal
    void setFemale(boolean female);
}
