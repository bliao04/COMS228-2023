package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Class testing the functionality of the Reseller class. 
 * @author Brandon Liao 
 *
 */
public class ResellerTest {
	
	@Test
	// If the number of CASUAL neighbors is >= 5, RESELLER becomes STREAMER
	void testA() {
		Town t = new Town(3,3);
		t.grid[0][0] = new Casual(t,0,0);
		t.grid[0][1] = new Casual(t,0,1);
		t.grid[0][2] = new Casual(t,0,2);
		t.grid[1][0] = new Casual(t,1,0);
		t.grid[1][1] = new Reseller(t,1,1);
		t.grid[1][2] = new Casual(t,1,2);
		t.grid[2][0] = new Casual(t,2,0);
		t.grid[2][1] = new Casual(t,2,1);
		t.grid[2][2] = new Casual(t,2,2);
        assertEquals(State.STREAMER,t.grid[1][1].next(t).who());
	}
	
	@Test
	// If the number of CASUAL neighbors is <= 5, and if the number of CASUAL neighbors is <= 3, RESELLER becomes EMPTY
	void testB() {
		Town t = new Town(2,2);
        t.grid[0][0] = new Reseller(t,0,0);
        t.grid[0][1] = new Empty(t,0,1);
        t.grid[1][0] = new Casual(t,1,0);
        t.grid[1][1] = new Casual(t,1,1);
        assertEquals(State.EMPTY, t.grid[0][0].next(t).who());
	}
	
	@Test
	// If the number of CASUAL neighbors is <= 5, and if the number of EMPTY neighbors is >= 3, RESELLER becomes EMPTY
	void testC() {
		Town t = new Town(2,2);
        t.grid[0][0] = new Reseller(t,0,0);
        t.grid[0][1] = new Empty(t,0,1);
        t.grid[1][0] = new Empty(t,1,0);
        t.grid[1][1] = new Empty(t,1,1);
        assertEquals(State.EMPTY, t.grid[0][0].next(t).who());
	}
	
	@Test
	// If no rules apply RESELLER becomes RESELLER
	void testD() {
		Town t = new Town(3,3);
		t.grid[0][0] = new Casual(t,0,0);
		t.grid[0][1] = new Casual(t,0,1);
		t.grid[0][2] = new Casual(t,0,2);
		t.grid[1][0] = new Casual(t,1,0);
		t.grid[1][1] = new Reseller(t,1,1);
		t.grid[1][2] = new Reseller(t,1,2);
		t.grid[2][0] = new Reseller(t,2,0);
		t.grid[2][1] = new Empty(t,2,1);
		t.grid[2][2] = new Empty(t,2,2);
        assertEquals(State.RESELLER,t.grid[1][1].next(t).who());
	}
	
}
