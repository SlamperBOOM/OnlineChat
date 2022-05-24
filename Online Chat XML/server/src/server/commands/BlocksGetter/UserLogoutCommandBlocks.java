package server.commands.BlocksGetter;

import server.OwnDateGetter;

import java.util.*;

public class UserLogoutCommandBlocks {
    public static List<String> getBlocks(String command){
        List<String> blocks = new ArrayList<>();
        blocks.add("event");
        blocks.add("attr");
        blocks.add("name");
        blocks.add("userlogout");
        blocks.add("time");
        blocks.add("value");
        blocks.add(OwnDateGetter.getDate().toString());
        blocks.add("/time");
        blocks.add("name");
        blocks.add("value");
        blocks.add(command);
        blocks.add("/name");
        blocks.add("/event");
        return blocks;
    }
}
