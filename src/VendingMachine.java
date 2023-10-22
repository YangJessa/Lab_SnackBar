/*
 * Author: Jessa Yang
 * Date : 3/14/23
 * Rev: 02
 * Note:
   This class represents a vending machine that oversees the vendor, transactions, and display.
   
   New additions:
   different colored vending machines and ice cream pops!
   receipt button
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VendingMachine extends JPanel
                            implements ActionListener
{
	private static final int FULL_STOCK = 5;
	private JButton deposit25c, deposit10c, deposit5c, go, receipt;
	private JTextField display;
	private Vendor vendor;
	boolean trayFull;
	Color brandColor;
	String brandName;

	public VendingMachine(String brand, Color color, int price, ImageIcon coin)
	{
		setBackground(new Color(0xa1ede1)); // teal background PREVIOUSLY Color.WHITE 
		brandColor = color;
		brandName = brand;

		JTextField banner = new JTextField("  " + brandName +
                                       "  " + price + "c  ");
		banner.setEditable(false);
		banner.setFont(new Font("Monospaced Bold Italic", Font.BOLD, 14)); // PREVIOUSLY Serif
		banner.setHorizontalAlignment(JTextField.CENTER);
		
		deposit25c = new JButton(" 25 ", coin);
		deposit25c.addActionListener(this);
		deposit25c.setBackground(Color.WHITE);
		deposit10c = new JButton(" 10 ", coin);
		deposit10c.addActionListener(this);
		deposit10c.setBackground(Color.WHITE);
		deposit5c = new JButton("  5 ", coin);
		deposit5c.addActionListener(this);
		deposit5c.setBackground(Color.WHITE);
		receipt = new JButton("receipt");
		receipt.addActionListener(this);
		receipt.setBackground(Color.WHITE);

		go = new JButton("enter");
		go.setBackground(Color.WHITE);
		go.addActionListener(this);
		
		JPanel buttons = new JPanel(new GridLayout(3, 1, 5, 0));
		buttons.setBackground(new Color(0xa1ede1)); // teal part PREVIOUSLY Color.BLUE
		buttons.add(deposit25c);
		buttons.add(deposit10c);
		buttons.add(deposit5c);
		
		JPanel toggle = new JPanel(new GridLayout(1, 2, 15 , 0));
		toggle.setBackground(new Color(0xa1ede1)); // teal part PREVIOUSLY Color.BLUE
		toggle.add(receipt);
		toggle.add(go);
	
		display = new JTextField("0  ");
		display.setFont(new Font("Monospaced Bold Italic", Font.BOLD, 16)); // PREVIOUSLY Monospace
		display.setBackground(new Color(0xbafff3)); // light teal (money amount) part PREVIOUSLY Color.YELLOW
		display.setEditable(false);
		display.setHorizontalAlignment(JTextField.RIGHT);
		
		Box b1 = Box.createVerticalBox();
		b1.add(banner);
    	b1.add(Box.createVerticalStrut(5));
    	b1.add(display);
    	b1.add(Box.createVerticalStrut(12));
    	Box b2 = Box.createHorizontalBox();
    	b2.add(Box.createHorizontalStrut(60));
    	Box b3 = Box.createVerticalBox();
    	b3.add(Box.createVerticalStrut(15));
    	b3.add(buttons);
    	
    	Box b4 = Box.createHorizontalBox();
    	b4.add(Box.createHorizontalStrut(2));
    	
    	Box b5 = Box.createVerticalBox();
    	b5.add(toggle);

    	b4.add(b5);
    	b1.add(b4);
    	b2.add(b3);
    	b1.add(b2);
    	b1.add(Box.createVerticalStrut(5));
    	add(b1);
		 
		/* With out the receipt button
		 * Note: set the window (in SnackBar) to 546 when using this code
		Box b1 = Box.createVerticalBox();
		b1.add(banner);
    	b1.add(Box.createVerticalStrut(5));
    	b1.add(display);
    	b1.add(Box.createVerticalStrut(12));
    	Box b2 = Box.createHorizontalBox();
    	b2.add(Box.createHorizontalStrut(60));
    	Box b3 = Box.createVerticalBox();
    	b3.add(go);
    	b3.add(Box.createVerticalStrut(8));
    	b3.add(buttons);
    	
    	b2.add(b3);
    	b1.add(b2);
    	b1.add(Box.createVerticalStrut(5));
    	add(b1);
		 */
		
	
	
    	vendor = new Vendor(price, FULL_STOCK);
	}

	public void reload()
	{
		vendor.setStock(FULL_STOCK);
		display.setText(" " + vendor.getDeposit() + "  ");
		repaint();
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if (b == deposit25c)
			vendor.addMoney(25);
		else if (b == deposit10c)
			vendor.addMoney(10);
		else if (b == deposit5c)
			vendor.addMoney(5);
		else if (b == go)
		{
			trayFull = vendor.makeSale();
			int change = vendor.getChange();
			if (trayFull)  // Successful sale
			{
				int count = vendor.getCount(); // R&D: you can now buy more than one candy at a time! 
				repaint();
				if (count > 1)
				{
					JOptionPane.showMessageDialog(null, 
							"Enjoy your " + count + " " + brandName + "s\n" + "Change " + change + "c",
							"Enjoy " + brandName, JOptionPane.PLAIN_MESSAGE);	
				}
				else
				{
					JOptionPane.showMessageDialog(null, 
							"Enjoy your " + brandName + "\n" + "Change " + change + "c",
							"Enjoy " + brandName, JOptionPane.PLAIN_MESSAGE);
				}
				trayFull = false;
			}	
		  
			else if (change > 0)   // Refund
			{
				JOptionPane.showMessageDialog(null,
						"Take " + change + "c back",
						"Money back", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (b==receipt)  // prints out the receipt!
		{
			int sold = vendor.getTotalSold();
			double money = sold*vendor.getPrice();
			//System.out.println("money: " + money + " money/100:" + (int)(money/100));
			int spaceNum = brandName.concat(" (" + sold + ")              ").length()-5;
			String spaces = "";
			String difference = "";
			for (int i = 0; i < spaceNum; i++)
			{
				spaces+=" ";
			}
			for (int i = brandName.length(); i < "Straberry Pop".length(); i++)
			{
				difference +=" ";
			}
			
			if (money%100==0)
			{
				JOptionPane.showMessageDialog(null, 
						"Your receipt is being printed to the console.",
						"Printing...", JOptionPane.PLAIN_MESSAGE);
				System.out.println("\n\n\n\n");
				System.out.println("-------------------------------------");
				System.out.println("               RECEIPT");
				System.out.println(".....................................");
				System.out.println(brandName + " (" + sold + ")" + difference + "              $" + (int)(money/100) + ".00");
				System.out.println(".....................................");
				System.out.println("Total" + spaces + difference + "$" + (int)(money/100) + ".00"); 
				System.out.println(".....................................");
				System.out.println("Have a nice day!");
				System.out.println("\n|||| | || ||||| |");
				System.out.println("\n-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-");
				
				/* Failed receipt window, couldn't get spaces right
				JOptionPane.showMessageDialog(null, 
						"RECEIPT\n" + brandName + " (" + sold + ")11111111111$" + (int)(money/100) + ".00" + 
						"\n...\nTotal" + spaces + "11111111111$" + (int)(money/100) + ".00" + "\nHave a nice day!",
						"Receipt", JOptionPane.PLAIN_MESSAGE);
				*/
			}
			else
			{
				JOptionPane.showMessageDialog(null, 
						"Your receipt is being printed to the console.",
						"Printing...", JOptionPane.PLAIN_MESSAGE);	
				System.out.println("\n\n\n\n");
				System.out.println("-------------------------------------");
				System.out.println("               RECEIPT");
				System.out.println(".....................................");
				System.out.println(brandName + " (" + sold + ")              $" + (int)(money/100) + "." + (int)(money%100));
				System.out.println(".....................................");
				System.out.println("Total" + spaces + "$" + (int)(money/100) + "." + (int)(money%100)); // gives the amount of money in dollars by dividing by 100 (rounded) and then  "." the remainder of that division (rounded) 
				System.out.println(".....................................");
				System.out.println("Have a nice day!");
				System.out.println("\n|||| | || ||||| |");
				System.out.println("\n-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-^-");
				
			}
			
		}

		if (vendor.getStock() > 0)
		{
			display.setText(" " + vendor.getDeposit() + "  ");
		}
		else
		{
			display.setText("Call service ");
		}
		
		repaint();
    
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		final int x0 = getWidth() / 12;
		final int y0 = getHeight() / 2;
		final int yStep = 12; // PREVIOUSLY 14
		
		g.setColor(Color.WHITE); // added
		g.fillRect(x0 - 5, y0 - 10, 50, FULL_STOCK * yStep + 30); // added
		// 50 PREVIOUSLY 28 and only x0, y0, and no + 30, but + 4
		g.setColor(new Color(0xe8e8e8));  // Drawing the box !!! PREVIOUSLY Color.BLACK
		g.drawRect(x0 - 5, y0 -10, 50, FULL_STOCK * yStep + 30); // border 
		
		int y = y0 + 20, x = x0 + 4;
    	int stock = vendor.getStock();
    	int count = FULL_STOCK;
    
    	g.setColor(new Color(0xefefef)); // added
    	g.fillRect(x0, getHeight()-10, 148, 10); // bottom of vending machine added
    
    	// legs added
    	g.setColor(new Color(0x868686)); 
    	g.fillRect(0, getHeight()-10, x0, 10);
    	g.fillRect(148+x0, getHeight()-10, x0+2, 10);
    
    

    	while (count > 0)
    	{
    		if (count <= stock)
    			drawCan(g, x + 15, y - 20 + (FULL_STOCK-count)*5); // PREVIOUSLY x, y
    		y += yStep;
    		count--;
    	}

   
    	y += yStep;
    	g.setColor(Color.WHITE);
    	g.fillRect(x0 - 5, y - 8, 50, 20); // 50s were 28, only x0, only y-4. and 20 was 18
    	g.setColor(new Color(0xe8e8e8)); // light gray dispensing box PREVIOUSLY Color.BLUE
    	g.drawRect(x0 - 5, y - 8, 50, 20); // border
    	
    	if (trayFull)
    	{
    		drawCan(g, x + 15, y+1); // PREVIOUSLY just x, y
    	}
	}

	private void drawCan(Graphics g, int x, int y)
	{
		/*
	  	g.setColor(brandColor);
	  	g.fillRoundRect(x, y, 20, 10, 4, 4);
	  	g.setColor(Color.WHITE);
	  	g.drawLine(x + 2, y + 4, x + 14, y + 4);
	  	g.drawLine(x + 2, y + 6, x + 14, y + 6);
		 */
		g.setColor(new Color(0xf1bf86)); // stick
		g.fillRoundRect(x, y, 25, 4, 5, 5);
		g.setColor(brandColor); // popsicle base
		g.fillRoundRect(x-15, y-6, 25, 15, 7, 7);
		g.fillOval(x-22, y-7, 16, 16);
		/*
	  	g.setColor(new Color(0xffb1a5));
	  	g.fillRect(x-15, y-1, 20, 2);
	  	g.fillRect(x-15, y+3, 20, 2);
		 */
	  
	}
}

