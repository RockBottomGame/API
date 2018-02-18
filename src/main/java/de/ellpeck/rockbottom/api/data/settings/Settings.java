/*
 * This file ("Settings.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.data.settings;

import com.google.gson.annotations.SerializedName;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

@ApiInternal
public class Settings implements IJsonSettings{

    public static final Keybind KEY_PLACE = new Keybind(RockBottomAPI.createInternalRes("place"), 1, true).register();
    public static final Keybind KEY_DESTROY = new Keybind(RockBottomAPI.createInternalRes("destroy"), 0, true).register();
    public static final Keybind KEY_GUI_ACTION_1 = new Keybind(RockBottomAPI.createInternalRes("gui_action_1"), 0, true).register();
    public static final Keybind KEY_GUI_ACTION_2 = new Keybind(RockBottomAPI.createInternalRes("gui_action_2"), 1, true).register();

    public static final Keybind KEY_INVENTORY = new Keybind(RockBottomAPI.createInternalRes("inventory"), GLFW.GLFW_KEY_E, false).register();
    public static final Keybind KEY_COMPENDIUM = new Keybind(RockBottomAPI.createInternalRes("compendium"), GLFW.GLFW_KEY_C, false).register();
    public static final Keybind KEY_MENU = new Keybind(RockBottomAPI.createInternalRes("menu"), GLFW.GLFW_KEY_ESCAPE, false).register();
    public static final Keybind KEY_LEFT = new Keybind(RockBottomAPI.createInternalRes("left"), GLFW.GLFW_KEY_A, false).register();
    public static final Keybind KEY_RIGHT = new Keybind(RockBottomAPI.createInternalRes("right"), GLFW.GLFW_KEY_D, false).register();
    public static final Keybind KEY_UP = new Keybind(RockBottomAPI.createInternalRes("up"), GLFW.GLFW_KEY_W, false).register();
    public static final Keybind KEY_DOWN = new Keybind(RockBottomAPI.createInternalRes("down"), GLFW.GLFW_KEY_S, false).register();
    public static final Keybind KEY_JUMP = new Keybind(RockBottomAPI.createInternalRes("jump"), GLFW.GLFW_KEY_SPACE, false).register();
    public static final Keybind KEY_BACKGROUND = new Keybind(RockBottomAPI.createInternalRes("background"), GLFW.GLFW_KEY_LEFT_SHIFT, false).register();
    public static final Keybind KEY_CHAT = new Keybind(RockBottomAPI.createInternalRes("chat"), GLFW.GLFW_KEY_ENTER, false).register();
    public static final Keybind KEY_ADVANCED_INFO = new Keybind(RockBottomAPI.createInternalRes("advanced_info"), GLFW.GLFW_KEY_LEFT_SHIFT, false).register();
    public static final Keybind KEY_SCREENSHOT = new Keybind(RockBottomAPI.createInternalRes("screenshot"), GLFW.GLFW_KEY_F10, false).register();
    public static final Keybind[] KEYS_ITEM_SELECTION = new Keybind[8];
    public static final int DEFAULT_GUI_COLOR = 0xFF30704E;

    static{
        int[] defKeys = new int[]{GLFW.GLFW_KEY_1, GLFW.GLFW_KEY_2, GLFW.GLFW_KEY_3, GLFW.GLFW_KEY_4, GLFW.GLFW_KEY_5, GLFW.GLFW_KEY_6, GLFW.GLFW_KEY_7, GLFW.GLFW_KEY_8};

        for(int i = 0; i < KEYS_ITEM_SELECTION.length; i++){
            KEYS_ITEM_SELECTION[i] = new Keybind(RockBottomAPI.createInternalRes("item_selection_"+i), defKeys[i], false).register();
        }
    }

    @SerializedName("autosave_interval")
    public int autosaveIntervalSeconds = 60;

    @SerializedName("text_speed")
    public float textSpeed = 0.5F;
    @SerializedName("gui_scale")
    public float guiScale = 1F;
    @SerializedName("world_scale")
    public float renderScale = 1F;
    @SerializedName("gui_color")
    public int guiColor = DEFAULT_GUI_COLOR;

    @SerializedName("hardware_cursor")
    public boolean hardwareCursor = false;
    @SerializedName("cursor_infos")
    public boolean cursorInfos = true;
    @SerializedName("fullscreen")
    public boolean fullscreen = false;
    @SerializedName("smooth_lighting")
    public boolean smoothLighting = true;

    @SerializedName("music")
    public float musicVolume = 0.5F;
    @SerializedName("sound")
    public float soundVolume = 1F;

    @SerializedName("last_server")
    public String lastServerIp = "";
    @SerializedName("locale")
    public String currentLocale = "rockbottom/us_english";

    @SerializedName("keybinds")
    public final Map<String, BindInfo> keybinds = new HashMap<>();

    public static class BindInfo{

        public int key;
        public boolean isMouse;

        public BindInfo(int key, boolean isMouse){
            this.key = key;
            this.isMouse = isMouse;
        }
    }
}
