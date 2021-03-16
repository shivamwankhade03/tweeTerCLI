package com.tweetApp;

import com.tweetApp.domain.UserDetails;
import com.tweetApp.exception.UserAlreadyExitsException;
import com.tweetApp.service.TweetsDetailsService;
import com.tweetApp.service.UserDetailsService;
import com.tweetApp.service.impl.TweetDetailServiceImpl;
import com.tweetApp.service.impl.UserDetailsServiceImpl;

import java.util.Scanner;

public class App 
{
    public static void main( String[] args ) {
        UserDetailsService userService = new UserDetailsServiceImpl();
        TweetsDetailsService tweetService = new TweetDetailServiceImpl();
        Scanner input = new Scanner(System.in);
        do {

            System.out.println("------------------------------------------- ");
            System.out.println(" Menu ");
            System.out.println("------------------------------------------- ");
            System.out.println("1. Register \n2. Login \n3. Forgot Password ");
            System.out.println("------------------------------------------- ");
            System.out.print("Please Select your choice : ");
            int choice = input.nextInt();
            System.out.println("------------------------------------------- ");

            switch (choice) {

                case 1:
                    System.out.println(">>>>>>>>>>>>>Registration Form<<<<<<<<<<<<<<");

                    System.out.println("\nPlease Enter Valid User details ");
                    try {
                        userService.registerNewUser();
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("-------------------------------------------\n");

                    }
                    break;

                case 2:
                    System.out.println(">>>>>>>>>>>>>>>>Login<<<<<<<<<<<<<<<<<<\n");
                    System.out.println("Please Enter the login details\n");
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Email (required) : ");
                    String email = sc.nextLine();

                    System.out.print("Password (required) : ");
                    String password = sc.nextLine();

                    boolean flag = userService.login(email,password);
                    UserDetails user = new UserDetails();
                    user = userService.getUserInfo(email);
                    if(flag){

                        do {

                            System.out.println("------------------------------------------- ");
                            System.out.println("Menu     ...........(' "+user.getFirstName()+" ') Logged-In");
                            System.out.println("------------------------------------------- ");

                            System.out.println("1. Post a tweet \n2. View my tweets \n3. View all tweets \n4. View All Users \n5. Reset Password \n6. Logout");
                            System.out.print("Please Select your choice : ");
                            int choiceForLoggedIn = input.nextInt();
                            System.out.println("------------------------------------------- ");

                            switch (choiceForLoggedIn) {

                                case 1:
                                    System.out.println("Post a tweet:");
                                    System.out.print("Please enter tweet :");
                                    tweetService.writeTweet(user.getUserId());
                                    break;
                                case 2:
                                    System.out.println("TWEETS of "+user.getFirstName()+" User");
                                    System.out.println("------------------------------------------- ");
                                    tweetService.viewMyTweets(user.getUserId());
                                    break;
                                case 3:
                                    System.out.println("------------------------------------------- ");
                                    System.out.println(" ALL Tweets ");
                                    System.out.println("------------------------------------------- ");
                                    tweetService.getAllTweets();
                                    break;
                                case 4:
                                    System.out.println("All Users:");
                                    userService.getAllUser();
                                    break;
                                case 5:
                                    System.out.println("Reset password:");
                                    userService.resetPassword(user.getEmail());
                                    break;
                                case 6:
                                         flag = false;
                                    break;
                                default: System.out.println("Please enter valid input ");

                                    break;
                            }

                            if(!flag){
                                break;
                            }
                            System.out.print("\n\nDo you want to continue( 1/0 ) :");
                            int s=input.nextInt();
                            if(s==0 ){
                                break;
                            }
                        }while(true);
                        System.out.println("\nLogged out successfully !");

                    }
                    break;

                case 3:
                    System.out.println("Forgot Password");
                    userService.forgotPassword();
                    break;

                default:
                    System.out.println("Please select valid choice");
                    break;
            }
            System.out.println("------------------------------------------- ");
            System.out.print("\n\nDo you want to continue (1/0): ");
            int s=input.nextInt();
            if(s==0){
                System.out.println("------------------------------------------- ");
                System.out.println("\n\n\nGood Bye !!");
                System.out.println("------------------------------------------- ");

                break;
            }

        }while(true);
        input.close();
    }


}
