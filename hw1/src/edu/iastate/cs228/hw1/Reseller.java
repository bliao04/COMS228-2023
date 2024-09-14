package edu.iastate.cs228.hw1;

/**
 * 
 * @author Brandon Liao
 *
 */
public class Reseller extends TownCell{
	
	private State cellState;

	public Reseller(Town p, int r, int c) {
		super(p, r, c);
		cellState = State.RESELLER;
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
		
		if(neighbors[CASUAL] >= 5) {
			newCell = new Streamer(tNew,row,col);
		} else if (neighbors[CASUAL] <= 3) {
			newCell = new Empty(tNew,row,col);
		} else if (neighbors[EMPTY] >= 3) {
			newCell = new Empty(tNew,row,col);
		} else {
			newCell = new Reseller(tNew,row,col);
		}
			
		return newCell;
	}

}
