package server.server.commands;

import commands.Message;
import commands.UserList;
import server.server.ClientInfo;
import server.server.OwnDateGetter;
import server.server.Server;
import server.server.ServerData;

import java.util.List;

public class UserListCommand implements ServerCommand{
    @Override
    public void doCommand(Server server, ServerData data, Message message) {
        StringBuilder userNicknames = new StringBuilder();
        List<ClientInfo> clients = data.getClients();
        synchronized (clients){
            for(ClientInfo client:clients){
                userNicknames.append(client.getNickname()).append("\n");
            }
            userNicknames.deleteCharAt(userNicknames.length()-1);
            System.out.println(OwnDateGetter.getDate() + ": User <" +
                    clients.get(message.getSenderID()).getNickname() + "> asked user list");
        }
        server.sendMessage(new UserList(userNicknames.toString(), -1), message.getSenderID());
    }
}
