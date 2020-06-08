package src;

public class MainClass{
    public static void main(String[] args) {
        Server server = new Server();
        ServerConsole servConsole = new ServerConsole(server);
        Thread consoleThread = new Thread(servConsole);
        consoleThread.start();
        server.start();
        
    }
}