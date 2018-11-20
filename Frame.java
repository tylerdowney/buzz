
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
	private int larvae;
	private double clutter;
	private double clutterMax;
	private int cells;
	private int cellMax;
	private double honeyPerCell;
	private double pollenPerCell;
	private int nectarCells;
	private int honeyCells;
	private int pollenCells;
	private int broodCells;
	private int emptyCells;

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
		larvae = 0;
		clutter = 0;
		cellMax = 3500;
		clutterMax = 5000;
		honeyPerCell = 1.0;
		pollenPerCell = 1.0;
		emptyCells = 0;
		nectarCells = 0;
		broodCells = 0;
		pollenCells = 0;
		honeyCells = 0;
		broodCells = 0;
		cells = 0;
	}

	public boolean getQueen()
	{
		return queen;
	}

	public double getHoney()
	{
		return honeyPerCell * honeyCells;
	}

	public int getBees()
	{
		return bees;
	}

	public boolean hasQueen()
	{
		return queen;
	}

	public double getPollen()
	{
		return pollenPerCell * pollenCells;
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
		return broodCells;
	}

	public int getLarvae()
	{
		return larvae;
	}

	public double getClutter()
	{
		return clutter;
	}

	public int getHoneyCells()
	{
		return honeyCells;
	}

	public int getEmptyCells()
	{
		return emptyCells;
	}

	public int getNectarCells()
	{
		return nectarCells;
	}

	public int getPollenCells()
	{
		return pollenCells;
	}

	public int getCellMax()
	{
		return cellMax;
	}

	public double getClutterMax()
	{
		return clutterMax;
	}

	public int getCells()
	{
		this.cells = this.pollenCells + this.honeyCells + this.nectarCells + this.emptyCells + this.broodCells;
		return cells;
	}

	public static void getAge(Frame[] frames, long t, String n, int hn, int fn)
	{
		if (frames[fn-1].hasQueen())
		{
			System.out.println(t/31536000 + " yr, " + (t%31536000)/86400 + " dy, " + (t%31536000%86400)/3600 + " hr, " + (t%31536000%86400%3600)/60 + " min, " + t%31536000%86400%3600%60 + " s\n");
		}
		else
		{
			System.out.println("Hive " + hn + ", Frame " + fn + " hasn't been drawn yet\n");
		}
	}

	public void addHoney(int h)
	{
		this.honeyCells = honeyCells + h;
	}

	public void addBees(int b)
	{
		this.bees = bees + b;
	}

	public void addPollen(int p)
	{
		this.pollenCells = pollenCells + p;
	}

	public void addNectar(int n)
	{
		this.nectarCells = nectarCells + n;
	}

	public void addEmptyCells(int e)
	{
		this.emptyCells = emptyCells + e;
	}

	public void addBeeUpgrade()
	{
		this.beeUpgrade = beeUpgrade + 1;
	}

	public void addQueenUpgrade()
	{
		this.queenUpgrade = queenUpgrade + 1;
	}

	public void addBrood(int b)
	{
		this.broodCells = broodCells + b;
	}

	public void addLarvae(int l)
	{
		this.larvae = larvae + l;
	}

	public void addClutter(double c)
	{
		this.clutter = clutter + c;
	}

	public void setQueen(boolean q)
	{
		this.queen = q;
	}

	public void setHoney(int h)
	{
		this.honeyCells = h;
	}

	public void setPollen(int p)
	{
		this.pollenCells = p;
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
		this.broodCells = br;
	}

	public void setEmptyCells(int ec)
	{
		this.emptyCells = ec;
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
