package client.client.commands;

import client.client.Client;
import client.client.ClientData;
import commands.Message;

public class NewClientCommand implements ClientCommand{

    @Override
    public void doCommand(Client client, ClientData data, Message message) {
        try {
            data.setID(Integer.parseInt(message.getText()));
        }catch (NumberFormatException e){
            e.printStackTrace();
            return;
        }
        if(!data.isConnected()) {
            client.sendNickname();
            data.setConnected();
        }
    }
}
