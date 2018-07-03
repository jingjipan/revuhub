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

        movies.add(new MovieObject("Deadpool","i","am","deadpool",1,4.3));
        movies.add(new MovieObject("The Avengers","i","am","invincible",1,4.3));
        movies.add(new MovieObject("Thor","i", "am","thor",1,4.3));
    }

    @Override
    public List<MovieObject> getMovieSequential() {
        return Collections.unmodifiableList(movies);
    }
    @Override
    public List<MovieObject> searchMovie(String movieName) {
        List<MovieObject> newMovies = new ArrayList<>();
        MovieObject MovieObjects;
        int counter;

        for(counter=0;counter< movies.size();counter++){
            MovieObjects =  movies.get(counter);
            if( MovieObjects .getTitle().equals(movieName)){
                newMovies.add(movies.get(counter));
            }
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
