/*
 * This file ("Animation.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.assets.anim;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.ellpeck.rockbottom.api.assets.tex.Texture;
import de.ellpeck.rockbottom.api.util.Util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Animation{

    private final int frameWidth;
    private final int frameHeight;

    private final Texture texture;
    private final List<AnimationRow> rows;

    public Animation(Texture texture, int frameWidth, int frameHeight, List<AnimationRow> rows){
        this.texture = texture;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.rows = rows;
    }

    public static Animation fromStream(InputStream textureStream, InputStream infoStream, String name) throws Exception{
        Texture texture = new Texture(textureStream, name, false);
        texture.setFilter(Texture.FILTER_NEAREST);

        List<AnimationRow> rows = new ArrayList<>();

        JsonObject main = new JsonParser().parse(new InputStreamReader(infoStream)).getAsJsonObject();
        JsonArray dims = main.getAsJsonArray("size");
        int frameWidth = dims.get(0).getAsInt();
        int frameHeight = dims.get(1).getAsInt();

        JsonArray data = main.getAsJsonArray("data");
        for(JsonElement element : data){
            JsonArray array = element.getAsJsonArray();
            float[] times = new float[array.size()];

            for(int i = 0; i < times.length; i++){
                times[i] = array.get(i).getAsFloat();
            }

            rows.add(new AnimationRow(times));
        }

        return new Animation(texture, frameWidth, frameHeight, rows);
    }

    public void drawFrame(int row, int frame, float x, float y, float scale, int filter){
        this.drawFrame(row, frame, x, y, scale, null, filter);
    }

    public void drawFrame(int row, int frame, float x, float y, float scale, int[] light, int filter){
        this.drawFrame(row, frame, x, y, x+scale, y+(this.frameHeight/this.frameWidth)*scale, 0, 0, this.frameWidth, this.frameHeight, light, filter);
    }

    public void drawFrame(int row, int frame, float x1, float y1, float x2, float y2, float srcX1, float srcY1, float srcX2, float srcY2, int[] light, int filter){
        if(row < 0 || row >= this.rows.size()){
            row = 0;
        }
        AnimationRow theRow = this.rows.get(row);

        if(frame < 0 || frame >= theRow.getFrameAmount()){
            frame = 0;
        }

        float srcX = frame*this.frameWidth;
        float srcY = row*this.frameHeight;

        this.texture.draw(x1, y1, x2, y2, srcX+srcX1, srcY+srcY1, srcX+srcX2, srcY+srcY2, light, filter);
    }

    public void drawRow(int row, float x, float y, float scale, int filter){
        this.drawRow(0, row, x, y, scale, null, filter);
    }

    public void drawRow(int row, float x, float y, float scale, int[] light, int filter){
        this.drawRow(0, row, x, y, x+scale, y+(this.frameHeight/this.frameWidth)*scale, 0, 0, this.frameWidth, this.frameHeight, light, filter);
    }

    public void drawRow(int row, float x1, float y1, float x2, float y2, float srcX1, float srcY1, float srcX2, float srcY2, int[] light, int filter){
        this.drawRow(0, row, x1, y1, x2, y2, srcX1, srcY1, srcX2, srcY2, light, filter);
    }

    public void drawRow(long timeOffsetMillis, int row, float x, float y, float scale, int filter){
        this.drawRow(timeOffsetMillis, row, x, y, scale, null, filter);
    }

    public void drawRow(long timeOffsetMillis, int row, float x, float y, float scale, int[] light, int filter){
        this.drawRow(timeOffsetMillis, row, x, y, x+scale, y+(this.frameHeight/this.frameWidth)*scale, 0, 0, this.frameWidth, this.frameHeight, light, filter);
    }

    public void drawRow(long timeOffsetMillis, int row, float x1, float y1, float x2, float y2, float srcX1, float srcY1, float srcX2, float srcY2, int[] light, int filter){
        if(row < 0 || row >= this.rows.size()){
            row = 0;
        }
        AnimationRow theRow = this.rows.get(row);

        long time = Util.getTimeMillis()+timeOffsetMillis;
        long runningTime = time%(long)(theRow.getTotalTime()*1000);

        int accum = 0;
        for(int i = 0; i < theRow.getFrameAmount(); i++){
            accum += theRow.getTime(i)*1000;
            if(accum >= runningTime){
                this.drawFrame(row, i, x1, y1, x2, y2, srcX1, srcY1, srcX2, srcY2, light, filter);
                break;
            }
        }
    }
}
