/*
 * This file ("ChatMessageEvent.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.net.chat.IChatLog;
import de.ellpeck.rockbottom.api.net.chat.ICommandSender;

/**
 * This event is fired when a message is sent in an {@link IChatLog}. Changing
 * the message variable will result in the input message being changed (meaning
 * changing it to a command will execute a command). Cancelling the event will
 * result in no message being sent.
 */
public class ChatMessageEvent extends Event{

    public final IChatLog log;
    public final ICommandSender sender;
    public String message;

    public ChatMessageEvent(IChatLog log, ICommandSender sender, String message){
        this.log = log;
        this.sender = sender;
        this.message = message;
    }
}
