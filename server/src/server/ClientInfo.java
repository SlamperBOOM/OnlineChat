package server.server;

import commands.Message;
import server.server.threads.ClientThread;


public class ClientInfo {
    private String nickname; //if nickname exists, client fully connected to the server
    private ClientThread thread;
    private int ID;

    public ClientInfo(String nickname, ClientThread thread, int ID) {
        this.nickname = nickname;
        this.thread = thread;
        setID(ID);
    }

    public void setID(int ID){
        this.ID = ID;
        thread.setID(ID);
    }

    public String getNickname(){
        return nickname;
    }

    public void stop(){
        thread.setStopped();
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void sendMessage(Message message){
        thread.sendMessage(message);
    }
}
