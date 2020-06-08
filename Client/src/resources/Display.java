package resources;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class Display {

	private JFrame frame;
	private Canvas canvas;
	private String title;
	private int width, height;
	private int moves;
	
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		createDisplay();
	}
	
	private void createDisplay() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.setFont(new Font("SansSerif", Font.BOLD, 48));
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		// take in arrow keys
		frame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				switch(keyCode) {
					case KeyEvent.VK_UP:
						moves = 0;
						break;
					case KeyEvent.VK_DOWN:
						moves = 1;
						break;
					case KeyEvent.VK_LEFT:
						moves = 2;
						break;
					case KeyEvent.VK_RIGHT:
						moves = 3;
						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		
		frame.add(canvas);
		frame.pack();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
}
