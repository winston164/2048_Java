package resources;
import java.io.*;
import java.net.*;

public class Client {
	BufferedReader serverInput;
	DataOutputStream serverOutput;
	Board p1;
	Board p2;

	public Client() {
		try {
			//Start the Boards
			p1 = new Board("Your Game", this);
			p2 = new Board("Opponent's Game", null);
			p1.setGameState(0);
			p2.setGameState(0);
			p1.start();
			p2.start();


			//make connection with the server
			Socket cSock = new Socket("192.168.0.113", 8080);
			serverInput = new BufferedReader(new InputStreamReader(cSock.getInputStream()));
			serverOutput = new DataOutputStream(cSock.getOutputStream());
			
			String inputString = "";
			while(!inputString.equals("Winner!") && !inputString.equals("Looser!")){
				inputString = serverInput.readLine();
				//System.out.println(inputString);
				switch (inputString) {
					case "Player1:":
						p1.setGameState(4);
						String p1Matrix = serverInput.readLine();

						p1.setMatrix(parseMatrix(p1Matrix));
						break;
						
					case "Winner!":
						//set p1 as winner and finish
						p1.setGameState(1);
						p2.setGameState(2);
						finish();
						break;

					case "Player2:":
						p2.setGameState(4);
						String p2Matrix = serverInput.readLine();
						p2.setMatrix(parseMatrix(p2Matrix));
						break;
					
					case "Loser!":
						//set p1 as looser and finish
						p1.setGameState(2);
						p2.setGameState(1);
						finish();
						break;

					case "Tie!":
						//set p1 as looser and finish
						p1.setGameState(3);
						p2.setGameState(3);
						finish();
						break;

					case "Close Connection":
						//set p1 as looser and finish
						p1.setGameState(0);
						p2.setGameState(0);
						finish();
						break;
					default:
						break;
				}
			}
			cSock.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendinput(int input) {
		if(serverOutput == null) return;
		try {
			serverOutput.writeBytes("Next Input:\n");
			serverOutput.writeBytes(Integer.toString(input) + "\n");
		} catch(IOException e){
			System.out.println(e.getMessage());
		}
		
	}

	private int[][] parseMatrix(String mString){
		int[][] value = new int[4][4];
		String[] stringValues = mString.split(" ");
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4 ; j++){
				try {
					value[i][j] = Integer.parseInt(stringValues[4*i + j]);
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return value;
	}

	private void finish(){
		try {
			serverOutput.writeBytes("Close Connection\n");
			serverInput.close();
			serverOutput.close();
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}