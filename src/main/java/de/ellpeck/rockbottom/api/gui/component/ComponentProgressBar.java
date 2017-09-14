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
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.function.Supplier;

public class ComponentProgressBar extends GuiComponent{

    private final int progressColor;
    private final int backgroundColor;

    private final Supplier<Float> supplier;
    private final boolean isVertical;

    public ComponentProgressBar(Gui gui, int x, int y, int sizeX, int sizeY, int progressColor, boolean isVertical, Supplier<Float> supplier){
        super(gui, x, y, sizeX, sizeY);
        this.supplier = supplier;
        this.isVertical = isVertical;
        this.progressColor = progressColor;
        this.backgroundColor = Colors.multiply(progressColor, 0.5F, 0.5F, 0.5F, 0.35F);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g){
        super.render(game, manager, g);

        float percent = this.supplier.get();

        g.fillRect(this.x, this.y, this.sizeX, this.sizeY, this.backgroundColor);

        if(this.isVertical){
            float height = percent*this.sizeY;
            g.fillRect(this.x, this.y+this.sizeY-height, this.sizeX, height, this.progressColor);
        }
        else{
            float width = percent*this.sizeX;
            g.fillRect(this.x, this.y, width, this.sizeY, this.progressColor);
        }

        g.drawRect(this.x, this.y, this.sizeX, this.sizeY, Colors.BLACK);
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("progress_bar");
    }
}
