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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.gui;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.gui.container.SlotContainer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public abstract class ContainerGui extends Gui {

    public final List<ShiftClickBehavior> shiftClickBehaviors = new ArrayList<>();
    public final AbstractPlayerEntity player;
    private ItemContainer container;

    public ContainerGui(AbstractPlayerEntity player, int sizeX, int sizeY) {
        super(sizeX, sizeY);
        this.player = player;
    }

    @Override
    public void onClosed(IGameInstance game) {
        super.onClosed(game);

        if (this.container.holdingInst != null) {
            RockBottomAPI.getInternalHooks().dropHeldItem(this.player, this.container);
        }

        if (this.shouldCloseContainer() && this.player.getContainer() == this.container) {
            this.player.closeContainer();
        }
    }

    @Override
    public boolean onMouseAction(IGameInstance game, int button, float x, float y) {
        if (super.onMouseAction(game, button, x, y)) {
            return true;
        }

        if (this.container.holdingInst != null && Settings.KEY_GUI_ACTION_1.isKey(button)) {
            if (!this.isMouseOver(game)) {
                RockBottomAPI.getInternalHooks().dropHeldItem(this.player, this.container);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canCloseWithInvKey() {
        return true;
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);

        this.container = this.player.getContainer();
        for (int i = 0; i < this.container.getSlotAmount(); i++) {
            SlotContainer slot = this.container.getSlot(i);
            this.components.add(slot.getGraphicalSlot(this, i, this.getSlotOffsetX(), this.getSlotOffsetY()));
        }
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g) {
        super.render(game, manager, g);

        if (this.container.holdingInst != null) {
            float mouseX = g.getMouseInGuiX();
            float mouseY = g.getMouseInGuiY();

            g.renderItemInGui(game, manager, this.container.holdingInst, mouseX - 2F, mouseY - 2F, 0.85F, Colors.WHITE, true, false);
        }
    }

    @Override
    public boolean doesPauseGame() {
        return false;
    }

    public boolean shouldCloseContainer() {
        return true;
    }

    public int getSlotOffsetX() {
        return 0;
    }

    public int getSlotOffsetY() {
        return 0;
    }

    public ItemContainer getContainer() {
        return this.container;
    }

    public static class ShiftClickBehavior {

        public final List<Integer> slots;
        public final List<Integer> slotsInto;
        public final BiFunction<SlotContainer, SlotContainer, Boolean> condition;

        public ShiftClickBehavior(List<Integer> slots, List<Integer> slotsInto, BiFunction<SlotContainer, SlotContainer, Boolean> condition) {
            this.slots = slots;
            this.slotsInto = slotsInto;
            this.condition = condition;
        }

        public ShiftClickBehavior(int startSlot, int endSlot, int slotsIntoStart, int slotsIntoEnd, BiFunction<SlotContainer, SlotContainer, Boolean> condition) {
            this(Util.makeIntList(startSlot, endSlot + 1), Util.makeIntList(slotsIntoStart, slotsIntoEnd + 1), condition);
        }

        public ShiftClickBehavior(int startSlot, int endSlot, int slotsIntoStart, int slotsIntoEnd) {
            this(startSlot, endSlot, slotsIntoStart, slotsIntoEnd, null);
        }

        public ShiftClickBehavior reversed(BiFunction<SlotContainer, SlotContainer, Boolean> condition) {
            return new ShiftClickBehavior(this.slotsInto, this.slots, condition);
        }

        public ShiftClickBehavior reversed() {
            return this.reversed(this.condition == null ? null : (slotFrom, slotTo) -> !this.condition.apply(slotFrom, slotTo));
        }
    }
}
