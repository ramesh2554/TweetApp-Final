package com.tweetapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tweetapp.config.JDBCConnection;
import com.tweetapp.model.TweetDetails;

public class TweetRepository {
	Connection conn=JDBCConnection.getConnection();
	
	public boolean postTweet(TweetDetails tweet) {
		if(conn!=null) {
			try {
				//create table tweetdetails(tweetid int(10) , username varchar(50) , tweet varchar(50));
				String query="insert into tweetdetails(tweetid,username,tweet) values (?,?,?);";
				PreparedStatement stmt=conn.prepareStatement(query);
				stmt.setInt(1, tweet.getTweetId());
				stmt.setString(2, tweet.getUsername());
				stmt.setString(3, tweet.getTweet());
				return stmt.execute();
			}
			catch(Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return false;
	}
	public List<TweetDetails> findAll(){
		List<TweetDetails> tweets=new ArrayList<>();
		if(conn!=null) {
			try {
				Statement stmt=conn.createStatement();
				String query="select * from tweetdetails;";
				ResultSet result=stmt.executeQuery(query);
				while(result.next()) {
					TweetDetails tweet=new TweetDetails(result.getInt("tweetid"),result.getString("username"),result.getString("tweet"));
					tweets.add(tweet);
				}
				return tweets;
			}
			catch(Exception ex) {
				ex.printStackTrace();
				return tweets;
			}
		}
		return tweets;
	}

}
