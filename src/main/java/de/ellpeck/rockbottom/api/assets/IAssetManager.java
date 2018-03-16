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

import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.assets.texture.stitcher.ITextureStitcher;
import de.ellpeck.rockbottom.api.gui.ISpecialCursor;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Map;

public interface IAssetManager{

    @ApiInternal
    void load();

    @ApiInternal
    void setCursor(ISpecialCursor cursor);

    <T extends IAsset> Map<IResourceName, T> getAllOfType(IResourceName identifier);

    <T extends IAsset> T getAssetWithFallback(IResourceName identifier, IResourceName path, T fallback);

    boolean hasAsset(IResourceName identifier, IResourceName path);

    ITexture getTexture(IResourceName path);

    IAnimation getAnimation(IResourceName path);

    ISound getSound(IResourceName path);

    IShaderProgram getShaderProgram(IResourceName path);

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

    URL getResourceURL(String s);

    ITexture getMissingTexture();

    SimpleDateFormat getLocalizedDateFormat();

    @ApiInternal
    ISpecialCursor pickCurrentCursor();

    ITextureStitcher getTextureStitcher();

    boolean addAsset(IAssetLoader loader, IResourceName name, IAsset asset);
}
