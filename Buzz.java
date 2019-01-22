// Importing required packages for specialized actions

import java.io.*;
import java.util.*;
import java.lang.*;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.Rectangle;

public class Buzz
{
	// Initialize flag to end game, action variable, money variable, sales variables, upgrade variables, load flag, gametime variables, off game time variable, resource/bee acquisition delays, hive and frame counters, resource/bee generation times, random variable, frames per hive, Hive and Frame object declarations, terminal scanner object, timer declarations, and username variable

	private static Integer h;
	private static Integer f;
	private static int endflag = 0;
	private static char action;
	private static double money = 0;
	private static char sellVar;
	private static int sell;
	private static char upgrade;
	private static double cUpCost = 1000; // Cost of carrying capacity upgrade
	private static double gUpCost = 1000; // Cost of queen laying upgrade
	private static double vUpCost = 1000; // Cost of bee/honey/pollen values upgrade
	private static double superCost = 5000; // Cost of Honey Super upgrade
	private static int superIncrease = 3500;
	private static double frameCost = 0;
	private static double valueUpgrade = 1;
	private static char load;
	private static long oldtime;
	private static Date newtime;
	private static long worldAge;
	private static long offgametime;
	private static int resourceOffset = 0;
	private static int honeyOffset = 0;
	private static int beeOffset = 0;
	private static int larvaeOffset = 0;
	private static int broodOffset = 0;
	private static int waxOffset = 0;
	private static int hiveCounter = 0;
	private static int frameCounter = 0;
	private static Timer resourceTimer = new Timer();
	private static Timer honeyTimer = new Timer();
	private static Timer consumeTimer = new Timer();
	private static Timer broodTimer = new Timer();
	private static Timer larvaeTimer = new Timer();
	private static Timer beeTimer = new Timer();
	private static Timer waxTimer = new Timer();
	private static Timer clutterTimer = new Timer();
	private static int resourceTime = 10000; // Adjusts rate of resource accrual in milliseconds
	private static int honeyTime = 30000; // Adjusts rate of conversion of nectar to honey in millseconds
	private static int consumeTime = 40000; // Adjusts rate of resource consumption in milliseconds
	private static int broodTime = 40000; // Adjusts rate of egg laying in milliseconds
	private static int larvaeTime = 355000; // Adjusts rate of eggs hatching into larvae milliseconds
	private static int beeTime = 475000; // Adjusts rate of larvae developing into bees in milliseconds
	private static int clutterTime = 5000; // Adjusts rate of clutter build up in milliseconds
	private static int waxTime = 5000; // Adjusts rate of comb development in milliseconds
	private static double clutPerSec = 0.01;
	private static double clutPerBee = 0.0001;
	private static double clutPerEgg = 0.001;
	private static Random rand = new Random();
	private static JTextField startText = new JTextField("Welcome! Please enter your name");
	private static int n;
	private static int framesPerHive = 10;
	private static int broodMax = 2000;
	private static Hive[] hives = new Hive[1000];
	private static Frame[] frames = new Frame[framesPerHive * hives.length];
	private static WorldClock world = new WorldClock();
	private static double[] clutterOffset = new double[1000];
	private static Scanner in = new Scanner(System.in);
	private static String name = "";

	public Buzz()
	{
	}

		/*System.out.println("Would you like to load a saved game (y/n)?");
		load = in.next().charAt(0);
		if (load == 'y')
		{*/

		public static void loadGame(JFrame prevFrame)
		{
			prevFrame.dispose();
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			JFrame frame = new JFrame("FileSelect");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			int result = fileChooser.showOpenDialog(frame);
			File inFile = new File("");
			if (result == JFileChooser.APPROVE_OPTION)
			{
    				inFile = fileChooser.getSelectedFile();
			}
			try
			{
				// Load game variables from file; add in resource/bee amounts corresponding to elapsed time since last game

				Scanner sc = new Scanner(inFile);
				name = sc.next();
				frameCounter = sc.nextInt();
				hiveCounter = sc.nextInt();
				money = sc.nextDouble();
				oldtime = sc.nextLong();
				world.setStartTime(sc.nextLong());
				newtime = new Date();
				offgametime = (newtime.getTime()/1000L - oldtime);
				int intoffgametime = (int) offgametime;
				resourceOffset = intoffgametime*1000/resourceTime;
				honeyOffset = intoffgametime*1000/honeyTime;
				beeOffset = intoffgametime*1000/beeTime;
				larvaeOffset = intoffgametime*1000/larvaeTime - intoffgametime*1000/beeTime;
				broodOffset = intoffgametime*1000/broodTime - intoffgametime*1000/larvaeTime;
				waxOffset = intoffgametime*1000/waxTime;

				for (int i = 1; i <= hiveCounter; i++)
				{
					hives[i-1] = new Hive(i);
					hives[i-1].setBees(sc.nextInt());
					hives[i-1].setBeeUpgrade(sc.nextInt());
					hives[i-1].setQueenUpgrade(sc.nextInt());
					hives[i-1].addBees(world.getRandomInt(beeOffset * 20 * hives[i-1].getQueenUpgrade()));	
					if (hives[i-1].getBees() > hives[i-1].getBeeMax())
					{
						hives[i-1].setBees(hives[i-1].getBeeMax());
					}
					clutterOffset[i-1] = clutPerSec*intoffgametime*1000.0/clutterTime - clutPerBee*intoffgametime*1000.0*hives[i-1].getBees()/clutterTime;
				}
				for (int j = 1; j <= frameCounter; j++)
				{
					int tempHid = -1;
					tempHid = sc.nextInt();
					hives[tempHid - 1].addFrames();

					frames[j-1] = new Frame(tempHid, hives[tempHid - 1].getFrames(), Math.abs(6 - hives[tempHid - 1].getFrames()));
					frames[j-1].setHoney(sc.nextInt());
					frames[j-1].setNectar(sc.nextInt());
					frames[j-1].setPollen(sc.nextInt());
					frames[j-1].setLarvae(sc.nextInt());
					frames[j-1].setBrood(sc.nextInt());
					frames[j-1].setEmptyCells(sc.nextInt());
					frames[j-1].setClutter(sc.nextDouble());
					frames[j-1].setStartTime(sc.nextLong());

					int addedHoney = world.getRandomInt(resourceOffset * hives[frames[j-1].getHid()-1].getBeeUpgrade());
					int addedNectar = world.getRandomInt(honeyOffset * hives[frames[j-1].getHid()-1].getBeeUpgrade());
					int addedPollen = world.getRandomInt(resourceOffset * hives[frames[j-1].getHid()-1].getBeeUpgrade());
					int addedLarvae = world.getRandomInt(larvaeOffset * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade());
					int addedBrood = world.getRandomInt(broodOffset * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade());
					int addedWax = world.getRandomInt(waxOffset * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade());

					if (frames[j-1].getDistToCent() == 0 )
					{
						frames[j-1].addHoney(addedHoney/16);
						frames[j-1].addNectar(addedNectar/16);
						frames[j-1].addPollen(addedPollen/16);
						frames[j-1].addLarvae(3*addedLarvae/16);	
						frames[j-1].addBrood(3*addedBrood/16);	
						frames[j-1].addEmptyCells(3*addedWax/16);
						frames[j-1].addEmptyCells(-addedHoney/16 - - addedNectar/16 - addedPollen/16 - 3*addedLarvae/16 - 3*addedBrood/16); 
						frames[j-1].addClutter(world.getRandomDouble(clutterOffset[tempHid-1] + (clutPerEgg*1000.0*intoffgametime*3*addedBrood/(16*broodTime) * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade())));	
					}
					else if (frames[j-1].getDistToCent() == 1 || frames[j-1].getDistToCent() == 2)
  					{
						frames[j-1].addHoney(2*addedHoney/16);
						frames[j-1].addNectar(2*addedNectar/16);
						frames[j-1].addPollen(2*addedPollen/16);
						frames[j-1].addLarvae(2*addedLarvae/16);	
						frames[j-1].addBrood(2*addedBrood/16);	
						frames[j-1].addEmptyCells(2*addedWax/16);
						frames[j-1].addEmptyCells(-2*addedHoney/16 - 2*addedNectar/16 -  2*addedPollen/16 - 2*addedLarvae/16 - 2*addedBrood/16); 
						frames[j-1].addClutter(world.getRandomDouble(clutterOffset[tempHid-1] + (clutPerEgg*1000.0*intoffgametime*2*addedBrood/(16*broodTime) * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade())));	
					}
					else if (frames[j-1].getDistToCent() > 2)
  					{
						frames[j-1].addHoney(3*addedHoney/16);
						frames[j-1].addNectar(3*addedNectar/16);
						frames[j-1].addPollen(3*addedPollen/16);
						frames[j-1].addLarvae(addedLarvae/16);	
						frames[j-1].addBrood(addedBrood/16);	
						frames[j-1].addEmptyCells(addedWax/16);
						frames[j-1].addEmptyCells(-3*addedHoney/16 - 3*addedNectar/16 - 3*addedPollen/16 - addedLarvae/16 - addedBrood/16); 
						frames[j-1].addClutter(world.getRandomDouble(clutterOffset[tempHid-1] + (clutPerEgg*1000.0*intoffgametime*addedBrood/(16*broodTime) * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade())));	
					}
					
					// Handle max and min clutter cases
					if (frames[j-1].getClutter() > 5000)
					{
						frames[j-1].setClutter(5000);
					}	
					else if (frames[j-1].getClutter() < 0)
					{
						frames[j-1].setClutter(0);
					}
					// Proportionally set new values if they exceed max
					if (frames[j-1].getCells() >= frames[j-1].getCellMax())
					{

						Long honeyFactor = Math.round((frames[j-1].getHoneyCells() * 1.0/frames[j-1].getCells()) * frames[j-1].getCellMax());
						Long nectarFactor = Math.round((frames[j-1].getNectarCells() * 1.0/frames[j-1].getCells()) * frames[j-1].getCellMax());
						Long pollenFactor = Math.round((frames[j-1].getPollenCells() * 1.0/frames[j-1].getCells()) * frames[j-1].getCellMax());
						Long larvaeFactor = Math.round((frames[j-1].getLarvae() * 1.0/frames[j-1].getCells()) * frames[j-1].getCellMax());
						Long broodFactor = Math.round((frames[j-1].getBroodCells() * 1.0/frames[j-1].getCells()) * frames[j-1].getCellMax());
						Long emptyFactor = Math.round((frames[j-1].getEmptyCells() * 1.0/frames[j-1].getCells()) * frames[j-1].getCellMax());

						frames[j-1].setHoney(honeyFactor.intValue());
						frames[j-1].setNectar(nectarFactor.intValue());
						frames[j-1].setPollen(pollenFactor.intValue());
						frames[j-1].setLarvae(larvaeFactor.intValue());
						frames[j-1].setBrood(broodFactor.intValue());
						frames[j-1].setEmptyCells(emptyFactor.intValue());
					}
				}
				sc.close();
				startTimers();
			}
			catch (FileNotFoundException e)
			{
				JFrame notLoadFrame = new JFrame();
				notLoadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				notLoadFrame.setSize(200,200);
				JPanel notLoadPanel = new JPanel();
				JLabel notLoadLabel = new JLabel("Could not load file");
				JButton notLoadButton = new JButton("Okay");
				notLoadLabel.setBounds(100, 0, 200, 200);
				notLoadPanel.add(notLoadLabel);
				notLoadPanel.add(notLoadButton);
				notLoadFrame.add(notLoadPanel);
				notLoadFrame.setVisible(true);

				ActionListener notLoadListener = new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent theEvent)
					{
						notLoadFrame.dispose();
					}
				};
			}
		}

		public static JFrame startNewGame(JFrame prevFrame)
		{
			// New game
			prevFrame.dispose();
			JFrame textFrame = new JFrame();
			textFrame.setSize(300,300);
			textFrame.setVisible(true);
			startText.addActionListener(submitTextListener);
			textFrame.add(startText);

			//Initialize Hive 1 Frame 1 resources (bees, honey, pollen). Start WorldClock
			
			Date starttime = new Date();
			long begin = starttime.getTime()/1000L;
			world.setStartTime(begin);
			hiveCounter++;
			hives[0] = new Hive(hiveCounter);
			hives[0].addFrames();
			frames[0] = new Frame(hives[0].getHid(), hives[0].getFrames(), Math.abs(6- hives[0].getFrames()));
			frameCounter++;
			frames[0].setStartTime(begin);

			for (int i = 2; i <= 10; i++)
			{
				hives[0].addFrames();
				frames[i-1] = new Frame(hives[0].getHid(), hives[0].getFrames(), Math.abs(6 -hives[0].getFrames()));
				frames[i-1].setStartTime(begin);
				frameCounter++;
			}
			startTimers();
			return textFrame;
		}

		//Start game timers

		public static void startTimers()
		{
			world.startResourceTimer(hives, frames, resourceTimer, frameCounter, hiveCounter);
			world.startConsumeTimer(hives, frames, consumeTimer, frameCounter, hiveCounter);	
			world.startBroodTimer(hives, frames, broodTimer, frameCounter, hiveCounter);
			world.startLarvaeTimer(hives, frames, larvaeTimer, frameCounter, hiveCounter);
			world.startBeeTimer(hives, frames, beeTimer, frameCounter, hiveCounter);
			world.startWaxTimer(hives, frames, waxTimer, frameCounter, hiveCounter);
			world.startClutterTimer(hives, frames, clutterTimer, frameCounter, hiveCounter);
		}

		public static void inspectHive(int hid)
		{
			int BEEFRAME_WIDTH = 800;
			int BEEFRAME_HEIGHT = 600;
			JFrame beeFrame = new JFrame();
			FrameComponents fc = new FrameComponents(hives, frames, hid, framesPerHive, BEEFRAME_WIDTH, BEEFRAME_HEIGHT, frameCounter);
			beeFrame.setSize(BEEFRAME_WIDTH, BEEFRAME_HEIGHT);
			beeFrame.add(fc);
			beeFrame.setVisible(true);
		}

				public static void sellResources()
				{
					for (int i = 1; i <= hiveCounter; i++)
					{
						System.out.println("Hive " + hives[i-1].getHid());
					}
					System.out.println("Sell from which hive?");
					h = in.nextInt();
					if (h.equals(null) || h > hiveCounter || h <= 0)
					{
						System.out.println("Invalid selection");
					}
					for (int j = 1; j <= hives[h-1].getFrames(); j++)
					{
						System.out.println("Frame " + j);
					}
					System.out.println("Sell from which frame?");
					f = in.nextInt();
					if (f.equals(null) || f > hives[h-1].getFrames() || f <= 0)
					{
						System.out.println("Invalid selection");
					}
					for (int i = 1; i <= frameCounter; i++)
					{
						if (frames[i-1].getHid() == h && frames[i-1].getFid() == f)
						{
							System.out.println("What would you like to sell? Bees (b), Honey (h), or Pollen (p)?");
							sellVar = in.next().charAt(0);
							if (sellVar == 'b')
							{
								System.out.println("Hive " + frames[i-1].getHid() + " has " + hives[h-1].getBees() + " bees. How many bees would you like to sell?");
								sell = in.nextInt();
								if (sell <= hives[h-1].getBees())
								{
									System.out.println("At $" + 2.50 * valueUpgrade + " per bee, that comes to $" + 2.50 * valueUpgrade * sell + ". Thank you!");
									money = money + 2.50 * valueUpgrade * sell;
									hives[h-1].addBees(-sell);
								}
								else
								{
									System.out.println("Not enough bees in this hive to sell");
								}
							}	
							if (sellVar == 'h')
							{
								System.out.println("Hive " + frames[i-1].getHid() + ", Frame " + frames[i-1].getFid() + " has " + frames[i-1].getHoney() + " mL of honey. How much honey would you like to sell?");
								sell = in.nextInt();
								if (sell <= frames[i-1].getHoney())
								{
									System.out.println("At $" + 1.50 * valueUpgrade + " per mL of Honey, that comes to $" +1.50 * valueUpgrade *sell + ". Thank you!");
									money = money + 1.50 * valueUpgrade * sell;
									frames[i-1].addHoney(-sell);
									frames[i-1].addEmptyCells(sell);
								}
								else
								{
									System.out.println("Not enough honey in this frame to sell");
								}
							}		
							if (sellVar == 'p')
							{
								System.out.println("Hive " + frames[i-1].getHid() + ", Frame " + frames[i-1].getFid() + " has " + frames[i-1].getPollen() + ". units of pollen. How much pollen would you like to sell?");
								sell = in.nextInt();
								if (sell <= frames[i-1].getPollen())
								{
									System.out.println("At $" + 1.50 * valueUpgrade + " per unit of pollen, that comes to $" + 1.50 * valueUpgrade * sell + ". Thank you!");
									money = money + 1.50 * valueUpgrade * sell;
									frames[i-1].addPollen(-sell);
									frames[i-1].addEmptyCells(sell);
								}
								else
								{
									System.out.println("Not enough pollen in this frame to sell");
								}
							}
							break;
						}
					}
				}

				public static void getUpgrades()
				{
					for (int i = 0; i < hiveCounter; i++)
					{
						System.out.println("Hive " + hives[i].getHid());
					}
					System.out.println("Select a hive");
					h = in.nextInt();
					if (h.equals(null) || h > hiveCounter || h <= 0)
					{
						System.out.println("Invalid selection");
					}
					System.out.println("Would you like to upgrade your bees carrying capacity (c) for $" + cUpCost + ", queens generating capacity (g) for $" + gUpCost + ", commodity value (v) for $ " + vUpCost + ", or buy a Honey Super (h)?");
					upgrade = in.next().charAt(0);
					if (upgrade == 'c')
					{
						if (money < cUpCost)
						{
							System.out.println("Not enough money");
						}	
						else
						{
							money = money - cUpCost;
							cUpCost = 1.5 * cUpCost;
							hives[h-1].addBeeUpgrade();
							System.out.println("Congratulations! Your bees can now carry " + hives[h-1].getBeeUpgrade() + " units of pollen and " + hives[h-1].getBeeUpgrade() + " mLs of honey each");
						}
					}
					if (upgrade == 'g')
					{
						if (money < gUpCost)
						{
							System.out.println("Not enough money");
						}
						else		
						{
							money = money - gUpCost;
							gUpCost = 1.5 * gUpCost;
							hives[h-1].addQueenUpgrade();
							System.out.println("Congratulations! Your queen can now generate " + hives[h-1].getQueenUpgrade() + " bees from 1 unit of pollen and 1 mL of honey");
						}
					}
					if (upgrade == 'v')
					{
						if (money < vUpCost)
						{
							System.out.println("Not enough money");
						}
						else
						{
							money = money - vUpCost;
							vUpCost = 1.5 * vUpCost;
							valueUpgrade = 1.2 * valueUpgrade;
							System.out.println("Congratulations! Bees are now worth $" + 2.50 * valueUpgrade + " and 1 unit of pollen and 1 mL of honey are now worth $" + 1.50 * valueUpgrade);
						}
					}
					if (upgrade =='h')
					{
						System.out.println("Add a honey super to which frame?");
						int hSuper = in.nextInt();
						for (int i = 1; i <= frameCounter; i++)
						{
							if (frames[i-1].getFid() == hSuper)
							{
								if (money < superCost)
								{
									System.out.println("Not enough money");
								}
								else
								{
									money = money - superCost;
									superCost = 1.5 * superCost;
									frames[i-1].setCellMax(frames[i-1].getCellMax() + superIncrease);
									System.out.println("Congratulations! Frame " + hSuper + "now has a maximum capacity of" + frames[i-1].getCellMax() + " cells");
								}
							}
							break;	
						}
					}		
				}

			public static void splitHive()
			{
				System.out.println("This will move frames to a new hive, but only for completely drawn frames. It also costs $5000 for the materials");
				System.out.println("How many frames would you like to move?");
				int numSplit = in.nextInt();
				if (money < numSplit * frameCost)
				{
					System.out.println("Not enough money");
				}
				else
				{
					money = money - numSplit * frameCost;
				}
				System.out.println("Choose a hive");
				int hiSplit = -1;
				for (int i = 1; i <= hiveCounter; i++)
				{
					System.out.println("Hive " + hives[i-1].getHid());
				}
				try
				{
					int moveCounter = 0;
					hiSplit = in.nextInt();
					if (hiSplit > hiveCounter || hiSplit <= 0)
					{
						throw new InputMismatchException();
					}
					for (int j = 1; j <= numSplit; j++)
					{
						System.out.println("Which frame will you move?");
						for (int i = 1; i <= hives[hiSplit-1].getFrames(); i++)
						{
							System.out.println("Frame " + i);
						}
						int found = 0;
						int frSplit = in.nextInt();
						for (int i = 1; i <= frameCounter; i++)
						{
							if (frames[i-1].getHid() == hiSplit && frames[i-1].getFid() == frSplit)
							{
								double tol = 2.0;
								if ((frames[i-1].getCells()*100.0/frames[i-1].getCellMax()) + tol > 100.0)
								{
									if (moveCounter < 1)
									{
										hives[hiveCounter] = new Hive(hiveCounter + 1);
										hiveCounter++;
									}
									hives[hiveCounter-1].addFrames();
									frames[frameCounter] = new Frame(hives[hiveCounter-1].getHid(), hives[hiveCounter-1].getFrames(), Math.abs(6 - hives[hiveCounter - 1].getFrames()));
									Date starttime = new Date();
									long begin = starttime.getTime()/1000L;
									frameCounter++;
									frames[frameCounter-1].setStartTime(begin);
									frames[frameCounter-1].setHoney(frames[i-1].getHoneyCells());
									frames[frameCounter-1].setNectar(frames[i-1].getNectarCells());
									frames[frameCounter-1].setPollen(frames[i-1].getPollenCells());
									frames[frameCounter-1].setLarvae(frames[i-1].getLarvae());
									frames[frameCounter-1].setBrood(frames[i-1].getBroodCells());
									frames[frameCounter-1].setEmptyCells(frames[i-1].getEmptyCells());
									frames[frameCounter-1].setClutter(frames[i-1].getClutter());
									frames[i-1].setHoney(0);
									frames[i-1].setNectar(0);
									frames[i-1].setPollen(0);
									frames[i-1].setLarvae(0);
									frames[i-1].setBrood(0);
									frames[i-1].setEmptyCells(0);
									frames[i-1].setClutter(0);
									Date date = new Date();
									found++;
									moveCounter++;
								}
								else
								{
									System.out.println("Frame " + frSplit + " is not sufficiently drawn to split");
									found++;
								}
							}
						}
						if (found != 1)
						{
							System.out.println("Could not find frame");
						}
					}
					for (int i = 1; i <= (framesPerHive - moveCounter); i++)
					{
						hives[hiveCounter - 1].addFrames();
						Date starttime = new Date();
						frames[frameCounter] = new Frame(hives[hiveCounter - 1].getHid(), hives[hiveCounter - 1].getFrames(), Math.abs(6 - hives[hiveCounter - 1].getFrames()));
						long begin = starttime.getTime()/1000L;
						frames[frameCounter].setStartTime(begin);
						frameCounter++;

						// Cancel the old Timers
						resourceTimer.cancel();
						resourceTimer.purge();
						honeyTimer.cancel();
						honeyTimer.purge();
						beeTimer.cancel();
						beeTimer.purge();
						consumeTimer.cancel();
						consumeTimer.purge();
						clutterTimer.cancel();
						clutterTimer.purge();
						broodTimer.cancel();
						broodTimer.purge();
						larvaeTimer.cancel();
						larvaeTimer.purge();
						waxTimer.cancel();
						waxTimer.purge();

						// Start the new Timers
						resourceTimer = new Timer();
						honeyTimer = new Timer();
						consumeTimer = new Timer();
						broodTimer = new Timer();
						larvaeTimer = new Timer();
						beeTimer = new Timer();
						waxTimer = new Timer();
						clutterTimer = new Timer();

						Date date = new Date();
						frames[frameCounter-1].setStartTime(date.getTime()/1000L);
							
						world.startResourceTimer(hives, frames, resourceTimer, frameCounter, hiveCounter);
						world.startConsumeTimer(hives, frames, consumeTimer, frameCounter, hiveCounter);	
						world.startBroodTimer(hives, frames, broodTimer, frameCounter, hiveCounter);
						world.startLarvaeTimer(hives, frames, larvaeTimer, frameCounter, hiveCounter);
						world.startBeeTimer(hives, frames, beeTimer, frameCounter, hiveCounter);
						world.startWaxTimer(hives, frames, waxTimer, frameCounter, hiveCounter);
						world.startClutterTimer(hives, frames, clutterTimer, frameCounter, hiveCounter);
					}
				}
				catch (InputMismatchException e)
				{
					System.out.println("Invalid entry");
				}
			}
	// Method to make a new frame in the same hive (with or without a queen)
	public static int makeNewFrame(Hive[] hive, Frame[] fr, int hcount, int fcount, boolean queen, int hid)
	{
		if (hid > hcount)
		{
			System.out.println("Invalid Selection");
			return fcount;
		}
		if (hive[hid-1].getFrames() < 10)
		{
			hive[hid-1].addFrames();
			fr[fcount] = new Frame(hid, hive[hid-1].getFrames(), Math.abs(6 - hive[hid - 1].getFrames()));
			Date date = new Date();
			fr[fcount].setStartTime(date.getTime()/1000L);
			fcount++;
		}
		return fcount;
	}

	// Method to save game variables to file for future load


	public static void saveGame()
	//public static void saveGame(Hive[] hv, Frame[] fr, String name, double money, int fcount, int hcount, WorldClock world)
	{
		endflag = 1;
		resourceTimer.cancel();
		resourceTimer.purge();
		honeyTimer.cancel();
		honeyTimer.purge();
		beeTimer.cancel();
		beeTimer.purge();
		consumeTimer.cancel();
		consumeTimer.purge();
		clutterTimer.cancel();
		clutterTimer.purge();
		broodTimer.cancel();
		broodTimer.purge();
		larvaeTimer.cancel();
		larvaeTimer.purge();
		waxTimer.cancel();
		waxTimer.purge();
		File outFile = new File("/home/tyler/Documents/Java Code/Buzz/" + name + ".txt");
		Date currentDate = new Date(); 

		try
		{
			PrintWriter out = new PrintWriter(outFile);
			out.println(name);
			out.println(frameCounter);
			out.println(hiveCounter);
			out.println(money);
			out.println(currentDate.getTime()/1000L);
			out.println(world.getStartTime());
		for (int i = 1; i <= hiveCounter; i++)
		{
			out.println(hives[i-1].getBees());
			out.println(hives[i-1].getBeeUpgrade());
			out.println(hives[i-1].getQueenUpgrade());
		}
			
		for (int i = 1; i <= frameCounter; i++)
		{
			out.println(frames[i-1].getHid());
			out.println(frames[i-1].getHoneyCells());
			out.println(frames[i-1].getNectarCells());
			out.println(frames[i-1].getPollenCells());
			out.println(frames[i-1].getLarvae());
			out.println(frames[i-1].getBroodCells());
			out.println(frames[i-1].getEmptyCells());
			out.println(frames[i-1].getClutter());
			out.println(frames[i-1].getStartTime());
		}
			out.close();
			System.out.println("Game saved");
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Could not save file");
		}
	}

	static ActionListener submitTextListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent theEvent) {
			name = startText.getText();
		}
	};

	public static int getHiveCounter()
	{
		return hiveCounter;
	}

	public static Hive[] getHiveArray()
	{
		return hives;
	}

	public static String getName()
	{
		return name;
	}

	public static int getEndFlag()
	{
		return endflag;
	}
}
