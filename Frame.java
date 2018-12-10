
public class Frame
{
	private double honey;
	private double pollen;
	private long starttime;
	private int hiveId;
	private int frameId;
	private int larvae;
	private double clutter;
	private double clutterMax;
	private int cells;
	private int cellMax;
	private double honeyPerCell;
	private double nectarPerCell;
	private double pollenPerCell;
	private int nectarCells;
	private int honeyCells;
	private int pollenCells;
	private int broodCells;
	private int emptyCells;
	private int distToCent;

	public Frame(int hid, int fid, int dist)
	{
		honey = 0;
		pollen = 0;
		hiveId = hid;
		frameId = fid;
		larvae = 0;
		clutter = 0;
		cellMax = 3500;
		clutterMax = 5000;
		honeyPerCell = 1.0;
		nectarPerCell = 1.0;
		pollenPerCell = 1.0;
		emptyCells = 0;
		nectarCells = 0;
		broodCells = 0;
		pollenCells = 0;
		honeyCells = 0;
		broodCells = 0;
		cells = 0;
		distToCent = dist;
	}

	public double getHoney()
	{
		return honeyPerCell * honeyCells;
	}

	public double getNectar()
	{
		return nectarPerCell * nectarCells;
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

	public int getBroodCells()
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

	public static void getAge(Hive[] hives, long t, String n, int hn, int fn)
	{
		System.out.println(t/31536000 + " yr, " + (t%31536000)/86400 + " dy, " + (t%31536000%86400)/3600 + " hr, " + (t%31536000%86400%3600)/60 + " min, " + t%31536000%86400%3600%60 + " s\n");
	}

	public int getDistToCent()
	{
		return distToCent;
	}

	public void addHoney(int h)
	{
		this.honeyCells = honeyCells + h;
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

	public void setHoney(int h)
	{
		this.honeyCells = h;
	}

	public void setNectar(int n)
	{
		this.nectarCells = n;
	}

	public void setPollen(int p)
	{
		this.pollenCells = p;
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

	public void setCells(int c)
	{
		this.cells = c;
	}

	public void setCellMax(int c)
	{
		this.cellMax = c;
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

	public void setDistToCent(int d)
	{
		this.distToCent = d;
	}
}
