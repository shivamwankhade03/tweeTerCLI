package com.tweetApp.domain;

public class TweetDetails {

    private Integer tweetId;
    private Integer user_id_fk;
    private String tweets;

    public Integer getTweetId() {
        return tweetId;
    }

    public void setTweetId(Integer tweetId) {
        this.tweetId = tweetId;
    }

    public Integer getUser_id_fk() {
        return user_id_fk;
    }

    public void setUser_id_fk(Integer user_id_fk) {
        this.user_id_fk = user_id_fk;
    }

    public String getTweets() {
        return tweets;
    }

    public void setTweets(String tweets) {
        this.tweets = tweets;
    }

    @Override
    public String toString() {
        return "TweetsDetails{" +
                "tweetId=" + tweetId +
                ", user_id_fk=" + user_id_fk +
                ", tweets='" + tweets + '\'' +
                '}';
    }
}
