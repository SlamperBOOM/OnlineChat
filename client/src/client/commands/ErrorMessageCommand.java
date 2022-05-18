package client.client.commands;

import client.client.Client;
import client.client.ClientData;
import commands.Message;

public class ErrorMessageCommand implements ClientCommand{
    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        data.getUI().showMessage(message.getErrorText());
    }
}
