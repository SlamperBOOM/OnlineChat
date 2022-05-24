package client.commands;

import client.Client;
import client.ClientData;
import client.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SuccessCommand implements ClientCommand{
    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        Document doc = message.getParsedXMLFile();
        Element elem = (Element) doc.getElementsByTagName("success").item(0);
        if(doc.getElementsByTagName("success").item(0).getChildNodes().getLength() > 0){
            try {
                ClientCommand command = (ClientCommand) Class.forName(
                        data.getCommands().getProperty(elem.getChildNodes().item(0).getNodeName())).newInstance();
                command.doCommand(client, data, message);
            }catch (ClassNotFoundException |InstantiationException | IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }
}
