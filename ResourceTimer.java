import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ResourceTimer
{	
	public void resourceTimer(int beeUpgrade)
	{
		Timer resourceTimer = new Timer();
		resourceTimer.scheduleAtFixedRate(new TimerTask()
			{
				ronHive.addHoney(beeUpgrade);
				ronHive.addPollen(beeUpgrade);
			}, new Date(), 5000);
	}
}
