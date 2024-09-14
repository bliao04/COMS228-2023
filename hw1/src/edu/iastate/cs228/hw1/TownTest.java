package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * Class testing the functionality of the Town class
 * @author Brandon Liao 
 *
 */
public class TownTest {
	
	@Test
	void testA() {
		Town t = new Town(5,6);
		assertEquals(t.getLength(),5);
		assertEquals(t.getWidth(),6);
		t.randomInit(999);
		System.out.println(t);
	}
	
	@Test
	void testFile() throws FileNotFoundException {
		Town tFile = new Town("/Users/bliao/School/CS228/workspace/hw1/grid.txt");
		assertEquals(State.STREAMER,tFile.grid[0][0].who());
		assertEquals(State.OUTAGE,tFile.grid[0][1].who());
		assertEquals(State.EMPTY,tFile.grid[1][0].who());
		assertEquals(State.RESELLER,tFile.grid[1][1].who());
	}
}
