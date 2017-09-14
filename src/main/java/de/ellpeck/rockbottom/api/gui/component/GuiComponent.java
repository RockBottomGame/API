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
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.lwjgl.input.Mouse;

public abstract class GuiComponent{

    protected final int guiColor = RockBottomAPI.getGame().getSettings().guiColor;
    protected final int colorButton = Colors.multiplyA(this.guiColor, 0.5F);
    protected final int colorOutline = Colors.multiply(this.guiColor, 0.75F);
    protected final int colorButtonUnselected = Colors.multiplyA(this.colorButton, 0.6F);

    protected int width;
    protected int height;
    public final Gui gui;
    protected int x;
    protected int y;
    public boolean isActive = true;

    public GuiComponent(Gui gui, int x, int y, int width, int height){
        this.gui = gui;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(IGameInstance game){

    }

    public void render(IGameInstance game, IAssetManager manager, IGraphics g, int x, int y){

    }

    public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g, int x, int y){

    }

    public boolean isMouseOverPrioritized(IGameInstance game){
        return this.gui == null ? this.isMouseOver(game) : this.gui.isMouseOverPrioritized(game, this);
    }

    public boolean isMouseOver(IGameInstance game){
        if(this.isActive && Mouse.isInsideWindow()){
            int mouseX = (int)game.getMouseInGuiX();
            int mouseY = (int)game.getMouseInGuiY();

            int renderX = this.getRenderX();
            int renderY = this.getRenderY();

            return mouseX >= renderX && mouseX < renderX+this.width && mouseY >= renderY && mouseY < renderY+this.height;
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

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getRenderX(){
        if(this.gui != null){
            return this.gui.getX()+this.getX();
        }
        else{
            return this.getX();
        }
    }

    public int getRenderY(){
        if(this.gui != null){
            return this.gui.getY()+this.getY();
        }
        else{
            return this.getY();
        }
    }
}
