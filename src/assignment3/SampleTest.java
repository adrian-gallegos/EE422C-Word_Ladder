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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

//import scoreannotation.Score; // Comment out this line if not using grading script
//import testutils.NoExitSecurityManager;

/**
 * This is the sample test cases for students
 *
 */
public class SampleTest {
    private static Set<String> dict;
    private static ByteArrayOutputStream outContent;

    private static final int SHORT_TIMEOUT = 300; // ms
    private static final int SEARCH_TIMEOUT = 30000; // ms

    private SecurityManager initialSecurityManager;

    // @Rule // Comment this rule and the next line out when debugging to remove timeouts
    // public Timeout globalTimeout = new Timeout(SEARCH_TIMEOUT);

    @Before // this method is run before each test
    public void setUp() {
        Main.initialize();
        dict = Main.makeDictionary();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        initialSecurityManager = System.getSecurityManager();
//		System.setSecurityManager(new NoExitSecurityManager());
    }

    @After
	public void cleanup() {
		System.setSecurityManager(initialSecurityManager);
	}

    private boolean verifyLadder(ArrayList<String> ladder, String start, String end) {
        String prev = null;
        if (ladder == null)
            return true;
        for (String word : ladder) {
            if (!dict.contains(word.toUpperCase()) && !dict.contains(word.toLowerCase())) {
                return false;
            }
            if (prev != null && !differByOne(prev, word))
                return false;
            prev = word;
        }
        return ladder.size() > 0
                && ladder.get(0).toLowerCase().equals(start)
                && ladder.get(ladder.size() - 1).toLowerCase().equals(end);
    }

    private static boolean differByOne(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;

        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i) && diff++ > 1) {
                return false;
            }
        }

        return true;
    }
    //Test for parse method
	@Test
	// @Score(1)  // Comment out this line if not using grading script
	public void testParse() {
		String input = "hello world";
		Scanner scan = new Scanner(input);
		ArrayList<String> expected = new ArrayList<>();
		expected.add("hello");
		expected.add("world");
		ArrayList<String> res = Main.parse(scan);
		assertEquals(expected.get(0), res.get(0).toLowerCase());
		assertEquals(expected.get(1), res.get(1).toLowerCase());
	}

    // @Ignore
	@Test(timeout = SHORT_TIMEOUT)
//	@Score(1) // Comment out this line if not using grading script
	public void testParseQuit() {
		String quit = "/quit";
		Scanner scan = new Scanner(quit);
		assertEquals(null, Main.parse(scan));
	}
    /**
     * Has Word Ladder
     **/
	@Test(timeout = SHORT_TIMEOUT)
//	@Score(1)
    public void testBFS1() {
        ArrayList<String> res = Main.getWordLadderBFS("hello", "cells");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "hello", "cells"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        assertTrue(res.size() < 6);
    }

    @Test
//	@Score(1)
    public void testDFS1() {
        ArrayList<String> res = Main.getWordLadderDFS("hello", "cells");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "hello", "cells"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }

    /**
     * No Word Ladder
     **/
    @Test
//	@Score(1)
    public void testBFS2() {
        ArrayList<String> res = Main.getWordLadderBFS("aldol", "drawl");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test
//	@Score(1)
    public void testDFS2() {
        ArrayList<String> res = Main.getWordLadderDFS("aldol", "drawl");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test
//	@Score(1)
    public void testPrintLadder() {
        ArrayList<String> res = Main.getWordLadderBFS("twixt", "hakus");
//        System.out.println(res.size());
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between twixt and hakus", str);
    }
    


    // Test for same word inputs
    @Test
//	@Score(1)
    public void testSameWord() {
    	// Test DFS
    	ArrayList<String> res = Main.getWordLadderDFS("hello", "hello");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        
        // Test BFS
        ArrayList<String> out = Main.getWordLadderBFS("hello", "cells");
        if (out != null) {
            HashSet<String> set = new HashSet<String>(out);
            assertEquals(set.size(), out.size());
        }
        assertFalse(out == null || out.size() == 0 || out.size() == 2);
    }
    
    // Test for words not in the dictionary
    @Test
//	@Score(1)
    public void notInDictionaryDFS() {
    	// Test DFS
    	ArrayList<String> res = Main.getWordLadderDFS("hellx", "xello");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }
    
    // Test for words not in the dictionary
    @Test
//	@Score(1)
    public void notInDictionaryBFS() {
        // Test BFS
        ArrayList<String> out = Main.getWordLadderBFS("hellx", "xello");
        if (out != null) {
            HashSet<String> set = new HashSet<String>(out);
            assertEquals(set.size(), out.size());
        }
        assertTrue(out == null || out.size() == 0 || out.size() == 2);
    }
    
 // Test for time out error
    @Test(timeout = SHORT_TIMEOUT)
//	@Score(1)
    public void timeOutErrorDFS() {
    	// Test DFS
    	ArrayList<String> res = Main.getWordLadderDFS("hello", "cells");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "hello", "cells"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    
    // Test for time out error
    @Test(timeout = SHORT_TIMEOUT)
//	@Score(1)
    public void timeOutErrorBFS() {
        // Test BFS
        ArrayList<String> out = Main.getWordLadderBFS("hello", "cells");
        if (out != null) {
            HashSet<String> set = new HashSet<String>(out);
            assertEquals(set.size(), out.size());
        }
        assertTrue(verifyLadder(out, "hello", "cells"));
        assertFalse(out == null || out.size() == 0 || out.size() == 2);
        assertTrue(out.size() < 6);
    }

    @Test
    public void testExistedInput() {
    	String start = "hakus";
    	String end = "dames";
    	// test BFS
        ArrayList<String> bfs_res = Main.getWordLadderBFS(start, end);
        if (bfs_res != null) {
            HashSet<String> set = new HashSet<String>(bfs_res);
            assertEquals(set.size(), bfs_res.size());
        }
        assertFalse(bfs_res == null || bfs_res.size() == 0 || bfs_res.size() == 2);
        
        // test DFS
        ArrayList<String> dfs_res = Main.getWordLadderDFS(start, end);
        if (dfs_res != null) {
            HashSet<String> set = new HashSet<String>(dfs_res);
            assertEquals(set.size(), dfs_res.size());
        }
        assertFalse(dfs_res == null || dfs_res.size() == 0 || dfs_res.size() == 2);
        
        // compare size
        assertTrue(bfs_res.size() <= dfs_res.size());
    	

    }
    
    
    @Test
    public void testExsitedInputCases() {
    	String start = "hakus";
    	String end = "dames";
    	// test BFS
        ArrayList<String> bfs_res = Main.getWordLadderBFS(start, end);
        ArrayList<String> bfs_res1 = Main.getWordLadderBFS("HaKus", "dAmes");

        if (bfs_res != null) {
            HashSet<String> set = new HashSet<String>(bfs_res);
            assertEquals(set.size(), bfs_res.size());
            
            set = new HashSet<String>(bfs_res1);
            assertEquals(set.size(), bfs_res1.size());
        }
        assertFalse(bfs_res == null || bfs_res.size() == 0 || bfs_res.size() == 2);
        assertFalse(bfs_res1 == null || bfs_res1.size() == 0 || bfs_res1.size() == 2);
        assertEquals(bfs_res, bfs_res1);
        
        // test DFS
        ArrayList<String> dfs_res = Main.getWordLadderDFS(start, end);
        ArrayList<String> dfs_res1 = Main.getWordLadderDFS("HaKus", "dAmes");
        if (dfs_res != null) {
            HashSet<String> set = new HashSet<String>(dfs_res);
            assertEquals(set.size(), dfs_res.size());
            
            set = new HashSet<String>(dfs_res1);
            assertEquals(set.size(), dfs_res1.size());
            
            
        }
        assertFalse(dfs_res == null || dfs_res.size() == 0 || dfs_res.size() == 2);
        
        // compare size
        assertTrue(bfs_res.size() <= dfs_res.size());
    	
    }
    
    
	@Test
	// @Score(1)  // Comment out this line if not using grading script
	public void testParseSpaces() {
		String[] inputs = new String[] {"hello world", "	hello             world    "};
		for(String input: inputs) {
			Scanner scan = new Scanner(input);
			ArrayList<String> expected = new ArrayList<>();
			expected.add("hello");
			expected.add("world");
			ArrayList<String> res = Main.parse(scan);
			assertEquals(expected.get(0), res.get(0).toLowerCase());
			assertEquals(expected.get(1), res.get(1).toLowerCase());
		}

	}
	
	
    @Test
//	@Score(1)
    public void testNonExist() {
        ArrayList<String> res = Main.getWordLadderBFS("twixt", "hakus");
//        System.out.println(res.size());
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between twixt and hakus", str);
    }
    
    
    @Test
//	@Score(1)
    public void testNoSameLength() {
    	//test BFS
        ArrayList<String> res = Main.getWordLadderBFS("dog", "hello");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between dog and hello", str);
        
    	//test DFS
        res = Main.getWordLadderDFS("dog", "hello");
        outContent.reset();
        Main.printLadder(res);
        str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between dog and hello", str);
    }
    
    @Test
//	@Score(1)
    public void testContainsNonLetter() {
    	//test BFS
        ArrayList<String> res = Main.getWordLadderBFS("world", "he1lo");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between world and he1lo", str);
        
    	//test DFS
        res = Main.getWordLadderDFS("world", "he1lo");
        outContent.reset();
        Main.printLadder(res);
        str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between world and he1lo", str);
    }
    


}
