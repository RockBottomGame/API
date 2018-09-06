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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.assets;

import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.assets.texture.stitcher.ITextureStitcher;
import de.ellpeck.rockbottom.api.gui.ISpecialCursor;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Map;

public interface IAssetManager {

    @ApiInternal
    void load();

    @ApiInternal
    void setCursor(ISpecialCursor cursor);

    <T extends IAsset> Map<ResourceName, T> getAllOfType(ResourceName identifier);

    <T extends IAsset> T getAssetWithFallback(ResourceName identifier, ResourceName path, T fallback);

    boolean hasAsset(ResourceName identifier, ResourceName path);

    ITexture getTexture(ResourceName path);

    IAnimation getAnimation(ResourceName path);

    ISound getSound(ResourceName path);

    IShaderProgram getShaderProgram(ResourceName path);

    @ApiInternal
    Locale getLocale(ResourceName path);

    @ApiInternal
    IFont getFont(ResourceName path);

    String localize(ResourceName unloc, Object... format);

    IFont getFont();

    @ApiInternal
    void setFont(IFont font);

    Locale getLocale();

    @ApiInternal
    void setLocale(Locale locale);

    InputStream getResourceStream(String s);

    URL getResourceURL(String s);

    ITexture getMissingTexture();

    SimpleDateFormat getLocalizedDateFormat();

    @ApiInternal
    ISpecialCursor pickCurrentCursor();

    ITextureStitcher getTextureStitcher();

    boolean addAsset(IAssetLoader loader, ResourceName name, IAsset asset);

    float getCursorScale();
}
