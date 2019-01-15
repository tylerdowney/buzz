import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class UpgradeListener implements ActionListener {
	private static Buzz buzzGame;
	private static final int FRAME_WIDTH = 475;
	private static final  int FRAME_HEIGHT = 200;

	public UpgradeListener(Buzz bg)
	{
		buzzGame = bg;
	}
	
	public void actionPerformed(ActionEvent event) {
		JFrame upgradeFrame = new JFrame();
		JPanel upgradePanel = new JPanel();
		JLabel upgradeLabel = new JLabel("What would you like to upgrade?");
		JButton eggButton = new JButton("Egg Production");
		JButton resourceButton = new JButton("Resource Collection");
		JButton commodityButton = new JButton("Commodity Value");
		BoxLayout upgradeLayout = new BoxLayout(upgradePanel, BoxLayout.X_AXIS);
		upgradePanel.setLayout(upgradeLayout);
		upgradePanel.add(eggButton);
		upgradePanel.add(resourceButton);
		upgradePanel.add(commodityButton);
		upgradeFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		upgradeFrame.add(upgradePanel);
		upgradeFrame.setTitle("What would you like to upgrade?");
		upgradeFrame.setVisible(true);
	}

	ActionListener honeyListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent theEvent) {
		}
	};
}
