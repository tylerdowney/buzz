import java.util.*;

public class WorldClock
{

	private long starttime;
	private long worldage;
	private int broodCounter;
		
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

	// Method to start timer for gradually increasing bee quantities

	public static void startBroodTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask broodTask = new TimerTask()
		{
			public void run()
			{
				int broodMax = 2000;
				for (int i = 1; i <= fcount; i++)
				{
					if (frame[i-1].getBrood() < broodMax)
					{
						frame[i-1].setBrood(frame[i-1].getBrood() + 20 * frame[i-1].getQueenUpgrade());
					}
				}
			}
		};	
		timer.scheduleAtFixedRate(broodTask, new Date(), time);
	}

	public static void startBeeTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask beeTask = new TimerTask()
		{
			public void run()
			{
				int beeMax = 3000;
				for (int i = 1; i <= fcount; i++)
				{
					if (frame[i-1].getBees() < beeMax)
					{
						frame[i-1].setBees(frame[i-1].getBees() + 20 * frame[i-1].getQueenUpgrade());
						frame[i-1].setBrood(frame[i-1].getBrood() - 20 * frame[i-1].getQueenUpgrade());
					}
				}
			}
		};	
		timer.scheduleAtFixedRate(beeTask, new Date(), time);
	}

	// Method to start timer to regularly consume resources 

	public static void startConsumeTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask consumeTask = new TimerTask()
		{
			public void run()
			{
				for (int i = 1; i <= fcount; i++)
				{
					frame[i-1].setHoney(frame[i-1].getHoney() - frame[i-1].getBees()/100 - frame[i-1].getBrood()/50);
					frame[i-1].setPollen(frame[i-1].getPollen() - frame[i-1].getBees()/100 - frame[i-1].getBrood()/50);
				}
			}
		};	
		timer.scheduleAtFixedRate(consumeTask, new Date(), time);
	}

	// Method to start timer for gradually increasing resources

	public static void startResourceTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask resourceTask = new TimerTask()
		{
			public void run()
			{
				for (int i = 1; i <= fcount; i++)
				{
					frame[i-1].setHoney(frame[i-1].getHoney() + frame[i-1].getBeeUpgrade() * (frame[i-1].getBees()/10 + 1));
					frame[i-1].setPollen(frame[i-1].getPollen() + frame[i-1].getBeeUpgrade() * (frame[i-1].getBees()/10 + 1));
				}
			}
		};
		timer.scheduleAtFixedRate(resourceTask, new Date(), time);
	}
}
