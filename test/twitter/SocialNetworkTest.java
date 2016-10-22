package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "is it reasonable to talk about rivest so much?", d1);

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /*
     * Test strategy for guessFollowsGraph Test for empty list of tweets Test
     * with author that follows no one Test with author that mentions himself
     */

    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());

        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsAuthorFollowsNoOne() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));

        assertTrue(followsGraph.containsKey(tweet1.getAuthor()));
        assertTrue(followsGraph.get(tweet1.getAuthor()).isEmpty());
    }

    @Test
    public void testGuessFollowsAuthorFollowsSelf() {
        final Tweet tweet = new Tweet(1, "alyssa", "is it reasonable to talk about @Alyssa so much?", d1);
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet));

        assertTrue(followsGraph.containsKey(tweet1.getAuthor()));
        assertTrue(followsGraph.get(tweet1.getAuthor()).isEmpty());
    }

    /*
     * Testing strategy for influencers Test with empty followsGraph Test with
     * followsGraphs with no edges Test with clear winner for most influential
     */

    private final static Map<String, Set<String>> discreteGraph = new HashMap<String, Set<String>>() {
        {
            put("A", new HashSet<String>());
            put("B", new HashSet<String>());
        }
    };

    private final static Set<String> follows1 = new HashSet<String>() {
        {
            add("Alice");
            add("Bob");
            add("Charles");
        }
    };

    private final static Set<String> follows2 = new HashSet<String>() {
        {
            add("Alice");
            add("Bob");
        }
    };

    private final static Set<String> follows3 = new HashSet<String>() {
        {
            add("Alice");
        }
    };

    private final static Map<String, Set<String>> winnerGraph = new HashMap<String, Set<String>>() {
        {
            put("A", follows1);
            put("B", follows2);
            put("C", follows3);
        }
    };
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersDiscreteGraph() {
        final List<String> influencers = SocialNetwork.influencers(discreteGraph);

        assertEquals(2, influencers.size());
    }
    
    @Test
    public void testInfluencersClearWinner() {
        List<String> influencers = SocialNetwork.influencers(winnerGraph);
        
        assertEquals(6, influencers.size());
        assertEquals("alice", influencers.get(0).toLowerCase());
        assertEquals("bob", influencers.get(1).toLowerCase());
        assertEquals("charles", influencers.get(2).toLowerCase());
    }
    
    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version. DO
     * NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

    /*
     * Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
