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

import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;

public class MovieHSQLDB implements MoviePersistence {


    private final String dbPath;

    public MovieHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private MovieObject fromResultSet(final ResultSet rs) throws SQLException {
        final String movieName = rs.getString("movieName");
        final String synopsis = rs.getString("synopsis");
        final String cast = rs.getString("moviecast");
        final int count = rs.getInt("count");
        final double rating = rs.getDouble("rating");
        final String pic = rs.getString("pic");
        return new MovieObject(movieName, synopsis, cast, pic, count, rating);
    }

    public List<MovieObject> getMovieSequential() {
        final List<MovieObject> movies = new ArrayList<>();

        try (final Connection c = connection()){
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
            throw new PersistenceException(e);
        }

    }

    public MovieObject addNewMovie(MovieObject newMovie) {
        try (final Connection c = connection()){
            final PreparedStatement st = c.prepareStatement("INSERT INTO movies VALUES(?, ?, ?, ?, ?, ?)");
            st.setString(1, newMovie.getTitle());
            st.setString(2, newMovie.getSynopsis());
            st.setString(3, newMovie.getCast());
            st.setString(4, newMovie.getPic());
            st.setInt(5, newMovie.getCount());
            st.setDouble(6, newMovie.getRating());
            st.executeUpdate();

            return newMovie;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public List<MovieObject> searchMovie(String movieName) {

        final List<MovieObject> movies = new ArrayList<>();

        try (final Connection c = connection()){
            final PreparedStatement st = c.prepareStatement("SELECT * FROM movies WHERE movieName = ?");
            st.setString(1, movieName);
            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final MovieObject movie = fromResultSet(rs);
                movies.add(movie);
            }
            rs.close();
            st.close();

            return movies;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    public MovieObject updateMovie(MovieObject movie) {
        try(final Connection c = connection()){
            final PreparedStatement st = c.prepareStatement("UPDATE movies SET synopsis = ?, moviecast = ?, pic = ?, count = ?, rating = ? WHERE movieName = ?");
            st.setString(1, movie.getSynopsis());
            st.setString(2, movie.getCast());
            st.setString(3, movie.getPic());
            st.setInt(4, movie.getCount());
            st.setDouble(5, movie.getRating());
            st.setString(6, movie.getTitle());
            st.executeUpdate();

            return movie;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public void deleteMovie(MovieObject movie) {
        try (final Connection c = connection()){
            final PreparedStatement st = c.prepareStatement("DELETE FROM movies WHERE movieName = ?");
            st.setString(1, movie.getTitle());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
