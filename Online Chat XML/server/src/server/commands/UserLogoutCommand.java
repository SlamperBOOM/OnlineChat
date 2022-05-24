package server.commands;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import server.*;
import server.commands.BlocksGetter.SuccessCommandBlocks;
import server.commands.BlocksGetter.UserLogoutCommandBlocks;

import java.util.List;

public class UserLogoutCommand implements ServerCommand{
    @Override
    public void doCommand(Server server, ServerData data, Message message) {
        Document doc = message.getParsedXMLFile();
        Element elem = (Element) doc.getElementsByTagName("command").item(0);

        int senderID = Integer.parseInt(elem.getElementsByTagName("session").item(0).getChildNodes().item(0).getNodeValue());
        Clients clients = data.getClients();
        ClientInfo client = clients.get(senderID);

        if(client == null){
            data.getLogger().logEvent("Unknown client tried to logout");
        }else {
            Message exitMessage = new Message();
            List<String> blocks = UserLogoutCommandBlocks.getBlocks(client.getNickname());
            exitMessage.getXMLFromString(blocks);
            int exitID = client.getID();
            server.broadcastMessage(exitMessage, senderID);
            data.getChat().add(new ChatItem(blocks));
            clients.remove(exitID);

            Message successMessage = new Message();
            successMessage.getXMLFromString(SuccessCommandBlocks.getBlocks());
            client.sendMessage(successMessage);
            client.stop();
            data.getLogger().logEvent("User <" + client.getNickname() + "> disconnected from chat");
        }
    }
}
