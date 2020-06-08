package src;
import java.net.*;

public class ClientHandler implements Runnable{

    private boolean running;
    private Server server;
    private Socket connectionSocket;
    private ClientHandler opponent;

    ClientHandler(Server server, Socket webSocket){
        running = false;
        this.server = server;
        connectionSocket = webSocket;
    }
    
    @Override
    public void run() {
        // Set running as true
        running = true;
        

        
    }

    public void setOpponent(ClientHandler opponent){
        this.opponent = opponent;
    }

    public void stop(){
        running = false;
    }
}