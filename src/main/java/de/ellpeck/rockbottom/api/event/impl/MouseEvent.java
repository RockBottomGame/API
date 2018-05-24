/*
 * This file ("MouseEvent.java") is part of the RockBottomAPI by Ellpeck.
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
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

/**
 * This event is fired when a mouse button is held down or pressed. It is fired
 * in the {@link GLFWMouseButtonCallback} that is assigned to {@link
 * GLFW#glfwSetMouseButtonCallback(long, GLFWMouseButtonCallbackI)}. Cancelling
 * it will make the moues input not be processed.
 */
public final class MouseEvent extends Event{

    public final long window;
    public final int mods;
    public int action;
    public int button;

    public MouseEvent(long window, int mods, int action, int button){
        this.window = window;
        this.mods = mods;
        this.action = action;
        this.button = button;
    }
}
