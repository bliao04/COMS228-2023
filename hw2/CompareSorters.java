package edu.iastate.cs228.hw2;

/**
 *  
 * @author Brandon Liao
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		
		System.out.println("Would you like to (1) generate an array of random points OR (2) generate an array from file input OR (3) exit the program? ");
		Scanner scnrIn = new Scanner(System.in);
		PointScanner[] scanners = new PointScanner[4]; 
		
		while (true) {
			
			int userChoice = scnrIn.nextInt();
			Random rand = new Random();
			
			if(userChoice == 1) {
				System.out.println("How many random numbers would you like to generate? ");
				int numRands = scnrIn.nextInt();
				Point[] pointArr = generateRandomPoints(numRands, rand);
				scanners[0] = new PointScanner(pointArr, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(pointArr, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(pointArr, Algorithm.MergeSort);
				scanners[3] = new PointScanner(pointArr, Algorithm.QuickSort);
			} else if (userChoice == 2) {
				System.out.println("Input the file name: ");
				String fileName = scnrIn.next();
				scanners[0] = new PointScanner(fileName, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(fileName, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(fileName, Algorithm.MergeSort);
				scanners[3] = new PointScanner(fileName, Algorithm.QuickSort);	
			} else {
				System.exit(0);
			}
			
			for(int i = 0; i < scanners.length; i++) {
				scanners[i].scan();
			}
			
			System.out.println("-----------------------------------------------------");
			System.out.printf("%-14s %-8s %-4s \n", "algorithm", "size", "time (ns)");
			System.out.println("-----------------------------------------------------");
			
			for(int i = 0; i < scanners.length; i++) {
				System.out.println(scanners[i].stats());
			}
			
			System.out.println("-----------------------------------------------------");
			
		}
		
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		Point[] points = new Point[numPts];
		int x;
		int y;
		
		for(int i = 0; i < numPts; i++)  {
			x = rand.nextInt(101) - 50;
			y = rand.nextInt(101)- 50;
			
			Point randP = new Point(x,y);
			points[i] = randP;
		}
		return(points);
	}
	
}
