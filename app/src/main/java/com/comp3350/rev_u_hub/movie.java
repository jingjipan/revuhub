package com.comp3350.rev_u_hub;

import java.util.List;

public class movie {
	String movieName; String intro; String[] pics; List<String> reviews;
	public movie(String movieName, String intro, String[] pics, List<String> reviews){
		this.movieName=movieName;
		this.intro=intro;
		this.pics=pics;
		this.reviews=reviews;
	}
	
	public void addReviews(String newReview){
		reviews.add(newReview);
	}
}
