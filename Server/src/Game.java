package src;
import java.util.Random;
import java.util.*; 
public class Game  {
	private static Random rand = new Random();
	
    private boolean checkingAvailableMoves;
    static int highest;
    static int score;
    private Tile[][] tiles;

	   void addRandomTile() {
	        int pos = rand.nextInt(16);
	        int row, col;
	        do {
	            pos = (pos + 1) % (16);
	            row = pos / 4;
	            col = pos % 4;
	        } while (tiles[row][col] != null);
	        
	        tiles[row][col] = new Tile();
	    }
	   void printmatrix() {
			for(int i=0;i<4;i++)
			 {
				 for(int j=0;j<4;j++)
				 {	
					 int va = 0;
					 if(tiles[i][j]!=null) {
					  va= tiles[i][j].getValue();
					 }
					 System.out.print(" "+va+" "); 
				 }
				 System.out.println("");
			 }
			System.out.println("");
		 }
	
	
	private boolean move(int countDownFrom, int yIncr, int xIncr) {
		boolean moved = false;
		
		for (int i = 0; i < 16 ; i++) {
            int j = Math.abs(countDownFrom - i);

            int r = j / 4;
            int c = j % 4;

            if (tiles[r][c] == null)
                continue;

            int nextR = r + yIncr;
            int nextC = c + xIncr;
            while (nextR >= 0 && nextR < 4 && nextC >= 0 && nextC < 4) {

                 Tile next = tiles[nextR][nextC];
                 Tile curr = tiles[r][c];

                if (next == null) {

                    if (checkingAvailableMoves)
                        return true;

                    tiles[nextR][nextC] = curr;
                    tiles[r][c] = null;
                    r = nextR;
                    c = nextC;
                    nextR += yIncr;
                    nextC += xIncr;
                    moved = true;
                } else if (next.canMergeWith(curr)) {

                    if (checkingAvailableMoves)
                        return true;

                    int value = next.mergeWith(curr);
          /*          if (value > highest)
                        highest = value;
                    score += value; */ 
                    tiles[r][c] = null;
                    moved = true;                    
                    break;
                } else
                    break;
            }
        }
		if (moved) {
            if (highest < 2048) {
                clearMerged();
                addRandomTile();
            }
		}
        return moved;
    }
    
	 void clearMerged() {
	        for (int i=0;i<4;i++)
	            for (int j=0;j<4;j++)
	                if (tiles[i][j]!=null)
	                    tiles[i][j].setMerged(false);
	        // to reset the mergeability

	    }
	void startGame() {
             score = 0;
             highest = 0;
             tiles = new Tile[4][4];
             addRandomTile();
             addRandomTile();
         }
	 boolean movesAvailable() {
	        checkingAvailableMoves = true;
	        boolean hasMoves = moveUp() || moveDown() || moveLeft() || moveRight();
	        checkingAvailableMoves = false;
	        return hasMoves;
	    }
	 boolean moveUp() {
	        return move(0, -1, 0);
	    }

	    boolean moveDown() {
	        return move(15, 1, 0);
	    }

	    boolean moveLeft() {
	        return move(0, 0, -1);
	    }

	    boolean moveRight() {
	        return move(15, 0, 1);
	    }
	    
	   
}
class Tile {
    private boolean merged;
    private int value;

    Tile() {
        value = 2;
    }

    int getValue() {
        return value;
    }

    void setMerged(boolean m) {
        merged = m;
    }

    boolean canMergeWith(Tile other) {
        return !merged && other != null && !other.merged && value == other.getValue();
    }

    int mergeWith(Tile other) {
        if (canMergeWith(other)) {
            value = value * 2;
            merged = true;
            return value;
        }
        return -1;
    }
    
}