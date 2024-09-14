package edu.iastate.cs228.hw1;


/**
 * 
 * @author <<Brandon Liao>>
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks the cell type of all neighboring cells at (row,column) away from the cell. 
	 * @param x
	 * @param y
	 * @return
	 */
	public int getState(int x, int y) {
		if (plain.grid[row + x][col + y].who()==State.RESELLER) {
			return RESELLER;
		} 
		if (plain.grid[row + x][col + y].who()==State.EMPTY) {
			return EMPTY;
		} 
		if (plain.grid[row + x][col + y].who()==State.CASUAL) {
			return CASUAL;
		} 
		if (plain.grid[row + x][col + y].who()==State.OUTAGE) {
			return OUTAGE;
		} 
		if (plain.grid[row + x][col + y].who()==State.STREAMER) {
			return STREAMER;
		} else {
			return -1;
		}
		
	}
	
	
	/**
	 * Checks all neighborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 
		
		boolean upNeighbors;
		boolean downNeighbors; 
		boolean leftNeighbors;
		boolean rightNeighbors;
		
		if (row == 0) {
			upNeighbors = false;
		} else {
			upNeighbors = true; 
		}
		if (row == plain.grid.length-1) {
			downNeighbors = false;
		} else {
			downNeighbors = true;
		}
		if (col == 0) {
			leftNeighbors = false;
		} else {
			leftNeighbors = true;
		}
		if(col == plain.grid[0].length -1) {
			rightNeighbors = false;
		} else {
			rightNeighbors = true;
		}
		
		if (upNeighbors) {
			if (leftNeighbors) {
				nCensus[getState(-1,-1)]++;
			}
			if (rightNeighbors) {
				nCensus[getState(-1,1)]++;
			}
			nCensus[getState(-1,0)]++;
		}
		
		if (downNeighbors) {
			if (leftNeighbors) {
				nCensus[getState(1,-1)]++;
			}
			if (rightNeighbors) {
				nCensus[getState(1,1)]++;
			}
			nCensus[getState(1,0)]++;
		}
		
		if(leftNeighbors) {
			nCensus[getState(0,-1)]++;
		}
		if(rightNeighbors) {
			nCensus[getState(0,1)]++;
		}
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}
