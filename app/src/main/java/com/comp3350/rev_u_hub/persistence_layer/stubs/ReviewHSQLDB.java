package com.comp3350.rev_u_hub.persistence_layer.stubs;

import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.comp3350.rev_u_hub.persistence_layer.ReviewPersistence;
public class ReviewHSQLDB implements ReviewPersistence{


    private final Connection c;

    public ReviewHSQLDB(final String dbPath) {
        try {
            this.c = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath, "SA", "");
        } catch (final SQLException e) {
            System.out.println("ERROR: Fail to get DB connection.");
            throw new PersistenceException(e);
        }
    }

    private ReviewObject fromResultSet(final ResultSet rs) throws SQLException {
        final String movieName = rs.getString("movieName");
        final String review = rs.getString("review");
        final String username = rs.getString("userName");
        return new ReviewObject(movieName,review, username);
    }

    public List<ReviewObject> getReviewsSequential() {
        final List<ReviewObject> reviews = new ArrayList<>();

        try {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM reviews");
            while (rs.next()) {
                final ReviewObject review = fromResultSet(rs);
                reviews.add(review );
            }
            rs.close();
            st.close();

            return reviews;
        } catch (final SQLException e) {
            System.out.println("ERROR: Not able to read reviews from DB.");
            throw new PersistenceException(e);
        }

    }

    public List<ReviewObject> searchReview(String userName, String movieName){

        final List<ReviewObject> reviews = new ArrayList<>();

        try {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM reviews WHERE movieName = ? AND userName = ?");
            st.setString(1, movieName);
            st.setString(2, userName);
            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final ReviewObject review = fromResultSet(rs);
                reviews.add(review );
            }
            rs.close();
            st.close();

            return reviews;
        } catch (final SQLException e) {
            System.out.println("ERROR: Not able to read reviews from DB.");
            throw new PersistenceException(e);
        }

    }
    public List<ReviewObject> getReviewsOfUser(String userName) {
        final List<ReviewObject> reviews = new ArrayList<>();

        try {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM reviews WHERE reviews.movieName=movies.movieName AND userName = ?");
            while (rs.next()) {
                final ReviewObject review = fromResultSet(rs);
                reviews.add(review );
            }
            rs.close();
            st.close();

            return reviews;
        } catch (final SQLException e) {
            System.out.println("ERROR: Not able to read reviews from DB.");
            throw new PersistenceException(e);
        }

    }

    public List<ReviewObject> getReviewsOfMovie(String movieName) {
        final List<ReviewObject> reviews = new ArrayList<>();

        try {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM reviews WHERE reviews.userName=movies.userName AND movieName = ?");
            st.setString(1, movieName);
            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final ReviewObject review = fromResultSet(rs);
                reviews.add(review );
            }
            rs.close();
            st.close();

            return reviews;
        } catch (final SQLException e) {
            System.out.println("ERROR: Not able to read reviews from DB.");
            throw new PersistenceException(e);
        }

    }

    public ReviewObject addNewReview(ReviewObject newReview) {
        try {
            final PreparedStatement st = c.prepareStatement("INSERT INTO reviews VALUES(?, ?, ?)");
            st.setString(1, newReview.getUserName());
            st.setString(2, newReview.getMovieName());
            st.setString(3, newReview.getReview());


            st.executeUpdate();

            return newReview;
        } catch (final SQLException e) {
            System.out.println("ERROR: Not able to add review: "+newReview.getReview());
            throw new PersistenceException(e);
        }
    }

    public ReviewObject updateReview(ReviewObject review){
        try {
            final PreparedStatement st = c.prepareStatement("UPDATE reviews SET review = ? WHERE movieName = ? AND userName = ?");
            st.setString(1,  review.getReview());
            st.setString(2,  review.getMovieName());
            st.setString(3,  review.getUserName());
            st.executeUpdate();

            return  review;
        } catch (final SQLException e) {
            System.out.println("ERROR: Not able to update review of movie: "+ review.getMovieName());
            throw new PersistenceException(e);
        }
    }

    public void deleteReview(ReviewObject review) {
        try {
            final PreparedStatement st = c.prepareStatement("DELETE FROM reviews WHERE movieName = ? AND userName = ?");
            st.setString(1,  review.getMovieName());
            st.setString(2,  review.getUserName());

            st.executeUpdate();
        } catch (final SQLException e) {
            System.out.println("ERROR: Not able to delete review: "+review.getReview());
            throw new PersistenceException(e);
        }
    }

    public void deleteAllReview(String userName) {
        try {
            final PreparedStatement st = c.prepareStatement("DELETE FROM reviews WHERE userName = ?");
            st.setString(1,  userName);

            st.executeUpdate();
        } catch (final SQLException e) {
            System.out.println("ERROR: Not able to delete all reviews of movie: "+userName);
            throw new PersistenceException(e);
        }
    }
}