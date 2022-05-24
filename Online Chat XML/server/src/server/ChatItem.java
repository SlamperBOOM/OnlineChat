package server;

import java.util.List;

public class ChatItem {
    private List<String> blocks;

    public ChatItem(List<String> blocks) {
        this.blocks = blocks;
    }

    public List<String> getItem() {
        return blocks;
    }
}
