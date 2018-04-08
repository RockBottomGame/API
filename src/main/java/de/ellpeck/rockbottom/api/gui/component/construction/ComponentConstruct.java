/*
 * This file ("ComponentConstruct.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.construction.IRecipe;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public class ComponentConstruct extends GuiComponent{

    private final IRecipe recipe;
    private final boolean canConstruct;

    public ComponentConstruct(Gui gui, IRecipe recipe, boolean canConstruct){
        super(gui, 94, 17, 30, 30);
        this.recipe = recipe;
        this.canConstruct = canConstruct;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y){
        if(this.recipe != null){
            g.renderItemInGui(game, manager, this.getOutput(game), x+2, y+2, 2.6F, Colors.WHITE);
        }
    }

    @Override
    public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y){
        if(this.isMouseOver(game)){
            ItemInstance instance = this.getOutput(game);

            String s = manager.localize(ResourceName.intern("info."+(this.canConstruct ? "click_to_construct" : "missing_items")));
            g.drawHoverInfoAtMouse(game, manager, true, 200, instance.getDisplayName()+" x"+instance.getAmount(), s);
        }
    }

    protected ItemInstance getOutput(IGameInstance game){
        List<ItemInstance> outputs = this.recipe.getOutputs();
        return outputs.get((game.getTotalTicks()/Constants.TARGET_TPS)%outputs.size());
    }

    @Override
    public ResourceName getName(){
        return ResourceName.intern("construct");
    }
}
