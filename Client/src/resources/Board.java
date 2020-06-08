package resources;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable{

	private Display display;
	private Thread thread;
	private BufferStrategy bs;
	private Graphics g;
	
	public String title;
	public int width, height;
	private boolean running = false;
	
	final static int target = 2048;
	
	private Color gridColor = new Color(0xBBADA0);
    private Color emptyColor = new Color(0xCDC1B4);
    private Color startColor = new Color(0xFFEBCD);
    
    final Color[] numbers = {
    	new Color(0xfff4d3), new Color(0xffdac3), 		// 2 	4
    	new Color(0xe7b08e), new Color(0xe7bf8e),		// 8	16
        new Color(0xffc4c3), new Color(0xE7948e), 		// 32	64	
        new Color(0xbe7e56), new Color(0xbe5e56), 		// 128	256
        new Color(0x9c3931), new Color(0x701710)		// 512	1028
   };
    
   final Color[] text = {
		new Color(0x701710), new Color(0xFFE4C3)		// dark		light
   };
    
    private int[][] matrix;								// get from other file
	private int gameState = 1;							// state of game by server
    
	public Board(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		
		// dummy test
		matrix = new int[4][4];
		for(int i=0; i<4; ++i) {
			for(int j=0; j<4; ++j) {
				matrix[i][j] = 2;
			}
		}
		
	}
	
	private void init() {
		display = new Display(title, width, height);
	}
	
	private void tick() {
		
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		// Clear screen
		g.clearRect(0, 0, width, height);
		
		// Drawing canvas
		// Waiting for player
		if(gameState == 0) {
			g.setColor(Color.decode("#BBADA0"));
			g.fillRoundRect(200, 50, 499, 599, 15, 15);
			for(int i=0; i<4; ++i) {
				for(int j=0; j<4; ++j) {
					if(matrix[i][j] == 0) {
						g.setColor(emptyColor);
						g.fillRoundRect(215+j*121, 65+i*121, 106, 106, 7, 7);
					}
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("SansSerif", Font.BOLD, 52));
			g.drawString("Waiting...", 340, 610);
		}
		// Player connected
		else {
			g.clearRect(0, 0, width, height);
			g.setColor(Color.decode("#BBADA0"));
			g.fillRoundRect(200, 100, 499, 499, 15, 15);
			for(int i=0; i<4; ++i) {
				for(int j=0; j<4; ++j) {
					g.setColor(emptyColor);
					g.fillRoundRect(215+j*121, 115+i*121, 106, 106, 7, 7);
					
					// set color of tile block
					int value = matrix[i][j];
					if(value != 0 & value != 2048) {
						g.setColor(numbers[(int) (Math.log(value) / Math.log(2) - 1)]);
						g.fillRoundRect(215+j*121, 115+i*121, 106, 106, 7, 7);
					
						String s = String.valueOf(value);
						g.setColor(value < 128 ? text[0] : text[1]);
						g.setFont(new Font("SansSerif", Font.BOLD, 52));
						FontMetrics fm = g.getFontMetrics();
						int asc = fm.getAscent();
						int dsc = fm.getDescent();
						int x = 215+j*121 + (106 - fm.stringWidth(s)) / 2;
				        int y = 115+i*121 + (asc + (106 - (asc + dsc)) / 2);
				        g.drawString(s, x, y);
					}
					// value is 0
					else if(value == 2048) {
						// TODO gameWon();
					}
				}
			}
		}
			
		// End drawing
		bs.show();
		g.dispose();
	}
	
	public void run() {
		init();
		while(running) {
			tick();
			render();
		}
		stop();
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}