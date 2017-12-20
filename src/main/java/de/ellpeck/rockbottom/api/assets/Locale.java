/*
 * This file ("Locale.java") is part of the RockBottomAPI by Ellpeck.
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

import com.google.common.base.Charsets;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.ApiInternal;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@ApiInternal
public class Locale implements IAsset{

    private final String name;
    private final Map<IResourceName, String> localization = new HashMap<>();

    public Locale(String name){
        this.name = name;
    }

    public static Locale fromStream(InputStream stream, String name) throws Exception{
        Locale locale = new Locale(name);

        JsonElement main = Util.JSON_PARSER.parse(new InputStreamReader(stream, Charsets.UTF_8));

        for(Entry<String, JsonElement> entry : main.getAsJsonObject().entrySet()){
            recurseLoad(locale, entry.getKey(), "", entry.getValue());
        }

        return locale;
    }

    private static void recurseLoad(Locale locale, String domain, String name, JsonElement element){
        if(element.isJsonPrimitive()){
            String key = domain+Constants.RESOURCE_SEPARATOR+name;
            String value = element.getAsJsonPrimitive().getAsString();

            locale.localization.put(RockBottomAPI.createRes(key), value);
            RockBottomAPI.logger().config("Added localization "+key+" -> "+value+" to locale with name "+locale.name);
        }
        else{
            for(Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()){
                String key = entry.getKey();
                recurseLoad(locale, domain, name.isEmpty() ? key : ("*".equals(key) ? name : name+"."+key), entry.getValue());
            }
        }
    }

    public boolean merge(Locale otherLocale){
        if(this.name.equals(otherLocale.name)){
            this.localization.putAll(otherLocale.localization);

            RockBottomAPI.logger().config("Merged locale "+this.name+" with "+otherLocale.localization.size()+" bits of additional localization information");
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

            RockBottomAPI.logger().warning("Localization with name "+unloc+" is missing from locale with name "+this.name+"!");
        }

        if(format == null || format.length <= 0){
            return loc;
        }
        else{
            return String.format(loc, format);
        }
    }
}
