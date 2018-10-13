import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Hive
{
	public double honey;
	public int bees;
	public boolean queen;
	public double pollen;


	public Hive(boolean q)
	{
		honey = 0;
		bees = 0;
		queen = q;
		pollen = 0;
	}

	public double getHoney()
	{
		return honey;
	}

	public int getBees()
	{
		return bees;
	}

	public boolean isQueen()
	{
		return queen;
	}

	public double getPollen()
	{
		return pollen;
	}

	public void addHoney(double h)
	{
		if (this.honey >= 1 && this.pollen >= 1)
		{
			this.honey = honey + h;
		}
	}

	public void addBees(int b)
	{
		this.bees = bees + b;
	}

	public void addPollen(double p)
	{
		if (this.honey >= 1 && this.pollen >= 1)
		{
			this.pollen = pollen + p;
		}
	}
}
