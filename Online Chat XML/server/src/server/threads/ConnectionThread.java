package server.threads;

import server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ConnectionThread extends Thread{
    private boolean isRunning = true;
    private ServerSocket serverSocket;
    private Server server;

    public ConnectionThread(int port, Server server){
        this.server = server;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(5000);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        Socket clientSocket = null;
        while(isRunning){
            try{
                clientSocket = serverSocket.accept();
            }catch (SocketTimeoutException e){
                continue;
            }catch (IOException e){
                e.printStackTrace();
            }
            if(clientSocket != null){
                server.connectClient(clientSocket);
                clientSocket = null;
            }
        }
        try {
            serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setStopped(){
        isRunning = false;
    }
}
