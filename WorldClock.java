import java.util.*;

public class WorldClock
{

	private long starttime;
	private long worldage;
		
	public WorldClock(long s)
	{
		starttime = s;
	}

	public long getAge()
	{
		Date currentDate = new Date();
 		return currentDate.getTime()/1000 - starttime;	
	}

	// Method to start timer for gradually increasing bee quantities (every 18 seconds)

	public static void startBroodTimer(Frame[] frame, Timer timer, int time, int fcount)
	{
		TimerTask broodTask = new TimerTask()
		{
			public void run()
			{
				for (int i = 1; i <= fcount; i++)
				{
					frame[i-1].setBrood(frame[i-1].getBees() + frame[i-1].getQueenUpgrade());
				}
			}
		};	
		timer.scheduleAtFixedRate(broodTask, new Date(), time);
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

	// Method to start timer for gradually increasing resources (every 5 seconds)

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
