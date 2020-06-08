package src;
import java.util.Scanner;

public class ServerConsole implements Runnable{
    private Server server;

    ServerConsole(Server ParentServer){
        server = ParentServer;
    }

    @Override
    public void run() {
        Scanner keyboard = new Scanner(System.in);
        String command = "";
        while(!command.equals("Stop Server")){
            command = keyboard.nextLine();
        }
        keyboard.close();
        server.stop();
    }
}