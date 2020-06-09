package resources;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		try {
			Socket cSock = new Socket("192.168.43.15", 8080);
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(cSock.getInputStream()));
			DataOutputStream serverOutput = new DataOutputStream(cSock.getOutputStream());
			Scanner keyboard = new Scanner(System.in);
			
			Thread thread = new Thread() {
				public void run() {
					try {
						String inputString;
						while(true) {
							inputString = serverInput.readLine();
							System.out.println(inputString);
						}
					}catch(IOException e) {
						
					}
				}
			};
			thread.start();
			
			String output = "";
			while(!output.equals("stop")) {
				output = keyboard.nextLine();
				serverOutput.writeBytes(output + "\n");
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void sendinput(int input) {
		
	}
}