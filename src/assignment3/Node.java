/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Yue Cheng
 * YC26939
 * 17360
 * Adrian Gallegos
 * ag76424
 * 17360
 * Slip days used: 0
 * Git URL: https://github.com/EE422C/sp-22-assignment-3-sp22-pr3-pair-3
 * Spring 2022
 */
package assignment3;

public class Node {

	public String word;	// the word to be stored
	public Node parent;	// the parent of the current word
	public int length;	// the length of the ladder from start
	
	public Node(Node parent, String word) {
		this.word = word;
		this.parent = parent;
		if (parent == null) {
			length = 0;
		} else {
			length = parent.length + 1;
		}
	}
}
