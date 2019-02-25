import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;

public class SplitListener implements ActionListener {
	private static Buzz bg = new Buzz();
	private static JFrame splitFrame = new JFrame();
	private static final int FRAME_WIDTH = 475;
	private static final  int FRAME_HEIGHT = 200;
	private static ArrayList<JCheckBox> frameSelector = new ArrayList<JCheckBox>();
	private static ArrayList<Integer> framesToSplit = new ArrayList<Integer>();
	
	public void actionPerformed(ActionEvent event) {
		JPanel splitPanel = new JPanel();
		JLabel splitLabel = new JLabel("It costs $5000/frame for the materials. Which frames would you like to split?:");
		JButton okayButton = new JButton("Okay");
		okayButton.addActionListener(okayListener);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(cancelListener);
		splitPanel.add(splitLabel);
		for (int i = 1; i <= bg.getFramesPerHive(); i++)
		{
			frameSelector.add(new JCheckBox("Frame " + i));
			splitPanel.add(frameSelector.get(i-1));
		}
		splitPanel.add(okayButton);
		splitPanel.add(cancelButton);
		splitFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		splitFrame.add(splitPanel);
		splitFrame.setTitle("Hive Splitting");
		splitFrame.setVisible(true);
	}

	ActionListener okayListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent theEvent) {
			framesToSplit.clear();
			for (int i = 0; i < frameSelector.size(); i++)
			{
				if (frameSelector.get(i).isSelected())
				{
					framesToSplit.add(i);
				}
			}
			bg.splitHive(framesToSplit);
		}
	};

	ActionListener cancelListener = new ActionListener() {
	@Override
		public void actionPerformed(ActionEvent theEvent) {
		splitFrame.dispose();
		}
	};
}
