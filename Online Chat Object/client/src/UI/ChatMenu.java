package client.UI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ChatMenu {
    private JMenu menu;
    private ActionListener listener;

    public ChatMenu(ActionListener listener){
        this.listener = listener;
    }

    public void createMenu(){
        menu = new JMenu("Menu");

        JMenuItem connectItem = new JMenuItem("Connect");
        connectItem.setActionCommand("connect");
        connectItem.addActionListener(listener);
        menu.add(connectItem);

        JMenuItem disconnectItem = new JMenuItem("Disconnect");
        disconnectItem.setActionCommand("disconnect");
        disconnectItem.addActionListener(listener);
        menu.add(disconnectItem);

        JMenuItem userList = new JMenuItem("Get connected user list");
        userList.setActionCommand("userList");
        userList.addActionListener(listener);
        menu.add(userList);

        menu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit");
        exit.setActionCommand("exit");
        exit.addActionListener(listener);
        menu.add(exit);
    }

    public JMenuBar getMenu(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        return menuBar;
    }
}
