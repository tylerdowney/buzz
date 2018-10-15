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
				frames[0].setBeeUpgrade(1);
				frames[0].setQueenUpgrade(1);
				frames[0].setHoney(sc.nextDouble());
				frames[0].setPollen(sc.nextDouble());
				frames[0].setBees(sc.nextInt());
				money = sc.nextDouble();
				frames[0].setBeeUpgrade(sc.nextInt());
				frames[0].setQueenUpgrade(sc.nextInt());
				oldtime = sc.nextLong();
				newtime = new Date();
				offgametime = (newtime.getTime()/1000 - oldtime);
				int intoffgametime = (int) offgametime;
				frames[0].setHoney(frames[0].getHoney() + (intoffgametime*1000/resourceTime) * frames[0].getBeeUpgrade());
				frames[0].setPollen(frames[0].getPollen() + (intoffgametime*1000/resourceTime) * frames[0].getBeeUpgrade());
				frames[0].setBees(frames[0].getBees() + (intoffgametime *1000/beeTime) * frames[0].getQueenUpgrade());
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
			System.out.println("Save and Quit (q)");

			action = in.next().charAt(0);
			switch (action)
			{

				// Inspect case:  Reveal your current honey, pollen, bee, and money quantities

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
					System.out.println(name + "'s hive has " + frames[f-1].getHoney() + " mL of honey, " + frames[f-1].getPollen() + " units of pollen, and " + frames[f-1].getBees() + " bees. You also have $" + money + " in total");
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
					System.out.println("What would you like to sell? Bees (b), Honey (h), or Pollen (p)?");
				sellVar = in.next().charAt(0);
				if (sellVar == 'b')
				{
					System.out.println("How many bees would you like to sell?");
					sell = in.nextInt();
					if (sell <= frames[0].getBees())
					{
						System.out.println("At $" + 2.50 * valueUpgrade + " per bee, that comes to $" + 2.50 * valueUpgrade * sell + ". Thank you!");
						money = money + 2.50 * valueUpgrade * sell;
						frames[0].addBees(-sell);
					}
					else
					{
						System.out.println("Not enough bees to sell");
					}
				}	
				if (sellVar == 'h')
				{
					System.out.println("How much honey would you like to sell?");
					sell = in.nextInt();
					if (sell <= frames[0].getHoney())
					{
						System.out.println("At $" + 1.50 * valueUpgrade + " per mL of Honey, that comes to $" +1.50 * valueUpgrade *sell + ". Thank you!");
						money = money + 1.50 * valueUpgrade * sell;
						frames[0].addHoney(-sell);
					}
					else
					{
						System.out.println("Not enough honey to sell");
					}
				}		
				if (sellVar == 'p')
				{
					System.out.println("How much pollen would you like to sell?");
					sell = in.nextInt();
					if (sell <= frames[0].getPollen())
					{
						System.out.println("At $" + 1.50 * valueUpgrade + " per unit of pollen, that comes to $" + 1.50 * valueUpgrade * sell + ". Thank you!");
						money = money + 1.50 * valueUpgrade * sell;
						frames[0].addPollen(-sell);
					}
					else
					{
						System.out.println("Not enough pollen to sell");
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
				// Quit case: Saves and Quits game

				case 'q' :
					endflag = 1;
					beeTimer.cancel();
					beeTimer.purge();
					resourceTimer.cancel();
					resourceTimer.purge();
					saveGame(name, frames[0].getHoney(), frames[0].getPollen(), frames[0].getBees(), money, frames[0].getBeeUpgrade(), frames[0].getQueenUpgrade());
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
				for (int i = 0; i <= fcount; i++)
				{
					frame[i].honey = frame[i].honey + frame[i].getBeeUpgrade();
					frame[i].pollen = frame[i].pollen + frame[i].getBeeUpgrade();
				}
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
				for (int i = 0; i <= fcount; i++)
				{ 
					frame[i].bees = frame[i].bees + frame[i].getQueenUpgrade();
					frame[i].honey = frame[i].honey - 1;
					frame[i].pollen = frame[i].pollen - 1;
				}
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

	// Method to save game variables to file for future load

	public static void saveGame(String name, double honey, double pollen, int bees, double money, int bUpgrade, int qUpgrade)
	{
		File outFile = new File("/home/tyler/Documents/Java Code/Buzz/savefile.txt");
		Date currentDate = new Date(); 
		try
		{
			PrintWriter out = new PrintWriter(outFile);
			out.println(name);
			out.println(honey);
			out.println(pollen);
			out.println(bees);
			out.println(money);
			out.println(bUpgrade);
			out.println(qUpgrade);
			out.println(currentDate.getTime()/1000);
			out.close();
			System.out.println("Game saved");
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Could not save file");
		}
	}
}
