/*
 * This file ("ParentedNameRegistry.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.util.reg;

public class ParentedNameRegistry<T> extends NameRegistry<T> {

    private final NameRegistry<T> parent;

    public ParentedNameRegistry(ResourceName name, boolean canUnregister, NameRegistry<T> parent) {
        super(name, canUnregister);
        this.parent = parent;
    }

    @Override
    public void register(ResourceName name, T value) {
        this.parent.register(name, value);
        super.register(name, value);
    }

    @Override
    public void unregister(ResourceName name) {
        this.parent.unregister(name);
        super.unregister(name);
    }
}
