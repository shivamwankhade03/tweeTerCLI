package com.tweetApp.dao.impl;

import com.tweetApp.config.DBConfiguration;
import com.tweetApp.dao.UserDetailsDao;
import com.tweetApp.domain.UserDetails;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsDaoImpl implements UserDetailsDao {

    private static ResultSet results;
    private static PreparedStatement preparedStatement;

    public List<UserDetails> getAllUserDetailsList() {
        String sql_select = "Select * From user_details";
        try (Connection conn = DBConfiguration.createNewDBconnection()) {

            preparedStatement = conn.prepareStatement(sql_select);
            results = preparedStatement.executeQuery();

            List<UserDetails> usersList = new ArrayList<UserDetails>();
            while (results.next()) {
                UserDetails userObject = new UserDetails();

                userObject.setUserId(Integer.valueOf(results.getString("userid")));
                userObject.setFirstName(results.getString("first_name"));
                userObject.setLastName(results.getString("last_name"));
                userObject.setEmail(results.getString("email"));
                userObject.setGender(results.getString("gender"));
                userObject.setPassword(results.getString("password"));
                userObject.setDateOfBirth(results.getDate("date_of_birth"));

                usersList.add(userObject);
            }
            conn.close();
            return usersList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public boolean addNewUser(UserDetails newUser) {

        try(Connection conn = DBConfiguration.createNewDBconnection()){
            String sql =  "INSERT INTO user_details( first_name, last_name, gender, date_of_birth, email, password ) VALUES " + "( ?,?,?,?,?,?);";

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1,newUser.getFirstName());
            preparedStatement.setString(2,newUser.getLastName());
            preparedStatement.setString(3,newUser.getGender());
            java.sql.Date dob = new java.sql.Date(newUser.getDateOfBirth().getTime());
            preparedStatement.setDate(4, dob);
            preparedStatement.setString(5,newUser.getEmail());
            preparedStatement.setString(6,newUser.getPassword());

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

    public UserDetails isUserPresent(String email) {
        UserDetails userObject = new UserDetails();
        String sql_select = "Select * From user_details where email = ?";
        try (Connection conn = DBConfiguration.createNewDBconnection()) {

            preparedStatement = conn.prepareStatement(sql_select);
            preparedStatement.setString(1, email);
            results = preparedStatement.executeQuery();

            while (results.next()) {
                userObject.setUserId(Integer.valueOf(results.getString("userid")));
                userObject.setFirstName(results.getString("first_name"));
                userObject.setLastName(results.getString("last_name"));
                userObject.setEmail(results.getString("email"));
                userObject.setGender(results.getString("gender"));
                userObject.setPassword(results.getString("password"));
                userObject.setDateOfBirth(results.getDate("date_of_birth"));
            }

            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return userObject;
    }

    public boolean updatePassword(String newPassword,String email) {
        UserDetails userObject = new UserDetails();
        String sql_select = "Update user_details SET password = ? where email = ?";
        try (Connection conn = DBConfiguration.createNewDBconnection()) {

             preparedStatement = conn.prepareStatement(sql_select);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);

            int row = preparedStatement.executeUpdate();
            return  row>0 ? true : false;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
