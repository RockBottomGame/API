/*
 * This file ("ScrollEvent.java") is part of the RockBottomAPI by Ellpeck.
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
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWScrollCallbackI;

/**
 * This is fired when the moues wheel is scrolled either horizontally or
 * vertically. It gets called in the {@link GLFWScrollCallback} that is assigned
 * to {@link GLFW#glfwSetScrollCallback(long, GLFWScrollCallbackI)}. Cancelling
 * it will cause the internal scroll status not to be updated.
 */
public final class ScrollEvent extends Event {

    public final long window;
    public double xOffset;
    public double yOffset;

    public ScrollEvent(long window, double xOffset, double yOffset) {
        this.window = window;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}
