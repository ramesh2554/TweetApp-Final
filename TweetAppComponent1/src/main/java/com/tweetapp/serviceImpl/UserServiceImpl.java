package com.tweetapp.serviceImpl;

import com.tweetapp.dao.UserRepository;
import com.tweetapp.exception.PasswordMismatchException;
import com.tweetapp.exception.UserExistsException;
import com.tweetapp.exception.UserNotFoundException;
import com.tweetapp.model.UserDetails;
import com.tweetapp.service.UserService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;






public class UserServiceImpl implements UserService {
	
    UserRepository userRepository=new UserRepository();

    @Override
    public String register(UserDetails userDetails) {
    	
        try {
            UserDetails user=userRepository.findbyId(userDetails.getUserName());
            if(user==null) {
                userRepository.addUser(userDetails);
                return "User Registration successful";
            }
            throw new UserExistsException("User deatils found in Database please ...!!!, Login Now");
        }
        catch (Exception ex){
            return ex.getMessage();
        }

    }

    @Override
    public boolean login(String username, String password) {
       // System.out.println("In service for login user ");
        try {
            UserDetails user = userRepository.findbyId(username);
            if (user==null) {
                throw new UserNotFoundException("User is not found ,  please Register....!!!");
            }
            if(user.getUserName().equalsIgnoreCase(username) && user.getPassword().equals(password)){
            	user.setStatus(false);
                userRepository.updateStatus(user);
            	user.setStatus(true);
                System.out.println(username + "\t Login Successful");
                return true;
            }
            System.out.println("Try Again .. !!! , Details not Matched .. !!! ");
            return false;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

    }

    @Override
    public List<String> viewAllUsers() {
        System.out.println("In user service to get all the users");
        return userRepository.findAll().stream().map(o->o.getUserName()).collect(Collectors.toList());
    }

    @Override
    public boolean resetPassword(String username, String oldPassword, String newPassword) {
        System.out.println("In service for reset user password");
        try {
            UserDetails user = userRepository.findbyId(username);
            if (user==null) {
                throw new UserNotFoundException("Username Not Found, Resister");
            }
            if(user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                user.setStatus(true);
                userRepository.updateStatus(user);
                userRepository.updatePassword(user);
                System.out.println("Password reset Successful , Loggin again");
                return true;
            }
            throw new PasswordMismatchException("Old password and new Password Not Matched");
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

    }


    @Override
    public boolean logout(String username) {
        System.out.println("In service for logout user ");
        try {
            UserDetails user = userRepository.findbyId(username);
            if (user==null) {
                throw new UserNotFoundException("Username Not Found, Resister");
            }
            user.setStatus(true);
            userRepository.updateStatus(user);
            System.out.println("Logout Successful");
            return true;

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
	@Override
    public boolean forgetPassword(String username,String newPassword) {
		
    	ScriptEngineManager ee=new ScriptEngineManager();
    	ScriptEngine e=ee.getEngineByName("Nashorn");
    	try {
    		UserDetails user = userRepository.findbyId(username);
            if (user==null) {
                throw new UserNotFoundException("Username Not Found, Resister");
            }
			e.eval(new FileReader("D:\\Rameswara\\Tranning\\fse1\\caseStudy\\TweetAppComponent1\\src\\main\\java\\com\\tweetapp\\serviceImpl\\forgotPassword.js"));
			Invocable inv=(Invocable)e;
			boolean result=(boolean) inv.invokeFunction("forgotPassword", username,newPassword);
			if(result)
				System.out.println("Password Reset Successful");
			else
				System.out.println("Password reset unsuccessful");
			return true;
		} catch (FileNotFoundException | ScriptException ex) {
			System.out.println(ex.getMessage());
			return false;
		} catch (NoSuchMethodException ex) {
			System.out.println(ex.getMessage());
			return false;
		} catch (UserNotFoundException e1) {
			System.out.println(e1.getMessage());
			return false;
		}
    }  
}
