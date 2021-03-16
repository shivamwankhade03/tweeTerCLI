package com.tweetApp.dao;

import com.tweetApp.domain.TweetDetails;

import java.util.List;

public interface TweetDetailsDao {

    public List<TweetDetails> getAllTweets();
    public List<TweetDetails> viewTweetById(Integer userId) ;
    public boolean insertTweet(Integer userId, String tweet);

}
