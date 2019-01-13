package view;

import javax.swing.JFrame;

public class HiveViewer {
	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 800;
	public static void main (String[] args) {
		HiveComponents hc = new HiveComponents();
		JFrame frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.add(hc);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
} 