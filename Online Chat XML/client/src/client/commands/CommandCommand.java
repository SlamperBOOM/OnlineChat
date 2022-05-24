package client.commands;

import client.Client;
import client.ClientData;
import client.Message;

public class CommandCommand implements ClientCommand{
    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        client.sendMessage(message);
    }
}
