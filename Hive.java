public class Hive
{
	private int frames;
	private int queens;
	int id;
	int framesMax;

	public Hive(int n)
	{
		frames = 0;
		id = n;
	}

	public int getFrames()
	{
		return frames;
	}

	public int getQueens()
	{
		return queens;
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

	public void addQueens()
	{
		this.queens = queens + 1;
	}
}
