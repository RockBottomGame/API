/*
 * This file ("ContentPack.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.content.pack;

public final class ContentPack{

    public static final String DEFAULT_PACK_ID = "default";

    private final String id;
    private final String name;
    private final String version;
    private final String[] author;
    private final String description;

    public ContentPack(String id, String name, String version, String[] author, String description){
        this.id = id;
        this.name = name;
        this.version = version;
        this.author = author;
        this.description = description;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getVersion(){
        return this.version;
    }

    public String[] getAuthor(){
        return this.author;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean isDefault(){
        return this.id.equals(DEFAULT_PACK_ID);
    }
}
