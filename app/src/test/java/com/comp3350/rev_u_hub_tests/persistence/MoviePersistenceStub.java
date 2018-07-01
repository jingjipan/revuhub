package com.comp3350.rev_u_hub_tests.persistence;


import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class MoviePersistenceStub implements MoviePersistence{
    private List<MovieObject> movies;

    public MoviePersistenceStub() {
        this.movies = new ArrayList<>();

        movies.add(new MovieObject("Iron man","Marvel movie", "Robert Downey",null,95,9.0));
        movies.add(new MovieObject("The Avengers","Heros movie", "Chris Evans",null,98,9.4));
        movies.add(new MovieObject("Thor","Science fiction movie", "Chris Hemsworth",null,89,8.5));
    }
    @Override
    public List<MovieObject> getMovieSequential() {
        return Collections.unmodifiableList(movies);
    }
    @Override
    public List<MovieObject> searchMovie(String movieName) {
        List<MovieObject> newMovies = new ArrayList<>();
        int index;

        index = movies.indexOf("movieName");
        if (index >= 0)
        {
            newMovies.add(movies.get(index));
        }
        return newMovies;
    }

    @Override
    public MovieObject addNewMovie(MovieObject newMovie)  {
        // don't bother checking for duplicates
        movies.add(newMovie);
        return newMovie;
    }

    @Override
    public MovieObject updateMovie(MovieObject movie){
        int index;

        index = movies.indexOf(movie);
        if (index >= 0)
        {
            movies.set(index, movie);
        }
        return movie;
    }

    @Override
    public void deleteMovie(MovieObject movie) {
        int index;

        index = movies.indexOf(movie);
        if (index >= 0)
        {
            movies.remove(index);
        }
    }


}
