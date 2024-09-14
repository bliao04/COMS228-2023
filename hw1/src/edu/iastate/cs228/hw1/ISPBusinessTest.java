package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Class testing the functionality of the ISPBusiness class
 * @author Brandon Liao
 *
 */
public class ISPBusinessTest {
	
	@Test
	void testUPlain() {
		Town t = new Town(2,2);
		t.grid[0][0] = new Casual(t,0,0);
        t.grid[0][1] = new Streamer(t,0,1);
        t.grid[1][0] = new Empty(t,1,0);
        t.grid[1][1] = new Outage(t,1,1);
        Town tNew = ISPBusiness.updatePlain(t);
        assertEquals(State.STREAMER, tNew.grid[0][0].who());
        assertEquals(State.EMPTY, tNew.grid[0][1].who());
        assertEquals(State.RESELLER, tNew.grid[1][0].who());
        assertEquals(State.EMPTY, tNew.grid[1][1].who());
	}
	
	@Test
	void testGProfit() {
		Town t = new Town(2,2);
		t.grid[0][0] = new Casual(t,0,0);
        t.grid[0][1] = new Streamer(t,0,1);
        t.grid[1][0] = new Empty(t,1,0);
        t.grid[1][1] = new Casual(t,1,1);
        assertEquals(2, ISPBusiness.getProfit(t));
	}
	
}
