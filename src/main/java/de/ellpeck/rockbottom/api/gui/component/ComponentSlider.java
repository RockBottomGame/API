/*
 * This file ("ComponentSlider.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.newdawn.slick.Graphics;

public class ComponentSlider extends ComponentButton{

    protected final ICallback callback;
    protected final int min;
    protected final int max;
    protected int number;

    private boolean wasMouseDown;

    public ComponentSlider(Gui gui, int id, int x, int y, int sizeX, int sizeY, int initialNumber, int min, int max, ICallback callback, String text, String... hover){
        super(gui, id, x, y, sizeX, sizeY, text, hover);
        this.min = min;
        this.max = max;
        this.number = initialNumber;
        this.callback = callback;
    }

    @Override
    protected String getText(){
        return super.getText()+": "+this.number;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g){
        super.render(game, manager, g);

        float percentage = (float)(this.number-this.min)/(float)(this.max-this.min);
        float x = this.x+percentage*(this.sizeX-5);

        g.setColor(this.isMouseOverPrioritized(game) ? this.colorButton : this.colorButtonUnselected);
        g.fillRect(x, this.y, 5F, this.sizeY);

        g.setColor(this.colorOutline);
        g.drawRect(x, this.y, 5F, this.sizeY);
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(this.isMouseOver(game)){
            if(!this.wasMouseDown){
                this.callback.onFirstClick(game.getMouseInGuiX(), game.getMouseInGuiY(), this.min, this.max, this.number);
                this.wasMouseDown = true;

                return true;
            }
        }
        return false;
    }

    @Override
    public void update(IGameInstance game){
        if(this.wasMouseDown){
            float mouseX = game.getMouseInGuiX();
            float mouseY = game.getMouseInGuiY();

            if(game.getInput().isMouseButtonDown(game.getSettings().buttonGuiAction1)){
                this.onClickOrMove(mouseX, mouseY);
            }
            else{
                this.callback.onLetGo(mouseX, mouseY, this.min, this.max, this.number);
                this.wasMouseDown = false;
            }
        }
    }

    private void onClickOrMove(float mouseX, float mouseY){
        float clickPercentage = (mouseX-this.x)/(float)this.sizeX;
        int number = Math.max(this.min, Math.min(this.max, (int)(clickPercentage*(this.max-this.min+1)+this.min)));

        if(number != this.number){
            this.number = number;

            this.callback.onNumberChange(mouseX, mouseY, this.min, this.max, this.number);
        }
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("slider");
    }

    public interface ICallback{

        default void onNumberChange(float mouseX, float mouseY, int min, int max, int number){

        }

        default void onFirstClick(float mouseX, float mouseY, int min, int max, int number){

        }

        default void onLetGo(float mouseX, float mouseY, int min, int max, int number){

        }
    }
}
