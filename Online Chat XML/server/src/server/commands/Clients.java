package server.commands;

import server.ClientInfo;
import server.Message;
import server.commands.BlocksGetter.UserLogoutCommandBlocks;

import java.util.ArrayList;
import java.util.List;

public class Clients {
    private final List<ClientInfo> clients;
    private int nextID = 0;

    public Clients(){
        clients = new ArrayList<>();
    }

    public synchronized void addClient(ClientInfo client){
        clients.add(client);
    }

    public synchronized ClientInfo get(int clientID){
        for(ClientInfo client: clients){
            if(client.getID() == clientID){
                return client;
            }
        }
        return null;
    }

    public synchronized ClientInfo get(String nickname){
        for(ClientInfo client: clients){
            if(client.getNickname().equals(nickname)){
                return client;
            }
        }
        return null;
    }

    public List<ClientInfo> getList(){
        return clients;
    }

    public synchronized void remove(int clientID){
        for(int i=0; i<clients.size(); ++i){
            ClientInfo client = clients.get(i);
            if(client.getID() == clientID){
                clients.remove(i);
                break;
            }
        }
    }

    public int size(){
        return clients.size();
    }

    public boolean checkNickname(String nickname){
        for(ClientInfo clientInfo : clients){
            if(clientInfo.getNickname().equals(nickname)){
                return true;
            }
        }
        return false;
    }

    public void stop(){
        for (ClientInfo client : clients) {
            Message message = new Message();
            message.getXMLFromString(UserLogoutCommandBlocks.getBlocks(client.getNickname()));
            client.sendMessage(message);
            client.stop();
        }
    }

    public int getNextID(){
        nextID++;
        return nextID-1;
    }

    public synchronized void broadcastMessage(Message message, int exceptID){
        for(ClientInfo client: clients){
            if(client.getID() != exceptID){
                client.sendMessage(message);
            }
        }
    }
}
