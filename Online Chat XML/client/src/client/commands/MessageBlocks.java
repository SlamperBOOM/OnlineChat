package client.commands;

import java.util.ArrayList;
import java.util.List;

public class MessageBlocks {
    public static List<String> getBlocks(String command){
        List<String> blocks = new ArrayList<>();
        String[] splitedCommand = command.split(" ");
        blocks.add("command");
        blocks.add("attr");
        blocks.add("name");
        blocks.add(splitedCommand[0]);
        blocks.add("/command");

        int index = 4;
        if(splitedCommand[1].equals("message")){
            int i=2;
            StringBuilder text = new StringBuilder();
            while (!splitedCommand[i].equals("/message")){
                text.append(splitedCommand[i]).append(" ");
                ++i;
            }
            text = new StringBuilder( text.substring(0, text.length()-1));
            String[] splitedMessage = {"message", "value", text.toString(), "/message"};
            insertSubBlock(blocks, index, splitedMessage);
            index+=4;
            String[] splitedID = {"session", "value", splitedCommand[i+2], "/session"};
            insertSubBlock(blocks, index, splitedID);
        }else{
            for(int i=1; i< splitedCommand.length; i+=2){
                String[] splitedSubBlock = {splitedCommand[i], "value", splitedCommand[i+1], "/"+splitedCommand[i]};
                insertSubBlock(blocks, index, splitedSubBlock);
                index+=4;
            }
        }
        return blocks;
    }

    private static void insertSubBlock(List<String> blocks, int startIndex, String[] splitedSubBlock){
        for (String s : splitedSubBlock) {
            blocks.add(startIndex, s);
            startIndex++;
        }
    }
}
