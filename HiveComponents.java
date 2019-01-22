import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

	public class HiveComponents extends JPanel{
		private static Buzz buzzGame;
		private int myTimerDelay;
		private final Timer myTimer;
		private ArrayList<Rectangle> hiveImage = new ArrayList<Rectangle>();
		private ArrayList<JLabel> beeLabel = new ArrayList<JLabel>();

		public HiveComponents(Buzz bg) {
			super();
			buzzGame = bg;
			myTimerDelay = 1000;
			myTimer = new Timer(myTimerDelay, hiveTimer);
			myTimer.start();
			setBackground(new Color(0,153,0));
			hiveClicker();
			addHive();
		}
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g2);
			g2.setColor(Color.YELLOW);
			for (int i = 1; i <= hiveImage.size(); i++)
			{
				g2.fill(hiveImage.get(i-1));
				beeLabel.get(i-1).setText("Hive " + (i-1) + " has " + buzzGame.getHiveArray()[i-1].getBees() + " bees");
				beeLabel.get(i-1).setBounds(10, (int) (hiveImage.get(i-1).getY() + hiveImage.get(i-1).getHeight() + 10), 200, 10);
				add(beeLabel.get(i-1));
			}
			//System.out.println(name + " has $" + money + " in total.\n");
		}
		public void addHive() {
			hiveImage.add(new Rectangle(20, 35 + 120 * hiveImage.size(), 80, 80));
			beeLabel.add(new JLabel());
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
	       			if (hiveImage.get(i-1).contains(me.getPoint()))
					{
            			buzzGame.inspectHive(i);
           		 	}
				}
			}
        });
    }
}
