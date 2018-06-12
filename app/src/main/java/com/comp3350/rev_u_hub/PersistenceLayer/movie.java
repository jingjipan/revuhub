package com.comp3350.rev_u_hub.PersistenceLayer;

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

	public String getMovieName(){
		return movieName;
	}

	public String getIntro(){
		return intro;
	}

	public List<String> getReviews(){
		return reviews;
	}

	public String[] getPicsUrls(){
		return pics;
	}

	public void changeMovieName(String movieName){
		this.movieName=movieName;
	}

	public void changeReviews(List<String> reviews){
		this.reviews=reviews;
	}

	public void changePicUrls(String[] pics){
		this.pics=pics;
	}

	public void changeIntro(String intro){
		this.intro=intro;
	}
}
