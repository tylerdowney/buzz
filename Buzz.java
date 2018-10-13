// Importing required packages for specialized actions

import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Buzz
{
	public static void main(String[] args)
	{

		// Initialize flag to end game, action variable, money variable, sales variable, bee variables, upgrade variables, load flag, gametime variables, off game time variable, resource/bee acquisition delays, random variable, terminal scanner object, username variable, and the all-important Hive object

		int endflag = 0;
		char action;
		double money = 0;
		char sellVar;
		int newBees;
		int deployBees;
		int sell;
		char upgrade;
		double valueUpgrade = 1;
		char load;
		long oldtime;
		Date newtime;
		long offgametime;
		int resourceTime = 5000;
		int beeTime = 18000;
		Random rand = new Random();
		int n;
		Scanner in = new Scanner(System.in);
		String name = "";
		Hive ronHive = new Hive(true);
		ronHive.setBeeUpgrade(1);
		ronHive.setQueenUpgrade(1);

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
				ronHive.setHoney(sc.nextDouble());
				ronHive.setPollen(sc.nextDouble());
				ronHive.setBees(sc.nextInt());
				money = sc.nextDouble();
				ronHive.setBeeUpgrade(sc.nextInt());
				ronHive.setQueenUpgrade(sc.nextInt());
				oldtime = sc.nextLong();
				newtime = new Date();
				offgametime = (newtime.getTime()/1000 - oldtime);
				int intoffgametime = (int) offgametime;
				ronHive.setHoney(ronHive.getHoney() + (intoffgametime*1000/resourceTime) * ronHive.getBeeUpgrade());
				ronHive.setPollen(ronHive.getPollen() + (intoffgametime*1000/resourceTime) * ronHive.getBeeUpgrade());
				ronHive.setBees(ronHive.getBees() + (intoffgametime*1000/beeTime) * ronHive.getQueenUpgrade());
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

			// Initialize hive resources (bees, honey, pollen)

			ronHive.addBees(1);
			ronHive.addHoney(5);
			ronHive.addPollen(5);
		}
		else
		{
			System.out.println("Invalid entry");
		}	

		// Create timers for game

		Timer resourceTimer = new Timer();
		Timer beeTimer = new Timer();
		startResourceTimer(ronHive, resourceTimer, resourceTime);
		startBeeTimer(ronHive, beeTimer, beeTime);	
		
		// Loop to determine and define actions

		while (endflag != 1)
		{

			System.out.println("What do you want to do?\n");
			System.out.println("Inspect (i)");
			//System.out.println("Deploy Bees (d)");DEPRECATED
			//System.out.println("Make Bees (m)");DEPRECATED
			System.out.println("Sell (s)");
			System.out.println("Upgrade (u)");
			System.out.println("Save and Quit (q)");

			action = in.next().charAt(0);
			switch (action)
			{

				// Inspect case:  Reveal your current honey, pollen, bee, and money quantities

				case 'i' :
					System.out.println(name + "'s hive has " + ronHive.getHoney() + " mL of honey, " + ronHive.getPollen() + " units of pollen, " + ronHive.getBees() + " bees, and $" + money);
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
					if (sell <= ronHive.getBees())
					{
						System.out.println("At $" + 2.50 * valueUpgrade + " per bee, that comes to $" + 2.50 * valueUpgrade * sell + ". Thank you!");
						money = money + 2.50 * valueUpgrade * sell;
						ronHive.addBees(-sell);
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
					if (sell <= ronHive.getHoney())
					{
						System.out.println("At $" + 1.50 * valueUpgrade + " per mL of Honey, that comes to $" +1.50 * valueUpgrade *sell + ". Thank you!");
						money = money + 1.50 * valueUpgrade * sell;
						ronHive.addHoney(-sell);
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
					if (sell <= ronHive.getPollen())
					{
						System.out.println("At $" + 1.50 * valueUpgrade + " per unit of pollen, that comes to $" + 1.50 * valueUpgrade * sell + ". Thank you!");
						money = money + 1.50 * valueUpgrade * sell;
						ronHive.addPollen(-sell);
					}
					else
					{
						System.out.println("Not enough pollen to sell");
					}
				}
				break;	

				// Upgrade case: Can purchase upgrades to bee carrying capacity, queen fertility, and commodity value. +1 to capacity and fertility, *1.2 to value

				case 'u' :
					System.out.println("Upgrades cost $100.00. Would you like to upgrade your bees carrying capacity (c), queens generating capacity (g), or commodity value (v)?");
					upgrade = in.next().charAt(0);
				if (upgrade == 'c')
				{
					if (money < 100)
					{
						System.out.println("Not enough money");
					}
					else
					{
						money = money - 100;
						ronHive.addBeeUpgrade();
						System.out.println("Congratulations! Your bees can now carry " + ronHive.getBeeUpgrade() + " units of pollen and " + ronHive.getBeeUpgrade() + " mLs of honey each");
					}
				}
				if (upgrade == 'g')
				{
					if (money < 100)
					{
						System.out.println("Not enough money");
					}
					else
					{
						money = money - 100;
						ronHive.addQueenUpgrade();
						System.out.println("Congratulations! Your queen can now generate " + ronHive.getQueenUpgrade() + " bees from 1 unit of pollen and 1 mL of honey");
					}
				}
				if (upgrade == 'v')
				{
					if (money < 100)
					{
						System.out.println("Not enough money");
					}
					else
					{
						money = money - 100;
						valueUpgrade = 1.2 * valueUpgrade;
						System.out.println("Congratulations! Bees are now worth $" + 2.50 * valueUpgrade + " and 1 unit of pollen and 1 mL of honey are now worth $" + 1.50 * valueUpgrade);
					}
				}					
					break;

				// Quit case: Saves and Quits game

				case 'q' :
					endflag = 1;
					beeTimer.cancel();
					beeTimer.purge();
					resourceTimer.cancel();
					resourceTimer.purge();
					saveGame(name, ronHive.getHoney(), ronHive.getPollen(), ronHive.getBees(), money, ronHive.getBeeUpgrade(), ronHive.getQueenUpgrade());
					break;

				// If you enter a dumb variable, you get a dumb answer

				default: 
					System.out.println("Invalid entry");
					break;
			}
			if (ronHive.getBees() <= 0 )
			{
				// All your bees are dead. Game over

				System.out.println("All your bees have died. Your queen has died of loneliness. Game Over\n");
				System.out.println("You finished with " + ronHive.getHoney() + " ml of honey and " + ronHive.getPollen() + " units of pollen");
				endflag = 1;
			}
		}	
	}

	// Method to start timer for gradually increasing resources (every 5 seconds)

	public static void startResourceTimer(Hive hive, Timer timer, int time)
	{
		TimerTask resourceTask = new TimerTask()
		{
			public void run()
			{
					hive.honey = hive.honey + hive.getBeeUpgrade();
					hive.pollen = hive.pollen + hive.getBeeUpgrade();
			}
		};
		timer.scheduleAtFixedRate(resourceTask, new Date(), time);
	}

	// Method to start timer for gradually increasing bee quantities (every 18 seconds)

	public static void startBeeTimer(Hive hive, Timer timer, int time)
	{
		TimerTask beeTask = new TimerTask()
		{
			public void run()
			{
				
				hive.bees = hive.bees + hive.getQueenUpgrade();
				hive.honey = hive.honey - 1;
				hive.pollen = hive.pollen - 1;
			}
		};	
		timer.scheduleAtFixedRate(beeTask, new Date(), time);
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
