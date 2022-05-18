package client.client.commands;

import client.client.Client;
import client.client.ClientData;
import commands.Message;

import java.util.Arrays;
import java.util.List;

public class UserListCommand implements ClientCommand{
    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        List<String> users;
        String text = message.getText();
        users = Arrays.asList(text.split("\n"));
        data.getUI().showUsers(users);
    }
}
