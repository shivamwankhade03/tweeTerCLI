package com.tweetApp.dao.impl;

import com.tweetApp.config.DBConfiguration;
import com.tweetApp.dao.TweetDetailsDao;
import com.tweetApp.domain.TweetDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TweetDetailsDaoImpl implements TweetDetailsDao {
    private static Statement stmt;
    private static ResultSet results;
    private static PreparedStatement preparedStatement;

    public List<TweetDetails> getAllTweets(){
        String sql_select = "Select * From tweets";
        try (Connection conn = DBConfiguration.createNewDBconnection()) {

            preparedStatement = conn.prepareStatement(sql_select);
            results = preparedStatement.executeQuery();

            List<TweetDetails> tweetsList = new ArrayList<TweetDetails>();
            while (results.next()) {
                TweetDetails tweetObject = new TweetDetails();

                tweetObject.setTweetId(Integer.valueOf(results.getString("tweet_id")));
                tweetObject.setUser_id_fk(Integer.valueOf(results.getString("user_id_fk")));
                tweetObject.setTweets(results.getString("tweets"));

                tweetsList.add(tweetObject);
            }
            if(tweetsList.size() ==0 ){
                System.out.println("No tweets are available ");
            }
            conn.close();
            return tweetsList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public List<TweetDetails> viewTweetById(Integer userId) {
        String sql_select = "Select * From tweets where user_id_fk = '"+userId+"';";
        try (Connection conn = DBConfiguration.createNewDBconnection()) {

            preparedStatement = conn.prepareStatement(sql_select);
            results = preparedStatement.executeQuery();

            List<TweetDetails> tweetList = new ArrayList<TweetDetails>();
            while (results.next()) {
                TweetDetails tweetObject = new TweetDetails();

                tweetObject.setTweetId(Integer.valueOf(results.getString("tweet_id")));
                tweetObject.setUser_id_fk(Integer.valueOf(results.getString("user_id_fk")));
                tweetObject.setTweets(results.getString("tweets"));

                tweetList.add(tweetObject);
            }
            if(tweetList.size()==0){
                System.out.println("No tweets are posted yet");
            }
            conn.close();
            return tweetList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public boolean insertTweet(Integer userId, String tweet) {

        try(Connection conn = DBConfiguration.createNewDBconnection()){

            String sql =  "INSERT INTO tweets( user_id_fk,tweets ) VALUES " + "( ?,? );";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,tweet);
            int row = preparedStatement.executeUpdate();
            if(row>0){
                return true;
            }
            conn.close();
        }catch(SQLException se){
            System.err.println(se.getMessage());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return false;

    }

}
