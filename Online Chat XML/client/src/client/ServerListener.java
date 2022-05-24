package client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ServerListener extends Thread{
    private Client client;
    private InputStream inputFromServer;
    private boolean isRunning = true;

    public ServerListener(Client client, InputStream stream){
        this.client = client;
        inputFromServer = stream;
    }

    @Override
    public void run(){
        while(isRunning){
            try{
                sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            try {
                if(inputFromServer.available() >0) {
                    byte[] size = new byte[4];
                    inputFromServer.read(size);
                    int bytes = ByteBuffer.wrap(size).getInt();
                    byte[] byteArray = new byte[bytes];
                    inputFromServer.read(byteArray);
                    Message message = new Message();
                    message.getDocFromXML(byteArray);
                    client.receiveMessage(message);
                }
            }catch (IOException e){
            }
        }
    }

    public void setStopped(){
        isRunning = false;
    }
}
