import javax.swing.JFrame;
import javax.swing.JButton;

public class BuzzMain {
	private static Buzz bg = new Buzz();
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 800;
	public static void main (String[] args) {
		HiveComponents hc = new HiveComponents(bg);
		JFrame frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		JButton sellButton = new JButton("Sell");
		SellListener sl = new SellListener(bg);
		sellButton.addActionListener(sl);
		hc.add(sellButton);
		JButton upgradeButton = new JButton("Buy Upgrades");
		UpgradeListener ul = new UpgradeListener(bg);
		upgradeButton.addActionListener(ul);
		hc.add(upgradeButton);
		JButton splitButton = new JButton("Split Hives");
		hc.add(splitButton);
		JButton sqButton = new JButton("Save and Quit");
		hc.add(sqButton);

		frame.add(hc);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
} 
