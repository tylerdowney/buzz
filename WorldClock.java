import java.util.*;

public class WorldClock
{
	private long starttime;
	private long worldage;
	private int resourceTime;
	private int consumeTime;
	private int broodTime;
	private int larvaeTime;
	private int beeTime;
	private int clutterTime;
	private int waxTime;
	private int[] Resources = new int[7]; /* Array Layout: 0 Add Brood/Add Larvae, 1 Add Bees/Consume Larvae, 2 Consume Honey/Add Empty Cells, 3 Consume Pollen/Add Empty Cells, 4 Add Honey/Remove Empty Cells 5, Add Pollen/Remove Empty Cells, 6 Add Empty Cells.*/
	private int dist;
		
	public WorldClock()
	{
		resourceTime = 10000; // Adjusts rate of resource accrual in milliseconds
		consumeTime = 40000; // Adjusts rate of resource consumption in milliseconds
		broodTime = 40000; // Adjusts rate of egg laying in milliseconds
		larvaeTime = 355000; // Adjusts rate of eggs hatching into larvae milliseconds
		beeTime = 475000; // Adjusts rate of larvae developing into bees in milliseconds
		clutterTime = 5000; // Adjusts rate of clutter build up in milliseconds
		waxTime = 5000; // Adjusts rate of comb development in milliseconds
		dist = 0; // Distance to centre of Hive variable
	}

	public long getStartTime()
	{
		return starttime;
	}

	public void setStartTime(long s)
	{
		this.starttime = s;
	}

	public static void getAge(long t, String n)
	{
		System.out.println(n + "'s Beeverse is " + t/31536000 + " years, " + (t%31536000)/86400 + " days, " + (t%31536000%86400)/3600 + " hours, " + (t%31536000%86400%3600)/60 + " minutes, and " + t%31536000%86400%3600%60 + " seconds old");
	}

	// Method to start timer for gradually increasing egg quantities as the queen lays, up to a max of 2000

	//public void startWorldClock(Hive[] hive, Frame[] frame, Timer worldTimer, int fcount, int hcount)
	public void startBroodTimer(Hive[] hive, Frame[] frame, Timer broodTimer, int fcount, int hcount)
	{
		TimerTask broodTask = new TimerTask()
		{
			public void run()
			{
				int addBrood = 0;
				int broodMax = 2000;
				int dist = 0;
				for (int i = 1; i <= hcount; i++)
				{
				Resources[0] = getRandomInt(20 * hive[i-1].getQueenUpgrade());
					for (int j = 1; j <= fcount; j++)
					{
						if (frame[j-1].getHid() == i)
						{
							dist = frame[j-1].getDistToCent();
							if (dist == 0)
							{
								addBrood = 3*Resources[0]/16;
							}
							else if (dist == 1 || dist == 2)
							{
								addBrood = 2*Resources[0]/16;
							}
							else if ( dist > 2)
							{
								addBrood = Resources[0]/16;
							}
							if (frame[j-1].getBroodCells() <= broodMax && frame[j-1].getEmptyCells() > addBrood)
							{
								frame[j-1].addBrood(addBrood);
								frame[j-1].addEmptyCells(-addBrood);
							}
						}
					}
				}
			}
		};	
		broodTimer.scheduleAtFixedRate(broodTask, new Date(), broodTime);
	}

	// Method for gradually increasing larvae quantity as eggs hatch

	public void startLarvaeTimer(Hive[] hive, Frame[] frame, Timer larvaeTimer, int fcount, int hcount)
	{
		double clutPerEgg = 0.001;
		TimerTask larvaeTask = new TimerTask()
		{
			int addBrood;
			int dist = 0;
			public void run()
			{
				for (int i = 1; i <= hcount; i++)
				{
					for (int j = 1; j <= fcount; j++)
					{
						if (frame[j-1].getHid() == i)
						{
							dist = frame[j-1].getDistToCent();
							if (dist == 0)
							{
								addBrood = 3*Resources[0]/16;
							}
							else if (dist == 1 || dist == 2)
							{
								addBrood = 2*Resources[0]/16;
							}
							else if ( dist > 2)
							{
								addBrood = Resources[0]/16;
							}
							if (frame[j-1].getBroodCells() > addBrood)
							{
								frame[j-1].addClutter(getRandomDouble(20 * clutPerEgg * hive[frame[j-1].getHid()-1].getQueenUpgrade()));
								frame[j-1].addLarvae(addBrood);
								frame[j-1].addBrood(-addBrood);
							}
						}
					}
				}
			}
		};	
		larvaeTimer.scheduleAtFixedRate(larvaeTask, new Date(), larvaeTime);
	}

	// Method for gradually increasing clutter as time goes on, and as eggs hatch. Bees clean clutter regularly

	public void startClutterTimer(Hive[] hive, Frame[] frame, Timer clutterTimer, int fcount, int hcount)
	{
		double clutPerSec = 0.01;
		double clutPerBee = 0.0001;
		TimerTask clutterTask = new TimerTask()
		{
			public void run()
			{
				for (int i = 1; i <= fcount; i++)
				{
					int hid = frame[i-1].getHid();
					double addClutter = getRandomDouble((clutterTime/1000) * clutPerSec);
					if (frame[i-1].getClutter() < frame[i-1].getClutterMax())
					{
						frame[i-1].addClutter(addClutter);
					}
					else
					{
						// swarm code here
					}
					if (frame[i-1].getClutter() > 0)
					{
						double removeClutter = getRandomDouble((clutterTime/1000) * hive[hid-1].getBees() * clutPerBee);
						frame[i-1].addClutter(-removeClutter);
					}
				}
			}
		};	
		clutterTimer.scheduleAtFixedRate(clutterTask, new Date(), clutterTime);
	}

	// Method for gradually increasing bee quantities as larvae grow into bees, up to a max of 3000

	public void startBeeTimer(Hive[] hive, Frame[] frame, Timer beeTimer, int fcount, int hcount)
	{
		TimerTask beeTask = new TimerTask()
		{
			int dist = 0;
			public void run()
			{
				int flag;
				int addBees = 0;
				for (int i = 1; i <= hcount; i++)
				{
					flag = 0;
					Resources[1] = getRandomInt(20 * hive[i-1].getQueenUpgrade());
					for (int j = 1; j <= fcount; j++)
					{
						if (frame[j-1].getHid() == i)
						{
							dist = frame[j-1].getDistToCent();
							if (dist == 0)
							{
								addBees = 3*Resources[1]/16;
								flag = flag + 3;
							}
							else if (dist == 1 || dist == 2)
							{
								addBees = 2*Resources[1]/16;
								flag = flag + 2;
							}
							else if ( dist > 2)
							{
								addBees = Resources[1]/16;
								flag++;
							}
							if (frame[j-1].getLarvae() >= addBees)
							{
								frame[j-1].addLarvae(-addBees);
								frame[j-1].addEmptyCells(addBees);
							}
						}
					}
					if (hive[i-1].getBees() <= hive[i-1].getBeeMax())
					{
						hive[i-1].addBees(flag*Resources[1]/16);
					}
					else
					{
						hive[i-1].setBees(hive[i-1].getBeeMax());
					}
				}
			}
		};	
		beeTimer.scheduleAtFixedRate(beeTask, new Date(), beeTime);
	}

	// Method to start timer to regularly consume resources.

	public void startConsumeTimer(Hive[] hive, Frame[] frame, Timer consumeTimer, int fcount, int hcount)
	{
		TimerTask consumeTask = new TimerTask()
		{
			int dist = 0;
			public void run()
			{
				int addHoney = 0;
				int addPollen = 0;
				for (int i = 1; i <= hcount; i++)
				{
					Resources[2] = getRandomInt(hive[i-1].getBees()/100);
					Resources[3] = getRandomInt(hive[i-1].getBees()/100);
					for (int j = 1; j <= fcount; j++)
					{
						if (frame[j-1].getHid() == i)
						{
							dist = frame[j-1].getDistToCent();
							if (dist == 0)
							{
								addHoney = Resources[2]/16 - getRandomInt(frame[j-1].getBroodCells()/50);
								addPollen = Resources[3]/16 - getRandomInt(frame[j-1].getBroodCells()/50);
							}
							else if (dist == 1 || dist == 2)
							{
								addHoney = 2*Resources[2]/16 - getRandomInt(frame[j-1].getBroodCells()/50);
								addPollen = 2*Resources[3]/16 - getRandomInt(frame[j-1].getBroodCells()/50);
							}
							else if (dist > 2)
							{
								addHoney = 3*Resources[2]/16 - getRandomInt(frame[j-1].getBroodCells()/50);
								addPollen = 3*Resources[3]/16 - getRandomInt(frame[j-1].getBroodCells()/50);
							}
							if (frame[j-1].getHoney() >= addHoney)
							{
								frame[j-1].addHoney(-addHoney);
								frame[j-1].addEmptyCells(addHoney);
							}
							else
							{
								frame[j-1].addEmptyCells(frame[j-1].getHoneyCells());
								frame[j-1].setHoney(0);
							}
							if (frame[j-1].getPollen() >= addPollen)
							{
								frame[j-1].addPollen(-addPollen);
								frame[j-1].addEmptyCells(addPollen);
							}
							else
							{
								frame[j-1].addEmptyCells(frame[j-1].getPollenCells());
								frame[j-1].setPollen(0);
							}
						}
					}
				}				
			}
		};	
		consumeTimer.scheduleAtFixedRate(consumeTask, new Date(), consumeTime);
	}

	// Method to start timer to gradually increase resources

	public void startResourceTimer(Hive[] hive, Frame[] frame, Timer resourceTimer, int fcount, int hcount)
	{
		TimerTask resourceTask = new TimerTask()
		{
			int dist = 0;
			public void run()
			{
				int addHoney = 0;
				int addPollen = 0;
				for (int i = 1; i <= hcount; i++)
				{
					Resources[4] = getRandomInt(hive[i-1].getBeeUpgrade() * (hive[i-1].getBees()/10 + 1));
					Resources[5] = getRandomInt(hive[i-1].getBeeUpgrade() * (hive[i-1].getBees()/10 + 1));
					for (int j = 1; j <= fcount; j++)
					{
						if (frame[j-1].getHid() == i)
						{
							dist = frame[j-1].getDistToCent();
							if (dist == 0)
							{
								addHoney = Resources[4]/16;
								addPollen = Resources[5]/16;
							}
							else if (dist == 1 || dist == 2)
							{
								addHoney = 2*Resources[4]/16;
								addPollen = 2*Resources[5]/16;
							}
							else if (dist > 2)
							{
								addHoney = 3*Resources[4]/16;
								addPollen = 3*Resources[5]/16;
							}
							if (frame[j-1].getEmptyCells() >= (addHoney + addPollen))
							{
								frame[j-1].addHoney(addHoney);
								frame[j-1].addEmptyCells(-addHoney);
								frame[j-1].addPollen(addPollen);
								frame[j-1].addEmptyCells(-addPollen);
							}
						}
					}
				}
			}
		};
		resourceTimer.scheduleAtFixedRate(resourceTask, new Date(), resourceTime);
	}

	// A Method to increase the number of empty cells

	public void startWaxTimer(Hive[] hive, Frame[] frame, Timer waxTimer, int fcount, int hcount)
	{
		TimerTask waxTask = new TimerTask()
		{
			int dist = 0;
			int addWax;
			public void run()
			{
				for (int i = 1; i <= hcount; i++)
				{
					Resources[6] = getRandomInt(hive[i-1].getBees() * hive[i-1].getBeeUpgrade());
					for (int j = 1; j <= fcount; j++)
					{
						dist = frame[j-1].getDistToCent();
						if (dist == 0)
						{
							addWax = 3*Resources[6]/16;
						}
						else if (dist == 1 || dist == 2)
						{
							addWax = 2*Resources[6]/16;
						}
						else if (dist > 2)
						{
							addWax = Resources[6]/16;
						}
						if (frame[j-1].getCellMax() - frame[j-1].getCells() >= addWax)
						{
							frame[j-1].addEmptyCells(addWax);
						}
						else if (frame[j-1].getCellMax() - frame[j-1].getCells() > 0)
						{
							frame[j-1].addEmptyCells(frame[j-1].getCellMax() - frame[j-1].getCells());
						}
					}
				}
			}
		};
		waxTimer.scheduleAtFixedRate(waxTask, new Date(), waxTime);
	}

	// Method to generate a random double within 20% of the given value

	public static double getRandomDouble(double value)
	{
		double randomFactor = 0.2;
		double lower = value - value*randomFactor;
		double upper = value + value*randomFactor;
		return lower + (upper - lower)*Math.random();
	}

	// Method to generate a random int within 20% of the given value

	public static int getRandomInt(int value)
	{
		double randomFactor = 0.2;
		double lower = value - value*randomFactor;
		double upper = value + value*randomFactor;
		Long random = Math.round(lower + (upper - lower)*Math.random());
		return random.intValue();
	}

	// Method to generate a random int from 1 to 20 

	public static int getBoundedRandomInt()
	{
		Long random = Math.round(Math.ceil(20*Math.random()));
		return random.intValue();
	}
}
