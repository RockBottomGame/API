/*
 * This file ("VertexProcessor.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.render.engine;

import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.util.Colors;

public class VertexProcessor {

    public void addTexturedRegion(IRenderer renderer, ITexture texture, float x, float y, float x2, float y2, float x3, float y3, float x4, float y4, float u, float v, float u2, float v2, int topLeft, int bottomLeft, int bottomRight, int topRight) {
        renderer.addTriangle(x, y, x2, y2, x3, y3, topLeft, bottomLeft, bottomRight, u, v, u, v2, u2, v2);
        renderer.addTriangle(x, y, x3, y3, x4, y4, topLeft, bottomRight, topRight, u, v, u2, v2, u2, v);
    }

    public void addTriangle(IRenderer renderer, float x1, float y1, float x2, float y2, float x3, float y3, int color1, int color2, int color3, float u1, float v1, float u2, float v2, float u3, float v3) {
        renderer.addVertex(x1, y1, color1, u1, v1);
        renderer.addVertex(x2, y2, color2, u2, v2);
        renderer.addVertex(x3, y3, color3, u3, v3);
    }

    public void addVertex(IRenderer renderer, float x, float y, int color, float u, float v) {
        renderer.put(x).put(y)
                .put(Colors.getR(color)).put(Colors.getG(color)).put(Colors.getB(color)).put(Colors.getA(color))
                .put(u).put(v);
    }

    public void onVertexCompleted(IRenderer renderer) {

    }

    public void onBegin(IRenderer renderer) {

    }

    public void onEnd(IRenderer renderer) {

    }

    public void onFlush(IRenderer renderer) {

    }
}
