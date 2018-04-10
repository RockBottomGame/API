/*
 * This file ("ComponentStatistic.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.function.Supplier;

public abstract class ComponentStatistic extends GuiComponent{

    private final Supplier<String> nameSupplier;
    private final Supplier<String> valueSupplier;
    private final int priority;

    public ComponentStatistic(Gui gui, Supplier<String> nameSupplier, Supplier<String> valueSupplier, int priority){
        super(gui, 0, 0, 150, 18);
        this.nameSupplier = nameSupplier;
        this.valueSupplier = valueSupplier;
        this.priority = priority;
    }

    @Override
    public int getPriority(){
        return this.priority;
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y){
        g.addFilledRect(x, y, this.width, this.height, getUnselectedElementColor());
        g.addEmptyRect(x, y, this.width, this.height, getElementOutlineColor());

        this.renderStatGraphic(game, manager, g, x+2, y+2);

        IFont font = manager.getFont();
        font.drawSplitString(x+20, y+2, this.nameSupplier.get(), 0.35F, this.width-50);
        font.drawStringFromRight(x+this.width-1, y+this.height-17, this.valueSupplier.get(), 0.8F);
    }

    public abstract void renderStatGraphic(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y);

    @Override
    public ResourceName getName(){
        return ResourceName.intern("statistic");
    }

    @Override
    public boolean shouldDoFingerCursor(IGameInstance game){
        return false;
    }
}
