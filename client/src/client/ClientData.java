package client.client;

import client.UI.UI;

import java.util.List;

public interface ClientData {
    String getNickname();
    List<String> getChat();
    UI getUI();
    boolean isConnected();
    void closeSocket();
    void setID(int ID);
    void setConnected();
}
