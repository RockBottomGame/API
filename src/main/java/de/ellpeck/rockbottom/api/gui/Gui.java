/*
 * This file ("Gui.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.gui;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.impl.ComponentRenderEvent;
import de.ellpeck.rockbottom.api.event.impl.ComponentRenderOverlayEvent;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Gui{

    public static final int GRADIENT_COLOR = Colors.rgb(0F, 0F, 0F, 0.7F);
    public static final int HOVER_INFO_BACKGROUND = Colors.rgb(0F, 0F, 0F, 0.65F);
    protected final Gui parent;
    protected final boolean hasUnspecifiedBounds;
    protected int width;
    protected int height;

    protected int x;
    protected int y;
    protected List<GuiComponent> components = new ArrayList<>();

    public Gui(){
        this(null);
    }

    public Gui(Gui parent){
        this(-1, -1, parent);
    }

    public Gui(int width, int height){
        this(width, height, null);
    }

    public Gui(int width, int height, Gui parent){
        this.width = width;
        this.height = height;
        this.parent = parent;
        this.hasUnspecifiedBounds = this.width <= 0 || this.height <= 0;
    }

    public void onOpened(IGameInstance game){
        game.getInput().allowKeyboardEvents(true);
    }

    public void onClosed(IGameInstance game){
        game.getInput().allowKeyboardEvents(false);
    }

    public void init(IGameInstance game){
        if(!this.components.isEmpty()){
            this.components.clear();
        }

        this.updateDimensions(game);
    }

    protected void updateDimensions(IGameInstance game){
        if(!this.hasUnspecifiedBounds){
            this.x = (int)game.getRenderer().getWidthInGui()/2-this.width/2;
            this.y = (int)game.getRenderer().getHeightInGui()/2-this.height/2;
        }
        else{
            this.x = 0;
            this.y = 0;

            this.width = Util.ceil(game.getRenderer().getWidthInGui());
            this.height = Util.ceil(game.getRenderer().getHeightInGui());
        }
    }

    public void update(IGameInstance game){
        for(int i = 0; i < this.components.size(); i++){
            GuiComponent component = this.components.get(i);
            if(component.isActive()){
                component.update(game);
            }
            else{
                component.updateInactive(game);
            }
        }
    }

    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        for(int i = 0; i < this.components.size(); i++){
            GuiComponent component = this.components.get(i);
            if(component.isActive()){
                if(component.onMouseAction(game, button, x, y)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onKeyPressed(IGameInstance game, int button){
        for(int i = 0; i < this.components.size(); i++){
            GuiComponent component = this.components.get(i);
            if(component.isActive()){
                if(component.onKeyPressed(game, button)){
                    return true;
                }
            }
        }

        return (Settings.KEY_MENU.isKey(button) || (this.canCloseWithInvKey() && this.components.stream().allMatch(GuiComponent :: canCloseWithInvKey) && Settings.KEY_INVENTORY.isKey(button))) && this.tryEscape(game);
    }

    public boolean onCharInput(IGameInstance game, int codePoint, char[] characters){
        for(int i = 0; i < this.components.size(); i++){
            GuiComponent component = this.components.get(i);
            if(component.isActive()){
                if(component.onCharInput(game, codePoint, characters)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canCloseWithInvKey(){
        return false;
    }

    public void render(IGameInstance game, IAssetManager manager, IRenderer g){
        for(int i = this.components.size()-1; i >= 0; i--){
            GuiComponent component = this.components.get(i);
            if(component.isActive()){
                if(RockBottomAPI.getEventHandler().fireEvent(new ComponentRenderEvent(this, i, component)) != EventResult.CANCELLED){
                    component.render(game, manager, g, component.getRenderX(), component.getRenderY());
                }
            }
        }
    }

    public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g){
        for(int i = this.components.size()-1; i >= 0; i--){
            GuiComponent component = this.components.get(i);
            if(component.isActive()){
                if(RockBottomAPI.getEventHandler().fireEvent(new ComponentRenderOverlayEvent(this, i, component)) != EventResult.CANCELLED){
                    component.renderOverlay(game, manager, g, component.getRenderX(), component.getRenderY());
                }
            }
        }
    }

    protected boolean tryEscape(IGameInstance game){
        if(this.parent != null){
            game.getGuiManager().openGui(this.parent);
        }
        else{
            game.getGuiManager().closeGui();
        }

        return true;
    }

    public boolean doesPauseGame(){
        return true;
    }

    public boolean isMouseOverComponent(IGameInstance game){
        return this.components.stream().anyMatch(component -> component.isActive() && component.isMouseOver(game));
    }

    public boolean isMouseOver(IGameInstance game){
        if(game.getInput().isMouseInWindow()){
            int mouseX = (int)game.getRenderer().getMouseInGuiX();
            int mouseY = (int)game.getRenderer().getMouseInGuiY();

            boolean overSelf = mouseX >= this.x && mouseX < this.x+this.width && mouseY >= this.y && mouseY < this.y+this.height;
            return overSelf || this.isMouseOverComponent(game);
        }
        else{
            return false;
        }
    }

    public boolean hasGradient(){
        return true;
    }

    public void sortComponents(){
        this.components.sort(Comparator.comparingInt(GuiComponent :: getPriority).reversed());
    }

    public boolean isMouseOverPrioritized(IGameInstance game, GuiComponent component){
        if(component.isMouseOver(game)){
            for(GuiComponent comp : this.components){
                if(comp == component){
                    return true;
                }
                else if(comp.isMouseOver(game)){
                    break;
                }
            }
        }
        return false;
    }

    public List<GuiComponent> getComponents(){
        return this.components;
    }

    @Override
    public String toString(){
        return this.getName().toString();
    }

    public abstract ResourceName getName();

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public boolean shouldDoFingerCursor(IGameInstance game){
        for(GuiComponent component : this.components){
            if(component.shouldDoFingerCursor(game)){
                return true;
            }
        }
        return false;
    }
}
