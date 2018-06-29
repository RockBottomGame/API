/*
 * This file ("Internals.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.internal;

import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;
import de.ellpeck.rockbottom.api.content.pack.IContentPackLoader;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.mod.IModLoader;
import de.ellpeck.rockbottom.api.net.INetHandler;
import de.ellpeck.rockbottom.api.util.ApiInternal;

/**
 * An API-Internal class that holds all of the interfaces referencing back to
 * non-API classes that handle their execution. Creating instances of or using
 * this class directly in any way is discouraged and rather useless.
 */
@ApiInternal
public final class Internals {

    private IGameInstance game;
    private IApiHandler api;
    private IEventHandler event;
    private INetHandler net;
    private IModLoader mod;
    private IContentPackLoader content;
    private IResourceRegistry resource;
    private IInternalHooks hooks;

    public IResourceRegistry getResource() {
        return this.resource;
    }

    public void setResource(IResourceRegistry resource) {
        this.resource = resource;
    }

    public IGameInstance getGame() {
        return this.game;
    }

    public void setGame(IGameInstance game) {
        this.game = game;
    }

    public IApiHandler getApi() {
        return this.api;
    }

    public void setApi(IApiHandler api) {
        this.api = api;
    }

    public IEventHandler getEvent() {
        return this.event;
    }

    public void setEvent(IEventHandler event) {
        this.event = event;
    }

    public INetHandler getNet() {
        return this.net;
    }

    public void setNet(INetHandler net) {
        this.net = net;
    }

    public IModLoader getMod() {
        return this.mod;
    }

    public void setMod(IModLoader mod) {
        this.mod = mod;
    }

    public IInternalHooks getHooks() {
        return this.hooks;
    }

    public void setHooks(IInternalHooks hooks) {
        this.hooks = hooks;
    }

    public IContentPackLoader getContent() {
        return this.content;
    }

    public void setContent(IContentPackLoader content) {
        this.content = content;
    }
}
