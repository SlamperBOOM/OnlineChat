package server;

import server.server.Server;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Server server = new Server();
        System.out.println("Write \"stop\" to stop the server");
        server.startServer();
        Scanner scanner = new Scanner(System.in);
        while (true){
            String line = scanner.nextLine();
            if(line.equals("stop")){
                server.stop();
                break;
            }
        }
    }
}
