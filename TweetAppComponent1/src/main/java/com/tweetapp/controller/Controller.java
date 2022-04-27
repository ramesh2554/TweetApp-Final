package com.tweetapp.controller;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

import com.tweetapp.exception.PasswordMatchException;
import com.tweetapp.exception.PasswordMismatchException;
import com.tweetapp.model.TweetDetails;
import com.tweetapp.model.UserDetails;
import com.tweetapp.service.TweetService;
import com.tweetapp.service.UserService;
import com.tweetapp.serviceImpl.TweetServiceImpl;
import com.tweetapp.serviceImpl.UserServiceImpl;

public class Controller {
	static Scanner sc = new Scanner(System.in);
	private static UserService userService = new UserServiceImpl();
	private static TweetService tweetService = new TweetServiceImpl();
	private static String username;

	public static String register() {
		UserDetails userDetails = new UserDetails();
		System.out.println("Enter details for user Registration  : ");
		System.out.println("Enter first_Name");
		userDetails.setFirstName(sc.nextLine());
		System.out.println("Enter last_Name");
		userDetails.setLastName(sc.nextLine());
		System.out.println("Enter gender [Male , Female or Others ] ");
		String gender = sc.nextLine();
		if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("others")) {
			userDetails.setGender(gender);
		} else {
			System.out.println("Invalid Gender should be Male ,Female or Others");
			return "user Registration failed";
		}
		System.out.println("Enter Date of Birth in YYYY-mm-dd[1999-05-01] format ");
		String date_of_birth = sc.nextLine();
		try {
			userDetails.setDob(Date.valueOf(date_of_birth));
		} catch (Exception e) {
			System.out.println("Invalid Date Format");
			return "user Registration failed";
		}
		System.out.println("Enter User_Name/E-mail [Username must follow proper email standards]");
		String username = sc.nextLine();
		String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		if (Pattern.compile(regex).matcher(username).matches()) {
			userDetails.setUserName(username);
		} else {
			System.out.println("Username must follow proper email standards");
			return "user Registration failed";
		}
		System.out.println("Enter Password");
		String password = sc.nextLine();
		if (password.length() < 7 || password.length() > 10) {
			System.out.println("Password must be more or equal to 8 characters and less than 10 characters");
			return "user Registration failed";
		}
		userDetails.setPassword(password);
		userDetails.setStatus(false);
		return userService.register(userDetails);
	}

	public static boolean login() {
		System.out.println("Login Here .... !!!");
		System.out.println("Enter User_Name / Email-id ");
		String uname = sc.nextLine();
		System.out.println("Enter Password");
		String password = sc.nextLine();
		if (uname == null || password == null || uname.trim().isEmpty() || password.trim().isEmpty()) {
			System.out.println("Login Failed ... !!! , please Check your Login details ... ");
			return false;
		} else {
			
			username = uname;
			//System.out.println(username);
			return userService.login(username, password);
		}
	}

	
//
	public static String postATweet(String username) {
		TweetDetails tweet = new TweetDetails();
		tweet.setTweetId(generateUniqueId());
		tweet.setUsername(Controller.username);
		System.out.println("Enter your tweet to post");
		tweet.setTweet(sc.nextLine());
		return tweetService.postATweet(tweet);
	}

	public static List<String> viewTweetByUser(String username) {
		try {
			return tweetService.viewTweetByUser(Controller.username);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	public static List<String> viewAllTweets() {
		try {
			return tweetService.viewAllTweets();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	
	public static List<String> viewAllUsers() {
		try {
			return userService.viewAllUsers();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	

	public static boolean resetPassword(String username) {
		try {
			System.out.println("Enter your old password");
			String oldPassword = sc.nextLine();
			System.out.println("Enter your new password");
			String newPassword = sc.nextLine();
			System.out.println("Re-Enter your new password");
			String newCheckPassword = sc.nextLine();
			if (newPassword.length() < 7 || newPassword.length() > 10) {
				System.out.println("Password should be between 8 to 10 characters");
				return true;
			}
			if (!newPassword.equals(newCheckPassword)) {
				throw new PasswordMismatchException("Re-entered password mismatch");
			}
			if (newPassword.equals(oldPassword)) {
				throw new PasswordMatchException("old password and new password should not be same");
			}
			if (userService.resetPassword(Controller.username, oldPassword, newPassword)) {
				System.out.println("Reset successfully");
				return false;
			}
			System.out.println("Reset Failed");
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return true;
		}
	}

	public static void forgotPassword() {
		try {
			
			System.out.println("Enter your username");
			String username = sc.nextLine();
			System.out.println("Enter your new password");
			String new_pass = sc.nextLine();
			System.out.println("Re-Enter your new password");
			String checked_new_pass = sc.nextLine();
			if (new_pass.length() < 7 || new_pass.length() > 10) {
				System.out.println("Password should be between 8 to 10 characters");
			}
			if (!new_pass.equals(checked_new_pass)) {
				throw new PasswordMismatchException("Re-entered password mismatch");
			}
			if (userService.forgetPassword(username, new_pass)) {
				System.out.println("Set NEW PASSWORD Sucessfull..!!!");
			} else
				System.out.println("Reset Failed");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static boolean logOut() {
		if (userService.logout(username)) {
			System.out.println("Logged out successfully");
			return false;
		}
		System.out.println("Logout Failed");
		return true;
	}
	
// 
	
	public static int generateUniqueId() {
		UUID idOne = UUID.randomUUID();
		String str = "" + idOne;
		int uid = str.hashCode();
		String filterStr = "" + uid;
		str = filterStr.replaceAll("-", "");
		return Integer.parseInt(str);
	}


	
}
