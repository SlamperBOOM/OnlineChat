package server.commands;

import server.Message;
import server.Server;
import server.ServerData;

public interface ServerCommand {
    void doCommand(Server server, ServerData data, Message message);
}
