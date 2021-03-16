package com.tweetApp.service.impl;

import com.tweetApp.dao.TweetDetailsDao;
import com.tweetApp.dao.UserDetailsDao;
import com.tweetApp.dao.impl.TweetDetailsDaoImpl;
import com.tweetApp.dao.impl.UserDetailsDaoImpl;
import com.tweetApp.domain.TweetDetails;
import com.tweetApp.domain.UserDetails;
import com.tweetApp.service.TweetsDetailsService;

import java.util.List;
import java.util.Scanner;

public class TweetDetailServiceImpl implements TweetsDetailsService {
    TweetDetailsDao tweetDetailsdao = new TweetDetailsDaoImpl();
    UserDetailsDao userDetailsDao = new UserDetailsDaoImpl();
    public void getAllTweets() {
        List<TweetDetails> tweetsList = tweetDetailsdao.getAllTweets();
        List<UserDetails> userList = userDetailsDao.getAllUserDetailsList();

        if(tweetsList != null ){
            for (int i=0 ;i<tweetsList.size();i++){
                for(int j=0;j<userList.size();j++){
                    if(tweetsList.get(i).getUser_id_fk().equals(userList.get(j).getUserId())){
                        System.out.println("  "+tweetsList.get(i).getTweets() + "   ..........tweeted by "+userList.get(j).getEmail());
                    }

                }
            }
        }else{
            System.err.println("No Tweets are available ");
        }

    }

    public void viewMyTweets(Integer userId) {
        List<TweetDetails> tweetList = tweetDetailsdao.viewTweetById(userId);

        if(tweetList!=null){

            for (int i=0 ;i<tweetList.size(); i++) {
                System.out.println("Tweet "+(i+1)+" : "+tweetList.get(i).getTweets());
            }
        }else{
            System.out.println("\n No Tweets posted yet ");
        }

        System.out.println("\n------------------------------------------- ");

    }

    public void writeTweet(Integer userId) {

        Scanner sc = new Scanner(System.in);
        String tweet = sc.nextLine();

        if(tweet!=null && !tweet.isEmpty()){

            if( tweetDetailsdao.insertTweet(userId,tweet)){

                System.out.println("------------------------------------------- ");
                System.out.println("            Tweet posted successfully ");
                System.out.println("------------------------------------------- ");

            }else{
                System.out.println("------------------------------------------- ");
                System.err.println("               Tweet Failed !!!!");
                System.out.println("------------------------------------------- ");

            }
        }

    }
}
