package client.commands;

import client.Client;
import client.ClientData;
import client.Message;
import client.OwnDateGetter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SessionCommand implements ClientCommand{
    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        Document doc = message.getParsedXMLFile();
        String text = ((Element)doc.getElementsByTagName("success").item(0))
                .getElementsByTagName("session").item(0).getChildNodes().item(0).getNodeValue();
        int ID = Integer.parseInt(text);
        data.getChat().add(OwnDateGetter.getDate()+": You have entered the chat!");
        data.getUI().updateChat(data.getChat());
        client.setID(ID);
        data.setConnected();
    }
}
