package client.commands;

import client.Client;
import client.ClientData;
import client.Message;
import client.OwnDateGetter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UserLogoutCommand implements ClientCommand{
    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        Document doc = message.getParsedXMLFile();
        String time = ((Element)doc.getElementsByTagName("event").item(0))
                .getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue();
        String text = ((Element)doc.getElementsByTagName("event").item(0))
                .getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();

        data.getChat().add(time + ": User <" + text + "> left the chat");
        data.getUI().updateChat(data.getChat());
    }
}
