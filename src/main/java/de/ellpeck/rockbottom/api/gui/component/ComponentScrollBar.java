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
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;

import java.util.function.Consumer;

public class ComponentScrollBar extends ComponentButton{

    protected final BoundBox scrollArea;
    protected final Consumer<Integer> consumer;
    protected int min;
    protected int max;
    protected int number;

    private boolean wasMouseDown;
    private boolean locked = false;

    public ComponentScrollBar(Gui gui, int x, int y, int sizeX, int sizeY, int initialNumber, int min, int max, BoundBox scrollArea, Consumer<Integer> consumer){
        super(gui, x, y, sizeX, sizeY, null, null);
        this.min = min;
        this.max = max;
        this.number = initialNumber;
        this.scrollArea = scrollArea;
        this.consumer = consumer;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g){
        super.render(game, manager, g);

        if(!this.locked){
            float percentage = (float)(this.number-this.min)/(float)(this.max-this.min);
            float y = this.y+percentage*(this.sizeY-10);

            g.setColor(this.isMouseOverPrioritized(game) ? this.colorButton : this.colorButtonUnselected);
            g.fillRect(this.x, y, this.sizeX, 10F);

            g.setColor(this.colorOutline);
            g.drawRect(this.x, y, this.sizeX, 10F);
        }
    }

    public void setMin(int min){
        if(this.min != min){
            this.min = min;
            this.number = min;
        }
    }

    public void setNum(int number){
        this.number = Math.max(this.min, Math.min(this.max, number));
    }

    public void setMax(int max){
        if(this.max != max){
            this.max = max;
            this.number = this.getMin();
        }
    }

    public void setLocked(boolean locked){
        this.locked = locked;
    }

    public int getMin(){
        return this.min;
    }

    public int getMax(){
        return this.max;
    }

    public int getNumber(){
        return this.number;
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(!this.locked && this.isMouseOver(game)){
            if(!this.wasMouseDown){
                this.wasMouseDown = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(IGameInstance game){
        if(!this.locked){
            float mouseX = game.getMouseInGuiX();
            float mouseY = game.getMouseInGuiY();

            if(this.wasMouseDown){
                if(Settings.KEY_GUI_ACTION_1.isDown()){
                    this.onClickOrMove(mouseY);
                }
                else{
                    this.wasMouseDown = false;
                }
            }
            else{
                int scroll = Mouse.getDWheel();
                if(scroll != 0 && this.scrollArea.contains(mouseX, mouseY)){
                    int number = Math.max(this.min, Math.min(this.max, this.number+(scroll < 0 ? 1 : -1)));

                    if(number != this.number){
                        this.number = number;
                        this.consumer.accept(this.number);
                    }
                }
            }
        }
    }

    private void onClickOrMove(float mouseY){
        float clickPercentage = (mouseY-this.y)/(float)this.sizeY;
        int number = Math.max(this.min, Math.min(this.max, (int)(clickPercentage*(this.max-this.min+1)+this.min)));

        if(number != this.number){
            this.number = number;
            this.consumer.accept(this.number);
        }
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("scroll_bar");
    }
}
