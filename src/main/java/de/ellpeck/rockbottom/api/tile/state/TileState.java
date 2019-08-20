/*
 * This file ("TileState.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.tile.state;

import com.google.common.base.Preconditions;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Map;

public final class TileState {

    private final Tile tile;
    private final ResourceName name;
    private final Map<String, Comparable> properties;
    private final Table<String, Comparable, TileState> subStates = TreeBasedTable.create();

    @ApiInternal
    public TileState(ResourceName name, Tile tile, Map<String, Comparable> properties) {
        this.tile = tile;
        this.properties = properties;
        this.name = name;

        RockBottomAPI.getInternalHooks().doTileStateInit(this, name, tile, properties, this.subStates);
    }

    public ResourceName getName() {
        return this.name;
    }

    public <T extends Comparable> TileState prop(String prop, T value) {
        if (value.equals(this.get(prop))) {
            return this;
        } else {
            return Preconditions.checkNotNull(this.subStates.get(prop, value), "The tile " + this.tile.getName() + " does not have property " + prop + " with value " + value);
        }
    }

    public boolean hasProp(String prop) {
        return this.properties.containsKey(prop);
    }

    public boolean hasProp(TileProp prop) {
        return this.hasProp(prop.getName());
    }

    public <T extends Comparable> TileState prop(TileProp<T> prop, T value) {
        return this.prop(prop.getName(), value);
    }

    public <T extends Comparable> TileState cycleProp(TileProp<T> prop) {
        int index = prop.getIndex(this.get(prop)) + 1;
        if (index >= prop.getVariants()) {
            index = 0;
        }
        return this.prop(prop, prop.getValue(index));
    }

    public <T extends Comparable> T get(String prop) {
        return Preconditions.checkNotNull((T) this.properties.get(prop), "The tile " + this.tile.getName() + " does not support property " + prop);
    }

    public <T extends Comparable> T get(TileProp<T> prop) {
        return this.get(prop.getName());
    }

    public TileState overrideProps(TileState other, TileProp... props) {
        Preconditions.checkArgument(other.tile == this.tile, "Cannot override properties of state " + this + " with " + other + " because they are not the same tile");

        TileState overriden = this;
        for (TileProp prop : props) {
            overriden = overriden.prop(prop, other.get(prop));
        }
        return overriden;
    }

    public Tile getTile() {
        return this.tile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof TileState) {
            TileState tileState = (TileState) o;
            return this.tile.equals(tileState.tile) && this.properties.equals(tileState.properties);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = this.tile.hashCode();
        result = 31 * result + this.properties.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.tile.getName() + "@" + this.properties;
    }
}
