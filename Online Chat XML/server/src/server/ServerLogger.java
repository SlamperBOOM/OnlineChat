package server;

import java.io.*;

public class ServerLogger {
    private OutputStreamWriter output;

    public ServerLogger(){
        try{
            output = new OutputStreamWriter(new FileOutputStream("server_log.log"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void logEvent(String event){
        System.out.println(OwnDateGetter.getDate()+ ": " + event);
        try{
            output.write(OwnDateGetter.getDate() + ": " + event);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            output.close();
        }catch (IOException e){

        }
    }
}
