package client.commands;

import client.Client;
import client.ClientData;
import client.Message;

public interface ClientCommand {
    void doCommand(Client client, ClientData data, Message message);
}
