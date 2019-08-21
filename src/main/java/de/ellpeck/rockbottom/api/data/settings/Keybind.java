/*
 * This file ("Keybind.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data.settings;

import de.ellpeck.rockbottom.api.IInputHandler;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import org.lwjgl.glfw.GLFW;

public final class Keybind {

    private final ResourceName name;
    private int key;

    public Keybind(ResourceName name, int defKey) {
        this.name = name;
        this.key = defKey;
    }

    public static boolean isMouse(int key) {
        return key <= GLFW.GLFW_MOUSE_BUTTON_8;
    }

    @ApiInternal
    public void setBind(int key) {
        this.key = key;
    }

    public boolean isDown() {
        IInputHandler input = RockBottomAPI.getGame().getInput();

        if (this.isMouse()) {
            return input.isMouseDown(this.key);
        } else {
            return input.isKeyDown(this.key);
        }
    }

    public boolean isPressed() {
        IInputHandler input = RockBottomAPI.getGame().getInput();

        if (this.isMouse()) {
            return input.wasMousePressed(this.key);
        } else {
            return input.wasKeyPressed(this.key);
        }
    }

    public String getDisplayName() {
        return RockBottomAPI.getInternalHooks().getKeyOrMouseName(this.key);
    }

    public boolean isKey(int key) {
        return this.key == key;
    }

    public ResourceName getName() {
        return this.name;
    }

    public int getKey() {
        return this.key;
    }

    public boolean isMouse() {
        return isMouse(this.key);
    }

    public Keybind register() {
        Registries.KEYBIND_REGISTRY.register(this.getName(), this);
        return this;
    }
}
