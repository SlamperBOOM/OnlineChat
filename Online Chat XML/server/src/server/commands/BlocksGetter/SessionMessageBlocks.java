package server.commands.BlocksGetter;

import java.util.*;

public class SessionMessageBlocks {
    public static List<String> getBlocks(String command){
        List<String> blocks = new ArrayList<>();
        blocks.add("success");
        blocks.add("session");
        blocks.add("value");
        blocks.add(command);
        blocks.add("/session");
        blocks.add("/success");
        return blocks;
    }
}
