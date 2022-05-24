package commands;

public interface Message {
    String getCommand();
    String getText();
    String getErrorText();
    int getSenderID();
}
