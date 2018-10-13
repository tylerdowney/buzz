public class Hive
{
	public double honey;
	public int bees;
	public boolean queen;
	public double pollen;
	public int age;
	public int beeUpgrade;
	public int queenUpgrade;

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

	public int getAge()
	{
		return age;
	}

	public int getBeeUpgrade()
	{
		return beeUpgrade;
	}

	public int getQueenUpgrade()
	{
		return queenUpgrade;
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

	public void addBeeUpgrade()
	{
		this.beeUpgrade = this.beeUpgrade + 1;
	}

	public void addQueenUpgrade()
	{
		this.queenUpgrade = this.queenUpgrade + 1;
	}

	public void setHoney(double h)
	{
		this.honey = h;
	}

	public void setPollen(double p)
	{
		this.pollen = p;
	}

	public void setBees(int b)
	{
		this.bees = b;
	}

	public void setBeeUpgrade(int bu)
	{
		this.beeUpgrade = bu;
	}

	public void setQueenUpgrade(int qu)
	{
		this.queenUpgrade = qu;
	}	
}
