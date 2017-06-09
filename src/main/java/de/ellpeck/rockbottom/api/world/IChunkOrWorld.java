/*
 * This file ("IChunkOrWorld.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 */

package de.ellpeck.rockbottom.api.world;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.BoundBox;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface IChunkOrWorld{

    Tile getTile(int x, int y);

    Tile getTile(TileLayer layer, int x, int y);

    int getMeta(int x, int y);

    int getMeta(TileLayer layer, int x, int y);

    void setTile(int x, int y, Tile tile);

    void setTile(TileLayer layer, int x, int y, Tile tile);

    void setTile(int x, int y, Tile tile, int meta);

    void setTile(TileLayer layer, int x, int y, Tile tile, int meta);

    void setMeta(int x, int y, int meta);

    void setMeta(TileLayer layer, int x, int y, int meta);

    void addEntity(Entity entity);

    void addTileEntity(TileEntity tile);

    void removeEntity(Entity entity);

    void removeTileEntity(int x, int y);

    TileEntity getTileEntity(int x, int y);

    <T extends TileEntity> T getTileEntity(int x, int y, Class<T> tileClass);

    List<Entity> getAllEntities();

    List<TileEntity> getAllTileEntities();

    Entity getEntity(UUID id);

    List<Entity> getEntities(BoundBox area);

    List<Entity> getEntities(BoundBox area, Predicate<Entity> test);

    <T extends Entity> List<T> getEntities(BoundBox area, Class<T> type);

    <T extends Entity> List<T> getEntities(BoundBox area, Class<T> type, Predicate<T> test);

    byte getCombinedLight(int x, int y);

    byte getSkyLight(int x, int y);

    byte getArtificialLight(int x, int y);

    void setSkyLight(int x, int y, byte light);

    void setArtificialLight(int x, int y, byte light);

    void scheduleUpdate(int x, int y, TileLayer layer, int time);

    void setDirty(int x, int y);

    int getLowestAirUpwards(TileLayer layer, int x, int y);
}
