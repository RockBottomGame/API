/*
 * This file ("ComponentScrollMenu.java") is part of the RockBottomAPI by Ellpeck.
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
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api.gui.component;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.ArrayList;
import java.util.List;

public class ComponentScrollMenu extends ComponentScrollBar{

    private final int contentsX;
    private final int contentsY;
    private final List<GuiComponent> contents = new ArrayList<>();

    public ComponentScrollMenu(Gui gui, int x, int y, int sizeY, int contentsX, int contentsY, BoundBox hoverArea){
        super(gui, x, y, sizeY, hoverArea, 0, null);
        this.contentsX = contentsX;
        this.contentsY = contentsY;
    }

    public void add(GuiComponent component){
        this.contents.add(component);
        this.gui.getComponents().add(component);
    }

    public void remove(GuiComponent component){
        this.contents.remove(component);
        this.gui.getComponents().remove(component);
    }

    public void clear(){
        if(!this.contents.isEmpty()){
            this.gui.getComponents().removeAll(this.contents);
            this.contents.clear();
        }
    }

    public boolean isEmpty(){
        return this.contents.isEmpty();
    }

    @Override
    protected void onScroll(){
        this.organize();
    }

    public void organize(){
        this.number = Util.clamp(this.number, 0, this.getMax());

        int index = 0;
        while(index < this.contents.size() && index < this.number*this.contentsX){
            this.contents.get(index).setActive(false);
            index++;
        }

        if(this.contents.size() > index){
            int showY = this.y;
            for(int y = 0; y < this.contentsY; y++){
                int showX = this.x+8;
                int highestHeight = 0;
                for(int x = 0; x < this.contentsX; x++){
                    GuiComponent component = this.contents.get(index);
                    component.setActive(true);
                    component.x = showX;
                    component.y = showY;

                    showX += component.width+2;
                    if(component.height > highestHeight){
                        highestHeight = component.height;
                    }

                    index++;
                    if(index >= this.contents.size()){
                        return;
                    }
                }
                showY += highestHeight+2;
            }

            while(index < this.contents.size()){
                this.contents.get(index).setActive(false);
                index++;
            }
        }
    }

    @Override
    public int getMax(){
        return (Util.ceil((float)this.contents.size()/(float)this.contentsX))-this.contentsY;
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("scroll_menu");
    }
}
