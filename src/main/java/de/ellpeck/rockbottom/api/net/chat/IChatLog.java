/*
 * This file ("IChatLog.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 */

package de.ellpeck.rockbottom.api.net.chat;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;

import java.util.List;

public interface IChatLog{

    static void registerCommand(Command command){
        RockBottomAPI.COMMAND_REGISTRY.put(command.getName(), command);
    }

    void displayMessage(String message);

    void sendPlayerMessage(String message, AbstractEntityPlayer player, String playerName);

    void sendMessageToPlayer(AbstractEntityPlayer player, String message);

    void broadcastMessage(String message);

    List<String> getMessages();
}
