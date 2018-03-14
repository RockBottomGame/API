/*
 * This file ("ComponentPolaroid.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui.component.construction;

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.construction.IRecipe;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.List;

public class ComponentPolaroid extends GuiComponent{

    private static final IResourceName RES = RockBottomAPI.createInternalRes("gui.construction.item_background");
    private static final IResourceName RES_HIGHLIGHTED = RockBottomAPI.createInternalRes("gui.construction.item_background_highlighted");

    public boolean isSelected;
    public final IRecipe recipe;
    public final boolean canConstruct;

    public ComponentPolaroid(Gui gui, IRecipe recipe, boolean canConstruct){
        super(gui, 0, 0, 18, 20);
        this.recipe = recipe;
        this.canConstruct = canConstruct;
    }

    @Override
    public IResourceName getName(){
        return RockBottomAPI.createInternalRes("polaroid");
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y){
        IResourceName res = this.recipe != null && (this.isSelected || this.isMouseOverPrioritized(game)) ? RES_HIGHLIGHTED : RES;
        manager.getTexture(res).draw(x, y, this.width, this.height);

        if(this.recipe != null){
            g.renderItemInGui(game, manager, this.getOutput(game), x+1, y+1, 1.6F, this.canConstruct ? Colors.WHITE : Colors.multiplyA(Colors.WHITE, 0.35F), false);
        }
        else{
            manager.getFont().drawString(x+4, y+1, "?", 0, 1, 0.9F, Colors.DARK_GRAY, Colors.NO_COLOR);
        }
    }

    @Override
    public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y){
        if(this.isMouseOver(game)){
            if(this.recipe != null){
                ItemInstance instance = this.getOutput(game);
                g.drawHoverInfoAtMouse(game, manager, true, 200, instance.getDisplayName()+" x"+instance.getAmount());
            }
            else{
                g.drawHoverInfoAtMouse(game, manager, false, 200, manager.localize(RockBottomAPI.createInternalRes("info.unknown_recipe")));
            }
        }
    }

    protected ItemInstance getOutput(IGameInstance game){
        List<ItemInstance> outputs = this.recipe.getOutputs();
        return outputs.get((game.getTotalTicks()/Constants.TARGET_TPS)%outputs.size());
    }

    @Override
    public boolean shouldDoFingerCursor(IGameInstance game){
        return super.shouldDoFingerCursor(game) && this.recipe != null;
    }
}
