package commands;

import java.io.Serializable;

public class TextMessage implements Message, Serializable {
    private String command;
    private String text;
    int ID; // -1 if sender is server

    public TextMessage(String text, int ID){
        this.text = text;
        command = "message";
        this.ID = ID;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getErrorText() {
        return null;
    }

    @Override
    public int getSenderID() {
        return ID;
    }
}
