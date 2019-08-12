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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.data.settings;

import com.google.gson.JsonObject;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.data.IDataManager;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import org.lwjgl.glfw.GLFW;

import java.io.File;

@ApiInternal
public final class Settings implements IJsonSettings {

    public static final Keybind KEY_PLACE = new Keybind(ResourceName.intern("place"), 1).register();
    public static final Keybind KEY_DESTROY = new Keybind(ResourceName.intern("destroy"), 0).register();
    public static final Keybind KEY_PICKUP = new Keybind(ResourceName.intern("pickup"), 2).register();
    public static final Keybind KEY_GUI_ACTION_1 = new Keybind(ResourceName.intern("gui_action_1"), 0).register();
    public static final Keybind KEY_GUI_ACTION_2 = new Keybind(ResourceName.intern("gui_action_2"), 1).register();

    public static final Keybind KEY_INVENTORY = new Keybind(ResourceName.intern("inventory"), GLFW.GLFW_KEY_E).register();
    public static final Keybind KEY_COMPENDIUM = new Keybind(ResourceName.intern("compendium"), GLFW.GLFW_KEY_C).register();
    public static final Keybind KEY_MENU = new Keybind(ResourceName.intern("menu"), GLFW.GLFW_KEY_ESCAPE).register();
    public static final Keybind KEY_LEFT = new Keybind(ResourceName.intern("left"), GLFW.GLFW_KEY_A).register();
    public static final Keybind KEY_RIGHT = new Keybind(ResourceName.intern("right"), GLFW.GLFW_KEY_D).register();
    public static final Keybind KEY_UP = new Keybind(ResourceName.intern("up"), GLFW.GLFW_KEY_W).register();
    public static final Keybind KEY_DOWN = new Keybind(ResourceName.intern("down"), GLFW.GLFW_KEY_S).register();
    public static final Keybind KEY_JUMP = new Keybind(ResourceName.intern("jump"), GLFW.GLFW_KEY_SPACE).register();
    public static final Keybind KEY_BACKGROUND = new Keybind(ResourceName.intern("background"), GLFW.GLFW_KEY_LEFT_SHIFT).register();
    public static final Keybind KEY_CHAT = new Keybind(ResourceName.intern("chat"), GLFW.GLFW_KEY_ENTER).register();
    public static final Keybind KEY_COMMAND = new Keybind(ResourceName.intern("command"), GLFW.GLFW_KEY_SLASH).register();
    public static final Keybind KEY_ADVANCED_INFO = new Keybind(ResourceName.intern("advanced_info"), GLFW.GLFW_KEY_LEFT_SHIFT).register();
    public static final Keybind KEY_SCREENSHOT = new Keybind(ResourceName.intern("screenshot"), GLFW.GLFW_KEY_F10).register();
    public static final Keybind KEY_PLAYER_LIST = new Keybind(ResourceName.intern("players"), GLFW.GLFW_KEY_TAB).register();
    public static final Keybind[] KEYS_ITEM_SELECTION = new Keybind[8];
    public static final int DEFAULT_GUI_COLOR = 0xFF30704E;

    static {
        int[] defKeys = new int[]{GLFW.GLFW_KEY_1, GLFW.GLFW_KEY_2, GLFW.GLFW_KEY_3, GLFW.GLFW_KEY_4, GLFW.GLFW_KEY_5, GLFW.GLFW_KEY_6, GLFW.GLFW_KEY_7, GLFW.GLFW_KEY_8};

        for (int i = 0; i < KEYS_ITEM_SELECTION.length; i++) {
            KEYS_ITEM_SELECTION[i] = new Keybind(ResourceName.intern("item_selection_" + i), defKeys[i]).register();
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

    public boolean betaTextDisplayed;

    @Override
    public void load(JsonObject object) {
        if (object.has("keybinds")) {
            JsonObject keybinds = object.get("keybinds").getAsJsonObject();
            for (Keybind keybind : Registries.KEYBIND_REGISTRY.values()) {
                String name = keybind.getName().toString();
                if (keybinds.has(name)) {
                    keybind.setBind(keybinds.get(name).getAsInt());
                }
            }
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

        this.betaTextDisplayed = this.get(object, "beta_text_displayed", false);
    }

    @Override
    public void save(JsonObject object) {
        JsonObject keybinds = new JsonObject();
        for (Keybind keybind : Registries.KEYBIND_REGISTRY.values()) {
            String name = keybind.getName().toString();
            keybinds.addProperty(name, keybind.getKey());
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

        this.set(object, "beta_text_displayed", this.betaTextDisplayed);
    }

    @Override
    public File getSettingsFile(IDataManager manager) {
        return manager.getSettingsFile();
    }

    @Override
    public String getName() {
        return "Game settings";
    }

}
