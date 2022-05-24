package client.commands;

import client.Client;
import client.ClientData;
import client.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ErrorCommand implements ClientCommand{
    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        Document doc = message.getParsedXMLFile();
        String errorText = ((Element)doc.getElementsByTagName("error").item(0))
                .getElementsByTagName("message").item(0).getChildNodes().item(0).getNodeValue();
        data.getUI().showMessage(errorText);
    }
}
