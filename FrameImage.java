public class FrameImage(){
	private static Buzz buzzGame;
	
	public FrameImage(Buzz bg){
		buzzGame = bg;
	}
	public void makeFrameImage(){
		int BEEFRAME_WIDTH = 600;
		int BEEFRAME_HEIGHT = 600;
		JFrame beeFrame = new JFrame();
		beeFrame.setSize(BEEFRAME_WIDTH, BEEFRAME_HEIGHT);
		beeFrame.setVisible(true);

