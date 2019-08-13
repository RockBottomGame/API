/*
 * This file ("IShaderProgram.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.assets;

import de.ellpeck.rockbottom.api.render.engine.IDisposable;
import de.ellpeck.rockbottom.api.render.engine.VertexProcessor;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import org.joml.Matrix4f;

public interface IShaderProgram extends IDisposable, IAsset {

    ResourceName ID = ResourceName.intern("shader");
    ResourceName GUI_SHADER = ResourceName.intern("gui");
    ResourceName WORLD_SHADER = ResourceName.intern("world");
    ResourceName BREAK_SHADER = ResourceName.intern("break");

    void setDefaultValues(int width, int height);

    void updateProjection(int width, int height);

    void bindFragmentDataLocation(String name, int location);

    void link();

    IShaderProgram bind();

    int getAttributeLocation(String name);

    int getUniformLocation(String name);

    void pointVertexAttribute(String name, int size);

    IShaderProgram setUniform(String name, Matrix4f matrix);

    IShaderProgram setUniform(String name, int value);

    IShaderProgram setUniform(String name, float f);

    IShaderProgram setUniform(String name, float x, float y);

    IShaderProgram setUniform(String name, float x, float y, float z);

    IShaderProgram unbind();

    int getId();

    void setVertexProcessing(int componentsPerVertex, VertexProcessor processor);

    int getComponentsPerVertex();

    VertexProcessor getProcessor();

    void draw(int amount);
}
