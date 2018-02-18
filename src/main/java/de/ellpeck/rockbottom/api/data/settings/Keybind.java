/*
 * This file ("Keybind.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data.settings;

import de.ellpeck.rockbottom.api.IInputHandler;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class Keybind{

    private final IResourceName name;
    private final String stringName;

    private final int defaultKey;
    private final boolean defaultIsMouse;

    public Keybind(IResourceName name, int defKey, boolean defIsMouse){
        this.name = name;
        this.stringName = name.toString();
        this.defaultKey = defKey;
        this.defaultIsMouse = defIsMouse;
    }

    public boolean isDown(){
        IInputHandler input = RockBottomAPI.getGame().getInput();

        if(this.isMouse()){
            return input.isMouseDown(this.getKey());
        }
        else{
            return input.isKeyDown(this.getKey());
        }
    }

    public boolean isPressed(){
        IInputHandler input = RockBottomAPI.getGame().getInput();

        if(this.isMouse()){
            return input.wasMousePressed(this.getKey());
        }
        else{
            return input.wasKeyPressed(this.getKey());
        }
    }

    public String getDisplayName(){
        return RockBottomAPI.getInternalHooks().getKeyOrMouseName(this.isMouse(), this.getKey());
    }

    public boolean isKey(int key){
        return this.getKey() == key;
    }

    public IResourceName getName(){
        return this.name;
    }

    public String getStringName(){
        return this.stringName;
    }

    public int getKey(){
        return this.getBindInfo().key;
    }

    public boolean isMouse(){
        return this.getBindInfo().isMouse;
    }

    private Settings.BindInfo getBindInfo(){
        return RockBottomAPI.getGame().getSettings().keybinds.get(this.stringName);
    }

    public int getDefaultKey(){
        return this.defaultKey;
    }

    public boolean isDefaultMouse(){
        return this.defaultIsMouse;
    }

    public Keybind register(){
        RockBottomAPI.KEYBIND_REGISTRY.register(this.getName(), this);
        return this;
    }
}
