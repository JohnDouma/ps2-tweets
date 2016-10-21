package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * Testing strategy for writtenBy
     * Test empty list
     * Test multiple tweets with single result
     * Test multiple tweets with multiple results
     * Test multiple tweets with no matches
     */
    
    @Test
    public void testWrittenByEmptyList() {
        List<Tweet> authors = Filter.writtenBy(new ArrayList<Tweet>(), "alyssa");
        
        assertTrue(authors.size() == 0);
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testWrittenByMultipleResults() {
    	List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "alyssa");
    	
    	assertEquals(2, writtenBy.size());
    	assertTrue(writtenBy.contains(tweet1));
    	assertTrue(writtenBy.contains(tweet3));
    }
    
    @Test
    public void testWrittenByNoResults() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "john");
    	
    	assertEquals(0, writtenBy.size());
    }
    
    /*
     * Testing strategy for inTimespan
     * Test empty list
     * Test no results
     * Test multiple results
     */
    
    @Test
    public void testInTimespanEmptyList() {
    	Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
    	List<Tweet> tweets = new ArrayList<Tweet>();
    	
    	List<Tweet> filtered = Filter.inTimespan(tweets, new Timespan(testStart, testEnd));
    	
    	assertTrue(filtered.isEmpty());
    }
    
    @Test
    public void testInTimespanNoResults() {
    	Instant testStart = Instant.parse("2015-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2015-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), new Timespan(testStart, testEnd));
        
        assertTrue(inTimespan.isEmpty());
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    /*
     * Testing strategy for containing
     * Test empty word list
     * Test empty tweet list
     * Test multiple matches in one tweet
     * Test no matches
     */
    
    @Test
    public void testContainingEmptyWordList() {
    	List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), new ArrayList<String>());
    	assertTrue(containing.isEmpty());
    }
    
    @Test
    public void testContainingEmptyTweetList() {
    	List<Tweet> containing = Filter.containing(new ArrayList<Tweet>(), Arrays.asList("this", "should", "be", "empty"));
    	assertTrue(containing.isEmpty());
    }
    
    @Test
    public void testContainingMultipleMatchesOneTweet() {
    	List<Tweet> containing = Filter.containing(Arrays.asList(tweet1), Arrays.asList("talk", "so", "much?"));
    	
    	assertEquals(1, containing.size());
    	assertTrue(containing.contains(tweet1));
    }
    
    @Test 
    public void testContainingNoMatches() {
    	List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("no", "matches"));
    	
    	assertTrue(containing.isEmpty());
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
