package server.commands;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import server.*;
import server.commands.BlocksGetter.MessageCommandBlocks;
import server.commands.BlocksGetter.SuccessCommandBlocks;

import java.util.List;

public class MessageCommand implements ServerCommand{
    @Override
    public void doCommand(Server server, ServerData data, Message message) {
        Document doc = message.getParsedXMLFile();
        Element elem = (Element)doc.getElementsByTagName("command").item(0);
        String text = elem.getElementsByTagName("message").item(0).getChildNodes().item(0).getNodeValue();
        int senderID = Integer.parseInt(elem.getElementsByTagName("session").item(0).getChildNodes().item(0).getNodeValue());

        Message messageToSender = new Message();
        messageToSender.getXMLFromString(SuccessCommandBlocks.getBlocks());
        server.sendMessage(messageToSender, senderID);

        Message messageToEveryone = new Message();
        List<String> blocks = MessageCommandBlocks.getBlocks( OwnDateGetter.getDate() + " /time " + text + " /message "
                + data.getClients().get(senderID).getNickname());
        messageToEveryone.getXMLFromString(blocks);
        data.getChat().add(new ChatItem(blocks));
        server.broadcastMessage(messageToEveryone, senderID);
        data.getLogger().logEvent("Received message from client "+senderID);

        Message successMessage = new Message();
        successMessage.getXMLFromString(SuccessCommandBlocks.getBlocks());
        server.sendMessage(successMessage, senderID);
    }
}
