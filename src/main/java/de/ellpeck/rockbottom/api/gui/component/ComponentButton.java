/*
 * This file ("ComponentButton.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.function.Supplier;

public class ComponentButton extends GuiComponent{

    private final String[] hover;
    private final Supplier<Boolean> supplier;
    public boolean hasBackground = true;
    protected String text;

    public ComponentButton(Gui gui, int x, int y, int sizeX, int sizeY, Supplier<Boolean> supplier, String text, String... hover){
        super(gui, x, y, sizeX, sizeY);
        this.text = text;
        this.hover = hover;
        this.supplier = supplier;
    }

    public void setText(String text){
        this.text = text;
    }

    public ComponentButton setHasBackground(boolean has){
        this.hasBackground = has;
        return this;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, int x, int y){
        if(this.hasBackground){
            g.fillRect(x, y, this.width, this.height, this.isMouseOverPrioritized(game) ? getElementColor() : getUnselectedElementColor());
            g.drawRect(x, y, this.width, this.height, getElementOutlineColor());
        }

        String text = this.getText();
        if(text != null){
            manager.getFont().drawCenteredString(x+this.width/2F, y+this.height/2F+0.5F, text, 0.35F, true);
        }
    }

    protected String getText(){
        return this.text;
    }

    protected String[] getHover(){
        return this.hover;
    }

    @Override
    public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g, int x, int y){
        if(this.isMouseOverPrioritized(game)){
            String[] hover = this.getHover();
            if(hover != null && hover.length > 0){
                g.drawHoverInfoAtMouse(game, manager, false, 100, hover);
            }
        }
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(Settings.KEY_GUI_ACTION_1.isKey(button) && this.isMouseOver(game)){
            if(this.onPressed(game)){
                return true;
            }
            else if(this.supplier != null && this.supplier.get()){
                return true;
            }
        }
        return false;
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("button");
    }

    public boolean onPressed(IGameInstance game){
        return false;
    }
}
