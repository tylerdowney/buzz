import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class BuzzMain {
	private static Buzz bg = new Buzz();
	private static JFrame startFrame = new JFrame();
	private static JFrame textFrame = new JFrame();
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 800;
	public static void main (String[] args) {
		gameStart();
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

	public static void gameStart()
	{
		JPanel startPanel = new JPanel();
		startFrame.setSize(FRAME_WIDTH/2, FRAME_HEIGHT/2);
		startFrame.setVisible(true);
		BoxLayout startLayout = new BoxLayout(startPanel, BoxLayout.X_AXIS);
		startPanel.setLayout(startLayout);
		JButton newButton = new JButton("New Game");
		newButton.addActionListener(newGameListener);
		JButton loadButton = new JButton("Load Game");
		loadButton.addActionListener(loadGameListener);
		startPanel.add(newButton);
		startPanel.add(loadButton);	
		startFrame.add(startPanel);
		while (bg.getName() == "")
		{
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException e)
			{}
		}
		textFrame.dispose();
	}
	
	static ActionListener newGameListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent theEvent) {
			textFrame = bg.startNewGame(startFrame);
		}
	};

	static ActionListener loadGameListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent theEvent) {
			bg.loadGame();
		}
	};
} 
