/*
 * This file ("ComponentColorPicker.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.function.BiConsumer;

public class ComponentColorPicker extends GuiComponent{

    private final ITexture texture = RockBottomAPI.getGame().getAssetManager().getTexture(RockBottomAPI.createInternalRes("gui.colorpick"));

    private final BiConsumer<Integer, Boolean> consumer;
    private final boolean isEnlargable;
    private final int defX;
    private final int defY;
    private final int defSizeX;
    private final int defSizeY;
    private boolean wasMouseDown;
    private boolean isEnlarged;
    private int color;

    public ComponentColorPicker(Gui gui, int x, int y, int sizeX, int sizeY, int defaultColor, BiConsumer<Integer, Boolean> consumer, boolean isEnlargable){
        super(gui, x, y, sizeX, sizeY);
        this.consumer = consumer;
        this.color = defaultColor;
        this.isEnlargable = isEnlargable;

        this.defX = x;
        this.defY = y;
        this.defSizeX = sizeX;
        this.defSizeY = sizeY;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y){
        this.texture.draw(x, y, this.width, this.height);
        g.addEmptyRect(x, y, this.width, this.height, getElementOutlineColor());
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(this.isMouseOver(game)){
            if(Settings.KEY_GUI_ACTION_1.isKey(button)){
                if(this.isEnlargable && !this.isEnlarged){
                    this.width *= 4;
                    this.height *= 4;

                    this.x = Util.clamp(this.x-(this.width/2-(this.width/8)), 0, (int)game.getRenderer().getWidthInGui()-this.width);
                    this.y = Util.clamp(this.y-(this.height/2-(this.height/8)), 0, (int)game.getRenderer().getHeightInGui()-this.height);

                    this.isEnlarged = true;
                    this.gui.sortComponents();
                }
                else if(!this.wasMouseDown){
                    this.consumer.accept(this.color, false);
                    this.wasMouseDown = true;
                }

                return true;
            }
        }
        else{
            if(this.isEnlarged){
                this.unenlarge();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onKeyPressed(IGameInstance game, int button){
        if(this.isEnlarged){
            if(Settings.KEY_MENU.isKey(button)){
                this.unenlarge();
                return true;
            }
        }
        return false;
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("color_picker");
    }

    private void unenlarge(){
        if(this.isEnlarged){
            this.x = this.defX;
            this.y = this.defY;
            this.width = this.defSizeX;
            this.height = this.defSizeY;

            this.isEnlarged = false;
            this.gui.sortComponents();
        }
    }

    @Override
    public void update(IGameInstance game){
        if(this.wasMouseDown){
            float mouseX = game.getRenderer().getMouseInGuiX();
            float mouseY = game.getRenderer().getMouseInGuiY();

            if(Settings.KEY_GUI_ACTION_1.isDown()){
                this.onClickOrMove(game, mouseX, mouseY);
            }
            else{
                this.consumer.accept(this.color, true);
                this.wasMouseDown = false;
            }
        }
    }

    private void onClickOrMove(IGameInstance game, float mouseX, float mouseY){
        if(this.isMouseOver(game)){
            float x = (mouseX-this.getRenderX())/this.width*this.texture.getRenderWidth();
            float y = (mouseY-this.getRenderY())/this.height*this.texture.getRenderHeight();
            int color = this.texture.getTextureColor((int)x, (int)y);

            if(this.color != color){
                this.color = color;
                this.consumer.accept(this.color, false);
            }
        }
    }

    @Override
    public int getPriority(){
        return this.isEnlarged ? 1000 : super.getPriority();
    }
}
