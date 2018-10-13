import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class BeeTimer
{
	public BeeTimer(int queenUpgrade)
	{
		Timer beeTimer = new Timer();
		beeTimer.scheduleAtFixedRate(new TimerTask()
		{
			if (ronHive.getHoney() >= 1 && ronHive.getPollen() >= 1)
			{
				ronHive.addBees(queenUpgrade);
				ronHive.addHoney(-1);
				ronHive.addPollen(-1);
			}
		}, new Date(), 20000);
	}
}
