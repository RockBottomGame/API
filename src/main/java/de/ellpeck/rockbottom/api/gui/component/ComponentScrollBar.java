/*
 * This file ("ComponentScrollBar.java") is part of the RockBottomAPI by Ellpeck.
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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.gui.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.function.Consumer;

public class ComponentScrollBar extends GuiComponent{

    protected final Consumer<Integer> scrollConsumer;
    protected final BoundBox hoverArea;
    protected int number;
    protected int max;

    protected boolean wasMouseDown;
    protected boolean drawReversed;
    protected final ResourceName scrollTexture;

    public ComponentScrollBar(Gui gui, int x, int y, int width, int height, BoundBox hoverArea, int max, Consumer<Integer> scrollConsumer, ResourceName scrollTexture){
        super(gui, x, y, width, height);
        this.scrollConsumer = scrollConsumer;
        this.hoverArea = hoverArea;
        this.max = max;
        this.scrollTexture = scrollTexture;
    }

    public ComponentScrollBar(Gui gui, int x, int y, int height, BoundBox hoverArea, int max, Consumer<Integer> scrollConsumer){
        this(gui, x, y, 6, height, hoverArea, max, scrollConsumer, null);
    }

    @Override
    public ResourceName getName(){
        return ResourceName.intern("scroll_bar");
    }

    public BoundBox getHoverArea(){
        return this.hoverArea;
    }

    public int getNumber(){
        return this.number;
    }

    public int getMax(){
        return this.max;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public void setMax(int max){
        this.max = max;
    }

    public void setDrawReversed(boolean should){
        this.drawReversed = should;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y){
        int max = this.getMax();
        float percentage = max <= 0 ? 0 : (float)this.number/(float)max;
        if(this.drawReversed){
            percentage = 1F-percentage;
        }

        boolean active = this.isMouseOverPrioritized(game) || this.hoverArea.contains(g.getMouseInGuiX(), g.getMouseInGuiY());
        if(this.scrollTexture == null){
            int color = active ? getElementColor() : getUnselectedElementColor();

            g.addFilledRect(x, y, 6F, this.height, color);
            g.addEmptyRect(x, y, 6F, this.height, getElementOutlineColor());

            float renderY = y+percentage*(this.height-10);
            g.addFilledRect(x, renderY, 6F, 10F, color);
            g.addEmptyRect(x, renderY, 6F, 10F, getElementOutlineColor());
        }
        else{
            ITexture texture = manager.getTexture(this.scrollTexture);

            float renderY = y+percentage*(this.height-texture.getRenderHeight());
            texture.draw(x, renderY, this.width, texture.getRenderHeight(), active ? Colors.WHITE : Colors.LIGHT_GRAY);
        }
    }

    @Override
    public void update(IGameInstance game){
        if(this.wasMouseDown){
            if(Settings.KEY_GUI_ACTION_1.isDown()){
                int max = this.getMax();
                float clickPercentage = (game.getRenderer().getMouseInGuiY()-this.getRenderY())/(float)this.height;
                if(this.drawReversed){
                    clickPercentage = 1F-clickPercentage;
                }

                int number = Util.clamp((int)(clickPercentage*max), 0, max);
                if(number != this.number){
                    this.number = number;
                    this.onScroll();
                }
            }
            else{
                this.wasMouseDown = false;
            }
        }
        else{
            int scroll = game.getInput().getMouseWheelChange();
            if(scroll != 0 && this.hoverArea.contains(game.getRenderer().getMouseInGuiX(), game.getRenderer().getMouseInGuiY())){
                if(this.drawReversed){
                    scroll = -scroll;
                }
                int number = Util.clamp(this.number+(scroll < 0 ? 1 : -1), 0, this.getMax());
                if(number != this.number){
                    this.number = number;
                    this.onScroll();
                }
            }
        }
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(this.isMouseOver(game)){
            if(!this.wasMouseDown){
                this.wasMouseDown = true;
                return true;
            }
        }
        return false;
    }

    protected void onScroll(){
        if(this.scrollConsumer != null){
            this.scrollConsumer.accept(this.number);
        }
    }
}
