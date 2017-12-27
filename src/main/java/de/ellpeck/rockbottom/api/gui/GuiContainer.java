/*
 * This file ("GuiContainer.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.EntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.packet.toserver.PacketDropItem;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class GuiContainer extends Gui{

    public final List<ShiftClickBehavior> shiftClickBehaviors = new ArrayList<>();
    public final AbstractEntityPlayer player;
    public ItemInstance holdingInst;

    public GuiContainer(AbstractEntityPlayer player, int sizeX, int sizeY){
        super(sizeX, sizeY);
        this.player = player;
    }

    @Override
    public void onClosed(IGameInstance game){
        super.onClosed(game);

        if(this.holdingInst != null){
            this.dropHeldItem();
        }

        this.player.closeContainer();
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y){
        if(super.onMouseAction(game, button, x, y)){
            return true;
        }

        if(this.holdingInst != null && Settings.KEY_GUI_ACTION_1.isKey(button)){
            if(!this.isMouseOver(game)){
                this.dropHeldItem();
                this.holdingInst = null;

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canCloseWithInvKey(){
        return true;
    }

    @Override
    public void init(IGameInstance game){
        super.init(game);

        ItemContainer container = this.player.getContainer();
        for(int i = 0; i < container.getSlotAmount(); i++){
            ContainerSlot slot = container.getSlot(i);
            this.components.add(new ComponentSlot(this, slot, i, slot.x, slot.y));
        }
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IGraphics g){
        super.render(game, manager, g);

        if(this.holdingInst != null){
            float mouseX = g.getMouseInGuiX();
            float mouseY = g.getMouseInGuiY();

            g.renderItemInGui(game, manager, this.holdingInst, mouseX-4F, mouseY-4F, 0.8F, Colors.WHITE);
        }
    }

    @ApiInternal
    private void dropHeldItem(){
        if(RockBottomAPI.getNet().isClient()){
            RockBottomAPI.getNet().sendToServer(new PacketDropItem(this.player.getUniqueId(), this.holdingInst));
        }
        else{
            EntityItem.spawn(this.player.world, this.holdingInst, this.player.x, this.player.y+1, this.player.facing.x*0.25, 0);
        }
    }

    @Override
    public boolean doesPauseGame(){
        return false;
    }

    public static class ShiftClickBehavior{

        public final List<Integer> slots;
        public final List<Integer> slotsInto;
        public final BiFunction<ContainerSlot, ContainerSlot, Boolean> condition;

        public ShiftClickBehavior(List<Integer> slots, List<Integer> slotsInto, BiFunction<ContainerSlot, ContainerSlot, Boolean> condition){
            this.slots = slots;
            this.slotsInto = slotsInto;
            this.condition = condition;
        }

        public ShiftClickBehavior(int startSlot, int endSlot, int slotsIntoStart, int slotsIntoEnd, BiFunction<ContainerSlot, ContainerSlot, Boolean> condition){
            this(Util.makeIntList(startSlot, endSlot+1), Util.makeIntList(slotsIntoStart, slotsIntoEnd+1), condition);
        }

        public ShiftClickBehavior(int startSlot, int endSlot, int slotsIntoStart, int slotsIntoEnd){
            this(startSlot, endSlot, slotsIntoStart, slotsIntoEnd, null);
        }

        public ShiftClickBehavior reversed(BiFunction<ContainerSlot, ContainerSlot, Boolean> condition){
            return new ShiftClickBehavior(this.slotsInto, this.slots, condition);
        }

        public ShiftClickBehavior reversed(){
           return this.reversed(this.condition == null ? null : (slotFrom, slotTo) -> !this.condition.apply(slotFrom, slotTo));
        }
    }
}
