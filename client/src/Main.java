package client;


import client.UI.UserInterface;
import client.client.Client;

public class Main {
    public static void main(String[] args){
        UserInterface ui = new UserInterface();
        Client client = new Client(ui);
        ui.setClient(client);
        ui.init();
    }
}
