/*
 * This file ("Keybind.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api.data.settings;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.newdawn.slick.Input;

public class Keybind{

    private final IResourceName name;

    private int key;
    private boolean isMouse;

    public Keybind(IResourceName name, int defKey, boolean defIsMouse){
        this.name = name;
        this.key = defKey;
        this.isMouse = defIsMouse;
    }

    public void setBind(int key, boolean isMouse){
        this.key = key;
        this.isMouse = isMouse;
    }

    public boolean isDown(){
        Input input = RockBottomAPI.getGame().getInput();

        if(this.isMouse){
            return input.isMouseButtonDown(this.key);
        }
        else{
            return input.isKeyDown(this.key);
        }
    }

    public boolean isPressed(){
        Input input = RockBottomAPI.getGame().getInput();

        if(this.isMouse){
            return input.isMousePressed(this.key);
        }
        else{
            return input.isKeyPressed(this.key);
        }
    }

    public boolean isKey(int key){
        return this.key == key;
    }

    public IResourceName getName(){
        return this.name;
    }

    public int getKey(){
        return this.key;
    }

    public boolean isMouse(){
        return this.isMouse;
    }

    public Keybind register(){
        RockBottomAPI.KEYBIND_REGISTRY.register(this.getName(), this);
        return this;
    }
}
