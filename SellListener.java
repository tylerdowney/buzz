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
	private static char sellFlag;
	private static final int FRAME_WIDTH = 225;
	private static final  int FRAME_HEIGHT = 200;
	private static boolean go = false;
	private static int sell;
	private static JFrame sellFrame = new JFrame();
	private static JButton okayButton = new JButton("Okay");
	private static JButton cancelButton = new JButton("Cancel");
	private static JPanel sellPanel = new JPanel();
	private static JPanel hpPanel = new JPanel();
	private static JFrame hpFrame = new JFrame();
	private static ButtonGroup bGroup = new ButtonGroup();
	private static JLabel sellLabel = new JLabel("What would you like to sell?");
	private static JLabel hpLabel = new JLabel("From which of the above frames would you like to sell?");
	private static ArrayList<JRadioButton> frameSelector = new ArrayList<JRadioButton>();

	public SellListener()
	{
	}
	
	public void actionPerformed(ActionEvent event) {
		JButton honeyButton = new JButton("Honey");
		honeyButton.addActionListener(honeyListener);
		JButton pollenButton = new JButton("Pollen");
		pollenButton.addActionListener(pollenListener);
		JButton beeButton = new JButton("Bees");
		beeButton.addActionListener(beeListener);
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
			sellFlag = 'h';
			// This works but I hate it. Maybe a better solution later?
			JRadioButton frame1 = new JRadioButton("Frame 1");
			frameSelector.add(frame1);
			bGroup.add(frame1);
			JRadioButton frame2 = new JRadioButton("Frame 2");
			frameSelector.add(frame2);
			bGroup.add(frame2);
			JRadioButton frame3 = new JRadioButton("Frame 3");
			frameSelector.add(frame3);
			bGroup.add(frame3);
			JRadioButton frame4 = new JRadioButton("Frame 4");
			frameSelector.add(frame4);
			bGroup.add(frame4);
			JRadioButton frame5 = new JRadioButton("Frame 5");
			frameSelector.add(frame5);
			bGroup.add(frame5);
			JRadioButton frame6 = new JRadioButton("Frame 6");
			frameSelector.add(frame6);
			bGroup.add(frame6);
			JRadioButton frame7 = new JRadioButton("Frame 7");
			frameSelector.add(frame7);
			bGroup.add(frame7);
			JRadioButton frame8 = new JRadioButton("Frame 8");
			frameSelector.add(frame8);
			bGroup.add(frame8);
			JRadioButton frame9 = new JRadioButton("Frame 9");
			frameSelector.add(frame9);
			bGroup.add(frame9);
			JRadioButton frame10 = new JRadioButton("Frame 10");
			frameSelector.add(frame10);
			bGroup.add(frame10);
			
			for (int i = 1; i <=buzzGame.getFramesPerHive(); i++)
			{
				hpPanel.add(frameSelector.get(i-1));
			}
			okayButton.addActionListener(okayListener);
			cancelButton.addActionListener(cancelListener);
			hpPanel.add(hpLabel);
			hpPanel.add(okayButton);
			hpPanel.add(cancelButton);
			hpFrame.setSize(600, 300);
			hpFrame.add(hpPanel);
			hpFrame.setTitle("Honey Selling");
			hpFrame.setVisible(true);
			}
		};
		
		ActionListener pollenListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				sellFlag = 'p';
				for (int i = 1; i <= buzzGame.getFramesPerHive(); i++)
				{
					frameSelector.add(new JRadioButton("Frame " + i));
				}
				for (int i = 1; i <=buzzGame.getFramesPerHive(); i++)
				{
					hpPanel.add(frameSelector.get(i-1));
				}
				okayButton.addActionListener(okayListener);
				cancelButton.addActionListener(cancelListener);
				hpPanel.add(hpLabel);
				hpPanel.add(okayButton);
				hpPanel.add(cancelButton);
				hpFrame.setSize(600, 300);
				hpFrame.add(hpPanel);
				hpFrame.setTitle("Pollen Selling");
				hpFrame.setVisible(true);
				}
			};
			
			ActionListener beeListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent theEvent) {
					buzzGame.sellBees();
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
						if (sellFlag == 'h')
						{
							buzzGame.sellHoney(sell);
							hpFrame.dispose();
						}
						else if (sellFlag == 'p')
						{		
							buzzGame.sellPollen(sell);
							hpFrame.dispose();
						}
					}
				}
			}
		};

		ActionListener cancelListener = new ActionListener() {
		@Override
			public void actionPerformed(ActionEvent theEvent) {
			hpFrame.dispose();
			}
		};
}
