package server.server.commands;

import commands.ExitMessage;
import commands.Message;
import commands.NewClient;
import commands.TextMessage;
import server.server.ClientInfo;
import server.server.OwnDateGetter;
import server.server.Server;
import server.server.ServerData;

import java.util.List;

public class ExitMessageCommand implements ServerCommand{
    @Override
    public void doCommand(Server server, ServerData data, Message message) {
        int ID = message.getSenderID();
        ClientInfo client;

        List<ClientInfo> clients = data.getClients();
        server.sendMessage(new ExitMessage(-1), ID);
        synchronized (clients){
            client = clients.remove(ID);
            for(int i=ID; i<clients.size(); ++i){
                server.sendMessage(new NewClient(String.valueOf(i), -1), i);
                client.setID(i);
            }
        }
        client.stop();
        String messageText = OwnDateGetter.getDate()+ ": User <" + client.getNickname() + "> left the chat";
        data.getLogger().logEvent("User <" + client.getNickname() + "> left the chat");
        server.broadcastMessage(new TextMessage(messageText, -1), -1);
        data.getChat().add(messageText);
    }
}
