/*
 * This file ("GuiMessageBox.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.gui.component.ComponentMessageBox;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public class GuiMessageBox extends Gui{

    private final float scale;
    private final int boxWidth;
    private final int heightOffset;
    private final ChatComponent message;

    public GuiMessageBox(int boxWidth, ChatComponent message){
        this(null, boxWidth, message);
    }

    public GuiMessageBox(Gui parent, int boxWidth, ChatComponent message){
        this(parent, 0.25F, boxWidth, 5,message);
    }

    public GuiMessageBox(Gui parent, float scale, int boxWidth, int heightOffset, ChatComponent message){
        super(parent);
        this.scale = scale;
        this.boxWidth = boxWidth;
        this.heightOffset = heightOffset;
        this.message = message;
    }

    @Override
    public void init(IGameInstance game){
        super.init(game);

        IAssetManager asset = game.getAssetManager();
        IFont font = asset.getFont();
        String message = this.message.getDisplayWithChildren(game, asset);

        List<String> split = font.splitTextToLength(this.boxWidth-4, this.scale, false, message);
        int height = Util.ceil(split.size()*font.getHeight(this.scale))+4;

        this.components.add(new ComponentMessageBox(this, this.width/2-this.boxWidth/2, this.height-height-this.heightOffset, this.boxWidth, height, message, this.scale, true, true, () -> {
            game.getGuiManager().closeGui();
            return true;
        }));
    }

    @Override
    public ResourceName getName(){
        return ResourceName.intern("message_box");
    }

    @Override
    public boolean hasGradient(){
        return false;
    }
}
