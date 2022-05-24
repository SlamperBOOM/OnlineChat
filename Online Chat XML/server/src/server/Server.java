package server;

import org.w3c.dom.Element;
import server.commands.BlocksGetter.ErrorMessageBlocks;
import server.commands.Clients;
import server.commands.ServerCommand;
import server.threads.ClientThread;
import server.threads.ConnectionThread;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements ServerData{
    private Properties config;
    private List<ChatItem> chat;
    private final Clients clients;
    private Properties commands;
    private final ConnectionThread connectionThread;
    private ServerLogger logger;

    public Server(){
        config = new Properties();
        commands = new Properties();
        try {
            config.load(this.getClass().getResourceAsStream("config.txt"));
            commands.load(this.getClass().getResourceAsStream("commands/commands.txt"));

        }catch (IOException e){
            e.printStackTrace();
        }
        clients = new Clients();
        chat = new ArrayList<>();
        logger = new ServerLogger();
        connectionThread = new ConnectionThread(Integer.parseInt(config.get("port").toString()), this);
        System.out.println("Server started at port " + Integer.parseInt(config.get("port").toString()));
    }

    public void startServer(){
        connectionThread.start();
    }

    public void stop(){
        clients.stop();
        connectionThread.setStopped();
        System.out.println("Give me a few seconds to finish");
    }

    public synchronized void receiveMessage(Message message){
        String commandName = message.getParsedXMLFile().getChildNodes().item(0).getNodeName();
        try {
            ServerCommand command = (ServerCommand) Class.forName(commands.getProperty(commandName)).newInstance();
            command.doCommand(this, this, message);
        }catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            Element elem = (Element) message.getParsedXMLFile().getElementsByTagName("command").item(0);
            System.out.println(OwnDateGetter.getDate() + ": Got wrong command from client " +
                    elem.getElementsByTagName("session").item(0).getNodeValue());
            Message errorMessage = new Message();
            errorMessage.getXMLFromString(ErrorMessageBlocks.getBlocks("Wrong command"));
            sendMessage(errorMessage, Integer.parseInt(elem.getElementsByTagName("session").item(0).getNodeValue()));
        }
    }

    public synchronized void connectClient(Socket socket){
        ClientThread thread = new ClientThread(this, socket);
        thread.start();
        int ID;
        synchronized (clients){
            ID = clients.getNextID();
            clients.addClient(new ClientInfo("", thread, ID));
        }
        System.out.println(OwnDateGetter.getDate()+ ": Client " + ID + " initiated connection");
    }

    public synchronized void sendMessage(Message message, int ID) {
        ClientInfo client = clients.get(ID);
        if(client!=null){
            client.sendMessage(message);
        }
    }

    public synchronized void broadcastMessage(Message message, int exceptID){
        clients.broadcastMessage(message, exceptID);
    }

    @Override
    public int getLowBorder() {
        return Integer.parseInt(config.get("bufferSize").toString());
    }

    @Override
    public List<ChatItem> getChat() {
        return chat;
    }

    @Override
    public Clients getClients() {
        return clients;
    }

    @Override
    public Properties getCommands() {
        return commands;
    }

    @Override
    public ServerLogger getLogger() {
        return logger;
    }
}
