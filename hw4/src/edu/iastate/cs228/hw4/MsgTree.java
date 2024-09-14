package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * 
 * @author Brandon Liao
 *
 */
public class MsgTree {
	
	// Variable representing the character payload for the node.
	public char payloadChar;
	
	// Variable representing the left child of the node. 
	public MsgTree left;
	
	// Variable representing the right child of the node.
	public MsgTree right; 
	
	// Static index used for character iteration. 
	private static int staticCharIdx = 0;
	
	/**
	 * Constructor to build a tree structure based on the encodingString.
	 * 
	 * @param encodingString
	 */
	public MsgTree (String encodingString) {
		
		if (encodingString == null || encodingString.length() < 2) {
			
			return; 
			
		}
		
		// Initialize a stack to help in tree creation.
		Stack<MsgTree> treeStk = new Stack<>();
		int index = 0;
		
		this.payloadChar = encodingString.charAt(index++);
		treeStk.push(this);
		
		MsgTree curTree = this;
		String lastOp = "in";
		
		// Iterate through the encodingString building the tree structure.
		while (index < encodingString.length()) {
			
			MsgTree treeNode = new MsgTree(encodingString.charAt(index++));
			
			// Determine whether to set the new node as left or right child based on lastOp.
			if (lastOp.equals("in")) {
				
				curTree.left = treeNode;
				
				// Check to see if the character is '^' indicating a subtree.
				if (treeNode.payloadChar == '^') {
					
					curTree = treeStk.push(treeNode);
					lastOp = "in";
					
				} else {
					
					if (!treeStk.empty()) {
						
						curTree = treeStk.pop();
						
					}
					
					lastOp = "out";
					
				}
				
			} else {
				
				curTree.right = treeNode;
				
				// Check to see if the character is '^' indicating a subtree.
				if (treeNode.payloadChar == '^') {
					
					curTree = treeStk.push(treeNode);
					lastOp = "in";
					
				} else {
					
					if (!treeStk.empty()) {
						
						curTree = treeStk.pop();
						
					}
					
					lastOp = "out";
					
				}
				
			}
			
		}
		
	}
	
	/**
	 * Constructor that creates a tree node with a given payload character.
	 * 
	 * @param payloadChar
	 */
	public MsgTree (char payloadChar) {
		
		this.payloadChar = payloadChar;
		this.left = null;
		this.right = null;
		
	}
	
	// String storing the binary path for character codes.
	private static String bin;
	
	/**
	 * Method used to retrieve the binary code for a given character in the tree.
	 * 
	 * @param root
	 * @param c
	 * @param path
	 * @return
	 */
	private static boolean getCode(MsgTree root, char c, String path) {
		
		if (root != null) {
			
			// Check to see if the character matches the payloadChar of the current node.
			if (root.payloadChar == c) {
				// Store the binary path and return true to indicate success of code retrieval. 
				bin = path;
				return true;
				
			}
			
			// Recursively traverse the left and right subtrees to find the character.
			return getCode(root.left, c, path + "0") || getCode(root.right, c, path + "1");
			
		}
		
		return false;
		
	}
	
	/**
	 * Method used to print character codes for a given tree. 
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes (MsgTree root, String code) {
		
		for (char c : code.toCharArray()) {
			
			getCode(root, c, bin = "");
			System.out.println("   " + (c == '\n' ? "\\n" : c + " ") + "   " + bin);
			
		}
		
	}
	
	/**
	 * Method to print statistics relating to encoding and decoding.
	 * 
	 * @param encodingString
	 * @param decodingString
	 */
	private void printStats (String encodingString, String decodingString) {
		
		System.out.println("STATISTICS: ");
		System.out.println("Avg bits/char: " + "        " + (encodingString.length() / (double)decodingString.length()));
		System.out.println("Total characters: " + "        " + decodingString.length());
		System.out.println("Space saving: " + "        " + ((1d - decodingString.length() / (double)encodingString.length()) * 100));
		
	}
	
	/**
	 * Method to decode a given message.
	 * 
	 * @param code
	 * @param msg
	 */
	public void decode(MsgTree code, String msg) {
		
		// Start decoding from the root of the code tree using a StringBuilder to store the message.
		MsgTree curTree = code;
		StringBuilder strBuilder = new StringBuilder();
		
		// Iterate through the encoded message.
		for (int i = 0; i < msg.length(); i++) {
			
			// Traverse either left or right based on the bit. 
			char c = msg.charAt(i);
			curTree = (c == '0' ? curTree.left : curTree.right);
			
			if (curTree.payloadChar != '^') {
				
				// Get the code for the found character and append it to the result.
				getCode(code, curTree.payloadChar, bin = "");
				strBuilder.append(curTree.payloadChar);
				// Reset the root of the code tree for the next character decoding. 
				curTree = code;
				
			}
			
		}
		
		// Print the decoded message and statistics. 
		System.out.println(strBuilder.toString());
		printStats(msg, strBuilder.toString());
		
	}
	
}