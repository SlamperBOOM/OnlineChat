package client.UI;

import java.util.List;

public interface UI {
    void updateChat(List<String> chat);
    void showUsers(List<String> users);
    void showMessage(String text);
}
