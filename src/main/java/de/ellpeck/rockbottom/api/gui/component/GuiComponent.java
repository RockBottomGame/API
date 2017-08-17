/*
 * This file ("GuiComponent.java") is part of the RockBottomAPI by Ellpeck.
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
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class GuiComponent{

    public final Color guiColor = RockBottomAPI.getGame().getSettings().guiColor;
    public final Color colorButton = this.guiColor.multiply(new Color(1F, 1F, 1F, 0.5F));
    public final Color colorButtonUnselected = this.colorButton.darker(0.4F);
    public final Color colorOutline = this.guiColor.darker(0.3F);
    public int sizeX;
    public int sizeY;
    public Gui gui;
    public int x;
    public int y;
    public boolean isActive = true;

    public GuiComponent(Gui gui, int x, int y, int sizeX, int sizeY){
        this.gui = gui;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void update(IGameInstance game){

    }

    public void render(IGameInstance game, IAssetManager manager, Graphics g){

    }

    public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g){

    }

    public boolean isMouseOverPrioritized(IGameInstance game){
        return this.gui == null ? this.isMouseOver(game) : this.gui.isMouseOverPrioritized(game, this);
    }

    public boolean isMouseOver(IGameInstance game){
        if(Mouse.isInsideWindow()){
            int mouseX = (int)game.getMouseInGuiX();
            int mouseY = (int)game.getMouseInGuiY();

            return mouseX >= this.x && mouseX < this.x+this.sizeX && mouseY >= this.y && mouseY < this.y+this.sizeY;
        }
        else{
            return false;
        }
    }

    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        return false;
    }

    public boolean onKeyboardAction(IGameInstance game, int button, char character){
        return false;
    }

    @Override
    public String toString(){
        return this.getName().toString();
    }

    public abstract IResourceName getName();
}
