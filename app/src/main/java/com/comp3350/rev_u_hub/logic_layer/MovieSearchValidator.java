package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;

public class MovieSearchValidator {
    private MovieSearch myMovieSearch;

    public MovieSearchValidator(MovieSearch setSearch) {
        myMovieSearch = setSearch;
    }

    public MovieObject getMovieSimple(String title) throws MovieDataNotFoundException {
        MovieObject movie = myMovieSearch.getMovieSimple(title);
        if (movie.isEmpty())
            throw new MovieDataNotFoundException("The movie "+title+
                    " does not exist.");
        return movie;
    }

    public MovieObject getMovie(String title) throws MovieDataNotFoundException {
        MovieObject movie = myMovieSearch.getMovie(title);

        if (movie==null || movie.isEmpty())
            throw new MovieDataNotFoundException("The selected movie does not exist.");
        return movie;
    }
}
