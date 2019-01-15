public class FrameImage(){
	frameImage(){
	}
	public void makeFrameImage(){
		int BEEFRAME_WIDTH = 600;
		int BEEFRAME_HEIGHT = 600;
		JFrame beeFrame = new JFrame();
		beeFrame.setSize(BEEFRAME_WIDTH, BEEFRAME_HEIGHT);
		beeFrame.setVisible(true);
					Integer h;
					Integer f;
					DecimalFormat dec = new DecimalFormat("#.##");
					for (int i = 1; i <= hiveCounter; i++)
					{
						System.out.println("Hive " + hives[i-1].getHid());
					}
					System.out.println("Which hive?");
					h = in.nextInt();
					if (h.equals(null) || h > hiveCounter || h <= 0)
					{
						System.out.println("Invalid selection");
						break;
					}
					System.out.println("Hive " + h + " has " + hives[h-1].getBees() + " bees\n");
					for (int i = 1; i <= frameCounter; i++)
					{
						if (frames[i-1].getHid() == h)
						{			
							System.out.println("Frame " + frames[i-1].getFid() + ": filled cells: " + dec.format((frames[i-1].getCells() - frames[i-1].getEmptyCells())*100.0/frames[i-1].getCellMax()) + "%, drawn cells: " + dec.format(frames[i-1].getCells()*100.0/frames[i-1].getCellMax()) + "%, nectar: " + frames[i-1].getNectar() + ", honey: " + frames[i-1].getHoney() + ", pollen: " + frames[i-1].getPollen() + ", larvae: " + frames[i-1].getLarvae() + ", eggs: " + frames[i-1].getBroodCells() + ", clutter: " + dec.format(frames[i-1].getClutter()*100.0/frames[i-1].getClutterMax()) + "%");
							long frameAge = now.getTime()/1000L - frames[i-1].getStartTime();
							frames[i-1].getAge(hives, frameAge, name, h,i);
						}
					}
					System.out.println(name + " also has $" + money + " in total.\n");
					break;
				// Sell case: Sell bees, honey, and pollen.

				case 's' :
					for (int i = 1; i <= hiveCounter; i++)
					{
						System.out.println("Hive " + hives[i-1].getHid());
					}
					System.out.println("Sell from which hive?");
					h = in.nextInt();
					if (h.equals(null) || h > hiveCounter || h <= 0)
					{
						System.out.println("Invalid selection");
						break;
					}
					for (int j = 1; j <= hives[h-1].getFrames(); j++)
					{
						System.out.println("Frame " + j);
					}
					System.out.println("Sell from which frame?");
					f = in.nextInt();
					if (f.equals(null) || f > hives[h-1].getFrames() || f <= 0)
					{
						System.out.println("Invalid selection");
					}
					for (int i = 1; i <= frameCounter; i++)
					{
						if (frames[i-1].getHid() == h && frames[i-1].getFid() == f)
						{
							System.out.println("What would you like to sell? Bees (b), Honey (h), or Pollen (p)?");
							sellVar = in.next().charAt(0);
							if (sellVar == 'b')
							{
								System.out.println("Hive " + frames[i-1].getHid() + " has " + hives[h-1].getBees() + " bees. How many bees would you like to sell?");
								sell = in.nextInt();
								if (sell <= hives[h-1].getBees())
								{
									System.out.println("At $" + 2.50 * valueUpgrade + " per bee, that comes to $" + 2.50 * valueUpgrade * sell + ". Thank you!");
									money = money + 2.50 * valueUpgrade * sell;
									hives[h-1].addBees(-sell);
								}
								else
								{
									System.out.println("Not enough bees in this hive to sell");
								}
							}	
							if (sellVar == 'h')
							{
								System.out.println("Hive " + frames[i-1].getHid() + ", Frame " + frames[i-1].getFid() + " has " + frames[i-1].getHoney() + " mL of honey. How much honey would you like to sell?");
								sell = in.nextInt();
								if (sell <= frames[i-1].getHoney())
								{
									System.out.println("At $" + 1.50 * valueUpgrade + " per mL of Honey, that comes to $" +1.50 * valueUpgrade *sell + ". Thank you!");
									money = money + 1.50 * valueUpgrade * sell;
									frames[i-1].addHoney(-sell);
									frames[i-1].addEmptyCells(sell);
								}
								else
								{
									System.out.println("Not enough honey in this frame to sell");
								}
							}		
							if (sellVar == 'p')
							{
								System.out.println("Hive " + frames[i-1].getHid() + ", Frame " + frames[i-1].getFid() + " has " + frames[i-1].getPollen() + ". units of pollen. How much pollen would you like to sell?");
								sell = in.nextInt();
								if (sell <= frames[i-1].getPollen())
								{
									System.out.println("At $" + 1.50 * valueUpgrade + " per unit of pollen, that comes to $" + 1.50 * valueUpgrade * sell + ". Thank you!");
									money = money + 1.50 * valueUpgrade * sell;
									frames[i-1].addPollen(-sell);
									frames[i-1].addEmptyCells(sell);
								}
								else
								{
									System.out.println("Not enough pollen in this frame to sell");
								}
							}
							break;
						}
					}
					break;

