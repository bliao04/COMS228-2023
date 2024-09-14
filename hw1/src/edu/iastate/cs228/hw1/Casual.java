package edu.iastate.cs228.hw1;

/**
 * 
 * @author Brandon Liao
 *
 */
public class Casual extends TownCell {
	
	private State cellState; 

	public Casual(Town p, int r, int c) {
		super(p, r, c);
		cellState = State.CASUAL;
	}

	@Override
	public State who() {
		return cellState;
	}

	@Override
	public TownCell next(Town tNew) {
		int neighbors[] = new int[5];
		super.census(neighbors);
		TownCell newCell;
		
		if (neighbors[EMPTY] + neighbors[OUTAGE] <= 1) {
			newCell = new Reseller(tNew,row,col);
		} else if (neighbors[RESELLER] > 0) {
			newCell = new Outage(tNew,row,col);
		} else if (neighbors[CASUAL] >= 5) {
			newCell = new Streamer(tNew,row,col);
		} else if (neighbors[STREAMER] > 0) {
			newCell = new Streamer(tNew,row,col);
		} else {
			newCell = new Casual(tNew,row,col);
		}
		
		return newCell;
	}
	
}
