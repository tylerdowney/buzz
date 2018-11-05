public class Hive
{
	private int frames;
	int id;

	public Hive(int n)
	{
		frames = 0;
		id = n;
	}

	public int getFrames()
	{
		return frames;
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
		if (this.frames < 10)
		{
			this.frames = this.frames + 1;
		}
		else
		{
			System.out.println("Hive is full. Create a new hive");
		}
	}
}
