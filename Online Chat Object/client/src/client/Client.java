package client.client;

import client.UI.UI;
import client.client.commands.ClientCommand;
import commands.ExitMessage;
import commands.Message;
import commands.NewClient;
import commands.UserList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements ClientData{
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
            } catch (IOException | NullPointerException e) {
            }
        }
        chat.add("You left the chat");
        ui.updateChat(chat);
        closeSocket();
    }

    public void sendNickname(){
        try {
            outputToServer.writeObject(new NewClient(nickname, ID));
        }catch (IOException e){
        }
    }

    public void askUserList(){
        try {
            outputToServer.writeObject(new UserList("", ID));
        }catch (IOException e){
        }
    }

    public void sendMessage(Message message){
        try {
            outputToServer.writeObject(message);
            chat.add(OwnDateGetter.getDate()+": <You>: " + message.getText());
            ui.updateChat(chat);
        }catch (IOException e){
            closeSocket();
        }
    }

    public void receiveMessage(Message message){
        try {
            String className = "client.client." + message.getClass().getName() + "Command";
            ClientCommand command = (ClientCommand) Class.forName(className).newInstance();
            command.doCommand(this, this, message);
        }catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
        }
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public int getID() {
        return ID;
    }

    @Override
    public void setConnected() {
        isConnected = true;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public List<String> getChat() {
        return chat;
    }

    @Override
    public UI getUI() {
        return ui;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void closeSocket() {
        try {
            socket.close();
            outputToServer.close();
            inputFromServer.close();
            listener.setStopped();
        }catch (IOException e){
        }
        isConnected = false;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }
}
