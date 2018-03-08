/*
 * This file ("IMod.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.mod;

import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.gui.Gui;

public interface IMod{

    String getDisplayName();

    String getId();

    String getVersion();

    String getResourceLocation();

    default String getContentLocation(){
        return "";
    }

    default String getDescription(){
        return "";
    }

    default String[] getAuthors(){
        return new String[0];
    }

    default int getSortingPriority(){
        return 0;
    }

    default Class<? extends Gui> getModGuiClass(){
        return null;
    }

    default boolean isDisableable(){
        return true;
    }

    default boolean isRequiredOnClient(){
        return true;
    }

    default boolean isRequiredOnServer(){
        return false;
    }

    default boolean isCompatibleWithModVersion(String version){
        return version.equals(this.getVersion());
    }

    default void prePreInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    default void preInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    default void init(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    default void postInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    default void postPostInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    default void preInitAssets(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler){

    }

    default void initAssets(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler){

    }

    default void postInitAssets(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler){

    }
}
