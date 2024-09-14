package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

/**
 * Class testing the functionality of the Casual class. 
 * @author Brandon Liao 
 *
 */
class CasualTest {

	@Test
	// If the number of EMPTY and OUTAGE neighbors is <= 1, CASUAL will become RESELLER
	void testA() {
        Town t = new Town(2,2);
        t.grid[0][0] = new Casual(t,0,0);
        t.grid[0][1] = new Streamer(t,0,1);
        t.grid[1][0] = new Casual(t,1,0);
        t.grid[1][1] = new Casual(t,1,1);
        assertEquals(State.RESELLER, t.grid[0][0].next(t).who());
	}
	
	@Test
	// If the number of EMPTY and OUTAGE neighbors is > 1, and if the number of RESELLERS is > 0, CASUAL will become OUTAGE
	void testB() {	
		Town t = new Town(2,2);
		t.grid[0][0] = new Casual(t,0,0);
        t.grid[0][1] = new Reseller(t,0,1);
        t.grid[1][0] = new Empty(t,1,0);
        t.grid[1][1] = new Empty(t,1,1);
        assertEquals(State.OUTAGE,t.grid[0][0].next(t).who());
	}
	
	@Test 
	// If the amount of EMPTY and/or OUTAGE neighbors is > 1, and there are 0 RESELLERS, if there are >= 5 CASUAL neighbors CASUAL will become STREAMER
	void testC() {
		Town t = new Town(3,3);
		t.grid[0][0] = new Empty(t,0,0);
		t.grid[0][1] = new Empty(t,0,1);
		t.grid[0][2] = new Outage(t,0,2);
		t.grid[1][0] = new Casual(t,1,0);
		t.grid[1][1] = new Casual(t,1,1);
		t.grid[1][2] = new Casual(t,1,2);
		t.grid[2][0] = new Casual(t,2,0);
		t.grid[2][1] = new Casual(t,2,1);
		t.grid[2][2] = new Casual(t,2,2);
        assertEquals(State.STREAMER,t.grid[1][1].next(t).who());
	}
	
	@Test
	// If the amount of EMPTY and OUTGAGE is > 1, and there are 0 RESELLERS, and there are < 5 CASUALS as neighbors, if there is more than 0 STREAMERS, CASUAL will become STREAMER
	void testD() {
		 Town t = new Town(2,2);
	     t.grid[0][0] = new Casual(t,0,0);
	     t.grid[0][1] = new Streamer(t,0,1);
	     t.grid[1][0] = new Empty(t,1,0);
	     t.grid[1][1] = new Empty(t,1,1);
	     assertEquals(State.STREAMER, t.grid[0][0].next(t).who());
	}
	
	@Test
	// If no rules apply, CASUAL will become CASUAL
	void testE() {
		Town t = new Town(3,3);
		t.grid[0][0] = new Empty(t,0,0);
		t.grid[0][1] = new Empty(t,0,1);
		t.grid[0][2] = new Empty(t,0,2);
		t.grid[1][0] = new Empty(t,1,0);
		t.grid[1][1] = new Casual(t,1,1);
		t.grid[1][2] = new Empty(t,1,2);
		t.grid[2][0] = new Empty(t,2,0);
		t.grid[2][1] = new Empty(t,2,1);
		t.grid[2][2] = new Empty(t,2,2);
        assertEquals(State.CASUAL,t.grid[1][1].next(t).who());
	}
	
 } 
