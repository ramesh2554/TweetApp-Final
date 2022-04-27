package com.tweetapp.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class JDBCConnection {
	public static Connection getConnection() {
		Properties p=new Properties();
		try {  
		     
		    FileInputStream reader=new FileInputStream(new File("D:\\Rameswara\\Tranning\\fse1\\caseStudy\\TweetAppComponent1\\src\\main\\resources\\db.properties"));
		    p.load(reader);
			Class.forName(p.getProperty("driver-class"));
		} catch (ClassNotFoundException ex) {
			System.out.println("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
		} catch (FileNotFoundException e) {
			System.out.println("Sorry, unable to find db.properties file");
		} catch (IOException e) {
			System.out.println("Sorry, unable to load db.properties file");
			return null;
			
		}

		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+p.getProperty("database"), p.getProperty("username"), p.getProperty("password"));
			if (conn != null) {
				return conn;
			} else {
				System.out.println("Failed to make connection!");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("MySQL Connection Failed!");
			e.printStackTrace();
			return null;
	}
	}
}
