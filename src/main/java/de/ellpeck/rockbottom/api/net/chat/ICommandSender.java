package de.ellpeck.rockbottom.api.net.chat;

import java.util.UUID;

public interface ICommandSender{

    int getCommandLevel();

    String getName();

    UUID getUniqueId();

    String getChatColorFormat();

    void sendMessageTo(IChatLog chat, String message);
}
