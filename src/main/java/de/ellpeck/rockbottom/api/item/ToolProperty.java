/*
 * This file ("ToolProperty.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.item;

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ToolProperty {

    public static final ToolProperty PICKAXE = new ToolProperty(ResourceName.intern("pickaxe")).register();
    public static final ToolProperty SHOVEL = new ToolProperty(ResourceName.intern("shovel")).register();
    public static final ToolProperty AXE = new ToolProperty(ResourceName.intern("axe")).register();
    public static final ToolProperty SWORD = new ToolProperty(ResourceName.intern("sword")).register();
    public static final ToolProperty PESTLE = new ToolProperty(ResourceName.intern("pestle")).register();
    public static final ToolProperty BOOMERANG = new ToolProperty(ResourceName.intern("boomerang")).register();
    public static final ToolProperty HOE = new ToolProperty(ResourceName.intern("hoe")).register();
    public static final ToolProperty CHISEL = new ToolProperty(ResourceName.intern("chisel")).register();

    private final ResourceName name;

    public ToolProperty(ResourceName name) {
        this.name = name;
    }

    public ResourceName getName() {
        return this.name;
    }

    public ToolProperty register() {
        Registries.TOOL_PROPERTY_REGISTRY.register(this.getName(), this);
        return this;
    }
}
