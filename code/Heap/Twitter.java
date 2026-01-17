package Heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Twitter {

    private Map<Integer, List<Tweet>> userTweets = new HashMap<>();

    private Map<Integer, List<Integer>> followings = new HashMap<>();


    public Twitter() {
        for (int i = 1; i <= 500; i++) {
            userTweets.put(i, 
                    new ArrayList<>()
            );
            followings.put(i, new ArrayList<>());
        }
    }
    
    public void postTweet(int userId, int tweetId) {
        userTweets.get(userId).add(new Tweet(userId, tweetId, System.nanoTime()));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> resultFeed = new ArrayList<>();

        PriorityQueue<Tweet> feed = new PriorityQueue<>((a, b) -> Long.compare(a.creationTime, b.creationTime));

        for (Tweet tweet : userTweets.get(userId)) {
            if (feed.size() >= 10) {
                feed.poll();
            }
            feed.add(tweet);
        }

        for (int following : followings.get(userId)) {
            for (Tweet tweet : userTweets.get(following)) {
                if (feed.size() > 10) {
                    feed.poll();
                }
                feed.add(tweet);
            }
        }
        
        while (!feed.isEmpty() && resultFeed.size() < 10) {
            System.out.println(feed.peek().creationTime);
            resultFeed.add(feed.poll().tweetId);
        }

        return resultFeed.reversed();
    }
    
    public void follow(int followerId, int followeeId) {
        if (followings.get(followerId).contains((Object) followeeId)) {
            return;
        }
        followings.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (followings.get(followerId).contains((Object) followeeId) == false) {
            return;
        }
        followings.get(followerId).remove((Object)followeeId);
    }
    
    static class Tweet {
        
        private int userId;
        private int tweetId;
        private long creationTime;

        Tweet(int userId, int tweetId, long creationTime) {
            this.userId = userId;
            this.tweetId = tweetId;
            this.creationTime = creationTime;
        }
    }

}
