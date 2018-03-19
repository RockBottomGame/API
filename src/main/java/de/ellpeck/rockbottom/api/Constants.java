/*
 * This file ("Constants.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api;

/**
 * This class houses a list of constant values that are used by the game to do
 * different things. It might be useful to have access to these values for
 * modders.
 */
public final class Constants{

    public static final String RESOURCE_SEPARATOR = "/";

    public static final int TARGET_TPS = 40;
    public static final int RANDOM_TILE_UPDATES = 5;
    public static final int RANDOM_TILE_RENDER_UPDATES = 150;

    public static final int CHUNK_SIZE = 32;
    public static final byte MAX_LIGHT = 30;

    public static final int TIME_PER_DAY = 24000;

    public static final int CHUNK_LOAD_DISTANCE = 3;
    public static final int PERSISTENT_CHUNK_DISTANCE = 1;
    public static final int CHUNK_LOAD_TIME = 250;

    public static final int ADMIN_PERMISSION = 10;

    public static final String UPDATE_LINK = "https://raw.githubusercontent.com/RockBottomGame/Changelog/master/changelog.json";
    public static final String ELLPECK_LINK = "https://ellpeck.de";
    public static final String WEBSITE_LINK = "https://rockbottom.ellpeck.de";
}
