package de.ellpeck.rockbottom.api.gui;

import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import org.newdawn.slick.Color;

public interface IMainMenuTheme{

    int TILE_AMOUNT = 16;

    TileState getState(int x, int y);

    int getBackgroundColor();
}
