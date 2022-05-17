package client.UI;

import client.client.Client;
import commands.TextMessage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class UserInterface implements UI, DocumentListener {
    private JFrame window;
    private JTextArea chatArea;
    private JTextArea messageArea;
    private JTextField nicknameField;
    private Client client;

    public UserInterface(){
        window = new JFrame("IRC");

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));

        nicknameField = new JTextField(30);
        nicknameField.setEditable(false);
        mainPane.add(nicknameField);

        chatArea = new JTextArea(30, 30);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JScrollPane pane = new JScrollPane(chatArea);

        messageArea = new JTextArea(2,30);
        messageArea.getDocument().addDocumentListener(this);

        mainPane.add(pane);
        mainPane.add(messageArea);
        window.setContentPane(mainPane);
        window.setBounds(50, 50, 700, 500);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
                super.windowClosing(e);
                System.exit(0);
            }
        });
        window.setVisible(true);
    }

    public void setClient(Client client){
        this.client = client;
    }

    public void init(){
        LoginDialog dialog = new LoginDialog(window, this);
        dialog.showDialog();
    }

    public void logIn(String nickname, int port){
        client.connect(port);
        client.startListening();
        client.setNickname(nickname);
        nicknameField.setText("Nickname: "+nickname);
    }

    public void sendMessage(String message){
        client.sendMessage(new TextMessage(message, client.getID()));
    }

    public void exit(){
        client.closeConnection();
    }

    @Override
    public void updateChat(List<String> chat) {
        StringBuilder text = new StringBuilder();
        for(String line: chat){
            text.append("\n").append(line);
        }
        chatArea.setText(text.toString());
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        String message = messageArea.getText();
        char[] charMessage = message.toCharArray();
        if(charMessage[message.length()-1] == '\n'){
            if(message.length()>1) {
                sendMessage(message.substring(0, message.length() - 1));
            }
            SwingUtilities.invokeLater(() -> messageArea.setText(""));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        //
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        //
    }
}
