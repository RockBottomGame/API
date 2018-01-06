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
import org.joml.Matrix4f;

public interface IShaderProgram extends IDisposable, IAsset{

    void setDefaultValues(int width, int height);

    void updateProjection(int width, int height);

    void bindFragmentDataLocation(String name, int location);

    void link();

    void bind();

    int getAttributeLocation(String name);

    int getUniformLocation(String name);

    void pointVertexAttribute(boolean enable, String name, int size, int stride, int offset);

    void setUniform(String name, Matrix4f matrix);

    void setUniform(String name, int value);

    void setUniform(String name, float f);

    void setUniform(String name, float x, float y);

    void setUniform(String name, float x, float y, float z);

    void unbind();

    int getId();

    void setComponentsPerVertex(int components);

    int getComponentsPerVertex();
}
