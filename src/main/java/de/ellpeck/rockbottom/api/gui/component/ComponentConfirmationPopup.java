/*
 * This file ("ComponentConfirmationPopup.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.assets.font.Font;
import de.ellpeck.rockbottom.api.assets.tex.Texture;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.newdawn.slick.Graphics;

import java.util.function.Consumer;

public class ComponentConfirmationPopup extends GuiComponent{

    private static final IResourceName RES = RockBottomAPI.createInternalRes("gui.popup");
    private final Consumer<Boolean> consumer;
    private final boolean isUpsideDown;
    private final BoundBox buttonArea;

    public ComponentConfirmationPopup(Gui gui, int x, int y, Consumer<Boolean> consumer){
        super(gui, x-27, y-42, 54, 42);
        this.consumer = consumer;

        this.buttonArea = new BoundBox(0, 0, 40, 15);

        this.isUpsideDown = this.y < 0;
        if(this.isUpsideDown){
            this.y += this.sizeY;
            this.buttonArea.add(this.x+7, this.y+23);
        }
        else{
            this.buttonArea.add(this.x+7, this.y+11);
        }
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g){
        Texture tex = manager.getTexture(RES);
        Font font = manager.getFont();
        String text = "Are you sure?";

        if(this.isUpsideDown){
            tex.draw(this.x, this.y+this.sizeY, this.x+this.sizeX, this.y, 0, 0, tex.getWidth(), tex.getHeight());
            font.drawCenteredString(this.x+this.sizeX/2, this.y+15, text, 0.25F, false);
        }
        else{
            tex.draw(this.x, this.y, this.sizeX, this.sizeY);
            font.drawCenteredString(this.x+this.sizeX/2, this.y+3, text, 0.25F, false);
        }

        int x = (int)this.buttonArea.getMinX();
        int y = (int)this.buttonArea.getMinY();
        int width = (int)this.buttonArea.getWidth();
        int height = (int)this.buttonArea.getHeight();

        g.setColor(this.isMouseOverPrioritized(game) && this.buttonArea.contains(game.getMouseInGuiX(), game.getMouseInGuiY()) ? this.colorButton : this.colorButtonUnselected);
        g.fillRect(x, y, width, height);

        g.setColor(this.colorOutline);
        g.drawRect(x, y, width, height);

        font.drawCenteredString(x+width/2F, y+height/2F+0.5F, "Yes", 0.35F, true);
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(Settings.KEY_GUI_ACTION_1.isKey(button) && this.isMouseOverPrioritized(game) && this.buttonArea.contains(game.getMouseInGuiX(), game.getMouseInGuiY())){
            this.consumer.accept(true);
        }
        else{
            this.consumer.accept(false);
        }

        this.gui.getComponents().remove(this);
        return true;
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("confirmation_popup");
    }
}
