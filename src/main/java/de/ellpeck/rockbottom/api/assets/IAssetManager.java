/*
 * This file ("IAssetManager.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.assets;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.gui.ISpecialCursor;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Map;

public interface IAssetManager{

    @ApiInternal
    void setCursor(IGameInstance game, ISpecialCursor cursor);

    <T extends IAsset> Map<IResourceName, T> getAllOfType(Class<T> type);

    <T extends IAsset> T getAssetWithFallback(IResourceName path, T fallback);

    ITexture getTexture(IResourceName path);

    IAnimation getAnimation(IResourceName path);

    ISound getSound(IResourceName path);

    @ApiInternal
    Locale getLocale(IResourceName path);

    @ApiInternal
    IFont getFont(IResourceName path);

    String localize(IResourceName unloc, Object... format);

    IFont getFont();

    @ApiInternal
    void setFont(IFont font);

    Locale getLocale();

    @ApiInternal
    void setLocale(Locale locale);

    InputStream getResourceStream(String s);

    ITexture getMissingTexture();

    SimpleDateFormat getLocalizedDateFormat();
}
