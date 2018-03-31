/*
 * This file ("ChatComponentTranslation.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.net.chat.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.util.Arrays;

public class ChatComponentTranslation extends ChatComponent{

    private IResourceName key;
    private String[] formatting;

    public ChatComponentTranslation(){
    }

    public ChatComponentTranslation(IResourceName key, String... formatting){
        this.key = key;
        this.formatting = formatting;
    }

    @Override
    public void save(DataSet set){
        super.save(set);
        set.addString("key", this.key.toString());

        set.addInt("format_amount", this.formatting.length);
        for(int i = 0; i < this.formatting.length; i++){
            set.addString("format_"+i, this.formatting[i]);
        }
    }

    @Override
    public void load(DataSet set){
        super.load(set);
        this.key = RockBottomAPI.createRes(set.getString("key"));

        this.formatting = new String[set.getInt("format_amount")];
        for(int i = 0; i < this.formatting.length; i++){
            this.formatting[i] = set.getString("format_"+i);
        }
    }

    @Override
    public String getDisplayString(IGameInstance game, IAssetManager manager){
        return manager.localize(this.key, (Object[])this.formatting);
    }

    @Override
    public String getUnformattedString(){
        return "Locale("+this.key+", "+Arrays.toString(this.formatting)+')';
    }
}
