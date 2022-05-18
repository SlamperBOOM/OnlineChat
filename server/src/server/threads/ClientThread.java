package server.server.threads;

import commands.ExitMessage;
import commands.Message;
import server.server.Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ClientThread extends Thread{
    private Server server;
    private Socket socket;
    private ObjectInputStream inputFromClient;
    private ObjectOutputStream outputToClient;
    private int timeoutCount = 0;
    private int ID;

    private boolean isRunning = true;

    public ClientThread(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
        try {
            socket.setSoTimeout(1000);
            inputFromClient = new ObjectInputStream(socket.getInputStream());
            outputToClient = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        Message message = null;
        while (isRunning){
            try{
                message = (Message) inputFromClient.readObject();
            }catch (ClassNotFoundException | SocketTimeoutException e){
                message = null;
            }catch (IOException e){
               server.receiveMessage(new ExitMessage(ID));
            }
            timeoutCount++;
            if(message != null){
                server.receiveMessage(message);
                message = null;
                timeoutCount = 0;
            }
            if(timeoutCount == 60){
                server.receiveMessage(new ExitMessage(ID));
            }
        }
        try{
            socket.close();
            inputFromClient.close();
            outputToClient.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void sendMessage(Message message){
        try {
            outputToClient.writeObject(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setStopped(){
        isRunning = false;
    }
}
