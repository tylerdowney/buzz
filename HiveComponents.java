import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HiveComponents extends JPanel{
		private int myTimerDelay;
		private final Timer myTimer;
		private ArrayList<Rectangle> hives = new ArrayList<Rectangle>();
		public HiveComponents() {
			super();
			myTimerDelay = 1000;
			myTimer = new Timer(myTimerDelay, hiveTimer);
			myTimer.start();
			setBackground(new Color(0,153,0));
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
				addHive();
				repaint();
		}
	};
}
