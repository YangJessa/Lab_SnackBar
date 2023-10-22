/*
 * Author: Jessa Yang
 * Date : 3/14/23
 * Rev: 01
 * Note:
   This class represents a snack bar made up of three vending machines. It names the different brands and their prices, along
   with the password to the replenish.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SnackBar extends JFrame
                      implements ActionListener
{
  private static final String MY_PASSWORD = "jinx";
  private VendingMachine machine1, machine2, machine3;

  public SnackBar()
  {
	  super("Snack Bar");
	  /*
	  Color brandColor1 = new Color(130, 30, 10); // r, g, b
	  Color brandColor2 = new Color(255, 180, 0);
	  Color brandColor3 = new Color(90, 180, 0);
	  */
    
	  // hexadecimal
    
	  Color brandColor1 = new Color(0xffefc6); // vanilla
	  Color brandColor2 = new Color(0xffc6b4); // strawberry
	  Color brandColor3 = new Color(0x7b563b); // chocolate
    

	  // Load the coin icon for the vending machine buttons:
	  ImageIcon coin = new ImageIcon("coin.png"); // PREVIOUSLY coin.gif

	  machine1 = new VendingMachine("Vanilla Pop", brandColor1, 45, coin);
	  machine2 = new VendingMachine("Strawberry Pop", brandColor2, 50, coin);
	  machine3 = new VendingMachine("Chocolate Pop", brandColor3, 35, coin);

	  Box wall = Box.createHorizontalBox();
	  wall.add(Box.createHorizontalStrut(5));
	  wall.add(machine1);
	  wall.add(Box.createHorizontalStrut(5));
	  wall.add(machine2);
	  wall.add(Box.createHorizontalStrut(5));
	  wall.add(machine3);
	  wall.add(Box.createHorizontalStrut(5));

	  JPanel service = new JPanel();
	  service.add(new JLabel(" Service login: "));
	  JPasswordField password = new JPasswordField("", 5);
	  password.addActionListener(this);
	  service.add(password);

	  Container c = getContentPane();
	  c.setBackground(new Color(0x84e6d5)); // dark teal PREVIOUSLY Color.GRAY
	  c.add(wall, BorderLayout.CENTER);
	  c.add(service, BorderLayout.SOUTH);
  }

  	/**
  	 *  Password field: user strikes <Enter>
  	 */
  
  		public void actionPerformed(ActionEvent e)
  		{
  			JPasswordField password = (JPasswordField)e.getSource();
  			String word = new String(password.getPassword());
  			password.setText("");
  			if (MY_PASSWORD.equals(word))
  			{
  				double amt = Vendor.getTotalSales();
  				machine1.reload();
  				machine2.reload();
  				machine3.reload();
  				JOptionPane.showMessageDialog(null,
  						String.format("Total sales: $%.2f\n", amt) + 
  						"Machines reloaded",
  						"Service", JOptionPane.INFORMATION_MESSAGE);
  			}
  			else
  			{
  				JOptionPane.showMessageDialog(null,
  						"Login failed", "Service", JOptionPane.ERROR_MESSAGE);
  			}
  		}

  // *****************************************************

  		public static void main(String[] args)
  		{
  			SnackBar window = new SnackBar();
  			window.setBounds(50, 50, 625, 310);
  			window.setDefaultCloseOperation(EXIT_ON_CLOSE);
  			window.setResizable(false);
  			window.setVisible(true);
  		}
}

