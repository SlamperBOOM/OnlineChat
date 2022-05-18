package server.server.commands;

import commands.Message;
import server.server.Server;
import server.server.ServerData;

public interface ServerCommand {
    void doCommand(Server server, ServerData data, Message message);
}
