// Importing required packages for specialized actions

import java.io.*;
import java.util.*;
import java.lang.*;

public class Buzz
{
	public static void main(String[] args)
	{

		// Initialize flag to end game, action variable, money variable, sales variable, bee variables, upgrade variables, load flag, gametime variables, off game time variable, resource/bee acquisition delays, random variable, terminal scanner object, username variable,Frame object

		int endflag = 0;
		char action;
		double money = 0;
		char sellVar;
		int newBees;
		int deployBees;
		int sell;
		char upgrade;
		double cUpCost = 1000;
		double gUpCost = 1000;
		double vUpCost = 1000;
		double valueUpgrade = 1;
		char load;
		long oldtime;
		Date newtime;
		long offgametime;
		int resourceOffset = 0;
		int beeOffset = 0;
		int hiveCounter = 1;
		int frameCounter = 1;
		int resourceTime = 5000;
		int beeTime = 18000;
		Random rand = new Random();
		int n;
		int framesPerHive = 10;
		Hive[] hives = new Hive[1000];
		hives[0] = new Hive(hiveCounter);
		Frame[] frames = new Frame[framesPerHive * hives.length];
		frames[0] = new Frame(true, hives[0].getHid(), frameCounter);
		frames[0].setFid(frameCounter);
		Scanner in = new Scanner(System.in);
		String name = "";

		// Enter username and explanation. Load game or new game options

		System.out.println("Would you like to load a saved game (y/n)?");
		load = in.next().charAt(0);
		if (load == 'y')
		{
			File inFile = new File("/home/tyler/Documents/Java Code/Buzz/savefile.txt");
			try
			{
				// Load game variables from file; add in resource/bee amounts corresponding to elapsed time since last game

				Scanner sc = new Scanner(inFile);
				name = sc.next();
				frameCounter = sc.nextInt();
				hiveCounter = sc.nextInt();
				money = sc.nextDouble();
				oldtime = sc.nextLong();
				newtime = new Date();
				offgametime = (newtime.getTime()/1000 - oldtime);
				int intoffgametime = (int) offgametime;
				resourceOffset = intoffgametime*1000/resourceTime;
				beeOffset = intoffgametime*1000/beeTime;

				for (int i = 1; i <= frameCounter; i++)
				{
					frames[i-1].setHoney(sc.nextDouble());
					frames[i-1].setPollen(sc.nextDouble());
					frames[i-1].setBees(sc.nextInt());
					frames[i-1].setBeeUpgrade(sc.nextInt());
					frames[i-1].setQueenUpgrade(sc.nextInt());
					frames[i-1].setHoney(frames[i-1].getHoney() + resourceOffset * frames[i-1].getBeeUpgrade());
					frames[i-1].setPollen(frames[i-1].getPollen() + resourceOffset * frames[i-1].getBeeUpgrade());
					frames[i-1].setBees(frames[i-1].getBees() + beeOffset * frames[i-1].getQueenUpgrade());
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
			System.out.println("Welcome " + name + "! Let's get started. You will begin with a queen, 1 bee, and some pollen and honey. Collect pollen and honey to make more bees, and sell the honey for money to buy upgrades.\n");

			//Initialize frame resources (bees, honey, pollen)

			frames[0].addBees(1);
			frames[0].addHoney(5);
			frames[0].addPollen(5);
		}
		else
		{
			System.out.println("Invalid entry");
		}	

		// Create timers for game

		Timer resourceTimer = new Timer();
		Timer beeTimer = new Timer();
		startResourceTimer(frames, resourceTimer, resourceTime, frameCounter);
		startBeeTimer(frames, beeTimer, beeTime, frameCounter);	
		
		// Loop to determine and define actions

		while (endflag != 1)
		{

			System.out.println("What do you want to do?\n");
			System.out.println("Inspect (i)");
			//System.out.println("Deploy Bees (d)");DEPRECATED
			//System.out.println("Make Bees (m)");DEPRECATED
			System.out.println("Sell (s)");
			System.out.println("Upgrade (u)");
			System.out.println("Split (p)");
			System.out.println("Make new frame (f)");
			System.out.println("Save and Quit (q)");

			action = in.next().charAt(0);
			switch (action)
			{

				// Inspect case:  Select a hive and frame, and reveal their current honey, pollen, and bee quantities. Also reveals money.

				case 'i' :
					Integer h;
					Integer f;
					for (int i = 0; i < hiveCounter; i++)
					{
						System.out.println(hives[i].getHid());
					}
					System.out.println("Which hive?");
					h = in.nextInt();
					if (h.equals(null) || h > hiveCounter || h <= 0)
					{
						System.out.println("Invalid selection");
						break;
					}
					for (int i = 0; i < frameCounter; i++)
					{
						if (frames[i].getHid() == h)
						{
							System.out.println(frames[i].getFid());
						}
					}
					System.out.println("Which frame?");
					f = in.nextInt();
					if (f.equals(null) || f > frameCounter || f <= 0)
					{
						System.out.println("Invalid selection");
						break;
					}
					System.out.println("Hive " + h + ", Frame " + f + " has " + frames[f-1].getHoney() + " mL of honey, " + frames[f-1].getPollen() + " units of pollen, and " + frames[f-1].getBees() + " bees. " + name + " also has $" + money + " in total");
					break;

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

				// Sell case: Sell bees, honey, and pollen. Amounts subject to change, but 1 bee = $2.50, while 1 pollen = 1 mL honey = $1.50 

				case 's' :
					for (int i = 0; i < hiveCounter; i++)
					{
						System.out.println(hives[i].getHid());
					}
					System.out.println("Sell from which hive?");
					h = in.nextInt();
					if (h.equals(null) || h > hiveCounter || h <= 0)
					{
						System.out.println("Invalid selection");
						break;
					}
					for (int i = 0; i < frameCounter; i++)
					{
						if (frames[i].getHid() == h)
						{
							System.out.println(frames[i].getFid());
						}
					}
					System.out.println("Sell from which frame?");
					f = in.nextInt();
					if (f.equals(null) || f > frameCounter || f <= 0)
					{
						System.out.println("Invalid selection");
						break;
					}
					System.out.println("What would you like to sell? Bees (b), Honey (h), or Pollen (p)?");
				sellVar = in.next().charAt(0);
				if (sellVar == 'b')
				{
					System.out.println("How many bees would you like to sell?");
					sell = in.nextInt();
					if (sell <= frames[f-1].getBees())
					{
						System.out.println("At $" + 2.50 * valueUpgrade + " per bee, that comes to $" + 2.50 * valueUpgrade * sell + ". Thank you!");
						money = money + 2.50 * valueUpgrade * sell;
						frames[f-1].addBees(-sell);
					}
					else
					{
						System.out.println("Not enough bees in this frame to sell");
					}
				}	
				if (sellVar == 'h')
				{
					System.out.println("How much honey would you like to sell?");
					sell = in.nextInt();
					if (sell <= frames[f-1].getHoney())
					{
						System.out.println("At $" + 1.50 * valueUpgrade + " per mL of Honey, that comes to $" +1.50 * valueUpgrade *sell + ". Thank you!");
						money = money + 1.50 * valueUpgrade * sell;
						frames[f-1].addHoney(-sell);
					}
					else
					{
						System.out.println("Not enough honey in this frame to sell");
					}
				}		
				if (sellVar == 'p')
				{
					System.out.println("How much pollen would you like to sell?");
					sell = in.nextInt();
					if (sell <= frames[f-1].getPollen())
					{
						System.out.println("At $" + 1.50 * valueUpgrade + " per unit of pollen, that comes to $" + 1.50 * valueUpgrade * sell + ". Thank you!");
						money = money + 1.50 * valueUpgrade * sell;
						frames[f-1].addPollen(-sell);
					}
					else
					{
						System.out.println("Not enough pollen in this frame to sell");
					}
				}
				break;	

				// Upgrade case: Can purchase upgrades to bee carrying capacity, queen fertility, and commodity value. +1 to capacity and fertility, * 1.2 to value. Cost rises by factor of 1.5 

				case 'u' :
					System.out.println("Would you like to upgrade your bees carrying capacity (c) for $" + cUpCost + ", queens generating capacity (g) for $" + gUpCost + ", or commodity value (v) for $ " + vUpCost + "?");
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
						frames[0].addBeeUpgrade();
						System.out.println("Congratulations! Your bees can now carry " + frames[0].getBeeUpgrade() + " units of pollen and " + frames[0].getBeeUpgrade() + " mLs of honey each");
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
						frames[0].addQueenUpgrade();
						System.out.println("Congratulations! Your queen can now generate " + frames[0].getQueenUpgrade() + " bees from 1 unit of pollen and 1 mL of honey");
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
					break;
				// Split case: Move resources to new Frame
				case 'p' :
				System.out.println("Which frame will you split?");
				try
				{
					int frSplit = in.nextInt();
					int val = 0;
					splitHive(frames, hives, frSplit, frameCounter, hiveCounter);
					hiveCounter++;
					frames[val].setHid(hiveCounter);
				}
				catch (InputMismatchException e)
				{
					System.out.println("Invalid entry");
				}
				break;
				// Make new frame
				case 'f' :
					frameCounter = makeNewFrame(hives, frames, hiveCounter, frameCounter);
					startResourceTimer(frames, resourceTimer, resourceTime, frameCounter);
					startBeeTimer(frames, beeTimer, beeTime, frameCounter);	
					break;
				// Quit case: Saves and Quits game

				case 'q' :
					endflag = 1;
					beeTimer.cancel();
					beeTimer.purge();
					resourceTimer.cancel();
					resourceTimer.purge();
					saveGame(frames, name, money, frameCounter, hiveCounter);

					break;
				// If you enter a dumb variable, you get a dumb answer

				default: 
					System.out.println("Invalid entry");
					break;
			}

			// Game Over screen. Possibly deprecated?
			/*if (frames[0].getBees() <= 0 )
			{
				// All your bees are dead. Game over

				System.out.println("All your bees have died. Your queen has died of loneliness. Game Over\n");
				System.out.println("You finished with " + frames[0].getHoney() + " ml of honey and " + frames[0].getPollen() + " units of pollen");
				endflag = 1;*/
		}	
	}

	// Method to start timer for gradually increasing resources (every 5 seconds)

	public static void startResourceTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask resourceTask = new TimerTask()
		{
			public void run()
			{
					frame[fcount-1].honey = frame[fcount-1].honey + frame[fcount-1].getBeeUpgrade();
					frame[fcount-1].pollen = frame[fcount-1].pollen + frame[fcount-1].getBeeUpgrade();
			}
		};
		timer.scheduleAtFixedRate(resourceTask, new Date(), time);
	}

	// Method to start timer for gradually increasing bee quantities (every 18 seconds)

	public static void startBeeTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask beeTask = new TimerTask()
		{
			public void run()
			{
					frame[fcount-1].bees = frame[fcount-1].bees + frame[fcount-1].getQueenUpgrade();
					frame[fcount-1].honey = frame[fcount-1].honey - 1;
					frame[fcount-1].pollen = frame[fcount-1].pollen - 1;
			}
		};	
		timer.scheduleAtFixedRate(beeTask, new Date(), time);
	}

	// Method to create a new hive by splitting an old one

	public static int splitHive(Frame[] fr, Hive[] hv, int fsplit, int fCounter, int hCounter)	
	{
		hv[hCounter] = new Hive(hCounter + 1);
		int tempframe;
		int val = 0;
		for (int i = 0; i <= fCounter; i++)
		{
			if (fsplit == fr[i].getFid())
			{
				val = fr[i].getFid();
				break;
			}
		}
		return val;
	}

	public static int makeNewFrame(Hive[] hive, Frame[] fr, int hcount, int fcount)
	{
		Scanner in = new Scanner(System.in);
		System.out.println("New frame in which hive?");
		for (int i = 1; i <= hcount; i++)
		{
			System.out.println(hive[i-1].getHid());
		}
		int hid = in.nextInt();
		if (hid > hcount)
		{
			System.out.println("Invalid selection");
			return fcount;
		}
		if (hive[hid-1].frames >= 10)
		{
			System.out.println("Hive is full");
			return fcount;
		}
		else
		{
			hive[hid-1].frames++;
			fcount++;
			fr[fcount-1] = new Frame(true, hid, fcount);
			fr[fcount-1].setHoney(5);
			fr[fcount-1].setPollen(5);
			fr[fcount-1].setBees(1);
			return fcount;
		}
	}

	// Method to save game variables to file for future load

	public static void saveGame(Frame[] fr, String name, double money, int fcount, int hcount)
	{
		File outFile = new File("/home/tyler/Documents/Java Code/Buzz/savefile.txt");
		Date currentDate = new Date(); 
		try
		{
			PrintWriter out = new PrintWriter(outFile);
			out.println(name);
			out.println(fcount);
			out.println(hcount);
			out.println(money);
			out.println(currentDate.getTime()/1000);
		for (int i = 1; i <= fcount; i++)
			{
				out.println(fr[i-1].honey);
				out.println(fr[i-1].pollen);
				out.println(fr[i-1].bees);
				out.println(fr[i-1].beeUpgrade);
				out.println(fr[i-1].queenUpgrade);
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
