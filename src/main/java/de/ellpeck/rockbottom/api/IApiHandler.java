/*
 * This file ("IApiHandler.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.part.DataPart;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

public interface IApiHandler{

    void writeDataSet(DataSet set, File file);

    void readDataSet(DataSet set, File file);

    void writeSet(DataOutput stream, DataSet set) throws Exception;

    void readSet(DataInput stream, DataSet set) throws Exception;

    void writePart(DataOutput stream, DataPart part) throws Exception;

    DataPart readPart(DataInput stream) throws Exception;

    @ApiInternal
    void doDefaultEntityUpdate(Entity entity);

    @ApiInternal
    void doWorldObjectMovement(MovableWorldObject object);

    @ApiInternal
    boolean doDefaultSlotMovement(IGameInstance game, int button, float x, float y, ComponentSlot slot);

    int[] interpolateLight(IWorld world, int x, int y);

    int[] interpolateWorldColor(int[] interpolatedLight, TileLayer layer);

    int getColorByLight(int light, TileLayer layer);

    INoiseGen makeSimplexNoise(Random random);

    @ApiInternal
    boolean isToolEffective(AbstractEntityPlayer player, ItemInstance instance, Tile tile, TileLayer layer, int x, int y);

    @ApiInternal
    boolean placeTile(int x, int y, TileLayer layer, AbstractEntityPlayer player, ItemInstance selected, Tile tile);

    Logger createLogger(String name);

    @ApiInternal
    Logger logger();
}
