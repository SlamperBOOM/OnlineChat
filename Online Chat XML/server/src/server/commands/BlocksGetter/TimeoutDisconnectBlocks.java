package server.commands.BlocksGetter;

import java.util.*;

public class TimeoutDisconnectBlocks {
    public static List<String> getBlocks(String command){
        List<String> blocks = new ArrayList<>();
        blocks.add("command");
        blocks.add("attr");
        blocks.add("name");
        blocks.add("logout");
        blocks.add("session");
        blocks.add("value");
        blocks.add(command);
        blocks.add("/session");
        blocks.add("/command");
        return blocks;
    }
}
