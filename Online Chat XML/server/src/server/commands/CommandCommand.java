package server.commands;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import server.Message;
import server.Server;
import server.ServerData;
import server.commands.BlocksGetter.ErrorMessageBlocks;

public class CommandCommand implements ServerCommand{
    @Override
    public void doCommand(Server server, ServerData data, Message message) {
        Document doc = message.getParsedXMLFile();
        Element elem = (Element) doc.getElementsByTagName("command").item(0);
        try{
            ServerCommand command = (ServerCommand) Class.forName(data.getCommands().getProperty(elem.getAttribute("name"))).newInstance();
            command.doCommand(server, data, message);
        }catch (ClassNotFoundException |InstantiationException | IllegalAccessException e){
            Message errorMessage = new Message();
            errorMessage.getXMLFromString(ErrorMessageBlocks.getBlocks("Unknown command name"));
            server.sendMessage(errorMessage, message.getID());
        }
    }
}
