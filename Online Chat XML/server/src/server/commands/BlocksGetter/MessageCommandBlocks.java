package server.commands.BlocksGetter;

import java.util.*;

public class MessageCommandBlocks {
    public static List<String> getBlocks(String command){
        List<String> blocks = new ArrayList<>();
        blocks.add("event");
        blocks.add("attr");
        blocks.add("name");
        blocks.add("message");
        String[] splitedCommand = command.split(" ");
        int i=0;
        StringBuilder time = new StringBuilder();
        while(!splitedCommand[i].equals("/time")){
            time.append(splitedCommand[i]).append(" ");
            ++i;
        }
        time = new StringBuilder( time.substring(0, time.length()-1));
        String[] splitedTime = {"time", "value", time.toString(), "/time"};
        Collections.addAll(blocks, splitedTime);

        ++i;
        StringBuilder text = new StringBuilder();
        while(!splitedCommand[i].equals("/message")){
            text.append(splitedCommand[i]).append(" ");
            ++i;
        }
        text = new StringBuilder( text.substring(0, text.length()-1));
        String[] splitedMessage = {"message", "value", text.toString(), "/message"};
        Collections.addAll(blocks, splitedMessage);

        String[] splitedName = {"name", "value", splitedCommand[i+1], "/name"};
        Collections.addAll(blocks, splitedName);
        blocks.add("/event");
        return blocks;
    }
}
