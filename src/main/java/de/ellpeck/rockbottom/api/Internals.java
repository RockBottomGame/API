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
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api;

import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.mod.IModLoader;
import de.ellpeck.rockbottom.api.net.INetHandler;
import de.ellpeck.rockbottom.api.util.ApiInternal;

@ApiInternal
public class Internals{

    private IGameInstance game;
    private IApiHandler api;
    private IEventHandler event;
    private INetHandler net;
    private IModLoader mod;

    public void setGame(IGameInstance game){
        this.game = game;
    }

    public void setApi(IApiHandler api){
        this.api = api;
    }

    public void setEvent(IEventHandler event){
        this.event = event;
    }

    public void setNet(INetHandler net){
        this.net = net;
    }

    public void setMod(IModLoader mod){
        this.mod = mod;
    }

    public IGameInstance getGame(){
        return this.game;
    }

    public IApiHandler getApi(){
        return this.api;
    }

    public IEventHandler getEvent(){
        return this.event;
    }

    public INetHandler getNet(){
        return this.net;
    }

    public IModLoader getMod(){
        return this.mod;
    }
}
