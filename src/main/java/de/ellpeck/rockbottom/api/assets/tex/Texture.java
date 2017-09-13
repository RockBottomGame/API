/*
 * This file ("Texture.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.assets.tex;

import de.ellpeck.rockbottom.api.util.Colors;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.renderer.SGL;

import java.io.InputStream;

public class Texture extends Image{

    public Texture(Texture other){
        super(other);
    }

    public Texture(){
    }

    public Texture(String ref) throws SlickException{
        super(ref);
    }

    public Texture(String ref, Color trans) throws SlickException{
        super(ref, trans);
    }

    public Texture(String ref, boolean flipped) throws SlickException{
        super(ref, flipped);
    }

    public Texture(String ref, boolean flipped, int filter) throws SlickException{
        super(ref, flipped, filter);
    }

    public Texture(String ref, boolean flipped, int f, Color transparent) throws SlickException{
        super(ref, flipped, f, transparent);
    }

    public Texture(int width, int height) throws SlickException{
        super(width, height);
    }

    public Texture(int width, int height, int f) throws SlickException{
        super(width, height, f);
    }

    public Texture(InputStream in, String ref, boolean flipped) throws SlickException{
        super(in, ref, flipped);
    }

    public Texture(InputStream in, String ref, boolean flipped, int filter) throws SlickException{
        super(in, ref, flipped, filter);
    }

    public Texture(ImageData data){
        super(data);
    }

    public Texture(ImageData data, int f){
        super(data, f);
    }

    public Texture getSubTexture(int x, int y, int width, int height){
        this.init();

        float texOffX = x/(float)this.width*this.textureWidth+this.textureOffsetX;
        float texOffY = y/(float)this.height*this.textureHeight+this.textureOffsetY;
        float texWidth = width/(float)this.width*this.textureWidth;
        float texHeight = height/(float)this.height*this.textureHeight;

        Texture sub = new Texture();
        sub.inited = true;
        sub.texture = this.texture;
        sub.textureOffsetX = texOffX;
        sub.textureOffsetY = texOffY;
        sub.textureWidth = texWidth;
        sub.textureHeight = texHeight;

        sub.width = width;
        sub.height = height;
        sub.ref = this.ref;
        sub.centerX = width/2;
        sub.centerY = height/2;

        return sub;
    }

    public void draw(float x, float y, float width, float height, int[] light){
        this.draw(x, y, width, height, light, Colors.WHITE);
    }

    public void draw(float x, float y, float width, float height, int filter){
        this.draw(x, y, x+width, y+height, 0, 0, this.width, this.height, null, filter);
    }

    public void draw(float x, float y, float width, float height, int[] light, int filter){
        this.draw(x, y, x+width, y+height, 0, 0, this.width, this.height, light, filter);
    }

    public void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light){
        this.draw(x, y, x2, y2, srcX, srcY, srcX2, srcY2, light, Colors.WHITE);
    }

    public void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int filter){
        this.draw(x, y, x2, y2, srcX, srcY, srcX2, srcY2, null, filter);
    }

    public void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light, int filter){
        this.texture.bind();

        GL.glTranslatef(x, y, 0);
        if(this.angle != 0){
            GL.glTranslatef(this.centerX, this.centerY, 0F);
            GL.glRotatef(this.angle, 0F, 0F, 1F);
            GL.glTranslatef(-this.centerX, -this.centerY, 0F);
        }

        GL.glBegin(SGL.GL_QUADS);
        this.drawEmbedded(x, y, x2, y2, srcX, srcY, srcX2, srcY2, light, filter);
        GL.glEnd();

        if(this.angle != 0){
            GL.glTranslatef(this.centerX, this.centerY, 0F);
            GL.glRotatef(-this.angle, 0F, 0F, 1F);
            GL.glTranslatef(-this.centerX, -this.centerY, 0F);
        }
        GL.glTranslatef(-x, -y, 0);
    }

    public void drawEmbedded(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light, int filter){
        this.init();

        float width = x2-x;
        float height = y2-y;

        float texOffX = srcX/this.width*this.textureWidth+this.textureOffsetX;
        float texOffY = srcY/this.height*this.textureHeight+this.textureOffsetY;
        float texWidth = (srcX2-srcX)/this.width*this.textureWidth;
        float texHeight = (srcY2-srcY)/this.height*this.textureHeight;

        this.bindLight(light, TOP_LEFT, filter);
        GL.glTexCoord2f(texOffX, texOffY);
        GL.glVertex3f(0, 0, 0);
        this.bindLight(light, BOTTOM_LEFT, filter);
        GL.glTexCoord2f(texOffX, texOffY+texHeight);
        GL.glVertex3f(0, height, 0);
        this.bindLight(light, BOTTOM_RIGHT, filter);
        GL.glTexCoord2f(texOffX+texWidth, texOffY+texHeight);
        GL.glVertex3f(width, height, 0);
        this.bindLight(light, TOP_RIGHT, filter);
        GL.glTexCoord2f(texOffX+texWidth, texOffY);
        GL.glVertex3f(width, 0, 0);
    }

    private void bindLight(int[] light, int index, int filter){
        if(light != null){
            Colors.bind(Colors.multiply(light[index], filter));
        }
        else{
            Colors.bind(filter);
        }
    }
}
