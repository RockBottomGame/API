/*
 * This file ("ComponentSlider.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.function.BiConsumer;

public class ComponentSlider extends ComponentButton{

    protected final BiConsumer<Integer, Boolean> consumer;
    protected final int min;
    protected final int max;
    protected int number;

    private boolean wasMouseDown;

    public ComponentSlider(Gui gui, int x, int y, int sizeX, int sizeY, int initialNumber, int min, int max, BiConsumer<Integer, Boolean> consumer, String text, String... hover){
        super(gui, x, y, sizeX, sizeY, null, text, hover);
        this.min = min;
        this.max = max;
        this.number = initialNumber;
        this.consumer = consumer;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g, int x, int y){
        super.render(game, manager, g, x, y);

        float percentage = (float)(this.number-this.min)/(float)(this.max-this.min);
        float renderX = x+percentage*(this.width-5);

        g.fillRect(renderX, y, 5F, this.height, this.isMouseOverPrioritized(game) ? getElementColor() : getUnselectedElementColor());
        g.drawRect(renderX, y, 5F, this.height, getElementOutlineColor());
    }

    @Override
    protected String getText(){
        return super.getText()+": "+this.number;
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(this.isMouseOver(game)){
            if(!this.wasMouseDown){
                this.consumer.accept(this.number, false);
                this.wasMouseDown = true;

                return true;
            }
        }
        return false;
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("slider");
    }

    @Override
    public void update(IGameInstance game){
        if(this.wasMouseDown){
            float mouseX = game.getGraphics().getMouseInGuiX();

            if(Settings.KEY_GUI_ACTION_1.isDown()){
                this.onClickOrMove(mouseX);
            }
            else{
                this.consumer.accept(this.number, true);
                game.getAssetManager().getSound(RockBottomAPI.createInternalRes("menu.click")).play();
                this.wasMouseDown = false;
            }
        }
    }

    @ApiInternal
    private void onClickOrMove(float mouseX){
        float clickPercentage = (mouseX-this.getRenderX())/(float)this.width;
        int number = Util.clamp((int)(clickPercentage*(this.max-this.min+1)+this.min), this.min, this.max);

        if(number != this.number){
            this.number = number;
            this.consumer.accept(this.number, false);
        }
    }
}
