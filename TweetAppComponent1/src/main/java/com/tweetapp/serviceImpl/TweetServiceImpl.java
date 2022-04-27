package com.tweetapp.serviceImpl;

import com.tweetapp.dao.TweetRepository;
import com.tweetapp.dao.UserRepository;
import com.tweetapp.model.TweetDetails;
import com.tweetapp.model.UserDetails;
import com.tweetapp.service.TweetService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TweetServiceImpl implements TweetService {
    
    TweetRepository tweetRepository=new TweetRepository();

    @Override
    public String postATweet(TweetDetails tweet) {
        try{
            tweetRepository.postTweet(tweet);
            return "Posted a Tweet Successfully ....";
        }
        catch (Exception ex){
            System.out.println("Failed to Posted a Tweet");
            return ex.getMessage();
        }

    }
    @Override
    public List<String> viewAllTweets() {
    	
    	System.out.println("Get All Tweets ");
        return tweetRepository.findAll().stream().map(TweetDetails::getTweet).collect(Collectors.toList());
    }
    
    @Override
    public List<String> viewTweetByUser(String username) {
    	System.out.println(  " My Tweets" + username);

        return tweetRepository.findAll().stream().filter(tweet->tweet.getUsername().equals(username))
                .map(TweetDetails::getTweet)
                .collect(Collectors.toList());
    }
   

}
