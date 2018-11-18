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
					if (frame[i-1].hasQueen() && frame[i-1].getBrood() <= broodMax)
					{
						frame[i-1].addBrood(20 * frame[i-1].getQueenUpgrade());
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
					if (frame[i-1].hasQueen() && frame[i-1].getBrood() > 0)
					{
						frame[i-1].addClutter(20 * clutPerEgg * frame[i-1].getQueenUpgrade());
						frame[i-1].addLarvae(20 * frame[i-1].getQueenUpgrade());
						frame[i-1].addBrood(-20 * frame[i-1].getQueenUpgrade());
					}
				}
			}
		};	
		timer.scheduleAtFixedRate(larvaeTask, new Date(), time);
	}

	// Method for gradually increasing clutter as time goes on, and as eggs hatch. Bees clean clutter regularly

	public static void startClutterTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		double clutPerSec = 0.01;
		double clutPerBee = 0.0001;
		TimerTask clutterTask = new TimerTask()
		{
			public void run()
			{
				for (int i = 1; i <= fcount; i++)
				{
					if (frame[i-1].hasQueen())
					{
						frame[i-1].addClutter((time/1000) * clutPerSec);
						if (frame[i-1].getClutter() > 0)
						{
							frame[i-1].addClutter(-(time/1000) * frame[i-1].getBees() * clutPerBee);
						}
						if (frame[i-1].getClutter() * 100.0/frame[i-1].getClutterMax() >= 100.0)
						{
							System.out.println("Your bees can't clean the hive fast enough and " + frame[i-1].getBees()/200 + " bees have died. Add more bees to clean faster, or make a new frame to replace this old one");
							frame[i-1].addBees(-frame[i-1].getBees()/200);
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
						frame[i-1].addBees(20 * frame[i-1].getQueenUpgrade());
						frame[i-1].addLarvae(-20 * frame[i-1].getQueenUpgrade());
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
						frame[i-1].addHoney(-frame[i-1].getBees()/100 - frame[i-1].getBrood()/50);
						frame[i-1].addEmptyCells(frame[i-1].getBees()/100 - frame[i-1].getBrood()/50);
					}
					if (frame[i-1].hasQueen() && frame[i-1].getPollen() > 0)
					{
						frame[i-1].addPollen(-frame[i-1].getBees()/100 - frame[i-1].getBrood()/50);
						frame[i-1].addEmptyCells(frame[i-1].getBees()/100 - frame[i-1].getBrood()/50);
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
					if (frame[i-1].hasQueen() && frame[i-1].getEmptyCells() > 1)
					{
						frame[i-1].addHoney(frame[i-1].getBeeUpgrade() * (frame[i-1].getBees()/10 + 1));
						frame[i-1].addEmptyCells(-1);
						frame[i-1].addPollen(frame[i-1].getBeeUpgrade() * (frame[i-1].getBees()/10 + 1));
						frame[i-1].addEmptyCells(-1);
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
						frame[i-1].addEmptyCells(1);
					}
				}
			}
		};
		timer.scheduleAtFixedRate(waxTask, new Date(), time);
	}
}
