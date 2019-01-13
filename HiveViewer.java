import javax.swing.JFrame;
import javax.swing.JButton;

public class HiveViewer {
	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 800;
	public static void main (String[] args) {
		HiveComponents hc = new HiveComponents();
		JFrame frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		JButton sellButton = new JButton("Sell");
		hc.add(sellButton);
		JButton buyButton = new JButton("Buy Upgrades");
		hc.add(buyButton);
		JButton splitButton = new JButton("Split Hives");
		hc.add(splitButton);
		JButton sqButton = new JButton("Save and Quit");
		hc.add(sqButton);

		frame.add(hc);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
} 
