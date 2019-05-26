import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class UpgradeListener implements ActionListener {
	private static Buzz buzzGame;
	private static final int FRAME_WIDTH = 475;
	private static final  int FRAME_HEIGHT = 200;

	public UpgradeListener()
	{
	}
	
	public void actionPerformed(ActionEvent event) {
		JFrame upgradeFrame = new JFrame();
		JPanel upgradePanel = new JPanel();
		JLabel upgradeLabel = new JLabel("What would you like to upgrade?");
		JButton eggButton = new JButton("Egg Production");
		eggButton.addActionListener(eggListener);
		JButton resourceButton = new JButton("Resource Collection");
		resourceButton.addActionListener(resourceListener);
		JButton commodityButton = new JButton("Commodity Value");
		commodityButton.addActionListener(commodityListener);
		JButton honeyButton = new JButton("Honey Supers");
		honeyButton.addActionListener(honeyListener);
		BoxLayout upgradeLayout = new BoxLayout(upgradePanel, BoxLayout.X_AXIS);
		upgradePanel.setLayout(upgradeLayout);
		upgradePanel.add(eggButton);
		upgradePanel.add(resourceButton);
		upgradePanel.add(commodityButton);
		upgradePanel.add(honeyButton);
		upgradeFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		upgradeFrame.add(upgradePanel);
		upgradeFrame.setTitle("What would you like to upgrade?");
		upgradeFrame.setVisible(true);
	}

	ActionListener resourceListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent theEvent) {
		buzzGame.upgradeResources();
		}
	};

	ActionListener eggListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent theEvent) {
		buzzGame.upgradeEggs();
		}
	};

	ActionListener commodityListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent theEvent) {
		buzzGame.upgradeCommodities();
		}
	};
	ActionListener honeyListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent theEvent) {
		buzzGame.upgradeHoneySuper();
		}
	};
}
