/*
 * This file ("ComponentScrollBar.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api.gui.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;

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
    public void render(IGameInstance game, IAssetManager manager, Graphics g){
        int max = this.getMax();
        float percentage = max <= 0 ? 0 : (float)this.number/(float)max;
        float y = this.y+percentage*(this.sizeY-10);

        g.setColor(this.isMouseOverPrioritized(game) || this.hoverArea.contains(game.getMouseInGuiX(), game.getMouseInGuiY()) ? this.colorButton : this.colorButtonUnselected);
        g.fillRect(this.x, this.y, 6F, this.sizeY);
        g.fillRect(this.x, y, 6F, 10F);

        g.setColor(this.colorOutline);
        g.drawRect(this.x, y, 6F, 10F);
        g.drawRect(this.x, this.y, 6F, this.sizeY);
    }

    public void organize(){
        this.number = Math.max(0, Math.min(this.getMax(), this.number));

        int index = 0;
        while(index < this.contents.size() && index < this.number*this.contentsX){
            this.contents.get(index).isActive = false;
            index++;
        }

        if(this.contents.size() > index){
            int showY = this.y;
            for(int y = 0; y < this.contentsY; y++){
                int showX = this.x+8;
                int highestHeight = 0;
                for(int x = 0; x < this.contentsX; x++){
                    GuiComponent component = this.contents.get(index);
                    component.isActive = true;
                    component.x = showX;
                    component.y = showY;

                    showX += component.sizeX+2;
                    if(component.sizeY > highestHeight){
                        highestHeight = component.sizeY;
                    }

                    index++;
                    if(index >= this.contents.size()){
                        return;
                    }
                }
                showY += highestHeight+2;
            }

            while(index < this.contents.size()){
                this.contents.get(index).isActive = false;
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
                this.onClickOrMove(game.getMouseInGuiY());
            }
            else{
                this.wasMouseDown = false;
            }
        }
        else{
            int scroll = Mouse.getDWheel();
            if(scroll != 0 && this.hoverArea.contains(game.getMouseInGuiX(), game.getMouseInGuiY())){
                int number = Math.max(0, Math.min(this.getMax(), this.number+(scroll < 0 ? 1 : -1)));
                if(number != this.number){
                    this.number = number;
                    this.organize();
                }
            }
        }
    }

    private void onClickOrMove(float mouseY){
        int max = this.getMax();
        float clickPercentage = (mouseY-this.y)/(float)this.sizeY;

        int number = Math.max(0, Math.min(max, (int)(clickPercentage*(max-1))));
        if(number != this.number){
            this.number = number;
            this.organize();
        }
    }

    private int getMax(){
        return (Util.ceil((float)this.contents.size()/(float)this.contentsX))-this.contentsY;
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("scroll_bar");
    }
}
