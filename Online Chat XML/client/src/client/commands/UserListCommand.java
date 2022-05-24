package client.commands;

import client.Client;
import client.ClientData;
import client.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;

public class UserListCommand implements ClientCommand{
    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        Document doc = message.getParsedXMLFile();
        Element element = (Element)doc.getElementsByTagName("success").item(0);
        element = (Element)element.getElementsByTagName("listusers").item(0);
        NodeList list = element.getElementsByTagName("user");
        List<String> users = new ArrayList<>();
        for(int i=0; i<list.getLength(); ++i){
            Element elem = (Element) list.item(i);
            users.add(elem.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
            users.add(elem.getElementsByTagName("type").item(0).getChildNodes().item(0).getNodeValue());
        }
        data.getUI().showUsers(users);
    }
}
