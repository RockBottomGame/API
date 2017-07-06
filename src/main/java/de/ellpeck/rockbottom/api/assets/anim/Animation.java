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

import de.ellpeck.rockbottom.api.assets.tex.Texture;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.io.BufferedReader;
import java.io.IOException;
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

    public static Animation fromStream(InputStream textureStream, InputStream infoStream, String name) throws IOException, SlickException{
        Texture texture = new Texture(textureStream, name, false);
        texture.setFilter(Texture.FILTER_NEAREST);

        BufferedReader reader = new BufferedReader(new InputStreamReader(infoStream));

        String line = reader.readLine();

        String[] aspect = line.split(",");
        int frameWidth = Integer.parseInt(aspect[0]);
        int frameHeight = Integer.parseInt(aspect[1]);

        List<AnimationRow> rows = new ArrayList<>();

        while(true){
            line = reader.readLine();

            if(line != null && !line.isEmpty()){
                String[] split = line.split(",");

                float[] times = new float[split.length];
                for(int i = 0; i < times.length; i++){
                    times[i] = Float.parseFloat(split[i]);
                }

                rows.add(new AnimationRow(times));
            }
            else{
                break;
            }
        }

        return new Animation(texture, frameWidth, frameHeight, rows);
    }

    public void drawFrame(int row, int frame, float x, float y, float scale, Color filter){
        if(row < 0 || row >= this.rows.size()){
            row = 0;
        }
        AnimationRow theRow = this.rows.get(row);

        if(frame < 0 || frame >= theRow.getFrameAmount()){
            frame = 0;
        }

        float srcX = frame*this.frameWidth;
        float srcY = row*this.frameHeight;
        this.texture.draw(x, y, x+scale, y+(this.frameHeight/this.frameWidth)*scale, srcX, srcY, srcX+this.frameWidth, srcY+this.frameHeight, filter);
    }

    public void drawRow(int row, float x, float y, float scale, Color filter){
        if(row < 0 || row >= this.rows.size()){
            row = 0;
        }
        AnimationRow theRow = this.rows.get(row);

        int runningTime = (int)System.currentTimeMillis()%(int)(theRow.getTotalTime()*1000);

        int accum = 0;
        for(int i = 0; i < theRow.getFrameAmount(); i++){
            accum += theRow.getTime(i)*1000;
            if(accum >= runningTime){
                this.drawFrame(row, i, x, y, scale, filter);
                break;
            }
        }
    }
}
