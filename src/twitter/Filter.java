package twitter;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import java.time.Instant;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        final List<Tweet> filteredTweets = new ArrayList<Tweet>();
        for (Tweet tweet: tweets) {
        	if (tweet.getAuthor().toLowerCase().equals(username.toLowerCase())) {
        		filteredTweets.add(tweet);
        	}
        }
        
        return filteredTweets;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        final List<Tweet> filteredTweets = new ArrayList<Tweet>();
        Instant tweetTime;
        final Instant start = timespan.getStart();
        final Instant end = timespan.getEnd();
        for (Tweet tweet: tweets) {
        	tweetTime = tweet.getTimestamp();
        	if (tweetTime.equals(start) || tweetTime.isAfter(start) && tweetTime.isBefore(end) || tweetTime.equals(end)) {
        		filteredTweets.add(tweet);
        	}
        }
        
        return filteredTweets;
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
    	final List<Tweet> filtered = new ArrayList<Tweet>();
        HashSet<String> wordsInTweet = null;
        String[] tweetWords = null;
        for (Tweet tweet: tweets) {
        	wordsInTweet = new HashSet<String>();
        	tweetWords = tweet.getText().split("\\s+");
        	for (int i = 0; i < tweetWords.length; i++) {
        		wordsInTweet.add(tweetWords[i].toLowerCase());
        	}
        	
        	for (String word: words) {
        		if (wordsInTweet.contains(word.toLowerCase())) {
        			filtered.add(tweet);
        			break;
        		}
        	}
        }
        
        return filtered;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
