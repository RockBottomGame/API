package de.ellpeck.rockbottom.api.net.chat.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.set.DataSet;

public class ChatComponentText extends ChatComponent{

    private String text;

    public ChatComponentText(){
    }

    @Override
    public String getDisplayString(IGameInstance game, IAssetManager manager){
        return this.text;
    }

    @Override
    public String getRawString(){
        return this.text;
    }

    public ChatComponentText(String text){
        this.text = text;
    }

    @Override
    public void save(DataSet set){
        super.save(set);
        set.addString("text", this.text);
    }

    @Override
    public void load(DataSet set){
        super.load(set);
        this.text = set.getString("text");
    }
}
