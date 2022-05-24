package server.commands.BlocksGetter;

import java.util.*;

public class UserListCommandBlocks{
    public static List<String> getBlocks(String command){
        List<String> blocks = new ArrayList<>();
        blocks.add("success");
        blocks.add("listusers");
        String[] splitedCommand = command.split(" ");
        for(int i=1; i< splitedCommand.length; i+=3){
            blocks.add("user");
            blocks.add("name");
            blocks.add("value");
            blocks.add(splitedCommand[i]);
            blocks.add("/name");
            blocks.add("type");
            blocks.add("value");
            blocks.add(splitedCommand[i+1]);
            blocks.add("/type");
            blocks.add("/user");
        }
        blocks.add("/listusers");
        blocks.add("/success");
        return blocks;
    }
}
