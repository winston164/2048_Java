package src;

import java.util.Scanner;

public class Test extends Game {
		
	 public static void main(String[] args) {
		 Game game = new Game();
		 int movement = 10;
		 game.startGame();
		 
		 game.printmatrix();
		 
		 Scanner scanner = new Scanner(System.in);
		 
		 while(scanner.hasNextInt())
		 {
			movement = scanner.nextInt();
		 if(movement == 0)
		 {
			 game.moveUp();
			 game.printmatrix();
		 }
		 else if(movement == 1)
		 {
			 game.moveDown();
			 game.printmatrix();
		 }
		 else if(movement ==2)
		 {
			 game.moveLeft();
			 game.printmatrix();
		 }
		 else if(movement ==3)
		 {
			 game.moveRight();
			 game.printmatrix();
		 }
		 }
	 }
}
