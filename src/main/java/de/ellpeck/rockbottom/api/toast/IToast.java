package de.ellpeck.rockbottom.api.toast;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;

public interface IToast {
    void render(IGameInstance game, IAssetManager manager, IRenderer g, float x, float y);

    float getHeight();
    float getWidth();

    int getDisplayTime();
    float getMovementTime();
}
