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
	private final Timer myTimer;
	private ArrayList<Rectangle> rectList = new ArrayList<Rectangle>();
	private ArrayList<JLabel> labelList = new ArrayList<JLabel>();
	private int counter;

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
		myTimerDelay = 1000;
		myTimer = new Timer(myTimerDelay, frameTimer);
		myTimer.start();
	}
	public void paintComponent(Graphics g)
	{
		System.out.println(counter);
		counter++;
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		setBackground(Color.BLACK);
		g2.setColor(Color.WHITE);
		//JLabel introLabel = new JLabel("Hive " + h + " has " + hives[h-1].getBees() + " bees\n", 0 ,0);
		makeRectList();
		makeLabels(labelList);
		for (Rectangle element : rectList) 
		{
			g2.fill(element);
		}
		for (JLabel element : labelList)
		{
			add(element);
		}
	}

	public void makeRectList()
	{
		for (int i = 1; i <= framesPerHive; i++)
		{
			rectList.add(i-1, new Rectangle(0, BEEFRAME_HEIGHT/(3*framesPerHive) + BEEFRAME_HEIGHT*(i-1)/(framesPerHive+1), BEEFRAME_WIDTH, 40));
		} 
	}	

	public void makeLabels(ArrayList<JLabel> labelList)
	{
		DecimalFormat dec = new DecimalFormat("#.##");
		for (int i = 1; i <= frameCounter; i++)
		{
			int frameImageCounter = 0;
			if (frames[i-1].getHid() == h)
			{
				Date now = new Date();
				long frameAge = now.getTime()/1000L - frames[i-1].getStartTime();
				labelList.add(frameImageCounter,new JLabel());
				labelList.get(frameImageCounter).setText("Frame " + frames[i-1].getFid() + ": filled cells: " + dec.format((frames[i-1].getCells() - frames[i-1].getEmptyCells())*100.0/frames[i-1].getCellMax()) + "%, drawn cells: " + dec.format(frames[i-1].getCells()*100.0/frames[i-1].getCellMax()) + "%, nectar: " + frames[i-1].getNectar() + ", honey: " + frames[i-1].getHoney() + ", pollen: " + frames[i-1].getPollen() + ", larvae: " + frames[i-1].getLarvae() + ", eggs: " + frames[i-1].getBroodCells() + ", clutter: " + dec.format(frames[i-1].getClutter()*100.0/frames[i-1].getClutterMax()) + "%");
				labelList.get(frameImageCounter).setBounds(0, BEEFRAME_HEIGHT/(3*framesPerHive) + BEEFRAME_HEIGHT*(i-1)/(framesPerHive+1), BEEFRAME_WIDTH, 40);
				frameImageCounter++;
 				//frames[i-1].getAge(hives, frameAge, name, h,i);
				//System.out.println(name + " also has $" + money + " in total.\n");
			}
		}	
	}

	ActionListener frameTimer = new ActionListener()
	{
	@Override
		public void actionPerformed(ActionEvent theEvent)
		{
			//repaint();
		}
	};
}
