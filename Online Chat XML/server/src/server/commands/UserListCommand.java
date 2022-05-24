package server.commands;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import server.*;
import server.commands.BlocksGetter.UserListCommandBlocks;

import java.util.List;

public class UserListCommand implements ServerCommand{
    @Override
    public void doCommand(Server server, ServerData data, Message message) {
        Document doc = message.getParsedXMLFile();
        Element elem = (Element) doc.getElementsByTagName("command").item(0);
        int senderID = Integer.parseInt(elem.getChildNodes().item(0).getChildNodes().item(0).getNodeValue());

        List<ClientInfo> clients = data.getClients().getList();
        StringBuilder list = new StringBuilder();
        for(ClientInfo client: clients){
            list.append("user ").append(client.getNickname()).append(" ").append(client.getType()).append(" ");
        }
        list = new StringBuilder(list.substring(0, list.length()-1));
        Message userListMessage = new Message();
        userListMessage.getXMLFromString(UserListCommandBlocks.getBlocks(list.toString()));
        server.sendMessage(userListMessage, senderID);
        data.getLogger().logEvent("User <" + data.getClients().get(senderID).getNickname()+"> ask user list");
    }
}
