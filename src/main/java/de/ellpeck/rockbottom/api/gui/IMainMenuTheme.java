package de.ellpeck.rockbottom.api.gui;

import de.ellpeck.rockbottom.api.tile.state.TileState;

public interface IMainMenuTheme{

    int TILE_AMOUNT = 16;

    TileState getState(int x, int y);

    int getBackgroundColor();
}
