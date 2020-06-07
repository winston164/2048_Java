package src;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {

        try {

            // Wait for connection to the client in port 8080
            System.out.println("Started server and waiting for connection...");
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket connectionSocket = serverSocket.accept();

            BufferedReader clientInput = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream clientOutput = new DataOutputStream(connectionSocket.getOutputStream());

            //Connection successful, get client input
            String clientText = "text";
            while(!clientText.equals("null")){
                clientText = clientInput.readLine();
                System.out.println("From client: " + clientText);
                if(clientText.equals("Accept-Language: en-US,en;q=0.9,es;q=0.8,zh-TW;q=0.7,zh;q=0.6")) clientOutput.writeBytes("200");
            }

            clientInput.close();
            connectionSocket.close();
            serverSocket.close();   
        } catch (Exception e) {
            System.out.println("Disconnected with exception: " + e.getMessage());
        }
    }
    
}