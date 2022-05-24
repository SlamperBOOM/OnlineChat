package client.client.commands;

import client.client.Client;
import client.client.ClientData;
import commands.Message;

public class TextMessageCommand implements ClientCommand{
    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        data.getChat().add(message.getText());
        data.getUI().updateChat(data.getChat());
    }
}
