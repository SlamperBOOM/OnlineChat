package server;

import server.commands.Clients;

import java.util.List;
import java.util.Properties;

public interface ServerData {
    int getLowBorder();
    List<ChatItem> getChat();
    Clients getClients();
    Properties getCommands();
    ServerLogger getLogger();
}
