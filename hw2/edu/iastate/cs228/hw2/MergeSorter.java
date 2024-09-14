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
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(this.points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		if(pts.length <= 1) 
			return;
		int med = pts.length/2;
		Point[] leftPar = new Point[med];
		Point[] rightPar = new Point[pts.length - med];
		
		for(int i = 0; i < med; i++) {
			leftPar[i] = pts[i];
		}
		
		int rightParIndex = 0;
		for(int j = med; j < pts.length; j++) {
			rightPar[rightParIndex] = pts[j];
			rightParIndex++;
		}
		
		mergeSortRec(leftPar);
		mergeSortRec(rightPar);
		
		Point[] tempArr = mergeParts(leftPar, rightPar);
		
		for(int i = 0;i < tempArr.length; i++) {
			pts[i] = tempArr[i];
		}
		
 	}

	
	/*
	 * Private method used to merge two point arrays together within mergeSortRec
	 * @param leftPar The left partition of the original array
	 * @param rightPar The right partition of the original array
	 */
	private Point[] mergeParts(Point[] leftPar, Point[] rightPar) {
		
		Point[] sortedArr = new Point[leftPar.length + rightPar.length]; 
		int leftP = 0;
		int rightP = 0;
		int curIteration = 0;
		
		while(leftP < leftPar.length && rightP < rightPar.length) {
			if(pointComparator.compare(leftPar[leftP], rightPar[rightP]) <= 0) {
				sortedArr[curIteration++] = leftPar[leftP]; 
				leftP++;
			} else {
				sortedArr[curIteration++] = rightPar[rightP];
				rightP++;
			}
		}
		
		if (rightP >= rightPar.length)
			while(leftP < leftPar.length) {
				sortedArr[curIteration++] = leftPar[leftP];
				leftP++;
			}
		else 
			while(rightP < rightPar.length) {
				sortedArr[curIteration++] = rightPar[rightP];
				rightP++;
			}
		
		return sortedArr;
		
	}

}
