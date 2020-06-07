import java.awt.*;

public class Board{
	Client client;
	
	public Board() {
        setPreferredSize(new Dimension(900, 700));
        setBackground(new Color(0xFAF8EF));
        setFont(new Font("SansSerif", Font.BOLD, 48));
        setFocusable(true);
 
        addKeyListener(new KeyAdapter()) {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                       // moveUp();
                        break;
                    case KeyEvent.VK_DOWN:
                      //  moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                       // moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                       // moveRight();
                        break;
                }
                repaint();
            }
        };
    }
	
	void displayMatrix(int[] board) {
		board = new int[16];
	}
	
	void gameWon(int state);
	Board(Client client, String name);
	void start();
	
}

public class Client{
	void sendinput(int input);
}




