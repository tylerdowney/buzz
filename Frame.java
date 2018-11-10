
public class Frame
{
	private double honey;
	private int bees;
	private boolean queen;
	private double pollen;
	private long starttime;
	private int beeUpgrade;
	private int queenUpgrade;
	private int hiveId;
	private int frameId;
	private int brood;
	private int larvae;
	private double clutter;

	public Frame(boolean q, int hid, int fid)
	{
		honey = 0;
		bees = 0;
		queen = q;
		pollen = 0;
		hiveId = hid;
		frameId = fid;
		queenUpgrade = 1;
		beeUpgrade = 1;
		brood = 0;
		larvae = 0;
		clutter = 0;
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

	public long getStartTime()
	{
		return starttime;
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

	public int getBrood()
	{
		return brood;
	}

	public int getLarvae()
	{
		return larvae;
	}

	public double getClutter()
	{
		return clutter;
	}

	public static void getAge(long t, String n, int hn, int fn)
	{
		System.out.println("Hive " + hn + ", Frame " + fn + " is " + t/31536000 + " years, " + (t%31536000)/86400 + " days, " + (t%31536000%86400)/3600 + " hours, " + (t%31536000%86400%3600)/60 + " minutes, and " + t%31536000%86400%3600%60 + " seconds old");
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

	public void addBrood(int b)
	{
		this.brood = brood + b;
	}

	public void addLarvae(int l)
	{
		this.larvae = larvae + l;
	}

	public void addCLutter(double c)
	{
		this.clutter = clutter + c;
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

	public void setBrood(int br)
	{
		this.brood = br;
	}

	public void setLarvae(int lr)
	{
		this.larvae = lr;
	}

	public void setClutter(double cl)
	{
		this.clutter = cl;
	}

	public void setStartTime(long s)
	{
		this.starttime = s;
	}
}
