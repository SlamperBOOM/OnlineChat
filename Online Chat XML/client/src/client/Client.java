package client;

import UI.UI;
import client.commands.ClientCommand;
import client.commands.MessageBlocks;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Client implements ClientData{
    private OutputStream outputToServer;
    private InputStream inputFromServer;
    private Socket socket;
    private ServerListener listener;
    private List<String> chat;
    private Properties commands;
    private String nickname;
    private int ID;
    private UI ui;
    private boolean isConnected = false;

    public Client(UI ui){
        this.ui = ui;
        chat = new ArrayList<>();
        commands = new Properties();
        try{
            commands.load(this.getClass().getResourceAsStream("commands/commands.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void connect(int port){
        try {
            socket = new Socket("localhost", port);
            outputToServer = socket.getOutputStream();
            inputFromServer = socket.getInputStream();
            listener = new ServerListener(this, inputFromServer);
            chat = new ArrayList<>();
            Message message = createMessage("login name "+nickname+" type java_client");
            sendMessage(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void startListening(){
        listener.start();
    }

    public void closeConnection(){
        if(isConnected) {
            Message message = createMessage("logout session " + ID);
            sendMessage(message);
        }
        chat.add("You left the chat");
        ui.updateChat(chat);
        closeSocket();
    }

    public Message createMessage(String command){//need to create xml command message from written text
        List<String> blocks = MessageBlocks.getBlocks(command);
        Message mes = new Message();
        mes.getXMLFromString(blocks);
        return mes;
    }

    public void sendMessage(Message message){
        try{
            int size = message.getXMLFile().getBytes().length;
            byte[] bytes = ByteBuffer.allocate(4).putInt(size).array();
            outputToServer.write(bytes);
            outputToServer.write(message.getXMLFile().getBytes());
        }catch (IOException e) {
            chat.add("You were disconnected from chat");
            ui.updateChat(chat);
            try {
                socket.close();
                outputToServer.close();
                inputFromServer.close();
            } catch (IOException e1) {
            }
        }
    }

    public void askUserList(){
        Message message = createMessage("list session "+ID);
        sendMessage(message);
    }

    public void receiveMessage(Message message){
        String commandName = message.getParsedXMLFile().getChildNodes().item(0).getNodeName();
        try {
            ClientCommand command = (ClientCommand) Class.forName(commands.get(commandName).toString()).newInstance();
            command.doCommand(this, this, message);
        }catch (ClassNotFoundException |InstantiationException | IllegalAccessException e){
            e.printStackTrace();
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
    public Properties getCommands() {
        return commands;
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
        if(socket!=null) {
            try {
                socket.close();
                outputToServer.close();
                inputFromServer.close();
                listener.setStopped();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        isConnected = false;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }
}
