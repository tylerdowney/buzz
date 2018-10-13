public class Hive
{
	private double honey;
	private int bees;
	private boolean queen;
	private double pollen;


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
		this.honey = honey + h;
	}

	public void addBees(int b)
	{
		this.bees = bees + b;
	}

	public void addPollen(double p)
	{
		this.pollen = pollen + p;
	}
}
