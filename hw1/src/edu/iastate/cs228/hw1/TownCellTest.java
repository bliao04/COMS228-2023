package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Class testing the functionality of the TownCell class
 * @author Brandon Liao
 *
 */
public class TownCellTest {
	
	@Test
	void testGState() {
		Town t = new Town(3,3);
		t.grid[0][0] = new Reseller(t,0,0);
		t.grid[0][1] = new Empty(t,0,1);
		t.grid[0][2] = new Casual(t,0,2);
		t.grid[1][0] = new Outage(t,1,0);
		t.grid[1][1] = new Streamer(t,1,1);
		t.grid[1][2] = new Reseller(t,1,2);
		t.grid[2][0] = new Empty(t,2,0);
		t.grid[2][1] = new Casual(t,2,1);
		t.grid[2][2] = new Outage(t,2,2);
        assertEquals(0,t.grid[1][1].getState(-1, -1));
        assertEquals(3, t.grid[1][1].getState(1, 1));
	}
	
	@Test
	void testCensus() {
		Town t = new Town(3,3);
		t.grid[0][0] = new Reseller(t,0,0);
		t.grid[0][1] = new Empty(t,0,1);
		t.grid[0][2] = new Casual(t,0,2);
		t.grid[1][0] = new Outage(t,1,0);
		t.grid[1][1] = new Streamer(t,1,1);
		t.grid[1][2] = new Reseller(t,1,2);
		t.grid[2][0] = new Empty(t,2,0);
		t.grid[2][1] = new Casual(t,2,1);
		t.grid[2][2] = new Outage(t,2,2);
		int neighbors[] = new int[5];
		t.grid[1][1].census(neighbors);
		assertEquals(2, neighbors[0]);
		assertEquals(2, neighbors[1]);
		assertEquals(2, neighbors[2]);
		assertEquals(2, neighbors[3]);
		assertEquals(0, neighbors[4]);
	}

}
