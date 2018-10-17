public class Frame
{
	private double honey;
	private int bees;
	private boolean queen;
	private double pollen;
	private int age;
	private int beeUpgrade;
	private int queenUpgrade;
	private int hiveId;
	private int frameId;

	public Frame(boolean q, int hid, int fid)
	{
		honey = 0;
		bees = 0;
		queen = q;
		pollen = 0;
		hiveId = hid;
		frameId = fid;
		age = 0;
		queenUpgrade = 1;
		beeUpgrade = 1;
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

	public int getHid()
	{
		return hiveId;
	}

	public int getFid()
	{
		return frameId;
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
	
	public void setHid(int hid)
	{
		this.hiveId = hid;
	}

	public void setFid(int fid)
	{
		this.frameId = fid;
	}
}
