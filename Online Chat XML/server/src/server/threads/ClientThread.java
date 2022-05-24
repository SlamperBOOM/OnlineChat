package server.threads;

import server.Message;
import server.Server;
import server.commands.BlocksGetter.TimeoutDisconnectBlocks;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class ClientThread extends Thread{
    private Server server;
    private Socket socket;
    private InputStream inputFromClient;
    private OutputStream outputToClient;
    private int timeoutCount = 0;
    private int ID;

    private boolean isRunning = true;

    public ClientThread(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
        try {
            inputFromClient = socket.getInputStream();
            outputToClient = socket.getOutputStream();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while (isRunning){
            try {
               sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            try {
                if (inputFromClient.available() > 0) {
                    byte[] size = new byte[4];
                    inputFromClient.read(size);
                    int bytes = ByteBuffer.wrap(size).getInt();
                    byte[] byteArray = new byte[bytes];
                    inputFromClient.read(byteArray);
                    Message message = new Message();
                    message.getDocFromXML(byteArray);
                    message.setID(ID);
                    server.receiveMessage(message);
                    timeoutCount = 0;
                } else {
                    timeoutCount++;
                    if (timeoutCount == 864000) {//1 hour timeout
                        Message message = new Message();
                        message.getXMLFromString(TimeoutDisconnectBlocks.getBlocks(String.valueOf(ID)));
                        server.receiveMessage(message);
                    }
                }
            }catch (SocketException e){
                break;
            }catch (IOException e){
            }
        }
        try{
            socket.close();
            inputFromClient.close();
            outputToClient.close();
        }catch (SocketException e){
        }catch (IOException e){
        }
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void sendMessage(Message message){
        try {
            int size = message.getXMLFile().getBytes().length;
            byte[] bytes = ByteBuffer.allocate(4).putInt(size).array();
            outputToClient.write(bytes);
            outputToClient.write(message.getXMLFile().getBytes());
        }catch (IOException e){
            //Socket already closed by client
        }
    }

    public void setStopped(){
        isRunning = false;
    }
}
