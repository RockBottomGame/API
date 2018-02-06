/*
 * This file ("IRecipe.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.construction;

import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentConstruct;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentIngredient;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentPolaroid;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface IRecipe{

    static boolean matchesInv(IRecipe recipe, IInventory inventory){
        for(IUseInfo info : recipe.getActualInputs(inventory)){
            if(!inventory.containsResource(info)){
                return false;
            }
        }
        return true;
    }

    List<IUseInfo> getInputs();

    default List<IUseInfo> getActualInputs(IInventory inventory){
        return this.getInputs();
    }

    List<ItemInstance> getOutputs();

    default List<ItemInstance> getActualOutputs(IInventory inventory, List<ItemInstance> inputs){
        return this.getOutputs();
    }

    boolean isKnown(AbstractEntityPlayer player);

    IResourceName getName();

    default List<ComponentIngredient> getIngredientButtons(Gui gui, AbstractEntityPlayer player){
        List<ComponentIngredient> ingredients = new ArrayList<>();
        for(IUseInfo info : this.getInputs()){
            ingredients.add(new ComponentIngredient(gui, player.getInv().containsResource(info), info.getItems()));
        }
        return ingredients;
    }

    default List<ComponentPolaroid> getPolaroidButtons(Gui gui, AbstractEntityPlayer player, boolean canConstruct){
        return Collections.singletonList(new ComponentPolaroid(gui, this, canConstruct));
    }

    default ComponentConstruct getConstructButton(Gui gui, AbstractEntityPlayer player, boolean canConstruct){
        return new ComponentConstruct(gui, this, canConstruct);
    }

    /**
     * @deprecated use {@link #getPolaroidButtons(Gui, AbstractEntityPlayer, boolean)}
     * instead
     */
    @Deprecated
    default GuiComponent getCustomComponent(IInventory inventory){
        return null;
    }

    /**
     * @deprecated Use {@link #getPolaroidButtons(Gui, AbstractEntityPlayer,
     * boolean)} instead
     */
    @Deprecated
    default List<GuiComponent> getCustomComponents(IInventory inventory){
        GuiComponent custom = this.getCustomComponent(inventory);
        return custom != null ? Collections.singletonList(custom) : Collections.emptyList();
    }

    /**
     * @deprecated Partly known recipes have been removed
     */
    @Deprecated
    default boolean shouldDisplayIngredient(AbstractEntityPlayer player, IUseInfo info){
        return false;
    }

    /**
     * @deprecated Partly known recipes have been removed
     */
    @Deprecated
    default boolean shouldDisplayOutput(AbstractEntityPlayer player, ItemInstance output){
        return false;
    }
}
