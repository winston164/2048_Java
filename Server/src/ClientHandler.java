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
    public boolean hasMoves;

    ClientHandler(Server server, Socket webSocket){
        hasMoves = true;
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
            System.out.println("From client: " + request);
            if(request == null) break;
            if(request.equals("Connected?")){
                clientOutput.writeBytes("Connected Sucesfully\n");
            }
            if(request.equals("Next Input:")){
                request = clientInput.readLine();
                input(Integer.parseInt(request));
                hasMoves = personalGame.movesAvailable();
                if(!hasMoves) stock(0);
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
                clientOutput.writeBytes("Winner!\n");
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

    public void stock(int state){
        System.out.println("Stock");
        if(state == 0)
        if(!opponent.hasMoves){
            
            //sum to know the winner
            String p1M = personalGame.getMatrixString();
            String p2M = opponent.getMatriString();

            String[] p1MSplit = p1M.split(" ");
            String[] p2MSplit = p2M.split(" ");

            int sumP1 = 0;
            int sumP2 = 0;


            for(int i = 0; i < p1MSplit.length; i++){
                sumP1 += Integer.parseInt(p1MSplit[i]);
                sumP2 += Integer.parseInt(p2MSplit[i]);
            }

            System.out.println(sumP1 + " " + sumP2);

            if(sumP1 > sumP2) {
                state = 1;
                opponent.stock(-1);
            }else if(sumP1 < sumP2){
                state = -1;
                opponent.stock(1);
            }else{
                state = 2;
                opponent.stock(2);
            }

        }

        if(state == 1){
            try{
            clientOutput.writeBytes("Winner!\n");
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
            //stop();
        }
        
        if(state == -1){
            try{
                clientOutput.writeBytes("Loser!\n");
            }catch(IOException e){
                    System.out.println(e.getMessage());
            }
            //stop();
        }

        if(state == 2){
            //Tie logic
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