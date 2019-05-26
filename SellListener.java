import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class SellListener implements ActionListener {
	private static Buzz buzzGame;
	private static final int FRAME_WIDTH = 225;
	private static final  int FRAME_HEIGHT = 200;
	private static boolean go = false;
	private static int sell;
	private static JFrame sellFrame = new JFrame();
	private static ArrayList<JRadioButton> frameSelector = new ArrayList<JRadioButton>();

	public SellListener()
	{
	}
	
	public void actionPerformed(ActionEvent event) {
		JFrame sellFrame = new JFrame();
		JPanel sellPanel = new JPanel();
		JLabel sellLabel = new JLabel("What would you like to sell?");
		JButton honeyButton = new JButton("Honey");
		honeyButton.addActionListener(honeyListener);
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
					
					JButton okayButton = new JButton("Okay");
					JButton cancelButton = new JButton("Cancel");
					JPanel sellPanel = new JPanel();
					go = false;
					JLabel sellLabel = new JLabel("From which of the above frames would you like to sell?");
					for (int i = 1; i <= buzzGame.getFramesPerHive(); i++)
					{
						frameSelector.add(new JRadioButton("Frame " + i));
					}
					for (int i = 1; i <=buzzGame.getFramesPerHive(); i++)
					{
						sellPanel.add(frameSelector.get(i-1));
					}
					okayButton.addActionListener(okayListener);
					cancelButton.addActionListener(cancelListener);
					sellPanel.add(sellLabel);
					sellPanel.add(okayButton);
					sellPanel.add(cancelButton);
					sellFrame.setSize(600, 300);
					sellFrame.add(sellPanel);
					sellFrame.setTitle("Honey Selling");
					sellFrame.setVisible(true);
					while (go == false)
					{
						try
						{
							Thread.sleep(10);
						}
						catch(InterruptedException e){}
					}
		buzzGame.sellHoney(sell);
		}
	};

					ActionListener okayListener = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent theEvent) {
							for (int i = 0; i < frameSelector.size(); i++)
							{
								if (frameSelector.get(i).isSelected())
								{
									sell = i;
									go = true;
								}
							}
						}
					};

					ActionListener cancelListener = new ActionListener() {
					@Override
						public void actionPerformed(ActionEvent theEvent) {
						sellFrame.dispose();
						}
					};
}
