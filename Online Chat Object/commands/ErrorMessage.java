package commands;

import java.io.Serializable;

public class ErrorMessage implements Message, Serializable {
    private String command;
    private String error;

    public ErrorMessage(String message){
        command = "error";
        error = message;
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
        return error;
    }

    @Override
    public int getSenderID() {
        return 0;
    }
}
