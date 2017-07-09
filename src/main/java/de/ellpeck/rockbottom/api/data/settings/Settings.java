/*
 * This file ("Settings.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api.data.settings;

import de.ellpeck.rockbottom.api.data.IDataManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Settings implements IPropSettings{

    public static final float DEFAULT_GUI_R = 0.32156864F;
    public static final float DEFAULT_GUI_G = 0.5882353F;
    public static final float DEFAULT_GUI_B = 0.32156864F;
    public List<Keybind> keybinds = new ArrayList<>();
    public Keybind keyInventory = new Keybind("inventory", Input.KEY_E);
    public Keybind keyMenu = new Keybind("menu", Input.KEY_ESCAPE);
    public Keybind keyLeft = new Keybind("left", Input.KEY_A);
    public Keybind keyRight = new Keybind("right", Input.KEY_D);
    public Keybind keyUp = new Keybind("up", Input.KEY_W);
    public Keybind keyDown = new Keybind("down", Input.KEY_S);
    public Keybind keyJump = new Keybind("jump", Input.KEY_SPACE);
    public Keybind keyBackground = new Keybind("background", Input.KEY_LSHIFT);
    public Keybind keyChat = new Keybind("chat", Input.KEY_ENTER);
    public Keybind keyAdvancedInfo = new Keybind("advanced_info", Input.KEY_LSHIFT);
    public Keybind keyScreenshot = new Keybind("screenshot", Input.KEY_F10);

    public int targetFps;
    public int autosaveIntervalSeconds;

    public float textSpeed;
    public int guiScale;
    public int renderScale;
    public Color guiColor;

    public boolean hardwareCursor;
    public boolean cursorInfos;
    public boolean fullscreen;
    public boolean vsync;
    public boolean smoothLighting;

    public int buttonDestroy;
    public int buttonPlace;
    public int buttonGuiAction1;
    public int buttonGuiAction2;

    public int[] keysItemSelection = new int[8];

    public String lastServerIp;
    public String currentLocale;

    @Override
    public void load(Properties props){
        for(Keybind bind : this.keybinds){
            bind.key = this.getProp(props, "key_"+bind.name, bind.def);
        }

        this.targetFps = this.getProp(props, "target_fps", 60);
        this.autosaveIntervalSeconds = this.getProp(props, "autosave_interval", 60);

        this.textSpeed = this.getProp(props, "text_speed", 0.5F);
        this.guiScale = this.getProp(props, "gui_scale", 4);
        this.renderScale = this.getProp(props, "render_scale", 48);

        this.guiColor = new Color(this.getProp(props, "gui_r", DEFAULT_GUI_R), this.getProp(props, "gui_g", DEFAULT_GUI_G), this.getProp(props, "gui_b", DEFAULT_GUI_B));

        this.hardwareCursor = this.getProp(props, "hardware_cursor", false);
        this.cursorInfos = this.getProp(props, "cursor_infos", true);
        this.fullscreen = this.getProp(props, "fullscreen", false);
        this.vsync = this.getProp(props, "vsync", false);
        this.smoothLighting = this.getProp(props, "smooth_lighting", true);

        this.buttonDestroy = this.getProp(props, "button_destroy", Input.MOUSE_LEFT_BUTTON);
        this.buttonPlace = this.getProp(props, "button_place", Input.MOUSE_RIGHT_BUTTON);
        this.buttonGuiAction1 = this.getProp(props, "button_gui_1", Input.MOUSE_LEFT_BUTTON);
        this.buttonGuiAction2 = this.getProp(props, "button_gui_2", Input.MOUSE_RIGHT_BUTTON);

        int[] defaultKeys = new int[]{Input.KEY_1, Input.KEY_2, Input.KEY_3, Input.KEY_4, Input.KEY_5, Input.KEY_6, Input.KEY_7, Input.KEY_8};
        for(int i = 0; i < this.keysItemSelection.length; i++){
            this.keysItemSelection[i] = this.getProp(props, "key_item_select_"+i, defaultKeys[i]);
        }

        this.lastServerIp = this.getProp(props, "last_server_ip", "");
        this.currentLocale = this.getProp(props, "locale", "us_english");
    }

    @Override
    public void save(Properties props){
        for(Keybind bind : this.keybinds){
            this.setProp(props, "key_"+bind.name, bind.key);
        }

        this.setProp(props, "target_fps", this.targetFps);
        this.setProp(props, "autosave_interval", this.autosaveIntervalSeconds);

        this.setProp(props, "text_speed", this.textSpeed);
        this.setProp(props, "gui_scale", this.guiScale);
        this.setProp(props, "render_scale", this.renderScale);

        this.setProp(props, "gui_r", this.guiColor.r);
        this.setProp(props, "gui_g", this.guiColor.g);
        this.setProp(props, "gui_b", this.guiColor.b);

        this.setProp(props, "hardware_cursor", this.hardwareCursor);
        this.setProp(props, "cursor_infos", this.cursorInfos);
        this.setProp(props, "fullscreen", this.fullscreen);
        this.setProp(props, "vsync", this.vsync);
        this.setProp(props, "smooth_lighting", this.smoothLighting);

        this.setProp(props, "button_destroy", this.buttonDestroy);
        this.setProp(props, "button_place", this.buttonPlace);
        this.setProp(props, "button_gui_1", this.buttonGuiAction1);
        this.setProp(props, "button_gui_2", this.buttonGuiAction2);

        for(int i = 0; i < this.keysItemSelection.length; i++){
            this.setProp(props, "key_item_select_"+i, this.keysItemSelection[i]);
        }

        this.setProp(props, "last_server_ip", this.lastServerIp);
        this.setProp(props, "locale", this.currentLocale);
    }

    @Override
    public File getFile(IDataManager manager){
        return manager.getSettingsFile();
    }

    @Override
    public String getName(){
        return "Game settings";
    }

    private <T> void setProp(Properties props, String name, T val){
        props.setProperty(name, String.valueOf(val));
    }

    private int getProp(Properties props, String name, int def){
        return Integer.parseInt(props.getProperty(name, String.valueOf(def)));
    }

    private boolean getProp(Properties props, String name, boolean def){
        return Boolean.parseBoolean(props.getProperty(name, String.valueOf(def)));
    }

    private float getProp(Properties props, String name, float def){
        return Float.parseFloat(props.getProperty(name, String.valueOf(def)));
    }

    private String getProp(Properties props, String name, String def){
        return props.getProperty(name, def);
    }

    public class Keybind{

        public final String name;
        private final int def;
        public int key;

        public Keybind(String name, int def){
            this.def = def;
            this.name = name;

            Settings.this.keybinds.add(this);
        }
    }
}
