package client.commands;

import client.Client;
import client.ClientData;
import client.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EventCommand implements ClientCommand{
    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        Document doc = message.getParsedXMLFile();
        Element elem = (Element) doc.getElementsByTagName("event").item(0);
        try {
            ClientCommand command = (ClientCommand) Class.forName(data.getCommands().getProperty(elem.getAttribute("name"))).newInstance();
            command.doCommand(client, data, message);
        }catch (ClassNotFoundException |InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
