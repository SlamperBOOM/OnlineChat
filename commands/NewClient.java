package commands;

import java.io.Serializable;

public class NewClient implements Message, Serializable {
    private String command;
    private String text;
    private int ID;

    public NewClient(String nickname, int ID){
        command = "login";
        text = nickname;
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
