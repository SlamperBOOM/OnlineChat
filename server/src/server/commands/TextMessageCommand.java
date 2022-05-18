package server.server.commands;

import commands.Message;
import commands.TextMessage;
import server.server.ClientInfo;
import server.server.OwnDateGetter;
import server.server.Server;
import server.server.ServerData;

import java.util.List;

public class TextMessageCommand implements ServerCommand{
    @Override
    public void doCommand(Server server, ServerData data, Message message) {
        String messageText;
        List<ClientInfo> clients = data.getClients();
        synchronized (clients) {
            messageText = OwnDateGetter.getDate() + ": <" + clients.get(message.getSenderID()).getNickname()
                    + ">: " + message.getText();
            data.getChat().add(messageText);
            System.out.println(OwnDateGetter.getDate()+ ": Received message from client " + message.getSenderID() +
                    " <" + clients.get(message.getSenderID()).getNickname()+ ">");
        }
        server.broadcastMessage(new TextMessage(messageText, -1), message.getSenderID());
    }
}
