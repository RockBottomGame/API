/*
 * This file ("ComponentMenu.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.util.BoundingBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;
import java.util.List;

public class ComponentMenu extends ComponentScrollBar {

    private final int displayedComponentsX;
    private final int displayedComponentsY;
    private final int componentsOffsetX;
    private final int componentsOffsetY;
    private final int componentGap;
    private final List<MenuComponent> contents = new ArrayList<>();

    public ComponentMenu(Gui gui, int x, int y, int barWidth, int height, int displayedComponentsX, int displayedComponentsY, int componentsOffsetX, int componentsOffsetY, BoundingBox hoverArea, ResourceName scrollTexture, int componentGap) {
        super(gui, x, y, barWidth, height, hoverArea, 0, null, scrollTexture);
        this.displayedComponentsX = displayedComponentsX;
        this.displayedComponentsY = displayedComponentsY;
        this.componentsOffsetX = componentsOffsetX;
        this.componentsOffsetY = componentsOffsetY;
        this.componentGap = componentGap;
    }

    public ComponentMenu(Gui gui, int x, int y, int barWidth, int height, int displayedComponentsX, int displayedComponentsY, int componentsOffsetX, int componentsOffsetY, BoundingBox hoverArea, ResourceName scrollTexture) {
        this(gui, x, y, barWidth, height, displayedComponentsX, displayedComponentsY, componentsOffsetX, componentsOffsetY, hoverArea, scrollTexture, 2);
    }

    public ComponentMenu(Gui gui, int x, int y, int height, int displayedComponentsX, int displayedComponentsY, BoundingBox hoverArea) {
        this(gui, x, y, 6, height, displayedComponentsX, displayedComponentsY, 0, 0, hoverArea, null);
    }

    public void add(MenuComponent component) {
        this.contents.add(component);
        component.init(this.gui);
    }

    public void remove(MenuComponent component) {
        this.contents.remove(component);
        component.onRemoved(this.gui);
    }

    public void clear() {
        if (!this.contents.isEmpty()) {
            for (MenuComponent component : this.contents) {
                component.onRemoved(this.gui);
            }
            this.contents.clear();
        }
    }

    public boolean isEmpty() {
        return this.contents.isEmpty();
    }

    @Override
    protected void onScroll() {
        this.organize();
    }

    public void organize() {
        this.number = Util.clamp(this.number, 0, this.getMax());

        int index = 0;
        while (index < this.contents.size() && index < this.number * this.displayedComponentsX) {
            this.contents.get(index).setActive(false);
            index++;
        }

        if (this.contents.size() > index) {
            int showY = this.getY() + this.componentsOffsetY;
            for (int y = 0; y < this.displayedComponentsY; y++) {
                int showX = this.getX() + this.componentsOffsetX + 8;
                int highestHeight = 0;
                for (int x = 0; x < this.displayedComponentsX; x++) {
                    MenuComponent component = this.contents.get(index);
                    component.setActive(true);
                    component.setPos(showX, showY);

                    showX += component.width + this.componentGap;
                    if (component.height > highestHeight) {
                        highestHeight = component.height;
                    }

                    index++;
                    if (index >= this.contents.size()) {
                        return;
                    }
                }
                showY += highestHeight + 2;
            }

            while (index < this.contents.size()) {
                this.contents.get(index).setActive(false);
                index++;
            }
        }
    }

    @Override
    public int getMax() {
        return (Util.ceil((float) this.contents.size() / (float) this.displayedComponentsX)) - this.displayedComponentsY;
    }

    @Override
    public ResourceName getName() {
        return ResourceName.intern("scroll_menu");
    }
}
