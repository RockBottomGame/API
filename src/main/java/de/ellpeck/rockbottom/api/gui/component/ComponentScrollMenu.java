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

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ComponentScrollMenu extends ComponentButton{

    private final int contentsX;
    private final int contentsY;
    private final List<GuiComponent> contents = new ArrayList<>();
    private final BoundBox hoverArea;

    protected int number;
    private boolean wasMouseDown;

    public ComponentScrollMenu(Gui gui, int x, int y, int sizeY, int contentsX, int contentsY, BoundBox hoverArea){
        super(gui, x, y, 6, sizeY, null, null);
        this.contentsX = contentsX;
        this.contentsY = contentsY;
        this.hoverArea = hoverArea;
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
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, int x, int y){
        int max = this.getMax();
        float percentage = max <= 0 ? 0 : (float)this.number/(float)max;
        float renderY = y+percentage*(this.height-10);
        int color = this.isMouseOverPrioritized(game) || this.hoverArea.contains(g.getMouseInGuiX(), g.getMouseInGuiY()) ? getElementColor() : getUnselectedElementColor();

        g.fillRect(x, y, 6F, this.height, color);
        g.drawRect(x, y, 6F, this.height, getElementOutlineColor());

        g.fillRect(x, renderY, 6F, 10F, color);
        g.drawRect(x, renderY, 6F, 10F, getElementOutlineColor());
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
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(this.isMouseOver(game)){
            if(!this.wasMouseDown){
                this.wasMouseDown = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(IGameInstance game){
        if(this.wasMouseDown){
            if(Settings.KEY_GUI_ACTION_1.isDown()){
                this.onClickOrMove(game.getGraphics().getMouseInGuiY());
            }
            else{
                this.wasMouseDown = false;
            }
        }
        else{
            int scroll = Mouse.getDWheel();
            if(scroll != 0 && this.hoverArea.contains(game.getGraphics().getMouseInGuiX(), game.getGraphics().getMouseInGuiY())){
                int number = Util.clamp(this.number+(scroll < 0 ? 1 : -1), 0, this.getMax());
                if(number != this.number){
                    this.number = number;
                    this.organize();
                }
            }
        }
    }

    @ApiInternal
    private void onClickOrMove(float mouseY){
        int max = this.getMax();
        float clickPercentage = (mouseY-this.getRenderY())/(float)this.height;

        int number = Util.clamp((int)(clickPercentage*(max-1)), 0, max);
        if(number != this.number){
            this.number = number;
            this.organize();
        }
    }

    @ApiInternal
    private int getMax(){
        return (Util.ceil((float)this.contents.size()/(float)this.contentsX))-this.contentsY;
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("scroll_bar");
    }
}
