package commands;

import java.io.Serializable;

public class UserListCommand implements Message, Serializable {
    private String command;
    private String text;
    private int ID;

    public UserListCommand(String text, int ID){
        this.text = text;
        this.ID = ID;
        command = "userList";
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
