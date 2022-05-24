package client.commands;

import client.Client;
import client.ClientData;
import client.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MessageCommand implements ClientCommand{

    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        Document doc = message.getParsedXMLFile();
        Element elem = (Element) doc.getElementsByTagName("event").item(0);

        String time = elem.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue();
        String text = elem.getElementsByTagName("message").item(0).getChildNodes().item(0).getNodeValue();
        String sentUser = elem.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
        data.getChat().add(time + ": <" + sentUser+">: " + text);
        data.getUI().updateChat(data.getChat());
    }
}
