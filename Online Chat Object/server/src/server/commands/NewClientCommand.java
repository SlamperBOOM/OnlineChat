package server.server.commands;

import commands.Message;
import commands.TextMessage;
import server.server.ClientInfo;
import server.server.OwnDateGetter;
import server.server.Server;
import server.server.ServerData;

import java.util.List;

public class NewClientCommand implements ServerCommand{
    @Override
    public void doCommand(Server server, ServerData data, Message message) {
        int ID = message.getSenderID();
        String nickname = message.getText();
        List<ClientInfo> clients = data.getClients();
        synchronized (clients) {
            clients.get(ID).setNickname(nickname);
        }
        String messageText = OwnDateGetter.getDate() + ": User <" + nickname + "> entered the chat!";
        server.broadcastMessage(new TextMessage(messageText, -1), ID);

        List<String> chat = data.getChat();
        chat.add(messageText);
        int lowBorderOfChat = chat.size()-data.getLowBorder();
        if(lowBorderOfChat < 0) lowBorderOfChat = 0;
        for(int i = lowBorderOfChat;
            i < chat.size(); ++i){
            server.sendMessage(new TextMessage(chat.get(i), -1), ID);
        }
        data.getLogger().logEvent("User <" + nickname + "> entered the chat!");
    }
}
