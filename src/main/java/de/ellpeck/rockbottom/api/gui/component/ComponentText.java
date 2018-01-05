/*
 * This file ("ComponentText.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.List;

public class ComponentText extends GuiComponent{

    private final float scale;
    private final boolean fromRight;
    private final String[] text;

    public ComponentText(Gui gui, int x, int y, int width, int height, float scale, boolean fromRight, String... text){
        super(gui, x, y, width, height);
        this.scale = scale;
        this.fromRight = fromRight;
        this.text = text;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y){
        IFont font = manager.getFont();
        List<String> text = font.splitTextToLength(this.width-2, this.scale, true, this.text);

        float height = font.getHeight(this.scale);
        int yOff = this.height/2-(text.size()*(int)height)/2;

        for(String s : text){
            if(this.fromRight){
                font.drawStringFromRight(x+this.width-1, y+yOff, s, this.scale);
            }
            else{
                font.drawString(x+1, y+yOff, s, this.scale);
            }

            yOff += height;
        }
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("text");
    }
}
