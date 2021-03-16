package com.tweetApp.service.impl;

import com.tweetApp.dao.UserDetailsDao;
import com.tweetApp.dao.impl.UserDetailsDaoImpl;
import com.tweetApp.domain.UserDetails;
import com.tweetApp.exception.InvalidPasswordException;
import com.tweetApp.exception.UserAlreadyExitsException;
import com.tweetApp.exception.UserNotFoundException;
import com.tweetApp.service.UserDetailsService;
import com.tweetApp.util.DateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserDetailsServiceImpl implements UserDetailsService {
    UserDetailsDao userDetailsDao = new UserDetailsDaoImpl();

    public UserDetails getRegistrationDetails() {
        UserDetails newUser = new UserDetails();
        Scanner sc = new Scanner(System.in);

        System.out.print("First Name (required): ");
        String firstName = sc.nextLine();
        newUser.setFirstName(firstName);

        System.out.print("Last Name (optional) : ");
        String lastName =sc.nextLine();
        if(lastName.isEmpty() || lastName==null){
            newUser.setLastName(null);
        }else{
            newUser.setLastName(lastName);
        }

        System.out.print("Gender (required) (M/F): ");
        String gender = sc.nextLine();
        if(gender.isEmpty() || lastName==null){
            newUser.setGender(null);
        }else{
            newUser.setGender(gender);
        }

        System.out.print("Date of Birth (optional) (dd-MM-yyyy): ");
        String dateOfBirth = sc.nextLine();
        if(dateOfBirth.isEmpty() || dateOfBirth==null){
            try {
                newUser.setDateOfBirth(DateUtil.convertToDate("00-00-0000"));
            } catch (ParseException e) {
                System.out.println("Please enter date in valid format");
            }
        }else{
            try {
                newUser.setDateOfBirth(DateUtil.convertToDate(dateOfBirth));
            } catch (ParseException e) {
                System.out.println("Please enter date in valid format");
            }
        }

        System.out.print("Email (required) : ");
        String email = sc.nextLine();
        newUser.setEmail(email);


        System.out.print("Password (required) : ");
        String password = sc.nextLine();
        newUser.setPassword(password);

        return newUser;
    }

    public void registerNewUser() throws UserAlreadyExitsException, ParseException {
        boolean idExists =false;
        //get data from user
        final UserDetails newUser = getRegistrationDetails();
        System.out.println("\n");
        //check if user already exits or not
        List<UserDetails> existingUserDeatilsList = getAllUserDetails();

        for (UserDetails user : existingUserDeatilsList) {
            if(user.getEmail().equals(newUser.getEmail())){
                idExists=true;
                break;
            }
        }
        if(idExists){
            System.out.println("------------------------------------------- ");
            throw new UserAlreadyExitsException("User Already Exits with given Email Address !!!!");
        }else{
            if(userDetailsDao.addNewUser(newUser)){
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Registration completed !! ");
            }
        }
    }

    public List<UserDetails> getAllUserDetails(){
        List<UserDetails> userList = null;
        userList = userDetailsDao.getAllUserDetailsList();
        return userList;
    }

    public boolean login(String email,String password) {
        UserDetails userInfo = null;
        try {
            userInfo = userDetailsDao.isUserPresent(email);

            if(userInfo.getEmail() == null || userInfo.getPassword() == null){
                throw new UserNotFoundException("User Not Found !!!");
            }
            if(userInfo.getPassword().equals(password)){
                return true;
            }else{
                throw new InvalidPasswordException("Invalid Password!!!");
            }
        }catch(UserNotFoundException e){
            System.out.println("------------------------------------------- ");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------- ");
        }catch (InvalidPasswordException e){
            System.out.println("------------------------------------------- ");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------- ");
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return false;
    }

    public void forgotPassword() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter registered email-Id : ");
        String email = sc.nextLine();
        UserDetails userInfo = null;
        String responseMsg = "Failed";

        try {
            userInfo = userDetailsDao.isUserPresent(email);
            if(userInfo.getEmail().equals(email)){
                System.out.print("Enter New Password : ");
                String newPassword = sc.nextLine();

                System.out.print("Enter Confirm-new Password : ");
                String confirmPassword = sc.nextLine();

                if(newPassword.equals(confirmPassword)){

                    if(userDetailsDao.updatePassword(newPassword,email))
                    {
                        System.out.println("------------------------------------------- ");
                        System.out.println("Password updated successfully for "+userInfo.getEmail());
                    }
                }else{
                    System.out.println("------------------------------------------- ");
                    System.out.println("Please enter password correctly (mismatched)!!");
                    System.out.println("------------------------------------------- ");

                }
            }
        }catch(Exception e){
            System.out.println("User not present");
        }
    }

    public void getAllUser() {
        List<UserDetails> userList = null;
        userList = userDetailsDao.getAllUserDetailsList();

        if(userList!=null){
            for(int i=0;i<userList.size();i++){

                System.out.println("User : "+(i+1));
                System.out.println("-------------------------------");
                System.out.println("First Name : "+userList.get(i).getFirstName() +"\nLast Name :"+userList.get(i).getLastName());
                System.out.println("Gender : "+userList.get(i).getGender()+"\nDate of Birth :"+userList.get(i).getDateOfBirth());
                System.out.println("Email : "+userList.get(i).getEmail());
                System.out.println("-------------------------------");

            }
        }
        System.out.println("\n------------------------------------------- ");

    }

    public  void resetPassword(String emailId){
        Scanner sc = new Scanner(System.in);
        System.out.print("Old Password : ");
        String oldPassword = sc.nextLine();

        System.out.print("New  Password : ");
        String newPassword = sc.nextLine();
        UserDetails userInfo = null;

        try {
            userInfo = userDetailsDao.isUserPresent(emailId);

            if(userInfo.getPassword().equals(oldPassword))
            {
            if(userDetailsDao.updatePassword(newPassword,emailId)) {
                System.out.println("\n------------------------------------------- ");
                System.out.println("\n Password reset successfully for  "+userInfo.getEmail());
                System.out.println("\n------------------------------------------- ");

            }else{
                throw new UserNotFoundException(">>>>>>>>>>>User Not Found !!!");
            }
            }else{
                System.out.println("\n------------------------------------------- ");
                System.out.println("\n Entered  worng old password  ");
                System.out.println("\n------------------------------------------- ");

            }
        }catch(UserNotFoundException e){

            System.out.println("\n------------------------------------------- ");
            System.out.println(e.getMessage());
            System.out.println("\n------------------------------------------- ");

        }catch(Exception e){
            System.err.println(e.getMessage());
        }

    }

    public UserDetails getUserInfo(String email){
        return userDetailsDao.isUserPresent(email);
    }
}
