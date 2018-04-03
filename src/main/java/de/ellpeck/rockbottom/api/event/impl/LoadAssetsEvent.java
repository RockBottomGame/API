/*
 * This file ("LoadAssetsEvent.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.assets.IShaderProgram;
import de.ellpeck.rockbottom.api.event.Event;

/**
 * This event gets fired after the {@link IAssetManager} loads or reloads
 * assets. You can use this to configure {@link IShaderProgram}s or do other
 * asset-based things.
 */
public final class LoadAssetsEvent extends Event{

    public final IGameInstance game;
    public final IAssetManager assetManager;
    public final IRenderer g;

    public LoadAssetsEvent(IGameInstance game, IAssetManager assetManager, IRenderer g){
        this.game = game;
        this.assetManager = assetManager;
        this.g = g;
    }
}
