package com.tweetapp;

import java.util.List;
import java.util.Scanner;

import com.tweetapp.controller.Controller;

public class TweetApp {
	static Scanner sc = new Scanner(System.in);
	static boolean isLoggeduser = false;

	private static String username;

	public static void main(String[] args) {
		int choice;
		while (true) {
			
			if (!isLoggeduser) {
				System.out.println("\t \t \t Menu Options For Non Logged User");
				System.out.println("1.Register\n2.Login\n3.Forget Password");
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					System.out.println(Controller.register());
					
					break;
				case 2:
					isLoggeduser = Controller.login();

					break;
				case 3:
					Controller.forgotPassword();
					break;
				default:
					System.out.println("Enter the correct choice");
				}
			} else {
				System.out.println("\t \t \t Menu Options For Logged User");
				System.out.println(
						"1.Post a tweet\n2.View my tweets\n3.View all tweets\n4.View all users\n5.Reset Password\n6.Logout");
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					System.out.println(Controller.postATweet(username));
					break;
				case 2:
					List<String> tweets = Controller.viewTweetByUser(username);
					if (tweets != null)
						tweets.forEach(x -> System.out.println("\t " +x ));
					else
						System.out.println("No tweets Found  for " +username);
					break;
				case 3:
					List<String> allTweets = Controller.viewAllTweets();
					if (allTweets != null)
						allTweets.forEach(System.out::println);
					else
						System.out.println("No tweets Found");
					break;
				case 4:
					List<String> users = Controller.viewAllUsers();
					if (users != null)
						users.forEach(System.out::println);
					else
						System.out.println("No users Found");
					break;
				
				case 5:
					isLoggeduser = Controller.resetPassword(username);
					break;
				case 6:
					isLoggeduser = Controller.logOut();
					break;
				default:
					System.out.println("Enter the correct choice");
				}
			}
		}
	}
}
