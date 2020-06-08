package src;
import java.net.*;


import java.io.*;

public class ClientHandler implements Runnable{

    private boolean running;
    private Server server;
    private Game personalGame;
    private Socket connectionSocket;
    private ClientHandler opponent;
    private DataOutputStream clientOutput;
    private BufferedReader clientInput;

    ClientHandler(Server server, Socket webSocket){
        running = false;
        this.server = server;
        connectionSocket = webSocket;
        opponent = null;

        personalGame = new Game();
    }
    
    @Override
    public void run() {
        // Set running as true
        running = true;

        try{
        clientOutput = new DataOutputStream(connectionSocket.getOutputStream());
        clientInput = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

        String request = "";
        while(running){
            request = clientInput.readLine();
            if(request == null) break;
            if(request.equals("Connected?")){
                clientOutput.writeBytes("Connected Sucesfully\n");
            }
            if(request.equals("Next Input:")){
                request = clientInput.readLine();
                input(Integer.parseInt(request));
            }

        }

        }catch(IOException e){
            System.out.println("Disconnected with exception: " + e.getMessage());
        }
        stop();
        System.out.println("Thread " + Thread.currentThread().getName() + " Finished");
    }

    public String getMatriString(){
        return personalGame.getMatrixString();
    }

    private void sendMatrixToClient(){
        try{
            clientOutput.writeBytes("Player1:\n");
            String gameMatrix = personalGame.getMatrixString();
            clientOutput.writeBytes(gameMatrix + "\n");
            opponent.sendOpponentMatrix(gameMatrix);
            if(gameMatrix.contains("2048")){
                clientOutput.writeBytes("Winner!S\n");
                stop();
            }
        }catch(IOException e){
            System.out.println("Disconnected with exception: " + e.getMessage());
        }
    }

    private void input(int move){
        switch (move) {
            case 0:
                personalGame.moveUp();
                sendMatrixToClient();
                break;

            case 1:
                personalGame.moveDown();
                sendMatrixToClient();
                break;
            
            case 2:
                personalGame.moveLeft();
                sendMatrixToClient();
                break;

            case 3:
                personalGame.moveRight();
                sendMatrixToClient();
                break;
            default:
                break;
        }
    }

    public void setOpponent(ClientHandler opponent){
        this.opponent = opponent;
        personalGame.startGame();
        sendMatrixToClient();
    }
    
    // Send opponent Matrix to Client
    public void sendOpponentMatrix(String opponentMatrix){
        if(!running) return;
        try{
            clientOutput.writeBytes("Player2:\n");
            clientOutput.writeBytes(opponentMatrix + "\n");
            if(opponentMatrix.contains("2048")){
                clientOutput.writeBytes("Loser!\n");
                stop();
            }
        }catch(IOException e){
            System.out.println("Disconnected with exception: " + e.getMessage());
        }
    }

    //Stop the game
    public void stop(){
        try {
            //clientOutput.writeBytes("Stop Game!");
            
            clientInput.close();
            clientOutput.close();
            connectionSocket.close();
        } catch (IOException e) {
            System.out.println("Disconnected with exception: " + e.getMessage());
        }
        running = false;
        server.deleteThread(this);


        System.out.println("Thread " + Thread.currentThread().getName() + " Stopped");
    }
}