package client.client.commands;

import client.client.Client;
import client.client.ClientData;
import commands.Message;

public interface ClientCommand {
    void doCommand(Client client, ClientData data, Message message);
}
