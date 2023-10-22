/*
 * Author: Jessa Yang
 * Date : 3/14/23
 * Rev: 01
 * Note:
   This class implements a vendor that sells one kind of items.
   A vendor carries out sales transactions.
   
   New additions:
   getCount() - changed how the vending machine works so you can but multiple candies at once!
   This method tells you how many candies were bought.
*/

public class Vendor
{
  // Fields:
	
	private int price, stock, deposit, change, count, totalSold;
	private static double totalSales;

  //  Constructor
  //  Parameters:
  //    int price of a single item in cents
  //    int number of items to place in stock

	public Vendor(int p, int s)
	{
		price = p;
		stock = s;
		deposit = 0;
		change = 0;
		totalSold =0;
	}

  //  Sets the quantity of items in stock.
  //  Parameters:
  //    int number of items to place in stock
  //  Return:
  //    None
	public void setStock(int num)
	{
		stock = num;
	}

  //  Returns the number of items currently in stock.
  //  Parameters:
  //    None
  //  Return:
  //    int number of items currently in stock
	public int getStock ()
	{
		return stock;
	}

  //  Adds a specified amount (in cents) to the deposited amount.
  //  Parameters:
  //    int number of cents to add to the deposit
  //  Return:
  //    None
	public void addMoney(int cents)
	{
		deposit += cents;
	}

  //  Returns the currently deposited amount (in cents).
  //  Parameters:
  //    None
  //  Return:
  //    int number of cents in the current deposit
	
	public int getDeposit()
	{
		return deposit;
	}

  //  Implements a sale.  If there are items in stock and
  //  the deposited amount is greater than or equal to
  //  the single item price, then adjusts the stock
  //  and calculates and sets change and returns true;
  //  otherwise refunds the whole deposit (moves it into change)
  //  and returns false.
  //  Parameters:
  //    None
  //  Return:
  //    boolean successful sale (true) or failure (false)
  
	public boolean makeSale()
	{
		if (stock > 0 && deposit >= price)
		{
			change = deposit;
			while (change >= price && stock > 0)
			{
				change -=price;
				count++;
				stock -= 1;
			}
			totalSales += (double)(count*price)/100;
			deposit = 0;
			return true;
			
			/*
			stock -=1;
			change = deposit-price;
			deposit = 0;
			return true;
			*/
		}
		else
		{
			change = deposit;
			deposit = 0;
			return false;
		}
	}
	
	// Returns the number of candies being sold and resets the number.
	public int getCount()
	{
		int num = count;
		totalSold += count;
		count = 0;
		return num;
	}

  //  Returns and zeroes out the amount of change (from the last
  //  sale or refund).
  //  Parameters:
  //    None
  //  Return:
  //    int number of cents in the current change
  
	public int getChange()
	{
		int changeBack = change;
		change = 0;
		return changeBack;
	}
	
	public int getTotalSold() // returns the total number of candies sold and resets to zero (for the receipt)
	{
		int returnNum = totalSold;
		totalSold = 0;
		return returnNum;
	}
	
	public int getPrice() // returns the price of the candy
	{
		return price;
	}
	
	public static double getTotalSales() // returns the total number of candies sold and resets to zero that count to zero (static version)
	{
		double returnSales = totalSales;
		totalSales = 0;
		return returnSales;
		
	}
}
