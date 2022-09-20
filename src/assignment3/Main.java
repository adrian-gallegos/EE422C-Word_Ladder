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
import java.util.*;

import java.io.*;

public class Main {
	
	// static variables and constants only here.
	/**
	 * Takes a number and returns its square root.
	 * @param x The value to square.
	 * @return The square root of the given number.
	 */
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		// initialize dict
		initialize();
		

		
		ArrayList<String> words = parse(kb);
		if(words.size()!=2) {
			return;
		}
		
		// call dfs or bfs

		ps.println(getWordLadderDFS(words.get(0), words.get(1)));
//		ps.println(getWordLadderBFS(words.get(0), words.get(1)));
//		printLadder(getWordLadderBFS(words.get(0), words.get(1)));

		
		
		
		
		
		
		
	}
	
	
	public static void initialize() {

		
	
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> res = new ArrayList<>();
		// get input string
		String input = keyboard.nextLine();


		input = input.trim();
		
		// if it's quit
		if(input.toUpperCase().equals("/QUIT")) {
			return null;
		}
		

		// slit words by white spaces, ref: https://stackoverflow.com/questions/7899525/how-to-split-a-string-by-space
		String[] splited = input.split("\\s+");
		
		if(splited.length != 2) {
			return res;
		}
		for(String each : splited) {
			res.add(each);
		}
		
		return res;
	
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		start = start.toUpperCase();
		end = end.toUpperCase();
		
		
		boolean valid = checkInputs(start, end);
		if(!valid) {
			ArrayList<String> res = new ArrayList<>();
			res.add(start);
			res.add(end);
			return res;
		}
		
		// Returned list should be ordered start to end.  Include start and end.
		Set<String> dict = makeDictionary();
		// If ladder is empty, return list with just start and end.
		Set<String> visited = new HashSet<>();
		Set<String> dead = new HashSet<>();
		LinkedList<String> sol = new LinkedList<>();
		
		if(dict.contains(start)) {
			dfs(start, end, sol, visited, dead, dict );
		}
		
		if(sol.isEmpty()) {
			sol.add(start.toLowerCase());
			sol.add(end.toLowerCase());
		}
		ArrayList<String> res = new ArrayList<>();
		for(String str : sol) {
			res.add(str.toLowerCase());
		}
			
			
		return res;
		
		
	}
	
	private static boolean checkInputs(String start, String end) {
		if(start.length() != end.length()) {
			return false;
		}
		
		int n = start.length();
		for(int i=0; i< n; i++) {
			if( !Character.isLetter(start.charAt(i)) || !Character.isLetter(end.charAt(i))) {
				return false;
			}
		}
		
		return true;
		
	}
	
	private static boolean dfs(String cur, String end,  LinkedList<String> sol, Set<String> visited, Set<String> dead, Set<String> dict) {
		if( visited.contains(cur) || dead.contains(cur)) {
			return false;
		}

		visited.add(cur);
		sol.add(cur);

		// base case
		if(cur.equals(end)) {
			return true;
		}
		
		int n = cur.length();
		char[] chars = cur.toCharArray();
		
		for(int i = 0; i < n; i++) {
			char tmp = chars[i];
			
			for(char ch= 'A'; ch <= 'Z'; ch++) {
				if(ch == tmp) {
					continue;
				}
				chars[i] = ch;
				String next = new String(chars);
				if(!dict.contains(next)) {
					continue;
				}
				boolean ret = dfs(next, end, sol, visited, dead, dict);
				if (ret) {
					return true;
				}

				
			}
			chars[i] = tmp;
		}
		visited.remove(cur);
		sol.pollLast();
		dead.add(cur);

		return false;
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		start = start.toUpperCase();
		end = end.toUpperCase();
		
		boolean valid = checkInputs(start, end);
		if(!valid) {
			ArrayList<String> res = new ArrayList<>();
			res.add(start);
			res.add(end);
			return res;
		}
		
		
	
    	Set<String> dict = makeDictionary();
    	if (!dict.contains(start) || !dict.contains(end)) {
    		ArrayList<String> notExist = new ArrayList<String>();
    		notExist.add(start.toLowerCase());
    		notExist.add(end.toLowerCase());
    		return notExist;
    	}
    	if (start.equals(end)) {
    		ArrayList<String> out = new ArrayList<String>();
    		out.add(start);
    		out.add(end);
    		return out;
    	}
    	
    	
    	LinkedList<Node> queue = new LinkedList<Node>();
    	LinkedList<String> ladder = new LinkedList<>();
    	Set<String> discovered = new HashSet<String>();
    	String[] dictWord = dict.toArray(new String[] {"0"});
    	
    	Node endNode = null;
    	
    	Node first = new Node(null, start);
    	queue.add(first);
    	discovered.add(start);
    	
    	while (!queue.isEmpty()) {
			int size = queue.size();
			for(int k=0; k < size; k++) {
				Node curr = queue.remove();
	    		if(curr.word.equals(end)) {
	    			endNode = curr;
	    			break;
	    		}

	    		int n = curr.word.length();
	    		char[] chars = curr.word.toCharArray();
	    		
	    		for(int i = 0; i < n; i++) {
	    			char tmp = chars[i];
	    			
	    			for(char ch= 'A'; ch <= 'Z'; ch++) {
	    				if(ch == tmp) {
	    					continue;
	    				}
	    				chars[i] = ch;
	    				String next = new String(chars);
	    				if(!dict.contains(next) || discovered.contains(next)) {
	    					int x =0;
	    					continue;
	    				}
	    				Node newNode = new Node(curr, next);
	    				queue.add(newNode);
	    				discovered.add(newNode.word);
	    			}
	    			
	    			chars[i]= tmp;
	    		}
			}
			if(endNode != null) {
				break;
			}
			
			
    	}
    	
    	while(endNode != null) {
			ladder.addFirst(endNode.word.toLowerCase());
			endNode = endNode.parent;
			
		}
    	
    	if(ladder.size() > 0) {
//    		System.out.println(ladder.size());
    		return new ArrayList<>(ladder);
    	}
		
		ArrayList<String> noLadderFound = new ArrayList<String>();


		noLadderFound.add(start.toLowerCase());
		noLadderFound.add(end.toLowerCase());

		return noLadderFound;
	}
    
	/*
	 * print ladder with each word in lower case
	 */
	public static void printLadder(ArrayList<String> ladder) {
		if(ladder == null || ladder.size() <2) {
			return;
		}
		if (ladder.size() == 2) {
			System.out.println("no word ladder can be found between " +

			ladder.get(0).toLowerCase() + " and " + ladder.get(ladder.size()-1).toLowerCase());

			return;
		}
		// Ladder exists
		System.out.println("a " + (ladder.size()-2) + "-rung word ladder exists between " +
							ladder.get(0).toLowerCase() + " and " + ladder.get(ladder.size()-1).toLowerCase() );
		for (int i = 0; i < ladder.size(); i++) {
			System.out.println(ladder.get(i).toLowerCase());
		}
		System.out.println();
	}


	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
