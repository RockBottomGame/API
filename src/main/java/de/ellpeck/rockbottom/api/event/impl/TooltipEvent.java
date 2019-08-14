/*
 * This file ("TooltipEvent.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.List;

/**
 * This event is fired whenever a tooltip is rendered using {@link
 * IRenderer#describeItem(IGameInstance, IAssetManager, ItemInstance, boolean)}. Note
 * that whatever scale is applied to render the tooltip will also be applied
 * within the event. This is most likely {@link IRenderer#getGuiScale()}. This
 * event cannot be cancelled.
 */
public final class TooltipEvent extends Event {

    public final ItemInstance instance;
    public final IGameInstance game;
    public final IAssetManager assetManager;
    public final IRenderer graphics;
    public final List<String> description;

    public TooltipEvent(ItemInstance instance, IGameInstance game, IAssetManager assetManager, IRenderer graphics, List<String> description) {
        this.instance = instance;
        this.game = game;
        this.assetManager = assetManager;
        this.graphics = graphics;
        this.description = description;
    }
}
