package edu.iastate.cs228.hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author Brandon Liao
 *
 */
public class Main {
	
	public static void main (String[] args) throws IOException {
		
		// Prompt the user for filename.
		System.out.println("Please enter filename to decode: ");
		
		// Read user input using scanner then close the scanner.
		Scanner scnr = new Scanner(System.in);
		String file = scnr.nextLine();
		scnr.close();
		
		// Read through the entire content of the file into a string and then remove whitespaces.
		String varContent = new String (Files.readAllBytes(Paths.get(file))).trim();
		int curPos = varContent.lastIndexOf('\n');
		String pattern = varContent.substring(0, curPos);
		String bin = varContent.substring(curPos).trim();
		Set<Character> c = new HashSet<>();
		
		// Iterate through the pattern string collecting unique characters excluding '^'.
		for (char ch : pattern.toCharArray()) {
			
			if (ch != '^') {
				
				c.add(ch);
				
			}
			
		}
		
		// Convert the set of characters to a string of characters.
		String charList = c.stream().map(String::valueOf).collect(Collectors.joining());
		
		// Create a tree with the given pattern.
		MsgTree root = new MsgTree(pattern);
		
		// Print codes for characters.
		MsgTree.printCodes(root, charList);
		root.decode(root, bin);
		
	}

}
