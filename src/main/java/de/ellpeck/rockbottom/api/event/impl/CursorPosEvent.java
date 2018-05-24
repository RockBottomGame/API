/*
 * This file ("CursorPosEvent.java") is part of the RockBottomAPI by Ellpeck.
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
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

/**
 * This event is fired when the cursor is being moved. It gets fired in the
 * {@link GLFWCursorPosCallback} that is assigned to {@link
 * GLFW#glfwSetCursorPosCallback(long, GLFWCursorPosCallbackI)}. Cancelling it
 * will cause the internal mouse position to not be updated.
 */
public final class CursorPosEvent extends Event{

    public final long window;
    public double x;
    public double y;

    public CursorPosEvent(long window, double x, double y){
        this.window = window;
        this.x = x;
        this.y = y;
    }
}
