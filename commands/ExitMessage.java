package commands;

import java.io.Serializable;

public class ExitMessage implements Message, Serializable {
    private String command;
    private int ID;

    public ExitMessage(int ID){
        command = "exit";
        this.ID = ID;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getText() {
        return null;
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
