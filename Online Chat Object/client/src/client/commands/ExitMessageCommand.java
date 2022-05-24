package client.client.commands;

import client.client.Client;
import client.client.ClientData;
import commands.Message;


public class ExitMessageCommand implements ClientCommand{
    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        data.getChat().add("You were disconnected from server");
        data.getUI().updateChat(data.getChat());
        data.closeSocket();
    }
}
