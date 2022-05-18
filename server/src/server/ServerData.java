package server.server;

import java.util.List;

public interface ServerData {
    int getLowBorder();
    List<String> getChat();
    List<ClientInfo> getClients();
}
