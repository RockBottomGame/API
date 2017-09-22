/*
 * This file ("ComponentRenderOverlayEvent.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame>.
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

package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;

/**
 * This event is fired when the overlay of a {@link GuiComponent} is rendered
 * <br> Cancelling it will stop the component's overlay from rendering
 */
public class ComponentRenderOverlayEvent extends Event{

    public final Gui gui;
    public final int id;
    public final GuiComponent component;

    public ComponentRenderOverlayEvent(Gui gui, int id, GuiComponent component){
        this.gui = gui;
        this.id = id;
        this.component = component;
    }
}
