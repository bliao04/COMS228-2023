package edu.iastate.cs228.hw1;

/**
 * 
 * @author Brandon Liao
 *
 */
public class Empty extends TownCell {
	
	private State cellState;

	public Empty(Town p, int r, int c) {
		super(p, r, c);
		cellState = State.EMPTY;
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
		
		if(neighbors[EMPTY] + neighbors[OUTAGE] <= 1) {
			newCell = new Reseller(tNew,row,col);
		} else {
			newCell = new Casual(tNew,row,col);
		}
		
		return newCell;
	}
	

}
