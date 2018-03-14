/*
 * This file ("ComponentConfirmationPopup.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.function.Consumer;

public class ComponentConfirmationPopup extends GuiComponent{

    @ApiInternal
    private static final IResourceName RES = RockBottomAPI.createInternalRes("gui.popup");
    private final Consumer<Boolean> consumer;
    private final boolean isUpsideDown;
    private final BoundBox buttonArea;

    public ComponentConfirmationPopup(Gui gui, int x, int y, Consumer<Boolean> consumer){
        super(gui, x-27, y-42, 54, 42);
        this.consumer = consumer;

        this.buttonArea = new BoundBox(0, 0, 40, 15);

        this.isUpsideDown = this.getRenderY() < 0;
        if(this.isUpsideDown){
            this.y += this.height;
            this.buttonArea.add(this.getRenderX()+7, this.getRenderY()+23);
        }
        else{
            this.buttonArea.add(this.getRenderX()+7, this.getRenderY()+11);
        }
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y){
        ITexture tex = manager.getTexture(RES);
        IFont font = manager.getFont();
        String text = manager.localize(RockBottomAPI.createInternalRes("info.are_you_sure"));

        if(this.isUpsideDown){
            tex.draw(x, y+this.height, x+this.width, y, 0, 0, tex.getRenderWidth(), tex.getRenderHeight());
            font.drawCenteredString(x+this.width/2, y+15, text, 0.25F, false);
        }
        else{
            tex.draw(x, y, this.width, this.height);
            font.drawCenteredString(x+this.width/2, y+3, text, 0.25F, false);
        }

        int renderX = (int)this.buttonArea.getMinX();
        int renderY = (int)this.buttonArea.getMinY();
        int width = (int)this.buttonArea.getWidth();
        int height = (int)this.buttonArea.getHeight();

        g.addFilledRect(renderX, renderY, width, height, this.isMouseOverPrioritized(game) && this.buttonArea.contains(game.getRenderer().getMouseInGuiX(), game.getRenderer().getMouseInGuiY()) ? getElementColor() : getUnselectedElementColor());
        g.addEmptyRect(renderX, renderY, width, height, getElementOutlineColor());

        font.drawCenteredString(renderX+width/2F, renderY+height/2F+0.5F, manager.localize(RockBottomAPI.createInternalRes("button.yes")), 0.35F, true);
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(Settings.KEY_GUI_ACTION_1.isKey(button) && this.isMouseOverPrioritized(game) && this.buttonArea.contains(game.getRenderer().getMouseInGuiX(), game.getRenderer().getMouseInGuiY())){
            game.getAssetManager().getSound(RockBottomAPI.createInternalRes("menu.click")).play();
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

    @Override
    public boolean shouldDoFingerCursor(IGameInstance game){
        IRenderer g = game.getRenderer();
        return this.buttonArea.contains(g.getMouseInGuiX(), g.getMouseInGuiY());
    }

    @Override
    public int getPriority(){
        return 5000;
    }
}
