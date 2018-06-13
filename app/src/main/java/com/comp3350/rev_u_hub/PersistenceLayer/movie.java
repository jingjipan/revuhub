package com.comp3350.rev_u_hub.PersistenceLayer;

import java.util.List;

public class movie {
    String movieName;
    String intro, cast;
    String[] pics;
    List<String> reviews, news;

    public movie(String movieName, String intro, String[] pics, String cast, List<String> news, List<String> reviews) {
        this.movieName = movieName;
        this.intro = intro;
        this.pics = pics;
        this.reviews = reviews;
        this.cast = cast;
        this.news = news;
    }

    public void addReviews(String newReview) {
        reviews.add(newReview);
    }

    public String getMovieName() {
        return movieName;
    }

    public String getIntro() {
        return intro;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public String[] getPicsUrls() {
        return pics;
    }

    public String getCast() {
        return cast;
    }

    public List<String> getNews() {
        return news;
    }

    public void changeMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void changeReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public void changePicUrls(String[] pics) {
        this.pics = pics;
    }

    public void changeIntro(String intro) {
        this.intro = intro;
    }

    public void changeNews(List<String> news) {
        this.news = news;
    }

    public void changeCast(String cast) {
        this.cast = cast;
    }

    ;
}
