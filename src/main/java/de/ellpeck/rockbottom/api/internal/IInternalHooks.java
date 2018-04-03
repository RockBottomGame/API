/*
 * This file ("IInternalHooks.java") is part of the RockBottomAPI by Ellpeck.
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

import com.google.common.collect.Table;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.effect.ActiveEffect;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentInputField;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.state.IStateHandler;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;
import java.util.Map;

@ApiInternal
public interface IInternalHooks{

    void doDefaultEntityUpdate(Entity entity, List<ActiveEffect> effects);

    void doWorldObjectMovement(MovableWorldObject object);

    boolean doDefaultSlotMovement(IGameInstance game, int button, float x, float y, GuiContainer gui, ComponentSlot slot);

    boolean doDefaultShiftClicking(IGameInstance game, int button, GuiContainer gui, ComponentSlot slot);

    boolean placeTile(int x, int y, TileLayer layer, AbstractEntityPlayer player, ItemInstance selected, Tile tile, boolean removeItem, boolean simulate);

    //Liquid behavior kindly provided by superaxander
    void doDefaultLiquidBehavior(IWorld world, int x, int y, TileLayer layer, TileLiquid tile);

    String getKeyOrMouseName(int key);

    boolean doInputFieldKeyPress(IGameInstance game, int button, ComponentInputField field);

    boolean doInputFieldCharInput(IGameInstance game, char[] characters, ComponentInputField field);

    void doInputFieldRender(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y, ComponentInputField field);

    void doTileStateInit(TileState thisState, IResourceName name, Tile tile, Map<String, Comparable> properties, Table<String, Comparable, TileState> subStates);

    IStateHandler makeStateHandler(Tile tile);

    FormattingCode getFormattingCode(String s, int index, Map<Character, FormattingCode> defaults);

    AbstractEntityItem makeItem(IWorld world, ItemInstance inst, double x, double y, double motionX, double motionY);
}
