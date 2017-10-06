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
import de.ellpeck.rockbottom.api.assets.anim.IAnimation;
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.assets.local.Locale;
import de.ellpeck.rockbottom.api.assets.sound.ISound;
import de.ellpeck.rockbottom.api.assets.tex.ITexture;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.newdawn.slick.Sound;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Map;

public interface IAssetManager{

    void reloadCursor(IGameInstance game);

    <T extends IAsset> Map<IResourceName, T> getAllOfType(Class<T> type);

    <T> T getAssetWithFallback(IResourceName path, IAsset<T> fallback);

    ITexture getTexture(IResourceName path);

    IAnimation getAnimation(IResourceName path);

    ISound getSound(IResourceName path);

    Locale getLocale(IResourceName path);

    IFont getFont(IResourceName path);

    String localize(IResourceName unloc, Object... format);

    IFont getFont();

    void setFont(IFont font);

    Locale getLocale();

    void setLocale(Locale locale);

    InputStream getResourceStream(String s);

    ITexture getMissingTexture();

    SimpleDateFormat getLocalizedDateFormat();
}
