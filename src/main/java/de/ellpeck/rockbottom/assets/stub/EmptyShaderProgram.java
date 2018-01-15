package de.ellpeck.rockbottom.assets.stub;

import de.ellpeck.rockbottom.api.assets.IShaderProgram;
import de.ellpeck.rockbottom.api.render.engine.VertexProcessor;
import org.joml.Matrix4f;

public class EmptyShaderProgram implements IShaderProgram{

    private final VertexProcessor processor = new VertexProcessor();

    @Override
    public void setDefaultValues(int width, int height){

    }

    @Override
    public void updateProjection(int width, int height){

    }

    @Override
    public void bindFragmentDataLocation(String name, int location){

    }

    @Override
    public void link(){

    }

    @Override
    public void bind(){

    }

    @Override
    public int getAttributeLocation(String name){
        return 0;
    }

    @Override
    public int getUniformLocation(String name){
        return 0;
    }

    @Override
    public void pointVertexAttribute(String name, int size){

    }

    @Override
    public void setUniform(String name, Matrix4f matrix){

    }

    @Override
    public void setUniform(String name, int value){

    }

    @Override
    public void setUniform(String name, float f){

    }

    @Override
    public void setUniform(String name, float x, float y){

    }

    @Override
    public void setUniform(String name, float x, float y, float z){

    }

    @Override
    public void unbind(){

    }

    @Override
    public int getId(){
        return 0;
    }

    @Override
    public void setVertexProcessing(int componentsPerVertex, VertexProcessor processor){

    }

    @Override
    public int getComponentsPerVertex(){
        return 0;
    }

    @Override
    public VertexProcessor getProcessor(){
        return this.processor;
    }

    @Override
    public void draw(int amount){

    }

    @Override
    public void dispose(){

    }
}