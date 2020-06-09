import resources.*;

public class MainClass{
    
    public static void main(String[] args){
    	
    	Board p1 = new Board("Player 1", 900, 700);
    	Board p2 = new Board("Player 2", 900, 700);
    	p1.start();
    	p2.start();
    	
    }
    
}



