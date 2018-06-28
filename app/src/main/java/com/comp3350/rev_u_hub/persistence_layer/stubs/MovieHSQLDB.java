package com.comp3350.rev_u_hub.persistence_layer.stubs;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieHSQLDB {


    private final Connection c;

    public MovieHSQLDB (final String dbPath) {
        try {
            this.c = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath, "SA", "");
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private MovieObject fromResultSet(final ResultSet rs) throws SQLException {
        final String movieName = rs.getString("movieName");
        final String synopsis = rs.getString("synopsis");
        final String cast = rs.getString("cast");
        final int count = rs.getInt("count");
        final double rating = rs.getDouble("rating");
        return new MovieObject(movieName,synopsis, cast,count,rating);
    }

    public List<MovieObject> getMovieSequential() {
        final List<MovieObject> movies = new ArrayList<>();

        try {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM movies");
            while (rs.next()) {
                final MovieObject movie = fromResultSet(rs);
                movies.add(movie);
            }
            rs.close();
            st.close();

            return movies;
        } catch (final SQLException e) {
            System.out.println("ERROR: Not able to read movie from DB.");
            throw new PersistenceException(e);
        }

    }

    public MovieObject addNewMovie(MovieObject newMovie) {
        try {
            final PreparedStatement st = c.prepareStatement("INSERT INTO movies VALUES(?, ?, ?, ?, ?)");
            st.setString(1, newMovie.getTitle());
            st.setString(2, newMovie.getSynopsis());
            st.setString(3, newMovie.getCast());
            st.setInt(4, newMovie.getCount());
            st.setDouble(5, newMovie.getRating());
            st.executeUpdate();

            return newMovie;
        } catch (final SQLException e) {
            System.out.println("ERROR: Not able to add moviee: "+newMovie.getTitle());
            throw new PersistenceException(e);
        }
    }

    public MovieObject updateMovie(MovieObject movie){
        try {
            final PreparedStatement st = c.prepareStatement("UPDATE movies SET synopsis = ?, cast = ?, count=?, rating=? WHERE movieName = ?");
            st.setString(1, movie.getSynopsis());
            st.setString(2, movie.getCast());
            st.setString(3, movie.getTitle());
            st.setInt(4, movie.getCount());
            st.setDouble(5, movie.getRating());

            st.executeUpdate();

            return movie;
        } catch (final SQLException e) {
            System.out.println("ERROR: Not able to update movie: "+movie.getTitle());
            throw new PersistenceException(e);
        }
    }

    public void deleteMovie(MovieObject movie) {
        try {
            final PreparedStatement st = c.prepareStatement("DELETE FROM movies WHERE movieName = ?");
        st.setString(1, movie.getTitle());
        st.executeUpdate();
    } catch (final SQLException e) {
            System.out.println("ERROR: Not able to delete movie: "+movie.getTitle());
        throw new PersistenceException(e);
    }
    }
}
