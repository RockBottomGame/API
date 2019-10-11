/*
 * This file ("ConstructEvent.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.construction.compendium.PlayerCompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;

import java.util.List;
import java.util.function.Function;

/**
 * This event is fired when construction takes place in {@link
 * IApiHandler#construct(AbstractEntityPlayer, Inventory, Inventory,
 * PlayerCompendiumRecipe, TileEntity, int, List, List, Function, float)}. Cancelling the event will
 * cause the construction not to take place.
 */
public final class ConstructEvent extends Event {
    public final AbstractEntityPlayer player;
    public Inventory inputInventory;
    public Inventory outputInventory;
    public PlayerCompendiumRecipe recipe;
    public TileEntity machine;
    public int amount;
    public List<IUseInfo> recipeInputs;
	public List<ItemInstance> actualInputs;
	public Function<List<ItemInstance>, List<ItemInstance>> outputGetter;
	public float skillReward;

    public ConstructEvent(AbstractEntityPlayer player, Inventory inputInventory, Inventory outputInventory, PlayerCompendiumRecipe recipe, TileEntity machine, int amount, List<IUseInfo> recipeInputs, List<ItemInstance> actualInputs, Function<List<ItemInstance>, List<ItemInstance>> outputGetter, float skillReward) {
        this.player = player;
        this.inputInventory = inputInventory;
        this.outputInventory = outputInventory;
        this.recipe = recipe;
        this.machine = machine;
        this.amount = amount;
        this.recipeInputs = recipeInputs;
        this.actualInputs = actualInputs;
        this.outputGetter = outputGetter;
        this.skillReward = skillReward;
    }
}
