/*
 * This file ("GameContent.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api;

import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;

public final class GameContent{

    public static final Tile TILE_AIR = getTile("air");

    public static final Biome BIOME_SKY = getBiome("sky");

    private static Biome getBiome(String name){
        return get(name, RockBottomAPI.BIOME_REGISTRY);
    }

    private static Item getItem(String name){
        return get(name, RockBottomAPI.ITEM_REGISTRY);
    }

    private static Tile getTile(String name){
        return get(name, RockBottomAPI.TILE_REGISTRY);
    }

    private static <T> T get(String name, NameRegistry<T> registry){
        IResourceName res = RockBottomAPI.createInternalRes(name);
        T thing = registry.get(res);

        if(thing == null){
            throw new IllegalStateException("Object with name "+res+" was not found in registry "+registry+"! This is probably due to GameContent being accessed before the game has initialized!");
        }
        else{
            return thing;
        }
    }
}
