package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author <<Brandon Liao>>
 *
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File file = new File(inputFileName);
		Scanner scnr = new Scanner(file);
		length = scnr.nextInt();
		width = scnr.nextInt();
		grid = new TownCell[length][width];
		for(int row = 0; row < length; row++) {
			for(int col = 0; col < width; col++) {
				String str = scnr.next();
				if (str.equals("R")) {
					grid[row][col] = new Reseller(this,row,col);
				} else if (str.equals("E")) {
					grid[row][col] = new Empty(this,row,col);
				} else if (str.equals("C")) {
					grid[row][col] = new Casual(this,row,col);
				} else if (str.equals("O")) {
					grid[row][col] = new Outage(this,row,col);
				} else if (str.equals("S")) {
					grid[row][col] = new Streamer(this,row,col);
				}
			}
		}
		scnr.close();
	}
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		for (int row = 0; row < length; row++) {
			for(int col = 0; col < width; col++) {
				int newRand = rand.nextInt(5);
				if (newRand == 0) {
					grid[row][col] = new Reseller(this,row,col);
				} else if (newRand == 1) {
					grid[row][col] = new Empty(this,row,col);
				} else if (newRand == 2) {
					grid[row][col] = new Casual(this,row,col);
				} else if (newRand == 3) {
					grid[row][col] = new Outage(this,row,col);
				} else if (newRand == 4) {
					grid[row][col] = new Streamer(this,row,col);
				}
			}
		}
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < width; col++) {
				if(grid[row][col].who() == State.RESELLER) {
					s += "R";
				} else if (grid[row][col].who() == State.EMPTY) {
					s += "E";
				} else if (grid[row][col].who() == State.CASUAL) {
					s += "C";
				} else if (grid[row][col].who() == State.OUTAGE) {
					s += "O";
				} else if (grid[row][col].who() == State.STREAMER) {
					s += "S";
				}
				
			}
			s += "\n";
		}
		return s;
	}
}
