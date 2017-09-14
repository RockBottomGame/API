package de.ellpeck.rockbottom.api;

import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.mod.IModLoader;
import de.ellpeck.rockbottom.api.net.INetHandler;

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
