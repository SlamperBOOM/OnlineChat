package server.server.threads;

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

    private boolean isRunning = true;

    public ClientThread(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
        try {
            socket.setSoTimeout(1000);
        }catch (SocketException e){
            e.printStackTrace();
        }
        try {
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
            }catch (SocketTimeoutException e){
                continue;
            }catch (ClassNotFoundException | IOException e){
                e.printStackTrace();
            }
            if(message != null){
                server.receiveMessage(message);
                message = null;
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
