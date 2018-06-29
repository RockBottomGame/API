/*
 * This file ("KeyEvent.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.event.Event;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;

/**
 * This event is fired when a key is pressed or held down. It is called in the
 * {@link GLFWKeyCallback} that is assigned to {@link GLFW#glfwSetKeyCallback(long,
 * GLFWKeyCallbackI)}. Cancelling it will cause the key not to be marked as
 * pressed or held down internally.
 */
public final class KeyEvent extends Event {

    public final long window;
    public final int scancode;
    public final int mods;
    public int action;
    public int key;

    public KeyEvent(long window, int scancode, int mods, int action, int key) {
        this.window = window;
        this.scancode = scancode;
        this.mods = mods;
        this.action = action;
        this.key = key;
    }
}
