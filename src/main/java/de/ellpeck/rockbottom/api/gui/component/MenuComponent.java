/*
 * This file ("MenuComponent.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui.component;

import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.Pos2;

import java.util.HashMap;
import java.util.Map;

public class MenuComponent{

    private final Map<Pos2, GuiComponent> components = new HashMap<>();
    public final int width;
    public final int height;

    public MenuComponent(int width, int height){
        this.width = width;
        this.height = height;
    }

    public MenuComponent add(int x, int y, GuiComponent component){
        this.components.put(new Pos2(x, y), component);
        return this;
    }

    public void init(Gui gui){
        for(GuiComponent component : this.components.values()){
            gui.getComponents().add(component);
        }
    }

    public void onRemoved(Gui gui){
        for(GuiComponent component : this.components.values()){
            gui.getComponents().remove(component);
        }
    }

    public void setActive(boolean active){
        for(GuiComponent component : this.components.values()){
            component.setActive(active);
        }
    }

    public void setPos(int x, int y){
        for(Map.Entry<Pos2, GuiComponent> entry : this.components.entrySet()){
            GuiComponent component = entry.getValue();
            Pos2 pos = entry.getKey();

            component.x = pos.getX()+x;
            component.y = pos.getY()+y;
        }
    }
}
