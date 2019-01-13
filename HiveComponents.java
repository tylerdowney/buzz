package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;

public class HiveComponents extends JComponent{
		private ArrayList<Rectangle> hives = new ArrayList<Rectangle>();
		public HiveComponents() {
		}
		public void paintComponent(Graphics g) {
			addHive();
			addHive();
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.YELLOW);
			g2.setBackground(Color.BLACK);
			for (Rectangle element : hives) {
				g2.fill(element);
			}
		}
		public void addHive() {
			hives.add(new Rectangle(getWidth() - 100, 100 * hives.size(), 80, 80));
		}
}