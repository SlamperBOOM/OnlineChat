package server.commands;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import server.*;
import server.commands.BlocksGetter.ErrorMessageBlocks;
import server.commands.BlocksGetter.SessionMessageBlocks;
import server.commands.BlocksGetter.UserLoginCommandBlocks;

import java.util.List;

public class UserLoginCommand implements ServerCommand{
    @Override
    public void doCommand(Server server, ServerData data, Message message) {
        Document doc = message.getParsedXMLFile();
        Element elem = (Element) doc.getElementsByTagName("command").item(0);

        String nickname = elem.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
        String type = elem.getElementsByTagName("type").item(0).getChildNodes().item(0).getNodeValue();
        Clients clients = data.getClients();

        ClientInfo client = data.getClients().get(message.getID());
        if(nickname.split(" ").length > 1){
            Message errorMessage = new Message();
            errorMessage.getXMLFromString(ErrorMessageBlocks.getBlocks("Please create nickname without spaces"));
            clients.remove(client.getID());
            client.sendMessage(errorMessage);
            client.stop();
            data.getLogger().logEvent("Client "+client.getID()+ " sent wrong nickname, closing connection");
            return;
        }
        if(clients.checkNickname(nickname)){
            Message errorMessage = new Message();
            errorMessage.getXMLFromString(ErrorMessageBlocks.getBlocks("This nickname already in use"));
            clients.remove(client.getID());
            client.sendMessage(errorMessage);
            client.stop();
            data.getLogger().logEvent("Client "+client.getID()+ " sent wrong nickname, closing connection");
            return;
        }
        client.setNickname(nickname);
        client.setType(type);
        Message broadcastMessage = new Message();
        List<String> blocks = UserLoginCommandBlocks.getBlocks(nickname);
        broadcastMessage.getXMLFromString(blocks);
        for(int i = Math.max(data.getChat().size() - data.getLowBorder(), 0); i<data.getChat().size(); ++i){
            Message historyMessage = new Message();
            historyMessage.getXMLFromString(data.getChat().get(i).getItem());
            client.sendMessage(historyMessage);
        }
        data.getChat().add(new ChatItem(blocks));
        server.broadcastMessage(broadcastMessage, client.getID());

        Message messageToConnectedClient = new Message();
        messageToConnectedClient.getXMLFromString(SessionMessageBlocks.getBlocks(String.valueOf(client.getID())));
        client.sendMessage(messageToConnectedClient);
        data.getLogger().logEvent("User <" + nickname + "> connected to the chat");
    }
}
