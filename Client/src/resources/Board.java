package resources;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Board implements Runnable{

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
    
    private int[][] matrix;								// get from other file
	private int gameState = 0;							// state of game by server
    
	public Board(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		// dummy test
		matrix = new int[4][4];
		for(int i=0; i<4; ++i) {
			for(int j=0; j<4; ++j) {
				matrix[i][j] = 0;
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
					if(matrix[i][j] == 0) {
						g.setColor(emptyColor);
						g.fillRoundRect(215+j*121, 115+i*121, 106, 106, 7, 7);
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