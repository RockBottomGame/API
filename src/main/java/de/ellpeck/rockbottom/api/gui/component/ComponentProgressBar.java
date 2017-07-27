/*
 * This file ("ComponentProgressBar.java") is part of the RockBottomAPI by Ellpeck.
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
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ComponentProgressBar extends GuiComponent{

    private final Color progressColor;
    private final Color backgroundColor;

    private final ICallback callback;
    private final boolean isVertical;

    public ComponentProgressBar(Gui gui, int x, int y, int sizeX, int sizeY, Color progressColor, boolean isVertical, ICallback callback){
        super(gui, x, y, sizeX, sizeY);
        this.callback = callback;
        this.isVertical = isVertical;
        this.progressColor = progressColor;
        this.backgroundColor = progressColor.multiply(new Color(0.5F, 0.5F, 0.5F, 0.35F));
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g){
        super.render(game, manager, g);

        float percent = this.callback.getProgress();

        g.setColor(this.backgroundColor);
        g.fillRect(this.x, this.y, this.sizeX, this.sizeY);

        g.setColor(this.progressColor);
        if(this.isVertical){
            float height = percent*this.sizeY;
            g.fillRect(this.x, this.y+this.sizeY-height, this.sizeX, height);
        }
        else{
            float width = percent*this.sizeX;
            g.fillRect(this.x, this.y, width, this.sizeY);
        }

        g.setColor(Color.black);
        g.drawRect(this.x, this.y, this.sizeX, this.sizeY);
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("progress_bar");
    }

    public interface ICallback{

        float getProgress();
    }
}
