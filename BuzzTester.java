public class BuzzTester 
{
	private static Buzz buzzGame;
	public BuzzTester(Buzz bg)
	{
		buzzGame = bg;
	}

	public static void main(String[] args)
	{
		buzzGame.loadGame();
	}
}
