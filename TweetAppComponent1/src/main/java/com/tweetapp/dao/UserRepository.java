package com.tweetapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tweetapp.config.JDBCConnection;
import com.tweetapp.model.UserDetails;

public class UserRepository {
	Connection conn=JDBCConnection.getConnection();
	
	public boolean addUser(UserDetails user) {
		if(conn!=null) {
			try {
				//create table userdetails(username varchar(50) , firstname varchar(50),lastname varchar(50) ,gender varchar(20),dob date,status boolean ,password varchar(30));
				String query="Insert into userdetails(username,firstname,lastname,gender,dob,status,password) values (?,?,?,?,?,?,?)";
				PreparedStatement stmt= conn.prepareStatement(query);
				stmt.setString(1, user.getUserName());
				stmt.setString(2, user.getFirstName());
				stmt.setString(3, user.getLastName());
				stmt.setString(4, user.getGender());
				stmt.setDate(5, user.getDob());
				stmt.setBoolean(6, user.isStatus());
				stmt.setString(7, user.getPassword());
				stmt.execute();
				return true;
			}
			catch(Exception ex) {
				return false;
			}
		}
		return false;
	}
	
	public UserDetails findbyId(String username) {
		UserDetails user=null;
		if(conn!=null) {
			try {
			
				String query="Select * from userdetails where username=?;";
				PreparedStatement stmt=conn.prepareStatement(query);
				stmt.setString(1, username);
				ResultSet quser=stmt.executeQuery();
				if(quser.next()) {
				user=new UserDetails(quser.getString("username"),quser.getString("firstname"),quser.getString("lastname"),
						quser.getString("gender"),quser.getDate("dob"),quser.getBoolean("status"),quser.getString("password"));
				}
				//System.out.println(user);
				return user;
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e);
				return user;
			}
		}
		return user;
	}
	public List<UserDetails> findAll(){
		List<UserDetails> users=new ArrayList<>();
		if(conn!=null) {
			try {
				Statement stmt=conn.createStatement();
				String query="Select * from userdetails;";
				ResultSet quser=stmt.executeQuery(query);
				while(quser.next()) {
				UserDetails user=new UserDetails(quser.getString("username"),quser.getString("firstname"),quser.getString("lastname"),
						quser.getString("gender"),quser.getDate("dob"),quser.getBoolean("status"),quser.getString("password"));
				users.add(user);
				}
				return users;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return users;
			}
		}
		return users;
	}
	
	public boolean updateStatus(UserDetails user) {
		if(conn!=null) {
			try {
				String query="update userdetails set status=? where username=?";
				PreparedStatement stmt=conn.prepareStatement(query);
				stmt.setBoolean(1, !user.isStatus());
				stmt.setString(2, user.getUserName());
				System.out.println(user.getUserName() + " Status Updated");
				return stmt.execute();
			} catch (SQLException e) {
				System.out.println("unable to update status");
				return false;
			}
			
		}
		System.out.println("Status Update is failed ");
		return false;
	}
	public boolean updatePassword(UserDetails user) {
		if(conn!=null) {
			try {
				String query="update userdetails set password=? where username=?";
				PreparedStatement stmt=conn.prepareStatement(query);
				stmt.setString(1, user.getPassword());
				stmt.setString(2, user.getUserName());
				System.out.println("Updated status");
				return stmt.execute();
			} catch (SQLException e) {
				System.out.println("Status updated Failed");
				return false;
			}
			
		}
		System.out.println("Status updated Failed");
		return false;
	}

}
