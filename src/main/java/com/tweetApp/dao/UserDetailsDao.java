package com.tweetApp.dao;

import com.tweetApp.config.DBConfiguration;
import com.tweetApp.domain.UserDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface UserDetailsDao {

    public List<UserDetails> getAllUserDetailsList();
    public boolean addNewUser(UserDetails newUser) ;
    public UserDetails isUserPresent(String email) ;
    public boolean updatePassword(String newPassword,String email);
}
