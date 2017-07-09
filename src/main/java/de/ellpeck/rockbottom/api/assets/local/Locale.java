/*
 * This file ("Locale.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.assets.local;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import org.newdawn.slick.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Locale{

    private final String name;
    private final Map<IResourceName, String> localization = new HashMap<>();

    public Locale(String name){
        this.name = name;
    }

    public static Locale fromStream(InputStream stream, String name) throws IOException{
        Locale locale = new Locale(name);

        Properties props = new Properties();
        props.load(stream);

        for(String key : props.stringPropertyNames()){
            String value = props.getProperty(key);

            try{
                locale.localization.put(RockBottomAPI.createRes(key), value);
                Log.debug("Added localization "+key+" -> "+value+" to locale "+name);
            }
            catch(IllegalArgumentException e){
                Log.error("Cannot add "+value+" to locale "+name+" because key "+key+" cannot be parsed", e);
            }
        }

        return locale;
    }

    public boolean merge(Locale otherLocale){
        if(this.name.equals(otherLocale.name)){
            this.localization.putAll(otherLocale.localization);

            Log.info("Merged locale "+this.name+" with "+otherLocale.localization.size()+" bits of additional localization information");
            return true;
        }
        else{
            return false;
        }
    }

    public String localize(Locale fallback, IResourceName unloc, Object... format){
        String loc = this.localization.get(unloc);

        if(loc == null){
            if(fallback != null && fallback != this){
                loc = fallback.localize(null, unloc, format);
            }
            else{
                loc = unloc.toString();
            }

            this.localization.put(unloc, loc);

            Log.warn("Localization with name "+unloc+" is missing from locale with name "+this.name+"!");
        }

        if(format == null || format.length <= 0){
            return loc;
        }
        else{
            return String.format(loc, format);
        }
    }
}
