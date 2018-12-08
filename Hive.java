public class Hive
{
	private int frames;
	int id;
	int framesMax;
	int beeMax;
	int beeUpgrade;
	int queenUpgrade;
	int bees;

	public Hive(int n)
	{
		frames = 0;
		id = n;
		framesMax = 10;
		beeMax = 35000;
		beeUpgrade = 1;
		queenUpgrade = 1;
		bees = 5;
	}

	public int getFrames()
	{
		return frames;
	}

	public int getBees()
	{
		return bees;
	}

	public void setBees(int b)
	{
		this.bees = b;
	}

	public void addBees(int b)
	{
		this.bees = bees + b;
	}

	public int getBeeMax()
	{
		return beeMax;
	}

	public int getBeeUpgrade()
	{
		return beeUpgrade;
	}

	public int getQueenUpgrade()
	{
		return queenUpgrade;
	}

	public void addBeeUpgrade()
	{
		this.beeUpgrade = beeUpgrade + 1;
	}

	public void addQueenUpgrade()
	{
		this.queenUpgrade = queenUpgrade + 1;
	}

	public void setBeeUpgrade(int b)
	{
		this.beeUpgrade = b;
	}

	public void setQueenUpgrade(int q)
	{
		this.queenUpgrade = q;
	}

	public int getHid()
	{
		return id;
	}

	public void setHid(int hid)
	{
		this.id = hid;
	}

	public void addFrames()
	{
		this.frames = frames + 1;
	}

	public int getFramesMax()
	{
		return framesMax;
	}
}
