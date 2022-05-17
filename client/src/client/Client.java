package client.client;

import client.UI.UI;
import commands.ExitMessage;
import commands.Message;
import commands.NewClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private ObjectOutputStream outputToServer;
    private ObjectInputStream inputFromServer;
    private Socket socket;
    private ServerListener listener;
    private List<String> chat;
    private String nickname;
    private int ID;
    private UI ui;
    private boolean isConnected = false;

    public Client(UI ui){
        this.ui = ui;
        chat = new ArrayList<>();
    }

    public void connect(int port){
        try {
            socket = new Socket("localhost", port);
            outputToServer = new ObjectOutputStream(socket.getOutputStream());
            inputFromServer = new ObjectInputStream(socket.getInputStream());
            listener = new ServerListener(this, inputFromServer);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void startListening(){
        listener.start();
    }

    public void closeConnection(){
        if(isConnected) {
            try {
                outputToServer.writeObject(new ExitMessage(ID));
                chat.add("You left the chat");
                ui.updateChat(chat);

                listener.setStopped();
                socket.close();
                outputToServer.close();
                inputFromServer.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendNickname(){
        try {
            outputToServer.writeObject(new NewClient(nickname, ID));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message){
        try {
            outputToServer.writeObject(message);
            chat.add(OwnDateGetter.getDate()+": <You>: " + message.getText());
            ui.updateChat(chat);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void receiveMessage(Message message){
        switch (message.getCommand()){
            case "login" : {
                try {
                    ID = Integer.parseInt(message.getText());
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    return;
                }
                if(!isConnected) {
                    sendNickname();
                    isConnected = true;
                }
                break;
            }
            case "message" : {
                chat.add(message.getText());
                ui.updateChat(chat);
                break;
            }
            case "exit" : {
                chat.add("You was disconnected from server");
                ui.updateChat(chat);
                try {
                    socket.close();
                    outputToServer.close();
                    inputFromServer.close();
                    listener.setStopped();
                }catch (IOException e){
                    e.printStackTrace();
                }
                isConnected = false;
                break;
            }
        }
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public int getID() {
        return ID;
    }
}
