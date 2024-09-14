package edu.iastate.cs228.hw1;

/**
 * 
 * @author Brandon Liao
 *
 */
public class Streamer extends TownCell{
	
	private State cellState;

	public Streamer(Town p, int r, int c) {
		super(p, r, c);
		cellState = State.STREAMER;
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
		} else if (neighbors[CASUAL] >= 5) {
			newCell = new Streamer(tNew,row,col);
		} else if (neighbors[RESELLER] >= 1) {
			newCell = new Outage(tNew,row,col);
		} else if (neighbors[OUTAGE] >= 1) {
			newCell = new Empty(tNew,row,col);
		} else {
			newCell = new Streamer(tNew,row,col);
		}
		
		return newCell;
	}

}
