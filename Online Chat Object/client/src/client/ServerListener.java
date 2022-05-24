package client.client;

import commands.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerListener extends Thread{
    private Client client;
    private ObjectInputStream inputFromServer;
    private boolean isRunning = true;

    public ServerListener(Client client, ObjectInputStream stream){
        this.client = client;
        inputFromServer = stream;
    }

    @Override
    public void run(){
        Message message = null;
        while(isRunning){
            try{
                message = (Message) inputFromServer.readObject();
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
            if(message != null){
                client.receiveMessage(message);
                message = null;
            }
        }
    }

    public void setStopped(){
        isRunning = false;
    }
}
