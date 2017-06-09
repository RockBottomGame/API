/*
 * This file ("ComponentButton.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 */

package de.ellpeck.rockbottom.api.gui.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.Gui;
import org.newdawn.slick.Graphics;

public class ComponentButton extends GuiComponent{

    public final int id;
    protected final String text;
    private final String[] hover;

    public boolean hasBackground = true;

    public ComponentButton(Gui gui, int id, int x, int y, int sizeX, int sizeY, String text, String... hover){
        super(gui, x, y, sizeX, sizeY);
        this.id = id;
        this.text = text;
        this.hover = hover;
    }

    public ComponentButton setHasBackground(boolean has){
        this.hasBackground = has;
        return this;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g){
        if(this.hasBackground){
            g.setColor(this.isMouseOver(game) ? this.colorButton : this.colorButtonUnselected);
            g.fillRect(this.x, this.y, this.sizeX, this.sizeY);

            g.setColor(this.colorOutline);
            g.drawRect(this.x, this.y, this.sizeX, this.sizeY);
        }

        String text = this.getText();
        if(text != null){
            manager.getFont().drawCenteredString(this.x+this.sizeX/2F, this.y+this.sizeY/2F+0.5F, text, 0.35F, true);
        }
    }

    protected String getText(){
        return this.text;
    }

    protected String[] getHover(){
        return this.hover;
    }

    @Override
    public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g){
        if(this.isMouseOver(game)){
            String[] hover = this.getHover();
            if(hover != null && hover.length > 0){
                RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 100, hover);
            }
        }
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(button == game.getSettings().buttonGuiAction1 && this.isMouseOver(game)){
            if(this.onPressed(game)){
                return true;
            }
            else if(this.gui != null && this.gui.onButtonActivated(game, this.id)){
                return true;
            }
        }
        return false;
    }

    public boolean onPressed(IGameInstance game){
        return false;
    }
}
