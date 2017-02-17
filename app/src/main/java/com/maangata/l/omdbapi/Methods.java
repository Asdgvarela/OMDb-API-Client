package com.maangata.l.omdbapi;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

/**
 * Created by l on 7/2/17.
 */
public class Methods {

    /**
     * It creates a Movie object, where all the data retrieved from the OMDb will be stored and dealt with.
     * @param in The ArrayString with the info.
     * @return A Movie object.
     */
    static public Movie newMovie(ArrayList<String> in) {

        Movie bMovie = new Movie();

        bMovie.setTitle(in.get(0));
        bMovie.setYear(in.get(1));
        bMovie.setRated(in.get(2));
        bMovie.setReleased(in.get(3));
        bMovie.setRuntime(in.get(4));
        bMovie.setGenre(in.get(5));
        bMovie.setDirector(in.get(6));
        bMovie.setWriter(in.get(7));
        bMovie.setActor(in.get(8));
        bMovie.setPlot(in.get(9));
        bMovie.setLanguage(in.get(10));
        bMovie.setCountry(in.get(11));
        bMovie.setAwards(in.get(12));
        bMovie.setPoster(in.get(13));
        bMovie.setMetascore(in.get(14));
        bMovie.setImdbrating(in.get(15));
        bMovie.setImdbvotes(in.get(16));
        bMovie.setImdbid(in.get(17));
        bMovie.setType(in.get(18));
        bMovie.setResponse(in.get(19));

        return bMovie;
    }
}
