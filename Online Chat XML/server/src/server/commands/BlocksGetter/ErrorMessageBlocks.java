package server.commands.BlocksGetter;

import java.util.*;

public class ErrorMessageBlocks {
    public static List<String> getBlocks(String command){
        List<String> blocks = new ArrayList<>();
        blocks.add("error");
        blocks.add("message");
        blocks.add("value");
        blocks.add(command);
        blocks.add("/message");
        blocks.add("/error");
        return blocks;
    }
}
