package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author Brandon Liao
 *
 */

/**
 * 
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts);
		super.algorithm = "selection sort";
	}	

	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 */
	@Override 
	public void sort()
	{
		
		for (int i = 0; i < super.points.length - 1; i++) {
			int lowIndex = i;
			
			for (int j = i + 1; j < super.points.length; j++) {
				if (pointComparator.compare(this.points[j], this.points[lowIndex]) < 0) {
					lowIndex = j;
				}
			}
			
			super.swap(i, lowIndex);
		}
		
	}	
	
}
