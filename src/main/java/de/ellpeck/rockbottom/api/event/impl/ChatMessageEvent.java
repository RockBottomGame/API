package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.net.chat.IChatLog;
import de.ellpeck.rockbottom.api.net.chat.ICommandSender;

/**
 * This event is fired when a chat message is sent in {@link IChatLog}
 * <br> Cancelling it will make the message not be sent
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
