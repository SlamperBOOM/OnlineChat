package server.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ServerLogger {
    private OutputStreamWriter output;

    public ServerLogger(boolean logging){
        if(logging) {
            try {
                output = new OutputStreamWriter(new FileOutputStream("server_log.log"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            output= null;
        }
    }

    public void logEvent(String event){
        System.out.println(OwnDateGetter.getDate()+ ": " + event);
        if(output!=null) {
            try {
                output.write(OwnDateGetter.getDate() + ": " + event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        if(output!=null) {
            try {
                output.close();
            } catch (IOException e) {

            }
        }
    }
}
