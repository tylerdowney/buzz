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
		private int hiveTimerDelay;
		private final Timer hiveTimer;

		public HiveComponents(Buzz bg) {
			super();
			buzzGame = bg;
			hiveTimerDelay = 1000;
			hiveTimer = new Timer(hiveTimerDelay, hivePainter);
			hiveTimer.start();
			setBackground(new Color(0,153,0));
			hiveClicker();
			buzzGame.addHive();
		}
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g2);
			g2.setColor(Color.YELLOW);
			for (int i = 1; i <= buzzGame.getHiveImage().size(); i++)
			{
				g2.fill(buzzGame.getHiveImage().get(i-1));
				buzzGame.getBeeLabel().get(i-1).setText("Hive " + i + " has " + buzzGame.getHiveArray()[i-1].getBees() + " bees");
				buzzGame.getBeeLabel().get(i-1).setBounds(10, (int) (buzzGame.getHiveImage().get(i-1).getY() + buzzGame.getHiveImage().get(i-1).getHeight() + 10), 200, 10);
				add(buzzGame.getBeeLabel().get(i-1));
			}
			//System.out.println(name + " has $" + money + " in total.\n");
		}

		ActionListener hivePainter = new ActionListener() {
		@Override
			public void actionPerformed(ActionEvent theEvent) {
			for (int i = 1; i <= buzzGame.getHiveImage().size(); i++)
			{	
				buzzGame.getBeeLabel().get(i-1).setText("Hive " + i + " has " + buzzGame.getHiveArray()[i-1].getBees() + " bees");
				repaint();
			}
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
	       			if (buzzGame.getHiveImage().get(i-1).contains(me.getPoint()))
					{
				Buzz.hiveSelected = i;
            			buzzGame.inspectHive(i);
           		 	}
				}
			}
        });
    }
}
