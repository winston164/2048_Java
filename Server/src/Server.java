package src;
import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class Server {

    private boolean running;
    LinkedList<ClientHandler> handlers;
    private ServerSocket serverSocket;

    Server() {
        running = false;
        handlers = new LinkedList<ClientHandler>();
    }

    public void start() {
        // Set running flag as true
        running = true;

        try {

            // Start listening on port 8080
            serverSocket = new ServerSocket(8080);
            System.out.println("Started server and waiting for clients...");
            ClientHandler p1 = null;
            // Continuosly accept clients in pairs
            while (running) {
                Socket connectionSocket = serverSocket.accept();
                System.out.println("Accepted Client");
                if(p1 == null){
                    p1 = new ClientHandler(this, connectionSocket);
                    Thread thread = new Thread(p1);
                    thread.start();
                }else{
                    ClientHandler p2 = new ClientHandler(this, connectionSocket);
                    Thread thread = new Thread(p2);
                    thread.start();
                    handlers.add(p1);
                    handlers.add(p2);
                    p1.setOpponent(p2);
                    p2.setOpponent(p1);
                    p1 = null;
                }
            }

            serverSocket.close();

        } catch (Exception e) {
           System.out.println("Disconnected with exception: " + e.getMessage());
            stop();
        }

    }

    public void deleteThread(ClientHandler delete) {
        System.out.println("Closed Client");
        handlers.remove(delete);
    }

    public void stop() {
        // Stop Client Handlers
        while (handlers.size() > 0) {
            System.out.println("Closing handelers");
            handlers.remove().stop();
        }

        // Set running to false
        running = false;
        
        try{
        // Close server socket
        System.out.println("Closing Server Socket");
        serverSocket.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("Closed Server");
    }

}