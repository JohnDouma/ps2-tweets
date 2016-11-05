package twitter;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.time.Instant;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {
	
	private static final Pattern authorRegexp = Pattern.compile("[^A-Za-z-_0-9]@[A-Za-z-_0-9]+");
	private static final Pattern authorExtractionPattern = Pattern.compile("[A-Za-z-_0-9]+");

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	if (tweets.size() == 0) {
    	    Instant now = Instant.now();
    		return new Timespan(now, now);
    	}
    	
    	Instant min = null;
    	Instant max = null; 
    	Instant tmp = null;
    	for (Tweet tweet: tweets) {
    		tmp = tweet.getTimestamp();
    		if (min == null || tmp.isBefore(min)) {
    			min = tmp;
    		}
    		if (max == null || tmp.isAfter(max)) {
    			max = tmp;
    		}
    	}
    	
        return new Timespan(min, max);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> usernames = new HashSet<String>();
        Matcher nameMatcher = null;
        Matcher nameExtractor = null;
        for (Tweet tweet: tweets) {
        	 nameMatcher = authorRegexp.matcher(tweet.getText());
        	 while (nameMatcher.find()) {
        	     nameExtractor = authorExtractionPattern.matcher(nameMatcher.group());
        	     nameExtractor.find();
        		 usernames.add(nameExtractor.group().trim().toLowerCase());
        	 }
        }
        
        return usernames;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
