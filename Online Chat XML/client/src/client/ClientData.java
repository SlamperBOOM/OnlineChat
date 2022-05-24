package client;

import UI.UI;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface ClientData {
    String getNickname();
    List<String> getChat();
    UI getUI();
    boolean isConnected();
    void closeSocket();
    void setID(int ID);
    void setConnected();
    Properties getCommands();
}
