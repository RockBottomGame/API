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

import com.google.gson.JsonObject;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.IDataManager;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.Properties;

@ApiInternal
public class Settings implements IPropSettings, IJsonSettings{

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

    public int autosaveIntervalSeconds;

    public float textSpeed;
    public float guiScale;
    public float renderScale;
    public int guiColor;

    public boolean hardwareCursor;
    public boolean cursorInfos;
    public boolean fullscreen;
    public boolean smoothLighting;

    public float musicVolume;
    public float soundVolume;

    public String lastServerIp;
    public String currentLocale;

    @Override
    public void load(Properties props){
        for(Keybind keybind : RockBottomAPI.KEYBIND_REGISTRY.getUnmodifiable().values()){
            String name = keybind.getName().toString();

            int key = this.getProp(props, name, keybind.getKey());
            boolean mouse = this.getProp(props, name+"_is_mouse", keybind.isMouse());
            keybind.setBind(key, mouse);
        }

        this.autosaveIntervalSeconds = this.getProp(props, "autosave_interval", 60);

        this.textSpeed = this.getProp(props, "text_speed", 0.5F);
        this.guiScale = this.getProp(props, "scale_gui", 1F);
        this.renderScale = this.getProp(props, "scale_world", 1F);
        this.guiColor = this.getProp(props, "gui_colors", DEFAULT_GUI_COLOR);

        this.hardwareCursor = this.getProp(props, "hardware_cursor", false);
        this.cursorInfos = this.getProp(props, "cursor_infos", true);
        this.fullscreen = this.getProp(props, "fullscreen", false);
        this.smoothLighting = this.getProp(props, "smooth_lighting", true);

        this.musicVolume = this.getProp(props, "music_volume", 0.5F);
        this.soundVolume = this.getProp(props, "sound_volume", 1F);

        this.lastServerIp = this.getProp(props, "last_server_ip", "");
        this.currentLocale = this.getProp(props, "curr_locale", "rockbottom/us_english");
    }

    @Override
    public void load(JsonObject object){
        JsonObject keybinds = object.get("keybinds").getAsJsonObject();
        for(Keybind keybind : RockBottomAPI.KEYBIND_REGISTRY.getUnmodifiable().values()){
            String name = keybind.getName().toString();

            JsonObject sub = keybinds.get(name).getAsJsonObject();
            int key = this.get(sub, "key", keybind.getKey());
            boolean mouse = this.get(sub, "is_mouse", keybind.isMouse());
            keybind.setBind(key, mouse);
        }

        this.autosaveIntervalSeconds = this.get(object, "autosave_interval", 60);

        this.textSpeed = this.get(object, "text_speed", 0.5F);
        this.guiScale = this.get(object, "scale_gui", 1F);
        this.renderScale = this.get(object, "scale_world", 1F);
        this.guiColor = this.get(object, "gui_colors", DEFAULT_GUI_COLOR);

        this.hardwareCursor = this.get(object, "hardware_cursor", false);
        this.cursorInfos = this.get(object, "cursor_infos", true);
        this.fullscreen = this.get(object, "fullscreen", false);
        this.smoothLighting = this.get(object, "smooth_lighting", true);

        this.musicVolume = this.get(object, "music_volume", 0.5F);
        this.soundVolume = this.get(object, "sound_volume", 1F);

        this.lastServerIp = this.get(object, "last_server_ip", "");
        this.currentLocale = this.get(object, "curr_locale", "rockbottom/us_english");
    }

    @Override
    public void save(JsonObject object){
        JsonObject keybinds = new JsonObject();
        for(Keybind keybind : RockBottomAPI.KEYBIND_REGISTRY.getUnmodifiable().values()){
            String name = keybind.getName().toString();

            JsonObject sub = new JsonObject();
            this.set(sub, "key", keybind.getKey());
            this.set(sub, "is_mouse", keybind.isMouse());
            keybinds.add(name, sub);
        }
        object.add("keybinds", keybinds);

        this.set(object, "autosave_interval", this.autosaveIntervalSeconds);

        this.set(object, "text_speed", this.textSpeed);
        this.set(object, "scale_gui", this.guiScale);
        this.set(object, "scale_world", this.renderScale);
        this.set(object, "gui_colors", this.guiColor);

        this.set(object, "hardware_cursor", this.hardwareCursor);
        this.set(object, "cursor_infos", this.cursorInfos);
        this.set(object, "fullscreen", this.fullscreen);
        this.set(object, "smooth_lighting", this.smoothLighting);

        this.set(object, "music_volume", this.musicVolume);
        this.set(object, "sound_volume", this.soundVolume);

        this.set(object, "last_server_ip", this.lastServerIp);
        this.set(object, "curr_locale", this.currentLocale);
    }

    @Override
    public File getFile(IDataManager manager){
        return new File(manager.getGameDir(), "settings.properties");
    }

    @Override
    public File getSettingsFile(IDataManager manager){
        return manager.getSettingsFile();
    }

    @Override
    public String getName(){
        return "Game settings";
    }

}
