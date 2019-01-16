import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

	public class HiveComponents extends JPanel{
		private static Buzz buzzGame;
		private int myTimerDelay;
		private final Timer myTimer;
		private ArrayList<Rectangle> hives = new ArrayList<Rectangle>();

		public HiveComponents(Buzz bg) {
			super();
			buzzGame = bg;
			myTimerDelay = 1000;
			myTimer = new Timer(myTimerDelay, hiveTimer);
			myTimer.start();
			setBackground(new Color(0,153,0));
			//FrameImage fi = new FrameImage();
			//fi.makeFrameImage();
			hiveClicker();
			addHive();
		}
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g2);
			g2.setColor(Color.YELLOW);
			for (Rectangle element : hives) {
				g2.fill(element);
			}
		}
		public void addHive() {
			hives.add(new Rectangle(20, 100 * hives.size(), 80, 80));
		}
		ActionListener hiveTimer = new ActionListener() {
		@Override
			public void actionPerformed(ActionEvent theEvent) {

		}
	};

	public void hiveClicker() {
        addMouseListener(new MouseAdapter()
		{
      		@Override
       		public void mouseClicked(MouseEvent me)
			{
       	 		super.mouseClicked(me);
				for (int i = 1; i <= buzzGame.getHiveCounter(); i++)
				{
	       			if (hives.get(i-1).contains(me.getPoint()))
					{
						System.out.println("hive clicked");
            			buzzGame.inspectHive(i);
           		 	}
				}
			}
        });
    }
}
