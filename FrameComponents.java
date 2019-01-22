import java.awt.Color;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

public class FrameComponents extends JPanel
{
	private int h;
	private int BEEFRAME_WIDTH;
	private int BEEFRAME_HEIGHT;
	private int framesPerHive;
	private int frameCounter;
	private Hive[] hives;
	private Frame[] frames;
	private int myTimerDelay;
	private	int frameImageCounter;
	private final Timer myTimer;
	private ArrayList<Rectangle> rectList = new ArrayList<Rectangle>();
	private ArrayList<JLabel> labelList = new ArrayList<JLabel>();
	private ArrayList<JLabel> ageList = new ArrayList<JLabel>();
	private static JButton sellButton = new JButton("Sell");
	private static JButton upgradeButton = new JButton("Buy Upgrades");
	private static JButton splitButton = new JButton("Split Hives");
	private static SellListener sl = new SellListener();
	private static UpgradeListener ul = new UpgradeListener();

	public FrameComponents(Hive[] hives, Frame[] frames, int hid, int fph, int w, int height, int fc)
	{
		super();
		this.hives = hives;
		this.frames = frames;
		h = hid;
		framesPerHive = fph;
		BEEFRAME_WIDTH = w;
		BEEFRAME_HEIGHT = height;
		frameCounter = fc;
		frameImageCounter = 0;
		myTimerDelay = 1000;
		myTimer = new Timer(myTimerDelay, frameTimer);
		myTimer.start();
		setLayout(null);
		sellButton.setBounds(0,BEEFRAME_HEIGHT/(3*framesPerHive) + (framesPerHive) * BEEFRAME_HEIGHT/(framesPerHive+2), 100,30);
		upgradeButton.setBounds(100,BEEFRAME_HEIGHT/(3*framesPerHive) + (framesPerHive) * BEEFRAME_HEIGHT/(framesPerHive+2), 150,30);
		splitButton.setBounds(250,BEEFRAME_HEIGHT/(3*framesPerHive) + (framesPerHive) * BEEFRAME_HEIGHT/(framesPerHive+2), 150,30);
		sellButton.addActionListener(sl);
		upgradeButton.addActionListener(ul);
		add(sellButton);
		add(upgradeButton);
		add(splitButton);
	}
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		setBackground(Color.BLACK);
		g2.setColor(Color.WHITE);
		makeRectList();
		makeLabels(labelList);
		for (Rectangle element : rectList) 
		{
			g2.fill(element);
		}
		for (int i = 1; i <= labelList.size(); i++)
		{
			add(labelList.get(i-1));
			add(ageList.get(i-1));
		}
	}

	public void makeRectList()
	{
		for (int i = 1; i <= framesPerHive; i++)
		{
			rectList.add(i-1, new Rectangle(0, BEEFRAME_HEIGHT/(3*framesPerHive) + BEEFRAME_HEIGHT*(i-1)/(framesPerHive+2), BEEFRAME_WIDTH, 40));
		} 
	}	

	public void makeLabels(ArrayList<JLabel> labelList)
	{
		DecimalFormat dec = new DecimalFormat("#.##");
		for (int i = 1; i <= frameCounter; i++)
		{
			if (frames[i-1].getHid() == h && frameImageCounter < framesPerHive)
			{
				Date now = new Date();
				long frameAge = now.getTime()/1000L - frames[i-1].getStartTime();
				labelList.add(frameImageCounter, new JLabel());
				ageList.add(frameImageCounter, new JLabel());
				labelList.get(frameImageCounter).setText("Frame " + frames[i-1].getFid() + ": filled cells: " + dec.format((frames[i-1].getCells() - frames[i-1].getEmptyCells())*100.0/frames[i-1].getCellMax()) + "%, drawn cells: " + dec.format(frames[i-1].getCells()*100.0/frames[i-1].getCellMax()) + "%, nectar: " + frames[i-1].getNectar() + ", honey: " + frames[i-1].getHoney() + ", pollen: " + frames[i-1].getPollen() + ", larvae: " + frames[i-1].getLarvae() + ", eggs: " + frames[i-1].getBroodCells() + ", clutter: " + dec.format(frames[i-1].getClutter()*100.0/frames[i-1].getClutterMax()) + "%");
				ageList.get(frameImageCounter).setText(frames[i-1].getAge(hives,frameAge, h, i));
				labelList.get(frameImageCounter).setBounds(0, -10 + BEEFRAME_HEIGHT/(3*framesPerHive) + BEEFRAME_HEIGHT*(i-1)/(framesPerHive+2), BEEFRAME_WIDTH, 40);
				ageList.get(frameImageCounter).setBounds(0, BEEFRAME_HEIGHT/(3*framesPerHive) + BEEFRAME_HEIGHT*(i-1)/(framesPerHive+2) + 10, BEEFRAME_WIDTH, 40);
				frameImageCounter++;
			}
		}	
	}

	ActionListener frameTimer = new ActionListener()
	{
	@Override
		public void actionPerformed(ActionEvent theEvent)
		{
			repaint();
		}
	};
}
