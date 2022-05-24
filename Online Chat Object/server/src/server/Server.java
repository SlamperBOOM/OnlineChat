package server.server;

import commands.*;
import server.server.commands.ServerCommand;
import server.server.threads.ClientThread;
import server.server.threads.ConnectionThread;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements ServerData{
    private Properties config;
    private List<String> chat;
    private final List<ClientInfo> clients;
    private final ConnectionThread connectionThread;
    private ServerLogger logger;

    public Server(){
        config = new Properties();
        try {
            config.load(this.getClass().getResourceAsStream("config.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }
        clients = new ArrayList<>();
        chat = new ArrayList<>();
        if(config.get("logServer").toString().equals("true")){
            logger = new ServerLogger(true);
        }else{
            logger = new ServerLogger(false);
        }
        connectionThread = new ConnectionThread(Integer.parseInt(config.get("port").toString()), this);
        System.out.println("Server started at port " + Integer.parseInt(config.get("port").toString()));
    }

    public void startServer(){
        connectionThread.start();
    }

    public void stop(){
        broadcastMessage(new ExitMessage(-1), -1);
        for(ClientInfo client:clients){
            client.stop();
        }
        connectionThread.setStopped();
        System.out.println("Give me a few seconds to finish");
    }

    public synchronized void receiveMessage(Message message){
        try {
            String className = "server.server." + message.getClass().getName() + "Command";
            ServerCommand command = (ServerCommand) Class.forName(className).newInstance();
            command.doCommand(this, this, message);
        }catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            logger.logEvent("Got wrong command from client " + message.getSenderID());
            sendMessage(new ErrorMessage("Wrong command"), message.getSenderID());
        }
    }

    public synchronized void connectClient(Socket socket){
        ClientThread thread = new ClientThread(this, socket);
        thread.start();
        int ID;
        synchronized (clients){
            ID = clients.size();
            clients.add(new ClientInfo("", thread, ID));
        }
        logger.logEvent("Client " + ID + " initiated connection");
        sendMessage(new NewClient(String.valueOf(ID), -1), ID);
    }

    public synchronized void sendMessage(Message message, int ID) {
        ClientInfo client;
        synchronized (clients){
            client = clients.get(ID);
        }
        if(message.getCommand().equals("login")){
            client.sendMessage(message);
        }else{
            if(!client.getNickname().equals("")){
                client.sendMessage(message);
            }
        }
    }

    public void broadcastMessage(Message message, int exceptID){
        synchronized (clients){
            for(int i=0; i<clients.size(); ++i){
                if(i!=exceptID) {
                    clients.get(i).sendMessage(message);
                }
            }
        }
    }

    @Override
    public int getLowBorder() {
        return Integer.parseInt(config.get("bufferSize").toString());
    }

    @Override
    public List<String> getChat() {
        return chat;
    }

    @Override
    public List<ClientInfo> getClients() {
        return clients;
    }

    @Override
    public ServerLogger getLogger() {
        return logger;
    }
}
