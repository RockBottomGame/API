/*
 * This file ("Command.java") is part of the RockBottomAPI by Ellpeck.
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
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api.net.chat;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Collections;
import java.util.List;

public abstract class Command{

    private final ResourceName name;
    private final String description;
    private final int level;
    private final String[] triggers;

    public Command(ResourceName name, String description, int level){
        this(name, description, level, name.getResourceName());
    }

    public Command(ResourceName name, String description, int level, String... triggers){
        this.name = name;
        this.description = description;
        this.level = level;
        this.triggers = triggers;
    }

    public ResourceName getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public String[] getTriggers(){
        return this.triggers;
    }

    public int getLevel(){
        return this.level;
    }

    public Command register(){
        RockBottomAPI.COMMAND_REGISTRY.register(this.getName(), this);
        return this;
    }

    public abstract ChatComponent execute(String[] args, ICommandSender sender, String playerName, IGameInstance game, IChatLog chat);

    public List<String> getAutocompleteSuggestions(String[] args, int argNumber, ICommandSender sender, IGameInstance game, IChatLog chat){
        return Collections.emptyList();
    }
}
