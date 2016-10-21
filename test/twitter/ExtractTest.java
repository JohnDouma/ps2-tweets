package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet4 = new Tweet(4, "bbitdiddle", "rivest talk in 30 minutes #hype", d1);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * Testing strategy for getTimespan
     * Test with list of length 1 and list with length greater than 1
     * Test with time stamps in order and out of order
     * Test with multiple tweets with same time stamp
     */
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanTwoTweetsOutOfOrder() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanOneTweet() {
    	Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
    	
    	assertEquals(d1, timespan.getStart());
    	assertEquals(d1, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanThreeTweetsSameTimestamp() {
    	Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet3, tweet4));
    	
    	assertEquals(d1, timespan.getStart());
    	assertEquals(d1, timespan.getEnd());
    }
    
    /*
     * Testing strategy for testGetMentionedUsersNoMention
     * Test with no authors mentioned
     * Test with multiple authors in the same tweet.
     * Test with two instances of same author
     * Test with two instances of same author but different case, e.g @Bitdiddle, @bitdiddle
     * Test that email addresses do not appear
     * 
     */
    
    private static final Tweet userjohn = new Tweet(5, "alyssa", "is it reasonable to talk about @john- so much?", d1);
    private static final Tweet userJohn = new Tweet(6, "bbitdiddle", "@John- talk in 30 minutes #hype", d1);
    private static final Tweet multipleUsers = new Tweet(7, "alyssa", "is it @reasonable to talk about @rivest so much?", d1);
    private static final Tweet emailAddress = new Tweet(8, "bbitdiddle", "rivest@mit.edu @tal_k in 30 minutes #hype", d1);
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersMultipleUsersSameTweet() {
        Set<String> users = Extract.getMentionedUsers(Arrays.asList(multipleUsers));	
        
        assertTrue(users.size() == 2);
        assertTrue(users.contains("reasonable"));
        assertTrue(users.contains("rivest"));
    }
    
    @Test
    public void testGetMentionedUsersSameUserDifferentCase() {
    	Set<String> users = Extract.getMentionedUsers(Arrays.asList(userjohn, userJohn));
    	
    	assertTrue(users.size() == 1);
        assertTrue(users.contains("john-"));
    }
    
    @Test
    public void testGetMentionedUsersFilterEmails() {
        Set<String> users = Extract.getMentionedUsers(Arrays.asList(emailAddress));
    	
    	assertTrue(users.size() == 1);
        assertTrue(users.contains("tal_k"));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
