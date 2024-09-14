package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author <<Brandon Liao>>
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		for (int row = 0; row < tOld.getLength(); row ++) {
			for (int col = 0; col < tOld.getWidth(); col++) {
				tNew.grid[row][col] = tOld.grid[row][col].next(tNew);
			}
		}
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		int profit = 0;
		for (int row = 0; row < town.getLength(); row ++) {
			for (int col = 0; col < town.getWidth(); col++) {
				if (town.grid[row][col].who() == State.CASUAL) {
					profit += 1;
				}
			}
		}
		return profit;
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 * 
	 */
	public static void main(String []args) throws FileNotFoundException {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Would you like to specify the elements of the grid via an input file (option 1) or would you like to generate it randomly (option 2)?");
		String userInput = scnr.nextLine();
		Town tNew;
		if (userInput.equals("option 1")) {
			System.out.println("Please enter file path: ");
			String filePath = scnr.nextLine();
			tNew = new Town(filePath);
			
		} else if (userInput.equals("option 2")) {
			System.out.println("Provide rows, cols and seed integer separated by\n" + "spaces:");
			int row = scnr.nextInt();
			int col = scnr.nextInt();
			int seed = scnr.nextInt();
			tNew = new Town(row,col);
			tNew.randomInit(seed);
		} else {
			tNew = null;
			System.out.println("Error: Please type either option 1 or option 2.");
		}
		double potential = tNew.getLength() * tNew.getWidth();
		int curBillCycle = 0;
		double profit = 0;
		int totalProfit = 0;
		while (curBillCycle < 12) {
			int monthlyProfit = getProfit(tNew);
			totalProfit += monthlyProfit;
			tNew = updatePlain(tNew);
			curBillCycle++;
		}
		profit = (100*totalProfit)/(potential*12);
		System.out.println(String.format("%.2f",profit) + "%");
		scnr.close();
	}
}
