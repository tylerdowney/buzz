// Importing required packages for specialized actions

import java.io.*;
import java.util.*;
import java.lang.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

public class Buzz
{
	public static void main(String[] args)
	{

		// Initialize flag to end game, action variable, money variable, sales variables, upgrade variables, load flag, gametime variables, off game time variable, resource/bee acquisition delays, hive and frame counters, resource/bee generation times, random variable, frames per hive, Hive and Frame object declarations, terminal scanner object, timer declarations, and username variable

		int endflag = 0;
		char action;
		double money = 0;
		char sellVar;
		int sell;
		char upgrade;
		double cUpCost = 1000; // Cost of carrying capacity upgrade
		double gUpCost = 1000; // Cost of queen laying upgrade
		double vUpCost = 1000; // Cost of bee/honey/pollen values upgrade
		double superCost = 0; // Cost of Honey Super upgrade
		int superIncrease = 3500;
		double frameCost = 0;
		double valueUpgrade = 1;
		char load;
		long oldtime;
		Date newtime;
		long worldAge = 0;
		long offgametime;
		int resourceOffset = 0;
		int beeOffset = 0;
		int larvaeOffset = 0;
		int broodOffset = 0;
		int waxOffset = 0;
		int hiveCounter = 0;
		int frameCounter = 0;
		Timer resourceTimer = new Timer();
		Timer consumeTimer = new Timer();
		Timer broodTimer = new Timer();
		Timer larvaeTimer = new Timer();
		Timer beeTimer = new Timer();
		Timer waxTimer = new Timer();
		Timer clutterTimer = new Timer();
		int resourceTime = 10000; // Adjusts rate of resource accrual in milliseconds
		int consumeTime = 40000; // Adjusts rate of resource consumption in milliseconds
		int broodTime = 40000; // Adjusts rate of egg laying in milliseconds
		int larvaeTime = 355000; // Adjusts rate of eggs hatching into larvae milliseconds
		int beeTime = 475000; // Adjusts rate of larvae developing into bees in milliseconds
		int clutterTime = 5000; // Adjusts rate of clutter build up in milliseconds
		int waxTime = 5000; // Adjusts rate of comb development in milliseconds
		double clutPerSec = 0.01;
		double clutPerBee = 0.0001;
		double clutPerEgg = 0.001;
		Random rand = new Random();
		int n;
		int framesPerHive = 10;
		int broodMax = 2000;
		Hive[] hives = new Hive[1000];
		Frame[] frames = new Frame[framesPerHive * hives.length];
		double[] clutterOffset = new double[1000];
		Scanner in = new Scanner(System.in);
		WorldClock world = new WorldClock();
		String name = "";

		//Load game or new game options

		System.out.println("Would you like to load a saved game (y/n)?");
		load = in.next().charAt(0);
		if (load == 'y')
		{
			// Create/run file chooser
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			JFrame frame = new JFrame("FileSelect");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
			int result = fileChooser.showOpenDialog(frame);
			File inFile = new File("");
			if (result == JFileChooser.APPROVE_OPTION)
			{
    				inFile = fileChooser.getSelectedFile();
    				System.out.println("Selected file: " + inFile.getAbsolutePath());
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
					frames[j-1].setPollen(sc.nextInt());
					frames[j-1].setLarvae(sc.nextInt());
					frames[j-1].setBrood(sc.nextInt());
					frames[j-1].setEmptyCells(sc.nextInt());
					frames[j-1].setClutter(sc.nextDouble());
					frames[j-1].setStartTime(sc.nextLong());

					int addedHoney = world.getRandomInt(resourceOffset * hives[frames[j-1].getHid()-1].getBeeUpgrade());
					int addedPollen = world.getRandomInt(resourceOffset * hives[frames[j-1].getHid()-1].getBeeUpgrade());
					int addedLarvae = world.getRandomInt(larvaeOffset * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade());
					int addedBrood = world.getRandomInt(broodOffset * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade());
					int addedWax = world.getRandomInt(waxOffset * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade());

					if (frames[j-1].getDistToCent() == 0 )
					{
						frames[j-1].addHoney(addedHoney/16);
						frames[j-1].addPollen(addedPollen/16);
						frames[j-1].addLarvae(3*addedLarvae/16);	
						frames[j-1].addBrood(3*addedBrood/16);	
						frames[j-1].addEmptyCells(3*addedWax/16);
						frames[j-1].addEmptyCells(-addedHoney/16 - addedPollen/16 - 3*addedLarvae/16 - 3*addedBrood/16); 
						frames[j-1].addClutter(world.getRandomDouble(clutterOffset[tempHid-1] + (clutPerEgg*1000.0*intoffgametime*3*addedBrood/(16*broodTime) * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade())));	
					}
					else if (frames[j-1].getDistToCent() == 1 || frames[j-1].getDistToCent() == 2)
  					{
						frames[j-1].addHoney(2*addedHoney/16);
						frames[j-1].addPollen(2*addedPollen/16);
						frames[j-1].addLarvae(2*addedLarvae/16);	
						frames[j-1].addBrood(2*addedBrood/16);	
						frames[j-1].addEmptyCells(2*addedWax/16);
						frames[j-1].addEmptyCells(-2*addedHoney/16 - 2*addedPollen/16 - 2*addedLarvae/16 - 2*addedBrood/16); 
						frames[j-1].addClutter(world.getRandomDouble(clutterOffset[tempHid-1] + (clutPerEgg*1000.0*intoffgametime*2*addedBrood/(16*broodTime) * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade())));	
					}
					else if (frames[j-1].getDistToCent() > 2)
  					{
						frames[j-1].addHoney(3*addedHoney/16);
						frames[j-1].addPollen(3*addedPollen/16);
						frames[j-1].addLarvae(addedLarvae/16);	
						frames[j-1].addBrood(addedBrood/16);	
						frames[j-1].addEmptyCells(addedWax/16);
						frames[j-1].addEmptyCells(-3*addedHoney/16 - 3*addedPollen/16 - addedLarvae/16 - addedBrood/16); 
						frames[j-1].addClutter(world.getRandomDouble(clutterOffset[tempHid-1] + (clutPerEgg*1000.0*intoffgametime*addedBrood/(16*broodTime) * 20 * hives[frames[j-1].getHid()-1].getQueenUpgrade())));	
					}
						
					if (frames[j-1].getClutter() > 5000)
					{
							frames[j-1].setClutter(5000);
					}
					// Proportionally set new values if they exceed max
					if (frames[j-1].getCells() >= frames[j-1].getCellMax())
					{

						Long honeyFactor = Math.round((frames[j-1].getHoneyCells() * 1.0/frames[j-1].getCells()) * frames[j-1].getCellMax());
						Long pollenFactor = Math.round((frames[j-1].getPollenCells() * 1.0/frames[j-1].getCells()) * frames[j-1].getCellMax());
						Long larvaeFactor = Math.round((frames[j-1].getLarvae() * 1.0/frames[j-1].getCells()) * frames[j-1].getCellMax());
						Long broodFactor = Math.round((frames[j-1].getBroodCells() * 1.0/frames[j-1].getCells()) * frames[j-1].getCellMax());
						Long emptyFactor = Math.round((frames[j-1].getEmptyCells() * 1.0/frames[j-1].getCells()) * frames[j-1].getCellMax());

						frames[j-1].setHoney(honeyFactor.intValue());
						frames[j-1].setPollen(pollenFactor.intValue());
						frames[j-1].setLarvae(larvaeFactor.intValue());
						frames[j-1].setBrood(broodFactor.intValue());
						frames[j-1].setEmptyCells(emptyFactor.intValue());
					}
				}
				sc.close();
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Could not load file");
			}
		}
		else if (load == 'n')
		{

			// New game

			System.out.println("Welcome! Please enter your name");
			name = in.nextLine(); // consume white space
			name = in.nextLine();
			System.out.println("Welcome " + name + "!");

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
		}
		else
		{
			System.out.println("Invalid entry");
		}	

		//Start game timers

		world.startResourceTimer(hives, frames, resourceTimer, frameCounter, hiveCounter);
		world.startConsumeTimer(hives, frames, consumeTimer, frameCounter, hiveCounter);	
		world.startBroodTimer(hives, frames, broodTimer, frameCounter, hiveCounter);
		world.startLarvaeTimer(hives, frames, larvaeTimer, frameCounter, hiveCounter);
		world.startBeeTimer(hives, frames, beeTimer, hiveCounter, hiveCounter);
		world.startWaxTimer(hives, frames, waxTimer, frameCounter, hiveCounter);
		world.startClutterTimer(hives, frames, clutterTimer, frameCounter, hiveCounter);
		
		// Loop to determine and define actions

		while (endflag != 1)
		{

			Date now = new Date();
			worldAge = now.getTime()/1000L - world.getStartTime();
			world.getAge(worldAge, name);

			System.out.println("What do you want to do?\n");
			System.out.println("Inspect (i)");
			//System.out.println("Deploy Bees (d)"); DEPRECATED
			//System.out.println("Make Bees (m)"); DEPRECATED
			System.out.println("Sell (s)");
			System.out.println("Buy upgrades (b)");
			System.out.println("Split (p)");
			//System.out.println("Add Queen to Frame (f)"); DEPRECATED
			System.out.println("Save and Quit (q)");

			action = in.next().charAt(0);
			switch (action)
			{


				// Inspect case:  Select a hive, and reveal the current honey, pollen, bee, larvae, and egg quantities of the frames. Also reveals money.

				case 'i' :
					Integer h;
					Integer f;
					DecimalFormat dec = new DecimalFormat("#.##");
					for (int i = 1; i <= hiveCounter; i++)
					{
						System.out.println("Hive " + hives[i-1].getHid());
					}
					System.out.println("Which hive?");
					h = in.nextInt();
					if (h.equals(null) || h > hiveCounter || h <= 0)
					{
						System.out.println("Invalid selection");
						break;
					}
					System.out.println("Hive " + h + " has " + hives[h-1].getBees() + " bees\n");
					for (int i = 1; i <= frameCounter; i++)
					{
						if (frames[i-1].getHid() == h)
						{			
							System.out.println("Frame " + frames[i-1].getFid() + ": filled cells: " + dec.format((frames[i-1].getCells() - frames[i-1].getEmptyCells())*100.0/frames[i-1].getCellMax()) + "%, drawn cells: " + dec.format(frames[i-1].getCells()*100.0/frames[i-1].getCellMax()) + "%, honey: " + frames[i-1].getHoney() + ", pollen: " + frames[i-1].getPollen() + ", larvae: " + frames[i-1].getLarvae() + ", eggs: " + frames[i-1].getBroodCells() + ", clutter: " + dec.format(frames[i-1].getClutter()*100.0/frames[i-1].getClutterMax()) + "%");
							long frameAge = now.getTime()/1000L - frames[i-1].getStartTime();
							frames[i-1].getAge(hives, frameAge, name, h,i);
						}
					}
					System.out.println(name + " also has $" + money + " in total.\n");
					break;
					/*System.out.println("Which frame?"); DEPRECATED
					f = in.nextInt();
					if (f.equals(null) || f > hives[h-1].getFrames() || f <= 0)
					{
						System.out.println("Invalid selection");
					}

					System.out.println("Frame " + f + " has " + frames[f-1].getHoney() + " mL of honey, " + frames[f-1].getPollen() + " units of pollen, " + frames[f-1].getBees() + " bees, " + frames[f-1].getLarvae() + " larvae, and " + frames[f-1].getBroodCells() + " eggs. " + name + " also has $" + money + " in total.");
					break;*/

				// Deploy case: Send bees out to collect honey and pollen. 1 bee = 1 mL honey and 1 unit pollen. Bees can be killed by predators (need to work on probabilistic model). DEPRECATED
				/*case 'd' :
					System.out.println("How many bees do you want to deploy?");
					deployBees = in.nextInt();
					if (deployBees > ronHive.getBees())
					{
						System.out.println("Not enough bees");
						break;
					}
					System.out.println("You sent " + deployBees + " bees out into the world");
					n = deployBees*rand.nextInt(100) + 1;
					if (n >= 100)
					{ 
						deployBees = deployBees - 1; //(n-100)/100;
						System.out.println("Only " + deployBees + " bees returned");
						ronHive.addBees(-1);
					}
					System.out.println("They brought back " + deployBees * beeUpgrade + " ml of honey and " + deployBees * beeUpgrade + " units of pollen");
					ronHive.addHoney(deployBees * beeUpgrade);
					ronHive.addPollen(deployBees * beeUpgrade);
					break; */
				// Make case: Make bees from honey + pollen. 1 bee = 1mL honey + 1 unit pollen. DEPRECATED
				/*case 'm' :
					System.out.println("How many units of pollen and mL of honey would you like to feed your queen?");
					newBees = in.nextInt();
					if (newBees > Math.max(ronHive.getHoney(), ronHive.getPollen()))
					{
						System.out.println("Not enough resources\n");
					}
					else
					{
						ronHive.addBees(newBees * beeUpgrade);
						ronHive.addHoney(-newBees);
						ronHive.addPollen(-newBees);
						System.out.println("Made " + newBees * beeUpgrade + " new bees");
					}
					break;*/

				// Sell case: Sell bees, honey, and pollen.

				case 's' :
					for (int i = 1; i <= hiveCounter; i++)
					{
						System.out.println("Hive " + hives[i-1].getHid());
					}
					System.out.println("Sell from which hive?");
					h = in.nextInt();
					if (h.equals(null) || h > hiveCounter || h <= 0)
					{
						System.out.println("Invalid selection");
						break;
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
					break;

				// Upgrade case: Can purchase upgrades to bee carrying capacity, queen fertility, and commodity value. +1 to capacity and fertility, * 1.2 to value. Cost rises by factor of 1.5 

				case 'b' :

					for (int i = 0; i < hiveCounter; i++)
					{
						System.out.println("Hive " + hives[i].getHid());
					}
					System.out.println("Select a hive");
					h = in.nextInt();
					if (h.equals(null) || h > hiveCounter || h <= 0)
					{
						System.out.println("Invalid selection");
						break;
					}
					/*for (int i = 0; i < frameCounter; i++)
					{
						if (frames[i].getHid() == h)
						{
							System.out.println("Frame " + frames[i].getFid());
						}
					}
					System.out.println("Upgrade which frame?");
					f = in.nextInt();
					if (f.equals(null) || f > hives[h-1].getFrames() || f <= 0)
					{
						System.out.println("Invalid selection");
						break;
					} DEPRECATED*/
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
					break;

				// Split case: Make a new hive by moving a full frame to a new hive

				case 'p' :
				System.out.println("This will move frames to a new hive, but only for completely drawn frames. It also costs $5000 for the materials");
				if (money < frameCost)
				{
					System.out.println("Not enough money");
					break;
				}
				else
				{
					money = money - frameCost;
				}
				System.out.println("Choose a hive");
				int hiSplit = -1;
				for (int i = 1; i <= hiveCounter; i++)
				{
					System.out.println("Hive " + hives[i-1].getHid());
				}
				try
				{
					int numSplit;
					int moveCounter = 0;
					hiSplit = in.nextInt();
					if (hiSplit > hiveCounter || hiSplit <= 0)
					{
						throw new InputMismatchException();
					}
					System.out.println("How many frames would you like to move?");
					numSplit = in.nextInt();
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
										hives[i-1].setBees(5);
									}
									hives[hiveCounter-1].addFrames();
									frames[frameCounter] = new Frame(hives[hiveCounter-1].getHid(), hives[hiveCounter-1].getFrames(), Math.abs(6 - hives[hiveCounter - 1].getFrames()));
									Date starttime = new Date();
									long begin = starttime.getTime()/1000L;
									frames[frameCounter-1].setStartTime(begin);
									frameCounter++;
									frames[frameCounter-1].setHoney(frames[i-1].getHoneyCells());
									frames[frameCounter-1].setPollen(frames[i-1].getPollenCells());
									frames[frameCounter-1].setLarvae(frames[i-1].getLarvae());
									frames[frameCounter-1].setBrood(frames[i-1].getBroodCells());
									frames[frameCounter-1].setEmptyCells(frames[i-1].getEmptyCells());
									frames[frameCounter-1].setClutter(frames[i-1].getClutter());
									frames[i-1].setHoney(0);
									frames[i-1].setPollen(0);
									frames[i-1].setLarvae(0);
									frames[i-1].setBrood(0);
									frames[i-1].setEmptyCells(0);
									frames[i-1].setClutter(0);
									Date date = new Date();
									frames[frameCounter-1].setStartTime(date.getTime()/1000L);
									
									world.startResourceTimer(hives, frames, resourceTimer, frameCounter, hiveCounter);
									world.startConsumeTimer(hives, frames, consumeTimer, frameCounter, hiveCounter);	
									world.startBroodTimer(hives, frames, broodTimer, frameCounter, hiveCounter);
									world.startLarvaeTimer(hives, frames, larvaeTimer, frameCounter, hiveCounter);
									world.startBeeTimer(hives, frames, beeTimer, frameCounter, hiveCounter);
									world.startWaxTimer(hives, frames, waxTimer, frameCounter, hiveCounter);
									world.startClutterTimer(hives, frames, clutterTimer, frameCounter, hiveCounter);
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
					if (moveCounter > 0)
					{
						for (int i = 1; i < (framesPerHive - moveCounter); i++)
						{
							hives[hiveCounter - 1].addFrames();
							Date starttime = new Date();
							frames[frameCounter] = new Frame(hives[hiveCounter - 1].getHid(), hives[hiveCounter - 1].getFrames(), Math.abs(6 - hives[hiveCounter - 1].getFrames()));
							long begin = starttime.getTime()/1000L;
							frames[frameCounter-1].setStartTime(begin);
							frameCounter++;
						}
					}
				}
				catch (InputMismatchException e)
				{
					System.out.println("Invalid entry");
				}
				break;

				/* Place a queen in a frame in an established hive DEPRECATED

				case 'f' :
				System.out.println("This will place a queen in a new frame in an existing hive. It costs $1000 for the queen");
				if (money < frameCost)
				{
					System.out.println("Not enough money");
					break;
				}
				else
				{
					money = money - frameCost;
				}
				Scanner scan = new Scanner(System.in);
				System.out.println("Add queen to which hive?");
				for (int i = 1; i <= hiveCounter; i++)
				{
					System.out.println("Hive " + hives[i-1].getHid());
				}
				int hid = scan.nextInt();
				frameCounter = makeNewFrame(hives, frames, hiveCounter, frameCounter, true, hid);
				break;*/

				// Quit case: Saves and Quits game

				case 'q' :
					endflag = 1;
					resourceTimer.cancel();
					resourceTimer.purge();
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
					saveGame(hives, frames, name, money, frameCounter, hiveCounter, world);
					break;

				// Bad variable handler

				default: 
					System.out.println("Invalid entry");
					break;
			}

			// Game Over screen. DEPRECATED
			/*if (frames[0].getBees() <= 0 )
			{
				// All your bees are dead. Game over

				System.out.println("All your bees have died. Your queen has died of loneliness. Game Over\n");
				System.out.println("You finished with " + frames[0].getHoney() + " ml of honey and " + frames[0].getPollen() + " units of pollen");
				endflag = 1;*/
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

	public static void saveGame(Hive[] hv, Frame[] fr, String name, double money, int fcount, int hcount, WorldClock world)
	{
		File outFile = new File("/home/tyler/Documents/Java Code/Buzz/" + name + ".txt");
		Date currentDate = new Date(); 

		try
		{
			PrintWriter out = new PrintWriter(outFile);
			out.println(name);
			out.println(fcount);
			out.println(hcount);
			out.println(money);
			out.println(currentDate.getTime()/1000L);
			out.println(world.getStartTime());
		for (int i = 1; i <= hcount; i++)
		{
			out.println(hv[i-1].getBees());
			out.println(hv[i-1].getBeeUpgrade());
			out.println(hv[i-1].getQueenUpgrade());
		}
			
		for (int i = 1; i <= fcount; i++)
		{
			out.println(fr[i-1].getHid());
			out.println(fr[i-1].getHoneyCells());
			out.println(fr[i-1].getPollenCells());
			out.println(fr[i-1].getLarvae());
			out.println(fr[i-1].getBroodCells());
			out.println(fr[i-1].getEmptyCells());
			out.println(fr[i-1].getClutter());
			out.println(fr[i-1].getStartTime());
		}
			out.close();
			System.out.println("Game saved");
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Could not save file");
		}
	}
}
