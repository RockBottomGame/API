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

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.tex.Texture;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

                int frameAmount = Integer.parseInt(split[0]);
                float frameTime = Float.parseFloat(split[1]);

                AnimationRow row = new AnimationRow(frameAmount, frameTime);
                rows.add(row);
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
        this.texture.draw(x, y, x+1F, y+(this.frameHeight/this.frameWidth), srcX, srcY, srcX+this.frameWidth, srcY+this.frameHeight, filter);
    }

    public void drawRow(int timer, int row, float x, float y, float scale, Color filter){
        if(row < 0 || row >= this.rows.size()){
            row = 0;
        }
        AnimationRow theRow = this.rows.get(row);

        int frame = (int)((timer/(theRow.getFrameTime()*Constants.TARGET_TPS))%theRow.getFrameAmount());
        this.drawFrame(row, frame, x, y, scale, filter);
    }
}
