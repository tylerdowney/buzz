import java.util.*;

public class WorldClock
{

	private long starttime;
	private long worldage;
		
	public WorldClock()
	{
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

	public static void startBroodTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask broodTask = new TimerTask()
		{
			public void run()
			{
				int broodMax = 2000;
				for (int i = 1; i <= fcount; i++)
				{
					if (frame[i-1].hasQueen() && frame[i-1].getBroodCells() <= broodMax)
					{
						int addBrood = getRandomInt(20 * frame[i-1].getQueenUpgrade());
						frame[i-1].addBrood(addBrood);
						frame[i-1].addEmptyCells(-addBrood);
					}
				}
			}
		};	
		timer.scheduleAtFixedRate(broodTask, new Date(), time);
	}

	// Method for gradually increasing larvae quantity as eggs hatch

	public static void startLarvaeTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		double clutPerEgg = 0.01;
		TimerTask larvaeTask = new TimerTask()
		{
			public void run()
			{
				for (int i = 1; i <= fcount; i++)
				{
					if (frame[i-1].hasQueen() && frame[i-1].getBroodCells() > 0)
					{
						int addLarvae = getRandomInt(20 * frame[i-1].getQueenUpgrade());
						frame[i-1].addClutter(getRandomDouble(20 * clutPerEgg * frame[i-1].getQueenUpgrade()));
						frame[i-1].addLarvae(addLarvae);
						frame[i-1].addBrood(-addLarvae);
					}
				}
			}
		};	
		timer.scheduleAtFixedRate(larvaeTask, new Date(), time);
	}

	// Method for gradually increasing clutter as time goes on, and as eggs hatch. Bees clean clutter regularly

	public static void startClutterTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		double clutPerSec = 1.0;
		double clutPerBee = 0.00001;
		TimerTask clutterTask = new TimerTask()
		{
			public void run()
			{
				for (int i = 1; i <= fcount; i++)
				{
					if (frame[i-1].hasQueen())
					{
						double addClutter = getRandomDouble((time/1000) * clutPerSec);
						frame[i-1].addClutter(addClutter);
						if (frame[i-1].getClutter() > 0)
						{
							double removeClutter = getRandomDouble((time/1000) * frame[i-1].getBees() * clutPerBee);
							frame[i-1].addClutter(-removeClutter);
						}
						if (frame[i-1].getClutter() * 100.0/frame[i-1].getClutterMax() >= 100.0)
						{
							System.out.println("Your bees can't clean the hive fast enough and " + frame[i-1].getBees()/200 + " bees have died. Add more bees to clean faster, or make a new frame to replace this old one");
							frame[i-1].addBees(getRandomInt(-frame[i-1].getBees()/200));
							frame[i-1].addClutter(-frame[i-1].getClutter()/20);
						}
					}
				}
			}
		};	
		timer.scheduleAtFixedRate(clutterTask, new Date(), time);
	}


	// Method for gradually increasing bee quantities as larvae grow into bees, up to a max of 3000

	public static void startBeeTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask beeTask = new TimerTask()
		{
			public void run()
			{
				int beeMax = 3000;
				for (int i = 1; i <= fcount; i++)
				{
					if (frame[i-1].hasQueen() && frame[i-1].getBees() <= beeMax && frame[i-1].getLarvae() > 0)
					{
						int addBees = getRandomInt(20 * frame[i-1].getQueenUpgrade());
						frame[i-1].addBees(addBees);
						frame[i-1].addLarvae(-addBees);
						frame[i-1].addEmptyCells(addBees);
					}
				}
			}
		};	
		timer.scheduleAtFixedRate(beeTask, new Date(), time);
	}

	// Method to start timer to regularly consume resources.

	public static void startConsumeTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask consumeTask = new TimerTask()
		{
			public void run()
			{
				for (int i = 1; i <= fcount; i++)
				{
					if (frame[i-1].hasQueen() && frame[i-1].getHoney() > 0)
					{
						int addHoney = getRandomInt(frame[i-1].getBees()/100 - frame[i-1].getBroodCells()/50);
						frame[i-1].addHoney(-addHoney);
						frame[i-1].addEmptyCells(addHoney);
					}
					if (frame[i-1].hasQueen() && frame[i-1].getPollen() > 0)
					{
						int addPollen = getRandomInt(frame[i-1].getBees()/100 - frame[i-1].getBroodCells()/50);
						frame[i-1].addPollen(-addPollen);
						frame[i-1].addEmptyCells(addPollen);
					}
				}
			}
		};	
		timer.scheduleAtFixedRate(consumeTask, new Date(), time);
	}

	// Method to start timer to gradually increase resources

	public static void startResourceTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask resourceTask = new TimerTask()
		{
			public void run()
			{
				for (int i = 1; i <= fcount; i++)
				{
					if (frame[i-1].hasQueen() && frame[i-1].getEmptyCells() > 2 * frame[i-1].getBeeUpgrade() * (frame[i-1].getBees()/10 + 1))
					{
						int addHoney = getRandomInt(frame[i-1].getBeeUpgrade() * (frame[i-1].getBees()/10 + 1));
						int addPollen = getRandomInt(frame[i-1].getBeeUpgrade() * (frame[i-1].getBees()/10 + 1));
						frame[i-1].addHoney(addHoney);
						frame[i-1].addEmptyCells(-addHoney);
						frame[i-1].addPollen(addPollen);
						frame[i-1].addEmptyCells(-addPollen);
					}
					if (frame[i-1].getEmptyCells() <= 2 * frame[i-1].getBeeUpgrade() * (frame[i-1].getBees()/10 + 1))
					{
						frame[i-1].addHoney(frame[i-1].getEmptyCells()/2);
						frame[i-1].addPollen(frame[i-1].getEmptyCells()/2);
						frame[i-1].setEmptyCells(0);
					}
				}
			}
		};
		timer.scheduleAtFixedRate(resourceTask, new Date(), time);
	}

	public static void startWaxTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask waxTask = new TimerTask()
		{
			public void run()
			{
				for (int i = 1; i <= fcount; i++)
				{
					if (frame[i-1].hasQueen() && frame[i-1].getCells() < frame[i-1].getCellMax())
					{
						frame[i-1].addEmptyCells(getRandomInt(frame[i-1].getBees()/100 * frame[i-1].getBeeUpgrade()));
					}
				}
			}
		};
		timer.scheduleAtFixedRate(waxTask, new Date(), time);
	}

	public static double getRandomDouble(double value)
	{
		double randomFactor = 0.2;
		double lower = value - value*randomFactor;
		double upper = value + value*randomFactor;
		return lower + (upper - lower)*Math.random();
	}

	public static int getRandomInt(int value)
	{
		double randomFactor = 0.2;
		double lower = value - value*randomFactor;
		double upper = value + value*randomFactor;
		Long random = Math.round(lower + (upper - lower)*Math.random());
		return random.intValue();
	}
}
