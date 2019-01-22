import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class SellListener implements ActionListener {
	private static Buzz buzzGame;
	private static final int FRAME_WIDTH = 225;
	private static final  int FRAME_HEIGHT = 200;

	public SellListener()
	{
	}
	
	public void actionPerformed(ActionEvent event) {
		JFrame sellFrame = new JFrame();
		JPanel sellPanel = new JPanel();
		JLabel sellLabel = new JLabel("What would you like to sell?");
		JButton honeyButton = new JButton("Honey");
		JButton pollenButton = new JButton("Pollen");
		JButton beeButton = new JButton("Bees");
		BoxLayout sellLayout = new BoxLayout(sellPanel, BoxLayout.X_AXIS);
		sellPanel.setLayout(sellLayout);
		sellPanel.add(honeyButton);
		sellPanel.add(pollenButton);
		sellPanel.add(beeButton);
		sellFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		sellFrame.add(sellPanel);
		sellFrame.setTitle("What would you like to sell?");
		sellFrame.setVisible(true);
	}

	ActionListener honeyListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent theEvent) {
		}
	};
}
