package server.server;

import commands.ExitMessage;
import commands.Message;
import commands.NewClient;
import commands.TextMessage;
import server.server.threads.ClientThread;
import server.server.threads.ConnectionThread;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private Properties config;
    private List<String> chat;
    private final List<ClientInfo> clients;
    private final ConnectionThread connectionThread;

    public Server(){
        config = new Properties();
        try {
            config.load(this.getClass().getResourceAsStream("config.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }
        clients = new ArrayList<>();
        chat = new ArrayList<>();
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
        switch (message.getCommand()){
            case "login" : {
                    int ID = message.getSenderID();
                    String nickname = message.getText();
                    synchronized (clients) {
                        clients.get(ID).setNickname(nickname);
                    }
                    String messageText = OwnDateGetter.getDate() + ": User <" + nickname + "> entered the chat!";
                    broadcastMessage(new TextMessage(messageText, -1), ID);

                    chat.add(messageText);
                    int lowBorderOfChat = chat.size()-Integer.parseInt(config.get("bufferSize").toString());
                    if(lowBorderOfChat < 0) lowBorderOfChat = 0;
                    for(int i = lowBorderOfChat;
                        i < chat.size(); ++i){
                        sendMessage(new TextMessage(chat.get(i), -1), ID);
                    }
                    System.out.println(messageText);
                    break;
            }
            case "message" : {
                String messageText;
                synchronized (clients) {
                    messageText = OwnDateGetter.getDate() + ": <" + clients.get(message.getSenderID()).getNickname()
                            + ">: " + message.getText();
                    chat.add(messageText);
                    System.out.println(OwnDateGetter.getDate()+ ": Received message from client " + message.getSenderID() +
                            " <" + clients.get(message.getSenderID()).getNickname()+ ">");
                }
                broadcastMessage(new TextMessage(messageText, -1), message.getSenderID());
                break;
            }
            case "exit" : {
                int ID = message.getSenderID();
                ClientInfo client;

                synchronized (clients){
                    client = clients.remove(ID);
                    for(int i=ID; i<clients.size(); ++i){
                        sendMessage(new NewClient(String.valueOf(i), -1), i);
                    }
                }
                client.stop();
                String messageText = OwnDateGetter.getDate()+ ": User <" + client.getNickname() + "> left the chat";
                System.out.println(messageText);
                broadcastMessage(new TextMessage(messageText, -1), -1);
                break;
            }
            case "userList" : {
                break;
            }
        }
    }

    public synchronized void connectClient(Socket socket){
        ClientThread thread = new ClientThread(this, socket);
        thread.start();
        int ID;
        synchronized (clients){
            clients.add(new ClientInfo("", thread));
            ID = clients.size() - 1;
        }
        System.out.println(OwnDateGetter.getDate()+ ": Client " + ID + " initiated connection");
        sendMessage(new NewClient(String.valueOf(ID), -1), ID);
    }

    private synchronized void sendMessage(Message message, int ID) {
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

    private void broadcastMessage(Message message, int exceptID){
        synchronized (clients){
            for(int i=0; i<clients.size(); ++i){
                if(i!=exceptID) {
                    clients.get(i).sendMessage(message);
                }
            }
        }
    }
}
