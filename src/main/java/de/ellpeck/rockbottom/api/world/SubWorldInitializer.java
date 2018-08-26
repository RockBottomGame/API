/*
 * This file ("SubWorldInitializer.java") is part of the RockBottomAPI by Ellpeck.
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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.world;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;

public abstract class SubWorldInitializer {

    private final ResourceName name;

    public SubWorldInitializer(ResourceName name) {
        this.name = name;
    }

    public abstract int getSeedModifier();

    public abstract List<Pos2> getDefaultPersistentChunks(IWorld subWorld);

    public abstract void onSave(IWorld subWorld);

    public abstract Biome getExpectedBiome(IWorld subWorld, int x, int y);

    public abstract BiomeLevel getExpectedBiomeLevel(IWorld subWorld, int x, int y);

    public abstract int getExpectedSurfaceHeight(IWorld subWorld, TileLayer layer, int x);

    public abstract void onGeneratorsInitialized(IWorld subWorld);

    public abstract void update(IWorld subWorld, IGameInstance game);

    public boolean shouldGenerateHere(IWorld subWorld, ResourceName name, IWorldGenerator generator){
        return generator.shouldExistInWorld(subWorld);
    }

    public ResourceName getWorldName(){
        return this.getName();
    }

    public ResourceName getName() {
        return this.name;
    }

    public SubWorldInitializer register() {
        Registries.SUB_WORLD_INITIALIZER_REGISTRY.register(this.getName(), this);
        return this;
    }
}
