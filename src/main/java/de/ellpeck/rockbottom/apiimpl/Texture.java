package de.ellpeck.rockbottom.apiimpl;

import de.ellpeck.rockbottom.api.assets.tex.ITexture;
import de.ellpeck.rockbottom.api.util.Colors;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.renderer.SGL;

import java.io.InputStream;

public class Texture extends Image implements ITexture{

    public Texture(){
    }

    public Texture(int width, int height) throws SlickException{
        super(width, height, FILTER_NEAREST);
    }

    public Texture(ImageData data){
        super(data, FILTER_NEAREST);
    }

    public Texture(InputStream in, String ref, boolean flipped) throws SlickException{
        super(in, ref, flipped, FILTER_NEAREST);
    }

    public Texture getSubTexture(int x, int y, int width, int height){
        this.init();

        float texOffsetX = ((x/(float)this.width)*this.textureWidth)+this.textureOffsetX;
        float texOffsetY = ((y/(float)this.height)*this.textureHeight)+this.textureOffsetY;
        float texWidth = ((width/(float)this.width)*this.textureWidth);
        float texHeight = ((height/(float)this.height)*this.textureHeight);

        Texture sub = new Texture();
        sub.inited = true;
        sub.texture = this.texture;
        sub.textureOffsetX = texOffsetX;
        sub.textureOffsetY = texOffsetY;
        sub.textureWidth = texWidth;
        sub.textureHeight = texHeight;

        sub.width = width;
        sub.height = height;
        sub.ref = this.ref;
        sub.centerX = width/2;
        sub.centerY = height/2;

        return sub;
    }

    @Override
    public void draw(float x, float y, float width, float height, int[] light){
        this.draw(x, y, width, height, light, Colors.WHITE);
    }

    @Override
    public void draw(float x, float y, float width, float height, int filter){
        this.draw(x, y, x+width, y+height, 0, 0, this.width, this.height, null, filter);
    }

    @Override
    public void draw(float x, float y, float width, float height, int[] light, int filter){
        this.draw(x, y, x+width, y+height, 0, 0, this.width, this.height, light, filter);
    }

    @Override
    public void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light){
        this.draw(x, y, x2, y2, srcX, srcY, srcX2, srcY2, light, Colors.WHITE);
    }

    @Override
    public void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int filter){
        this.draw(x, y, x2, y2, srcX, srcY, srcX2, srcY2, null, filter);
    }

    @Override
    public void draw(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light, int filter){
        this.texture.bind();

        GL.glTranslatef(x, y, 0F);
        if(this.angle != 0F){
            GL.glTranslatef(this.centerX, this.centerY, 0F);
            GL.glRotatef(this.angle, 0F, 0F, 1F);
            GL.glTranslatef(-this.centerX, -this.centerY, 0F);
        }

        GL.glBegin(SGL.GL_QUADS);
        this.drawEmbedded(x, y, x2, y2, srcX, srcY, srcX2, srcY2, light, filter);
        GL.glEnd();

        if(this.angle != 0F){
            GL.glTranslatef(this.centerX, this.centerY, 0F);
            GL.glRotatef(-this.angle, 0F, 0F, 1F);
            GL.glTranslatef(-this.centerX, -this.centerY, 0F);
        }
        GL.glTranslatef(-x, -y, 0F);
    }

    private void drawEmbedded(float x, float y, float x2, float y2, float srcX, float srcY, float srcX2, float srcY2, int[] light, int filter){
        this.init();

        float width = x2-x;
        float height = y2-y;

        float texOffX = srcX/this.width*this.textureWidth+this.textureOffsetX;
        float texOffY = srcY/this.height*this.textureHeight+this.textureOffsetY;
        float texWidth = (srcX2-srcX)/this.width*this.textureWidth;
        float texHeight = (srcY2-srcY)/this.height*this.textureHeight;

        this.bindLight(light, TOP_LEFT, filter);
        GL.glTexCoord2f(texOffX, texOffY);
        GL.glVertex3f(0F, 0F, 0F);
        this.bindLight(light, BOTTOM_LEFT, filter);
        GL.glTexCoord2f(texOffX, texOffY+texHeight);
        GL.glVertex3f(0F, height, 0F);
        this.bindLight(light, BOTTOM_RIGHT, filter);
        GL.glTexCoord2f(texOffX+texWidth, texOffY+texHeight);
        GL.glVertex3f(width, height, 0F);
        this.bindLight(light, TOP_RIGHT, filter);
        GL.glTexCoord2f(texOffX+texWidth, texOffY);
        GL.glVertex3f(width, 0F, 0F);
    }

    private void bindLight(int[] light, int index, int filter){
        if(light != null){
            Colors.bind(Colors.multiply(light[index], filter));
        }
        else{
            Colors.bind(filter);
        }
    }

    @Override
    public int getTextureColor(int x, int y){
        this.init();

        if(this.pixelData == null){
            this.pixelData = this.texture.getTextureData();
        }

        int offX = (int)(this.textureOffsetX*this.texture.getTextureWidth());
        int offY = (int)(this.textureOffsetY*this.texture.getTextureHeight());

        x = (this.textureWidth < 0 ? offX-x : offX+x)%this.texture.getTextureWidth();
        y = (this.textureHeight < 0 ? offY-y : offY+y)%this.texture.getTextureHeight();

        int offset = (x+(y*this.texture.getTextureWidth()))*(this.texture.hasAlpha() ? 4 : 3);

        if(this.texture.hasAlpha()){
            return Colors.rgb(this.translate(this.pixelData[offset]), this.translate(this.pixelData[offset+1]), this.translate(this.pixelData[offset+2]), this.translate(this.pixelData[offset+3]));
        }
        else{
            return Colors.rgb(this.translate(this.pixelData[offset]), this.translate(this.pixelData[offset+1]), this.translate(this.pixelData[offset+2]));
        }
    }

    private int translate(byte b){
        return b < 0 ? 256+b : b;
    }
}